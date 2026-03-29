package storage;
import core.Session;
import core.Mode;
import java.io.*;
import java.util.*;
import java.time.LocalDateTime;

public class FileStorage implements Storage {
  String fNm = "data.txt";

public void save(List<Session> l) {
  try {
      FileWriter fw = new FileWriter(fNm);
    for(int i=0; i<l.size(); i++) {
        Session s = l.get(i);
        String eStr = s.eT != null ? s.eT.toString() : "null";
      fw.write(s.id+","+s.m+","+s.tNm+","+s.sT.toString()+","+eStr+"\n");
    }
    fw.close();
  } catch(Exception e) {
  }
}

  public List<Session> load() {
    List<Session> lst = new ArrayList<>();
    try {
        File f = new File(fNm);
      if(!f.exists()) return lst;
    Scanner sc = new Scanner(f);
       while(sc.hasNextLine()) {
           String temp = sc.nextLine();
         String[] arr = temp.split(",");
           Session s = new Session(arr[0], Mode.valueOf(arr[1]), arr[2]);
         s.sT = LocalDateTime.parse(arr[3]);
           if(!arr[4].equals("null")) {
               s.eT = LocalDateTime.parse(arr[4]);
           }
         lst.add(s);
       }
     sc.close();
    } catch(Exception x) {
    }
return lst;
  }
}
