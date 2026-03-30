public class LifeService {
    static int LIFESPAN_YEARS = 80;

    public static void printLifeMatrix(int age, int totalProdMins, int totalLeisureMins) {
        int totalWeeks = LIFESPAN_YEARS * 52;
        int weeksLived = age * 52;
        int weeksLeft = totalWeeks - weeksLived;
        
        int percentLived = Math.round((float)weeksLived / totalWeeks * 100);

        System.out.println("Life Progress: " + percentLived + "%");
        
        int matrixWidth = 20;
        int charsFilled = Math.round((float)matrixWidth * percentLived / 100);
        
        for(int i=0; i<matrixWidth; i++) {
            if(i < charsFilled) System.out.print("█");
            else System.out.print("░");
        }
        System.out.println("\n");
        System.out.println("Age: " + age);
        System.out.println("Weeks lived: " + weeksLived + " / " + totalWeeks);
        
        int totalTrackedMins = totalProdMins + totalLeisureMins;
        if(totalTrackedMins > 0) {
            System.out.println("\nAt current pace:");
            float prodRatio = (float)totalProdMins / totalTrackedMins;
            float leisureRatio = (float)totalLeisureMins / totalTrackedMins;
            
            float yearsLeft = LIFESPAN_YEARS - age;
            float projProdYears = yearsLeft * prodRatio;
            float projLeisureYears = yearsLeft * leisureRatio;
            
            System.out.printf("- %.1f years in leisure\n", projLeisureYears);
            System.out.printf("- %.1f years in productive work\n", projProdYears);
        }
    }
}
