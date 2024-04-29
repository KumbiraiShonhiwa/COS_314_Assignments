import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.IOException;

public class GAV2 {
    private int generations;
    private int weightLimit;
    private double mutationRate;
    private double crossoverRate;
    public ArrayList<Knapsack> population;
    public ArrayList<Item> items;
    private Random rand;
    long seed;
    private int populationSize;
    private int bestWeight;
    private int bestValue;
    private int bestGeneration;

    public GAV2(double mutationRate, double crossoverRate, long seed,
            int populationSize, int weightLimit, int generations) {
        // this.populationSize = population.numItems;
        rand = new Random(seed);
        this.seed = seed;
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
        System.out.println("Initial Population");
       System.out.println(print(population));
    }

    public boolean checkDuplicateSolution(Knapsack knapsack) {
        for (int i = 0; i < population.size(); i++) {
            if (population.get(i).sameItems(knapsack)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkedDuplicateSolution(ArrayList<Knapsack> population, Knapsack knapsack) {
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
        sort(all);
        ArrayList<Knapsack> topHalf = new ArrayList<>(populationSize / 2);
        for (int i = 0; i < populationSize / 2; i++) {
            topHalf.add(all.get(i));
        }
        return topHalf;
    }

    public ArrayList<Knapsack> sort(ArrayList<Knapsack> unsorted) {
        ArrayList<Knapsack> sorted = new ArrayList<>(unsorted.size());
        for (int i = 0; i < unsorted.size(); i++) {
            for (int j = 0; j < unsorted.size(); j++) {
                if (unsorted.get(i).calculateFitness() > unsorted.get(j).calculateFitness()) {
                    sorted.add(unsorted.get(i));
                }
            }
        }
        return sorted;
    }

    public ArrayList<Knapsack> crossover(ArrayList<Knapsack> population) {
        ArrayList<Knapsack> crossover = new ArrayList<>(population.size());
        for (int i = 0; i < population.size(); i++) {
            int cross_over_point = rand.nextInt(3);
            int parent1 = rand.nextInt(population.size());
            int parent2 = rand.nextInt(population.size());
            Knapsack childA = new Knapsack(population.get(parent1));
            Knapsack childB = new Knapsack(population.get(parent2));
            if (crossoverRate < rand.nextDouble()) {
                continue;
            }
            if (childA != null && childB != null) {
                if (childA.getItemAtIndex(cross_over_point) != null
                        && childB.getItemAtIndex(cross_over_point) != null) {
                    if (childA.hasSameItem(childB.getItemAtIndex(cross_over_point))) {
                        i--;
                        continue;
                    }
                    Item temp = childA.getItemAtIndex(cross_over_point);
                    childA.setItemAtIndex(cross_over_point,
                            childB.getItemAtIndex(cross_over_point));
                    childB.setItemAtIndex(cross_over_point, temp);
                }
            }
            if (checkedDuplicateSolution(crossover, childA) || checkedDuplicateSolution(crossover, childB)) {
                i--;
                continue;
            }
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
        System.out.println("Seed_Value "+ seed);
        initializePopulation();
        best = population.get(0);
        while (iterations < generations) {
            // System.out.println("Generation " + iterations + "\n");
            ArrayList<Knapsack> mating_pool = topHalf();
            ArrayList<Knapsack> crossover_pool = crossover(mating_pool);
            mutate(crossover_pool);
            SteadyState(crossover_pool, population);
            best = findBest(best,population);
            iterations++;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken: " + (double) (endTime - startTime)/1000 + "s");
        writeToFile(print(population));
        best.print();
        System.out.println("\n");
    }
}