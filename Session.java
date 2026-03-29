public class Session {
    int id;
    Mode mode;
    String taskName;
    int durationMinutes;

    public Session(int i, Mode m, int mins, String task) {
        id = i;
        mode = m;
        durationMinutes = mins;
        taskName = task;
    }
}
