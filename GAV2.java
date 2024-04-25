import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class GAV2 {
    private int generations;
    private int weightLimit;
    private double mutationRate;
    private double crossoverRate;
    public ArrayList<Knapsack> population;
    public ArrayList<Item> items;
    private Random rand;
    private int populationSize;
    private int bestWeight;
    private int bestValue;
    private int bestGeneration;

    public GAV2(double mutationRate, double crossoverRate, long seed,
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

    public void print(ArrayList<Knapsack> population) {
        for (int i = 0; i < population.size(); i++) {
            System.out.println(
                    "Knapsack Solution " + i + ":" + " Fitness of Solution: " + population.get(i).calculateFitness()
                            + " Value: " + population.get(i).totalValue + " Weight: " + population.get(i).totalWeight
                            + "\n");
            population.get(i).print();
        }
    }

    public void readFile(String file) {
        int value = 0;
        int weight = 0;
        try {
            File object = new File(file);
            Scanner readFile = new Scanner(object);
            while (readFile.hasNextLine()) {
                if (readFile.hasNextInt()) {
                    value = readFile.nextInt();
                }
                if (readFile.hasNextInt()) {
                    weight = readFile.nextInt();
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
        print(population);
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

    public void SteadyState(ArrayList<Knapsack> offspring, ArrayList<Knapsack> population) {
        for (int i = 0; i < offspring.size(); i++) {
            if (offspring.get(i) != null) {
                for (int j = 0; j < population.size(); j++) {
                    if (offspring.get(i).calculateFitness() > population.get(j).calculateFitness()) {
                        population.set(j, offspring.get(i));
                        break;
                    }
                }
            }
        }
    }

    public void run(String file) {
        int iterations = 0;
        readFile(file);
        initializePopulation();
        while (iterations < generations) {
            // System.out.println("Generation " + iterations + "\n");
            ArrayList<Knapsack> mating_pool = topHalf();
            ArrayList<Knapsack> crossover_pool = crossover(mating_pool);
            mutate(crossover_pool);
            SteadyState(crossover_pool, population);
            iterations++;
        } 
        print(population);
    }
}