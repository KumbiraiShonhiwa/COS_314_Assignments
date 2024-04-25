import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class SAS {

    private int[][] distanceMatrix;
    private int totalDistance;
    private Random random = new Random();

    public void setDistanceMatrix(int[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    public void myShuffle(List<Integer> solution) {
        Random rand = new Random();
        for (int i = solution.size() - 1; i > 0; i--) {
            int index = rand.nextInt(i + 1);
            int temp = solution.get(index);
            solution.set(index, solution.get(i));
            solution.set(i, temp);
        }
    }

    public void mySwap(List<Integer> solution, int firstIndex, int secondIndex) {
        int temp = solution.get(firstIndex);
        solution.set(firstIndex, solution.get(secondIndex));
        solution.set(secondIndex, temp);
    }

    public List<Integer> runSA() {
        Long startTime = System.nanoTime();
        List<Integer> currentSolution = generateRandomSolution();
        List<Integer> bestSolution = new ArrayList<>(currentSolution);
        List<Integer> neighborSolution;
        int bestDistance = calculateTotalDistance(bestSolution);
        int totalDistanceAcrossIterations = 0;
        int currentDistance;
        int neighborDistance;
        double initialTemperature = 1000.0;
        int iterations = 50; // stopping criteria
        int t = 1;
        int noImprovementCount = 0;
        while (noImprovementCount < iterations && initialTemperature > 0) {
            neighborSolution = perturbSolution(currentSolution);
            currentDistance = calculateTotalDistance(currentSolution);
            neighborDistance = calculateTotalDistance(neighborSolution);
            double deltaCost = neighborDistance - currentDistance;
            if (shouldAccept(deltaCost, initialTemperature / Math.log(t + 1))) {
                currentSolution = new ArrayList<>(neighborSolution);
                currentDistance = calculateTotalDistance(currentSolution);
                if (currentDistance < bestDistance) {
                    bestSolution = new ArrayList<>(currentSolution);
                    bestDistance = neighborDistance;
                    noImprovementCount = 0;
                } else {
                    noImprovementCount++;
                }
            }
            initialTemperature = initialTemperature / Math.log(t + 1);
            totalDistanceAcrossIterations += currentDistance;
            t++;
        }
        totalDistance = bestDistance;
        Long endTime = System.nanoTime();
        double totalTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Runtime: " + totalTime + " seconds");
        System.out.println("Average Object Function: " + totalDistanceAcrossIterations / noImprovementCount
                + ", after the total number of iterations: "
                + noImprovementCount);
        return bestSolution;
    }

    private boolean shouldAccept(double deltaCost, double temperature) {
        if (deltaCost < 0) {
            return true;
        }
        return random.nextDouble() < Math.exp(-deltaCost / temperature);
    }

    public List<Integer> generateRandomSolution() {
        List<Integer> solution = new ArrayList<>();
        for (int i = 0; i < distanceMatrix.length; i++) {
            solution.add(i);
        }
        myShuffle(solution);
        return solution;
    }

    public List<Integer> perturbSolution(List<Integer> solution) {
        int firstIndex = (int) (Math.random() * solution.size());
        int secondIndex = (int) (Math.random() * solution.size());

        mySwap(solution, firstIndex, secondIndex);
        return solution;
    }

    public int calculateTotalDistance(List<Integer> solution) {
        int total = 0;
        for (int i = 0; i < solution.size() - 1; i++) {
            int from = solution.get(i);
            int to = solution.get(i + 1);
            total += distanceMatrix[from][to];
        }
        total += distanceMatrix[solution.get(solution.size() - 1)][solution.get(0)]; // Return to starting point
        return total;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

}
