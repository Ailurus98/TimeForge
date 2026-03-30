import java.util.ArrayList;
import java.util.HashMap;

public class InsightService {
    public static ArrayList<Insight> getInsights(ArrayList<Session> history) {
        ArrayList<Insight> list = new ArrayList<>();
        if(history.isEmpty()) return list;

        int totalProd = 0;
        int totalLeisure = 0;
        int totalOther = 0;
        HashMap<String, Integer> taskCount = new HashMap<>();
        String mostFreqTask = "";
        int maxTaskVisits = 0;

        for(int i=0; i<history.size(); i++) {
            Session s = history.get(i);
            if(s.mode == Mode.PRODUCTIVE) totalProd += s.durationMinutes;
            if(s.mode == Mode.LEISURE) {
                totalLeisure += s.durationMinutes;
                if(s.durationMinutes > 90) {
                    list.add(new Insight(Insight.Severity.WARNING, "Long leisure session detected: " + s.taskName + " (" + s.durationMinutes + " mins)"));
                }
            }
            if(s.mode == Mode.OTHER) totalOther += s.durationMinutes;

            int count = taskCount.getOrDefault(s.taskName, 0) + 1;
            taskCount.put(s.taskName, count);
            if(count > maxTaskVisits) {
                maxTaskVisits = count;
                mostFreqTask = s.taskName;
            }
        }

        int totalTime = totalProd + totalLeisure + totalOther;
        if(totalTime > 0) {
            int prodRatio = Math.round((float)totalProd / totalTime * 100);
            if(prodRatio < 30) {
                list.add(new Insight(Insight.Severity.CRITICAL, "Low productivity ratio (" + prodRatio + "%). You are spending a lot of time in low-value activity."));
            } else if(prodRatio > 70) {
                list.add(new Insight(Insight.Severity.INFO, "Great focus! Overall productivity ratio is " + prodRatio + "%."));
            }
        }

        if(totalProd > 300) { 
            list.add(new Insight(Insight.Severity.WARNING, "Burnout risk: Over 5 hours of total productive work logged."));
        }

        if(!mostFreqTask.isEmpty()) {
            list.add(new Insight(Insight.Severity.INFO, "Most frequent task logged: " + mostFreqTask + " (" + maxTaskVisits + " logs)"));
        }

        return list;
    }
}
