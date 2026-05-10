package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.*;
import gitlet.*;

/** Represents a gitlet commit object.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Commit implements Serializable {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Commit class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided one example for `message`.
     */

    /** The message of this Commit. */
    private String message;
    private Date time;
    private String parentID;
    private TreeMap<String, String> blobs;

    public static Commit initCommit() {
        Commit initialCommit = new Commit("initial commit", null, new TreeMap<>());
        initialCommit.time = new Date(0);
        initialCommit.saveCommit();
        return initialCommit;
    }

    public Commit(String message, String parentID, TreeMap<String, String> blobs) {
        this.message = message;
        this.time = new Date();
        this.parentID = parentID;
        this.blobs = new TreeMap<>(blobs);
    }

    public static void newCommit(String message) {
        Stage stage = Stage.getStage();
        if (stage.isEmpty()) {
            System.out.println("No changes added to the commit");
            return;
        }

        Commit parent = getLatestCommit();
        TreeMap<String, String> newBlobs = new TreeMap<>(parent.getBlobs());
        for (String fileName : stage.getAdditions().keySet()) {
            newBlobs.put(fileName, stage.getAdditions().get(fileName));
        }

        for (String fileName : stage.getRemovals()) {
            newBlobs.remove(fileName);
        }

        stage.clearStage();
        stage.saveStage();

        Commit currCommit = new Commit(message, parent.generateID(), newBlobs);
        currCommit.saveCommit();

        File currentBranch = Repository.getCurrentBranch();
        Utils.writeContents(currentBranch, currCommit.generateID());

        System.out.println("Commit successfully.");
    }

    public String getMessage() {
        return this.message;
    }

    public Date getTime() {
        return this.time;
    }

    public String getParentID() {
        return this.parentID;
    }

    public TreeMap<String, String> getBlobs() {
        return new TreeMap<>(this.blobs);
    }

    public String generateID() {
        return Utils.sha1(this.message,
                this.time.toString(),
                this.parentID == null ? "null" : this.parentID,
                this.blobs.toString()
        );
    }

    public void saveCommit() {
        File newCommit = Utils.join(Repository.COMMITS, this.generateID());
        Utils.writeObject(newCommit, this);
    }

    public void printCommit() {
        System.out.println("===\n" +
                "commit " + this.generateID() + "\n" +
                "Date: " + this.getTime() + "\n" +
                this.message + "\n"
        );

        /*
        StringJoiner fileNameSet = new StringJoiner(", ");
        for (String fileName : this.getBlobs().keySet()) {
            fileNameSet.add(fileName);
        }
        System.out.println(fileNameSet + "\n" + "===\n");
        */

    }

    public static Commit getLatestCommit() {
        String commitID = Utils.readContentsAsString(Repository.getCurrentBranch());
        File commitFile = Utils.join(Repository.COMMITS, commitID);
        return Utils.readObject(commitFile, Commit.class);
    }

    public static Commit getCommitByID(String hashID) {
        List<String> commitIDs = Utils.plainFilenamesIn(Repository.COMMITS);
        String matchID = null;
        for (String fullID : commitIDs) {
            if (fullID.startsWith(hashID)) {
                if (matchID != null) {
                    return null;
                }
                matchID = fullID;
            }
        }

        if (matchID == null) {
            return null;
        }

        File commitFile = Utils.join(Repository.COMMITS, matchID);
        return Utils.readObject(commitFile, Commit.class);
    }
}
