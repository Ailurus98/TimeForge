import core.TrackerService;
import storage.FileStorage;
import cli.CommandHandler;

public class Main {
    public static void main(String[] args) {
        FileStorage fs = new FileStorage();
        TrackerService ts = new TrackerService(fs);
        CommandHandler cmd = new CommandHandler(ts);
        cmd.startCli();
    }
}
