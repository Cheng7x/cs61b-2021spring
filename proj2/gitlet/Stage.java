package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.TreeMap;

public class Stage implements Serializable {
    private static TreeMap<String, String> additions;
    private static TreeMap<String, String> removals;

    public static void initStage() {
        // key: fileName; value: sha1Code
        additions = null;
        removals = null;
        // Utils.writeObject();
    }

    public static void addBlobs(String fileName) {
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
    }

    public static void removeBlobs(String fileName) {
        File workingFile = Utils.join(Repository.CWD, fileName);
        byte[] content = Utils.readContents(workingFile);
        String blobID = Utils.sha1((Object) content);
        File blobFile = Utils.join(Repository.BLOBS, blobID);
        if (additions.containsKey(fileName)) {
            additions.remove(fileName);
        } else {
            removals.put(fileName, blobID);
        }
    }

    public static void clearStage() {
        additions.clear();
        removals.clear();
    }

    public static boolean isEmpty() {
        return additions.isEmpty() && removals.isEmpty();
    }

    public static TreeMap<String, String> getAdditions() {
        return additions;
    }

    public static TreeMap<String, String> getRemovals() {
        return removals;
    }
}
