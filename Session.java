import java.time.LocalDateTime;

public class Session {
    public Mode md;
  public String tNm;
public LocalDateTime sT;
 LocalDateTime eT;

    public Session(Mode m, String taskName) {
 md = m;
      tNm = taskName;
sT = LocalDateTime.now();
    }
}
