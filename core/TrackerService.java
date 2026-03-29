package core;

import storage.Storage;
import java.util.ArrayList;
import java.util.List;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;
import java.time.DayOfWeek;
import java.time.temporal.ChronoUnit;

public class TrackerService {
    public List<Session> hist;
 Session cur;
  Storage store;

  public TrackerService(Storage s) {
      store = s;
      hist = store.load();
      for(int i=0; i<hist.size(); i++) {
          if(hist.get(i).eT == null) {
              cur = hist.get(i);
          }
      }
  }

  public void startSession(Mode m, String tsk) {
        if(cur != null) {
          stopSession();
        }
      String genId = UUID.randomUUID().toString();
    cur= new Session(genId, m, tsk);
      hist.add(cur);
      store.save(hist);
  }

public void stopSession() {
if(cur != null && cur.eT == null) {
        cur.eT = LocalDateTime.now();
        store.save(hist);
    cur = null;
    }
}

public Session getCurrentSession() {
 return cur;
}
public List<Session> getAllSessions() {
return hist;
}

  public long calcToday(Mode x) {
    long totRes=0;
    LocalDateTime now = LocalDateTime.now();
    for(Session s : hist) {
        if(s.m == x && s.sT.toLocalDate().equals(now.toLocalDate())) {
LocalDateTime stop=s.eT;
            if(stop==null) {
stop=now;
            }
          totRes += Duration.between(s.sT,stop).toMinutes();
        }
    }
return totRes;
  }

public long calcWeek(Mode mod) {
    long tott=0;
      LocalDateTime no = LocalDateTime.now();
      LocalDateTime strtOfWk = no.truncatedTo(ChronoUnit.DAYS).minusDays(no.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue());
  for(int j=0; j<hist.size(); j++) {
      Session ss = hist.get(j);
      if(ss.m == mod && !ss.sT.isBefore(strtOfWk)) {
          LocalDateTime ed = ss.eT;
          if(ed == null) ed = no;
        tott += Duration.between(ss.sT, ed).toMinutes();
      }
  }
  return tott;
}
}
