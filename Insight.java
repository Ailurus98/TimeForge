public class Insight {
    public enum Severity { INFO, WARNING, CRITICAL }

    public Severity severity;
    public String message;

    public Insight(Severity s, String m) {
        severity = s;
        message = m;
    }

    public String toString() {
        if(severity == Severity.CRITICAL) return "!!! " + message;
        if(severity == Severity.WARNING) return "⚠ " + message;
        return "✓ " + message;
    }
}
