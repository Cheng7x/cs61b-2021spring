package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashSet;
import java.util.TreeMap;

public class Stage implements Serializable {
    // key: fileName; value: sha1Code
    private TreeMap<String, String> additions = new TreeMap<>();
    private HashSet<String> removals = new HashSet<>();

    public Stage() {}

    public static Stage getStage() {
        return Utils.readObject(Repository.STAGE, Stage.class);
    }

    public void addBlobs(String fileName) {
        File workingFile = Utils.join(Repository.CWD, fileName);
        // 判断文件是否存在
        if (!workingFile.exists()) {
            throw new GitletException("File does not exist.");
        }
        byte[] content = Utils.readContents(workingFile);
        String blobID = Utils.sha1((Object) content);
        // 如果当前文件在 removals 中则取消
        removals.remove(fileName);
        // 如果当前文件与 HEAD commit 中追踪的版本一致则不需要 stage
        Commit headCommit = Commit.getLatestCommit();
        String headBlobID = headCommit.getBlobs().get(fileName);
        if (blobID.equals(headBlobID)) {
            // 如果已经 stage 了取消 stage
            additions.remove(fileName);
            return;
        }
        // 保存 workingFile 到 blobs
        File blobFile = Utils.join(Repository.BLOBS, blobID);
        if (!blobFile.exists()) {
            Utils.writeContents(blobFile, (Object) content);
        }
        additions.put(fileName, blobID);
        saveStage();

        System.out.println(fileName + " added successfully.");
    }

    public void removeBlobs(String fileName) {
        File workingFile = Utils.join(Repository.CWD, fileName);
        byte[] content = Utils.readContents(workingFile);
        String blobID = Utils.sha1((Object) content);
        // 如果当前文件在 additions 中则取消
        additions.remove(fileName);
        // 如果当前文件被 lastest Commit 追踪 则取消追踪
        Commit headCommit = Commit.getLatestCommit();
        if (headCommit.getBlobs().containsKey(fileName)) {
            removals.add(fileName);
        }
        Utils.restrictedDelete(workingFile);
        saveStage();

        System.out.println(fileName + " removed successfully.");
    }

    public void saveStage() {
        Utils.writeObject(Repository.STAGE, this);
    }

    public void clearStage() {
        additions.clear();
        removals.clear();
        this.saveStage();
    }

    public boolean isEmpty() {
        return additions.isEmpty() && removals.isEmpty();
    }

    public TreeMap<String, String> getAdditions() {
        return additions;
    }

    public HashSet<String> getRemovals() {
        return removals;
    }
}
