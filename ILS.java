import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ILS {
    private int[][] distanceMatrix;
    int totalDistance;

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

    public List<Integer> runILS() {
        Long startTime = System.nanoTime();
        List<Integer> currentSolution = generateRandomSolution();
        List<Integer> bestSolution = new ArrayList<>(currentSolution);
        int bestDistance = calculateTotalDistance(bestSolution);
        int total = 0;
        int maxIterations = 50; // stopping criteria
        for (int i = 0; i < maxIterations; i++) {
            // List<Integer> perturbedSolution = perturbSolution(currentSolution);
            List<Integer> localOptima = localSearch(currentSolution);
            if (calculateTotalDistance(localOptima) < bestDistance) {
                bestSolution = new ArrayList<>(localOptima);
                bestDistance = calculateTotalDistance(bestSolution);
            }
            total += calculateTotalDistance(currentSolution);
            currentSolution = new ArrayList<>(bestSolution);
        }
        totalDistance = bestDistance;
        Long endTime = System.nanoTime();
        double totalTime = (endTime - startTime) / 1_000_000_000.0;
        System.out.println("Runtime: " + totalTime + " seconds");
        System.out.println("Average distance: " + total / maxIterations);
        return bestSolution;
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

    public List<Integer> localSearch(List<Integer> solution) {
        while (true) {
            List<Integer> neighbor = perturbSolution(solution);
            if (calculateTotalDistance(neighbor) >= calculateTotalDistance(solution)) {
                break;
            }
            solution = neighbor;
        }
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
