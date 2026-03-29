import java.util.ArrayList;

public class TrackerService {
    ArrayList<Session> history = new ArrayList<>();
    int nextId = 1;

    public void addSession(Mode m, int mins, String t) {
        Session s = new Session(nextId, m, mins, t);
        history.add(s);
        nextId++;
    }
}
