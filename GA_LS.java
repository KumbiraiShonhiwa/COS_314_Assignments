import java.util.Random;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class GA_LS {
    private int generations;
    private int weightLimit;
    private double mutationRate;
    private double crossoverRate;
    public ArrayList<Knapsack> population;
    public ArrayList<Item> items;
    private Knapsack best_solution;
    private Random rand;
    private int populationSize;
    private int bestWeight;
    private int bestValue;
    private int bestGeneration;

    public GA_LS(double mutationRate, double crossoverRate, long seed,
            int populationSize, int weightLimit, int generations) {
        // this.populationSize = population.numItems;
        rand = new Random(seed);
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.population = new ArrayList<Knapsack>();
        this.populationSize = populationSize;
        this.weightLimit = weightLimit;
        this.items = new ArrayList<Item>();
        this.generations = generations;
    }

    public String print(ArrayList<Knapsack> population) {
        String string = "";
        for (int i = 0; i < population.size(); i++) {
            string += ("Knapsack Solution " + i + ":" + " Fitness of Solution: " + population.get(i).calculateFitness()
                    + " Value: " + population.get(i).totalValue + " Weight: " + population.get(i).totalWeight
                    + "\n");
        }
        return string;
    }

    public void writeToFile(String input) {
        try {
            File file = new File("output.txt");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(input);
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readFile(String file) {
        int value = 0;
        int weight = 0;
        try {
            File object = new File(file);
            Scanner readFile = new Scanner(object);
            if (readFile.hasNext()) {
                readFile.nextLine();
            }
            while (readFile.hasNextLine()) {
                if (readFile.hasNextDouble()) {
                    value = (int) readFile.nextDouble();
                }
                if (readFile.hasNextDouble()) {
                    weight = (int) readFile.nextDouble();
                }
                items.add(new Item(value, weight));
            }
            readFile.close();
        } catch (FileNotFoundException error) {
            System.out.println(error.getMessage());
        }
    }

    public void initializePopulation() {
        for (int i = 0; i < populationSize; i++) {
            Knapsack knapsack = new Knapsack(weightLimit);
            int[] duplicate = new int[items.size()];
            for (int k = 0; k < duplicate.length; k++) {
                duplicate[k] = -1;
            }
            for (int j = 0; j < items.size(); j++) {
                int index = rand.nextInt(items.size());
                if (!checkDuplicates(duplicate, index)) {
                    duplicate[j] = index;
                    knapsack.addItem(items.get(index));
                } else if (checkDuplicates(duplicate, index)) {
                    j--;
                } else if (j == 10 && checkDuplicates(duplicate, index)) {
                    break;
                }
            }
            if (!checkDuplicateSolution(knapsack)) {
                population.add(knapsack);
            } else if (checkDuplicateSolution(knapsack)) {
                i--;
            } else if (i == population.size() && checkDuplicateSolution(knapsack)) {
                break;
            }
        }
        writeToFile(print(population));
    }

    public boolean checkDuplicateSolution(Knapsack knapsack) {
        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).sameItems(knapsack)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDuplicates(int[] duplicate, int index) {
        boolean result = false;
        for (int i = 0; i < duplicate.length; i++) {
            if (duplicate[i] == index) {
                return true;
            } else {
                result = false;
            }
        }
        return result;
    }

    public ArrayList<Knapsack> topHalf() {
        ArrayList<Knapsack> all = new ArrayList<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            all.add(population.get(i));
        }
        for (int i = 0; i < populationSize; i++) {
            all.get(i).calculateFitness();
        }
        all.sort((a, b) -> Double.compare(b.calculateFitness(), a.calculateFitness()));
        ArrayList<Knapsack> topHalf = new ArrayList<>(populationSize / 2);
        for (int i = 0; i < populationSize / 2; i++) {
            topHalf.add(all.get(i));
        }
        return topHalf;
    }

    public ArrayList<Knapsack> crossover(ArrayList<Knapsack> population) {
        ArrayList<Knapsack> crossover = new ArrayList<>(population.size());
        for (int i = 0; i < population.size(); i++) {
            int cross_over_point = rand.nextInt(3);
            int parent1 = rand.nextInt(population.size());
            int parent2 = rand.nextInt(population.size());
            Knapsack childA = new Knapsack(weightLimit);
            Knapsack childB = new Knapsack(weightLimit);
            if (crossoverRate > rand.nextDouble()) {
                continue;
            }
            if (population.get(parent1) != null && population.get(parent2) != null) {
                if (population.get(parent1).getItemAtIndex(cross_over_point) != null
                        && population.get(parent2).getItemAtIndex(cross_over_point) != null) {
                    if (population.get(parent1).hasSameItem(population.get(parent2).getItemAtIndex(cross_over_point))) {
                        continue;
                    }
                    Item temp = population.get(parent1).getItemAtIndex(cross_over_point);
                    population.get(parent1).setItemAtIndex(cross_over_point,
                            population.get(parent2).getItemAtIndex(cross_over_point));
                    population.get(parent2).setItemAtIndex(cross_over_point, temp);
                }
            }
            childA = population.get(parent1);
            childB = population.get(parent2);
            crossover.add(childA);
            crossover.add(childB);
        }
        return crossover;
    }

    public void mutate(ArrayList<Knapsack> sack) {
        for (int i = 0; i < sack.size(); i++) {
            if (mutationRate > rand.nextDouble()) {
                int index = rand.nextInt(sack.size());
                if (sack.get(index) != null && index < sack.get(index).getItems().size() && index >= 0) {
                    if (sack.get(index).getItems().get(index) != null) {
                        sack.get(index).getItems().set(index, randomItem());
                    }
                }
            }
        }
    }

    public Item randomItem() {
        int index = rand.nextInt(items.size());
        if (items.get(index) != null)
            return items.get(index);
        return null;
    }

    public void SteadyState(Knapsack offspring, ArrayList<Knapsack> population) {

        if (offspring != null) {
            for (int j = 0; j < population.size(); j++) {
                if (offspring.calculateFitness() > population.get(j).calculateFitness()) {
                    population.set(j, offspring);
                    break;
                }
            }
        }
    }

    private Knapsack perturbSolution(Knapsack knapsack) {
        Knapsack newKnapsack = new Knapsack(knapsack.getWeightLimit());
        for (int i = 0; i < knapsack.getItems().size(); i++) {
            Item item = randomItem();
            if (!newKnapsack.hasSameItem(item))
                newKnapsack.addItem(item);
            else {
                i--;

            }
        }
        return newKnapsack;
    }

    private boolean shouldAccept(double deltaCost, double temperature) {
        if (deltaCost < 0) {
            return true;
        }
        return rand.nextDouble() < Math.exp(-deltaCost / temperature);
    }

    private Knapsack SimulatedAnnealing(ArrayList<Knapsack> bag, int number_iterations) {
        double temperature = 1000;
        if (bag.size() == 0) {
            return null;
        }
        int index = rand.nextInt(bag.size());
        Knapsack current = bag.get(index);
        Knapsack best = bag.get(index);
        Knapsack neighbor = new Knapsack(bag.get(index).getWeightLimit());
        int t = 1;
        int no_Improvement = 0;
        while (temperature > 0 && no_Improvement < number_iterations) {
            neighbor = perturbSolution(current);
            double deltaCost = neighbor.totalValue - current.totalValue;
            if (shouldAccept(deltaCost, temperature / Math.log(t + 1))) {
                current = new Knapsack(neighbor);
                if (current.calculateFitness() > best.calculateFitness()) {
                    best = new Knapsack(current);
                    no_Improvement = 0;
                } else {
                    no_Improvement++;
                }
            }
            temperature = temperature / Math.log(t + 1);
            t++;
        }
        return best;
    }

    public Knapsack findBest(Knapsack best,ArrayList<Knapsack> population) {
        // Knapsack best = population.get(0);
        for (int i = 0; i < population.size(); i++) {
            if (best != null && population.get(i) != null)
                if (population.get(i).calculateFitness() > best.calculateFitness()) {
                    best = population.get(i);
                }
        }
        return best;
    }

    public void run(String file) {
        long startTime = System.currentTimeMillis();
        int iterations = 0;
        Knapsack best = new Knapsack(weightLimit);
        readFile(file);
        initializePopulation();
        best = population.get(0);
        while (iterations < generations) {
            // System.out.println("Generation " + iterations + "\n");
            ArrayList<Knapsack> mating_pool = topHalf();
            ArrayList<Knapsack> crossover_pool = crossover(mating_pool);
            Knapsack SA = SimulatedAnnealing(crossover_pool, 100);
            best = findBest(best, population);
            mutate(crossover_pool);
            SteadyState(SA, population);
            iterations++;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (double) (endTime - startTime) / 1000 + "s");
        writeToFile(print(population));
        best.print();
        System.out.println("\n");

    }
}
