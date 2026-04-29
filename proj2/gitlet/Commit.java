package gitlet;

// TODO: any imports you need here

import java.io.File;
import java.io.Serializable;
import java.util.Date; // TODO: You'll likely use this in this class
import java.util.TreeMap;
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
        return initialCommit;
    }

    public Commit(String message, String parentID, TreeMap<String, String> blobs) {
        this.message = message;
        this.time = new Date();
        this.parentID = parentID;
        this.blobs = new TreeMap<>(blobs);
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
        Utils.writeObject(newCommit, Commit.class);
    }
}
