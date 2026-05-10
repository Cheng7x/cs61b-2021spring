package gitlet;

import java.io.File;
import java.util.*;

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

    public static boolean isInitialized() {
        return GITLET_DIR.exists();
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
        } else {
            for (String fileName : sameMessageCommits) {
                File commitFile = Utils.join(COMMITS, fileName);
                Commit commit = Utils.readObject(commitFile, Commit.class);
                commit.printCommit();
            }
        }
    }

    public static void status() {
        List<String> branches = Utils.plainFilenamesIn(BRANCHES);
        String currBranch = Utils.readContentsAsString(HEAD);
        if (branches == null) return;
        for (String branchName : branches) {
            if (branchName.equals(currBranch)) {
                System.out.print("*");
            }
            System.out.println(branchName);
        }

        Stage stage = Stage.getStage();
        System.out.println("=== Staged Files ===");
        for (String fileName : stage.getAdditions().keySet()) {
            System.out.println(fileName);
        }
        System.out.println();

        List<String> removeFiles = new ArrayList<>(stage.getRemovals());
        Collections.sort(removeFiles);
        System.out.println("=== Removed Files ===");
        for (String fileName : removeFiles) {
            System.out.println(fileName);
        }
        System.out.println();
    }

    public static void checkoutFile(Commit currCommit, String fileName) {
        for (String name : currCommit.getBlobs().keySet()) {
            if (name.equals(fileName)) {
                File checkOutFile = Utils.join(Repository.BLOBS, currCommit.getBlobs().get(fileName));
                File newWorkingFile = Utils.join(Repository.CWD, fileName);
                byte[] content = Utils.readContents(checkOutFile);
                Utils.writeContents(newWorkingFile, (Object) content);
                System.out.println("File modified successfully.");
                return;
            }
        }
        System.out.println("File do not exist.");
    }

    private static boolean isCurrentBranch(String branchName) {
        return branchName.equals(Utils.readContentsAsString(HEAD));
    }

    public static void removeBranch(String branchName) {
        File branchFile = Utils.join(BRANCHES, branchName);
        if (!branchFile.exists()) {
            System.out.println("A branch with that name does not exist.");
            return;
        }

        if (isCurrentBranch(branchName)) {
            System.out.println("Cannot remove the current branch.");
            return;
        }

        Utils.join(BRANCHES, branchName).delete();
        System.out.println("Removed successfully.");

    }

    public static void switchBranch(String branchName) {
        File branchFile = Utils.join(BRANCHES, branchName);
        Stage stage = Stage.getStage();

        if (!branchFile.exists()) {
            System.out.println("No such branch exists.");
            return;
        }

        if (isCurrentBranch(branchName)) {
            System.out.println("No need to checkout the current branch.");
            return;
        }

        Commit currCommit = Commit.getLatestCommit();
        String targetCommitID = Utils.readContentsAsString(branchFile);
        Commit targetCommit = Commit.getCommitByID(targetCommitID);

        // 检查有没有 untracked file
        for (String fileName : Utils.plainFilenamesIn(CWD)) {
            boolean trackedByCurrent = currCommit.getBlobs().containsKey(fileName);
            boolean stagedForAdd = stage.getAdditions().containsKey(fileName);
            boolean existsInTarget = targetCommit.getBlobs().containsKey(fileName);

            if (!trackedByCurrent && !stagedForAdd && existsInTarget) {
                System.out.println("There is an untracked file in the way; delete it, or add and commit it first.");
                return;
            }
        }

        // 将目标 commit file 写到 working directory
        for (String fileName : targetCommit.getBlobs().keySet()) {
            String blobID = targetCommit.getBlobs().get(fileName);

            byte[] content = Utils.readContents(Utils.join(BLOBS, blobID));
            Utils.writeContents(Utils.join(CWD, fileName), (Object) content);
        }

        for (String fileName : currCommit.getBlobs().keySet()) {
            if (!targetCommit.getBlobs().containsKey(fileName)) {
                Utils.restrictedDelete(Utils.join(CWD, fileName));
            }
        }

        stage.clearStage();
        setCurrentHead(branchName);
    }
}
