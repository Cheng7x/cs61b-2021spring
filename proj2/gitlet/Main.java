package gitlet;

/** Driver class for Gitlet, a subset of the Git version-control system.
 *  @author Cheng7x
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
            case "init": {
                validateNumArgs(args, 1);
                Repository.setUpRepository();
                System.out.println("Your repository has initialized.");
                break;
            }

            case "add": {
                validateNumArgs(args, 2);
                String fileName = args[1];
                Stage.getStage().addBlobs(fileName);
                break;
            }

            case "rm": {
                validateNumArgs(args, 2);
                String fileName = args[1];
                Stage.getStage().removeBlobs(fileName);
                break;
            }

            case "commit": {
                // java gitlet.Main commit message;
                validateNumArgs(args, 2);
                String message = args[1];
                Commit.newCommit(message);
                break;
            }

            case "log": {
                validateNumArgs(args, 1);
                Repository.log();
                break;
            }

            case "global-log": {
                validateNumArgs(args, 1);
                Repository.globalLog();
                break;
            }

            case "find": {
                validateNumArgs(args, 2);
                String message = args[1];
                Repository.findMessage(message);
                break;
            }

            case "status": {
                validateNumArgs(args, 1);
                
                break;
            }

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
