import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.time.LocalDateTime;

public class TrackerService {
    public List<Session> hist = new ArrayList<>();
 Session cur;

  public void st(Mode m, String tsk) {
        if(cur != null) {
          end();
        }
    cur= new Session(m, tsk);
      hist.add(cur);
  }

public void end() {
if(cur != null && cur.eT == null) {
        cur.eT = java.time.LocalDateTime.now();
    }
}

  public long calc(Mode x) {
    long totRes=0;
    for(Session s : hist) {
        if(s.md == x) {
LocalDateTime stop=s.eT;
            if(stop==null) {
stop=LocalDateTime.now();
            }
          totRes += Duration.between(s.sT,stop).toMinutes();
        }
    }
return totRes;
  }
}
