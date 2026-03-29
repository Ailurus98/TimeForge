package cli;

import core.TrackerService;
import core.Mode;
import core.Session;
import java.util.Scanner;
import java.time.Duration;
import java.time.LocalDateTime;

public class CommandHandler {
TrackerService svc;

public CommandHandler(TrackerService t) {
 svc = t;
}

public void startCli() {
 Scanner s = new Scanner(System.in);
    while(true) {
        System.out.print("> ");
        if(!s.hasNextLine()) break;
        String lin = s.nextLine().trim();
        if(lin.isEmpty()) continue;
        String[] wds = lin.split(" ");
        if(wds[0].equals("exit")) {
            break;
        } else if(wds[0].equals("start")) {
            try {
                Mode m = Mode.valueOf(wds[1].toUpperCase());
                 String tsk = "";
                for(int k=2; k<wds.length; k++) tsk += wds[k] + " ";
                svc.startSession(m, tsk.trim());
            } catch(Exception e) { System.out.println("err"); }
        } else if(wds[0].equals("stop")) {
            svc.stopSession();
        } else if(wds[0].equals("current")) {
            Session curr = svc.getCurrentSession();
            if(curr != null) {
                long mns = Duration.between(curr.sT, LocalDateTime.now()).toMinutes();
                System.out.println("Running: "+curr.m+" - "+curr.tNm+" ("+mns+" mins)");
            } else {
                System.out.println("non");
            }
        } else if(wds[0].equals("today")) {
            for(Mode mx : Mode.values()) {
                long min = svc.calcToday(mx);
                if(min > 0) System.out.println(mx.name().substring(0,1).toUpperCase() + mx.name().substring(1).toLowerCase() + ": "+(min/60)+"h "+(min%60)+"m");
            }
        } else if(wds[0].equals("week")) {
           for(Mode mx : Mode.values()) {
                long mi = svc.calcWeek(mx);
                if(mi > 0) System.out.println(mx.name().substring(0,1).toUpperCase() + mx.name().substring(1).toLowerCase() + ": "+(mi/60)+"h "+(mi%60)+"m");
            }
        } else if (wds[0].equals("summary")) {
           System.out.println("Today:");
             for(Mode mx : Mode.values()) {
                long min = svc.calcToday(mx);
                System.out.println(mx.name().substring(0,1).toUpperCase() + mx.name().substring(1).toLowerCase()+": "+(min/60)+"h "+(min%60)+"m");
            }
System.out.println("\nWeek:");
for(Mode mx : Mode.values()) {
                long mi = svc.calcWeek(mx);
                System.out.println(mx.name().substring(0,1).toUpperCase() + mx.name().substring(1).toLowerCase()+": "+(mi/60)+"h "+(mi%60)+"m");
            }
        } else {
            System.out.println("unk cmd");
        }
    }
}
}
