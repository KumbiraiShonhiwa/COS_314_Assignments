import java.util.List;

public class Main {

    // Method to print the matrix with the distances between the campuses with the
    // names of campuses attached
    public void printDistanceMatrix(int[][] distanceMatrix, Campus[] campuses) {
        System.out.print("    ");
        for (Campus campus : campuses) {
            System.out.printf("%-10s ", campus.getName());
        }
        System.out.println();

        for (int i = 0; i < distanceMatrix.length; i++) {
            System.out.printf("%-10s ", campuses[i].getName());
            for (int j = 0; j < distanceMatrix[i].length; j++) {
                System.out.printf("%-10d ", distanceMatrix[i][j]);
            }
            System.out.println();
        }
    }

    // Method to print the best route found by Iterated Local Search
    public void printBestRoute(List<Integer> bestRoute, Campus[] campuses) {
        System.out.println("Best route found by Iterated Local Search:");
        for (int campusIndex : bestRoute) {
            System.out.print(campuses[campusIndex].getName() + " -> ");
        }
        System.out.println(campuses[bestRoute.get(0)].getName()); // Return to starting point
    }

    public void printBestRoute2(List<Integer> bestRoute, Campus[] campuses) {
        System.out.println("Best Solution(route) found by Simulated Annealing Search:");
        for (int campusIndex : bestRoute) {
            System.out.print(campuses[campusIndex].getName() + " -> ");
        }
        System.out.println(campuses[bestRoute.get(0)].getName()); // Return to starting point
    }

    public static void main(String[] args) {
        Main main = new Main(); // Create an instance of Main class

        Campus Hatfield = new Campus("Hatfield", new int[] { 0, 15, 20, 22, 30 });
        Campus Hilcrest = new Campus("Hilcrest", new int[] { 15, 0, 10, 12, 25 });
        Campus Groenkloof = new Campus("Groenkloof", new int[] { 20, 10, 0, 8, 22 });
        Campus Mamelodi = new Campus("Mamelodi", new int[] { 22, 12, 8, 0, 18 });
        Campus Prinsof = new Campus("Prinsof", new int[] { 30, 25, 22, 18, 0 });

        Campus[] campuses = new Campus[] { Hatfield, Hilcrest, Groenkloof, Mamelodi, Prinsof };

        int[][] distanceMatrix = new int[campuses.length][campuses.length];
        for (int i = 0; i < campuses.length; i++) {
            for (int j = 0; j < campuses.length; j++) {
                distanceMatrix[i][j] = campuses[i].getDistances()[j];
            }
        }

        // Print distance matrix using instance method
       
        SimulatedAnnealing obj2 = new SimulatedAnnealing();
        SAS obj3 = new SAS();
        ILS obj4 = new ILS();
        obj2.setDistanceMatrix(distanceMatrix);
        obj3.setDistanceMatrix(distanceMatrix);
        obj4.setDistanceMatrix(distanceMatrix);
        // main.printDistanceMatrix(distanceMatrix, campuses);

        // Run Iterated Local Search
        // List<Integer> bestRoute = obj.runILS();
        // List<Integer> bestRoute2 = obj2.runSA();
        System.out.println("RUNNING THE SIMULATED ANNEALING ALGORITHM ");
        List<Integer> bestRoute3 = obj3.runSA();
        System.out.println("\n");
        System.out.println("RUNNING THE ITERATED LOCAL SEARCH ALGORITHM ");
        List<Integer> bestRoute4 = obj4.runILS();
        System.out.println("\n");

        // Print best route using instance method
        // main.printBestRoute(bestRoute, campuses);

        main.printBestRoute2(bestRoute3, campuses);
        System.out.println("Objective Function Value(Best distance) FOR SA: " +
                obj3.getTotalDistance());
        main.printBestRoute(bestRoute4, campuses);
        System.out.println("Objective Function Value(Best distance) FOR ILS: " + obj4.getTotalDistance());
    }
}
