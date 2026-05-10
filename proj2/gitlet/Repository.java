package gitlet;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public static final File HEAD = Utils.join(GITLET_DIR, "HEAD");
    public static final File STAGE = Utils.join(GITLET_DIR, "STAGE");
    public static final String DEFAULT_BRANCH = "master";
    /* TODO: fill in the rest of this class. */
    public static void setUpRepository() {
        if (GITLET_DIR.exists()) {
            throw new GitletException("A Gitlet version-control system already exists in the current directory.");
        }
        // Create folders
        GITLET_DIR.mkdir();
        COMMITS.mkdir();
        BLOBS.mkdir();
        BRANCHES.mkdir();

        Stage stage = new Stage();
        stage.saveStage();

        Commit initCommit = Commit.initCommit();    // Initialize commit
        newBranch(DEFAULT_BRANCH, initCommit);    // Initialize branch
        setCurrentHead(DEFAULT_BRANCH);     // Initialize head
    }

    /** Create a new branch
     *  @param name Name of the new branch
     *  @param commit Commit which branch points to
     *  */
    public static void newBranch(String name, Commit commit) {
        File branch = Utils.join(BRANCHES, name);
        Utils.writeContents(branch, commit.generateID());
    }

    public static File getCurrentBranch() {
        String currentBranchName = Utils.readContentsAsString(HEAD);
        return Utils.join(BRANCHES, currentBranchName);
    }

    public static void setCurrentHead(String name) {
        Utils.writeContents(HEAD, name);
    }

    public static void log() {
        Commit currCommit = Commit.getLatestCommit();
        String parentID = currCommit.getParentID();
        while (parentID != null) {
            currCommit.printCommit();

            File currCommitFile = Utils.join(COMMITS, parentID);
            currCommit = Utils.readObject(currCommitFile, Commit.class);
            parentID = currCommit.getParentID();
        }
    }

    public static void globalLog() {
        List<String> commits = Utils.plainFilenamesIn(COMMITS);
        if (commits == null || commits.size() <= 1) return;
        for (String fileName : commits) {
            File commitFile = Utils.join(COMMITS, fileName);
            Commit commit = Utils.readObject(commitFile, Commit.class);
            if (commit.getParentID() != null) {
                commit.printCommit();
            }
        }
    }

    public static void findMessage(String message) {
        Commit currCommit = Commit.getLatestCommit();
        String parentID = currCommit.getParentID();
        Set<String> sameMessageCommits = new HashSet<>();
        while (parentID != null) {
            if (currCommit.getMessage().equals(message)) {
                sameMessageCommits.add(currCommit.generateID());
            }

            File currCommitFile = Utils.join(COMMITS, parentID);
            currCommit = Utils.readObject(currCommitFile, Commit.class);
            parentID = currCommit.getParentID();
        }

        if (sameMessageCommits.isEmpty()) {
            System.out.println("Not found the commit.");
            return;
        } else {
            for (String fileName : sameMessageCommits) {
                File commitFile = Utils.join(COMMITS, fileName);
                Commit commit = Utils.readObject(commitFile, Commit.class);
                commit.printCommit();
            }
        }
    }
}
