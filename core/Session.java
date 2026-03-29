package core;
import java.time.LocalDateTime;

public class Session {
    public String id;
  public Mode m;
public String tNm;
public LocalDateTime sT;
 public LocalDateTime eT;

    public Session(String i, Mode m, String taskName) {
 id = i;
 this.m = m;
      tNm = taskName;
sT = LocalDateTime.now();
    }
}
