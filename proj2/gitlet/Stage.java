package gitlet;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;

public class Stage implements Serializable {
    private static HashMap<String, String> additions;
    private static HashMap<String, String> removals;

    public static void initStage() {
        additions = null;
        removals = null;
        // Utils.writeObject();
    }

    public static void addBlobs(String fileName) {
        File workingFile = Utils.join(Repository.CWD, fileName);
        if (!workingFile.exists()) {
            throw new GitletException("The file does not exist.");
        }
        byte[] content = Utils.readContents(workingFile);
        String blobID = Utils.sha1((Object) content);
        File blobFile = Utils.join(Repository.BLOBS, blobID);
        if (!blobFile.exists()) {
            Utils.writeContents(blobFile, (Object) content);
        }
        Stage.additions.put(fileName, blobID);
    }

    public static void removeBlobs(String fileName) {
        File workingFile = Utils.join(Repository.CWD, fileName);
        byte[] content = Utils.readContents(workingFile);
        String blobID = Utils.sha1((Object) content);
        File blobFile = Utils.join(Repository.BLOBS, blobID);
        // if ()

    }
}
