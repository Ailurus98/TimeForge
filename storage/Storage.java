package storage;
import java.util.List;
import core.Session;

public interface Storage {
    void save(List<Session> l);
  List<Session> load();
}
