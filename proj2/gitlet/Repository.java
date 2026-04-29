package gitlet;

import java.io.File;
import static gitlet.Utils.*;

// TODO: any imports you need here

/** Represents a gitlet repository.
 *  TODO: It's a good idea to give a description here of what else this Class
 *  does at a high level.
 *
 *  @author TODO
 */
public class Repository {
    /**
     * TODO: add instance variables here.
     *
     * List all instance variables of the Repository class here with a useful
     * comment above them describing what that variable represents and how that
     * variable is used. We've provided two examples for you.
     */

    /** The current working directory. */
    public static final File CWD = new File(System.getProperty("user.dir"));
    /** The .gitlet directory. */
    public static final File GITLET_DIR = Utils.join(CWD, ".gitlet");
    public static final File COMMITS = Utils.join(GITLET_DIR, "commits");
    public static final File BLOBS = Utils.join(GITLET_DIR, "blobs");
    public static final File BRANCHES = Utils.join(GITLET_DIR, "branches");
    public static final File STAGE = Utils.join(GITLET_DIR, "STAGE");
    public static final File HEAD = Utils.join(GITLET_DIR, "HEAD");
    /* TODO: fill in the rest of this class. */
    public static void setUpRepository() {
        if (GITLET_DIR.exists()) {
            throw new GitletException("A Gitlet version-control system already exists in the current directory.");
        }
        GITLET_DIR.mkdir();
        COMMITS.mkdir();
        BLOBS.mkdir();
        BRANCHES.mkdir();

        Commit initCommit = Commit.initCommit();
        initCommit.saveCommit();
    }

    public static void newBranch(String name) {
        File branch = Utils.join(BRANCHES, name);
        branch.mkdir();
        
    }


}
