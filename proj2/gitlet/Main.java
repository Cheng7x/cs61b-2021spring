package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author TODO
 */
public class Main {

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND1> <OPERAND2> ... 
     */
    public static void run(String[] args) {
        // TODO: what if args is empty?
        if (args.length == 0) {
            throw new GitletException("Please enter a command");
        }

        String firstArg = args[0];
        switch(firstArg) {
            case "init":
                // TODO: handle the `init` command
                validateNumArgs(args, 1);
                Repository.setUpRepository();
                System.out.println("Your repository has initialized.");
                break;
            case "add":
                // TODO: handle the `add [filename]` command
                validateNumArgs(args, 2);
                String fileName = args[1];
                Stage.addBlobs(fileName);
                break;
            case "commit":
                // java gitlet.Main commit message;
                validateNumArgs(args, 2);
                String message = args[1];
                Commit.newCommit(message);
            // temp
            case "temp":
                // getInitCommit
                Commit currCommit = Commit.getLatestCommit();
                System.out.println(
                        "===\n" +
                        currCommit.getMessage() + "\n" +
                        currCommit.getTime() + "\n" +
                        currCommit.getBlobs()
                );
                break;
            default:
                throw new GitletException("No command with that name exists.");
        }
    }

    public static void main(String[] args) {
        try {
            run(args);
        } catch (GitletException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void validateNumArgs(String[] args, int n) {
        if (args.length != n) {
            throw new GitletException("Incorrect operands.");
        }
    }
}
