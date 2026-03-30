import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    static TrackerService tracker = new TrackerService();
    static int userAge = -1;

    public static void main(String[] args) {
        loadData();
        loadAge();
        
        System.out.println("=================================");
        System.out.println("    Welcome to Time Tracker      ");
        System.out.println("=================================");
        System.out.println("Available Commands:");
        System.out.println(" -> add <PRODUCTIVE|LEISURE|OTHER> <MINUTES> <TASK>");
        System.out.println(" -> summary");
        System.out.println(" -> insights");
        System.out.println(" -> life");
        System.out.println(" -> life now");
        System.out.println(" -> exit");
        System.out.println("---------------------------------");
        
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("\nEnter command: ");
            if(!sc.hasNextLine()) break;
            String line = sc.nextLine().trim();
            if(line.equals("")) continue;
            
            String[] words = line.split(" ");
            String cmd = words[0];

            if (cmd.equals("exit")) {
                saveData();
                System.out.println("Bye!");
                break;
            } else if (cmd.equals("add")) {
                try {
                    Mode m = Mode.valueOf(words[1].toUpperCase());
                    int mins = Integer.parseInt(words[2]);
                    String task = "";
                    for(int i=3; i<words.length; i++) {
                        task += words[i] + " ";
                    }
                    tracker.addSession(m, mins, task.trim());
                    saveData();
                    System.out.println("Successfully logged " + mins + " minutes for " + m + ".");
                    
                    ArrayList<Insight> ins = InsightService.getInsights(tracker.history);
                    if(!ins.isEmpty()) {
                        System.out.println("\n[!] New Insights Available: ");
                        for(Insight in : ins) {
                            if(in.severity != Insight.Severity.INFO) {
                                System.out.println(in.toString());
                            }
                        }
                    }
                } catch(Exception e) { System.out.println("Error formatting! Use: add MODE MINUTES TASK"); }
            } else if (line.equals("today") || line.equals("week") || line.equals("summary")) {
                int pTime = 0; int lTime = 0; int oTime = 0;
                for (int i=0; i<tracker.history.size(); i++) {
                    Session s = tracker.history.get(i);
                    if(s.mode == Mode.PRODUCTIVE) pTime += s.durationMinutes;
                    if(s.mode == Mode.LEISURE) lTime += s.durationMinutes;
                    if(s.mode == Mode.OTHER) oTime += s.durationMinutes;
                }
                
                System.out.println("--- YOUR SUMMARY ---");
                System.out.println("Productive: " + (pTime/60) + "h " + (pTime%60) + "m");
                System.out.println("Leisure: " + (lTime/60) + "h " + (lTime%60) + "m");
                if (oTime > 0) System.out.println("Other: " + (oTime/60) + "h " + (oTime%60) + "m");
            } else if (line.startsWith("life")) {
                if (userAge <= 0) {
                    System.out.print("What is your age? ");
                    try {
                        userAge = Integer.parseInt(sc.nextLine().trim());
                        PrintWriter pw = new PrintWriter("profile.txt");
                        pw.println(userAge);
                        pw.close();
                    } catch(Exception e) {
                        System.out.println("Invalid age. Try again next time.");
                        continue;
                    }
                }
                
                int pTime = 0; int lTime = 0;
                for (Session s : tracker.history) {
                    if(s.mode == Mode.PRODUCTIVE) pTime += s.durationMinutes;
                    if(s.mode == Mode.LEISURE) lTime += s.durationMinutes;
                }

                if(line.equals("life now")) {
                    System.out.println("\n--- LOGGED TOTALS ---");
                    System.out.println("Productive: " + (pTime/60) + "h " + (pTime%60) + "m");
                    System.out.println("Leisure: " + (lTime/60) + "h " + (lTime%60) + "m");

                    System.out.println("\n--- INSIGHTS ---");
                    ArrayList<Insight> ins = InsightService.getInsights(tracker.history);
                    if(ins.isEmpty()) System.out.println("No insights yet.");
                    for(Insight in : ins) {
                        System.out.println(in.toString());
                    }
                }

                System.out.println("\n--- LIFE PROGRESS ---");
                LifeService.printLifeMatrix(userAge, pTime, lTime);
            } else if (cmd.equals("insights")) {
                System.out.println("\n--- INSIGHTS ---");
                ArrayList<Insight> ins = InsightService.getInsights(tracker.history);
                if(ins.isEmpty()) System.out.println("No insights yet.");
                for(Insight in : ins) {
                    System.out.println(in.toString());
                }
            } else {
                System.out.println("Unknown command! Type 'add', 'summary', or 'exit'.");
            }
        }
    }

    public static void loadAge() {
        try {
            Scanner s = new Scanner(new File("profile.txt"));
            userAge = s.nextInt();
            s.close();
        } catch(Exception e) {}
    }

    public static void loadData() {
        try {
            File f = new File("data.txt");
            if(!f.exists()) return;
            Scanner fSc = new Scanner(f);
            while(fSc.hasNextLine()) {
                String[] parts = fSc.nextLine().split(",");
                Session s = new Session(Integer.parseInt(parts[0]), Mode.valueOf(parts[1]), Integer.parseInt(parts[2]), parts[3]);
                tracker.history.add(s);
                tracker.nextId = s.id + 1;
            }
            fSc.close();
        } catch(Exception e) { }
    }

    public static void saveData() {
        try {
            PrintWriter fw = new PrintWriter("data.txt");
            for(int i=0; i<tracker.history.size(); i++) {
                Session s = tracker.history.get(i);
                fw.println(s.id + "," + s.mode + "," + s.durationMinutes + "," + s.taskName);
            }
            fw.close();
        } catch(Exception e) { }
    }
}
