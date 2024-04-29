import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class GA {
    private int generations;
    private double mutationRate;
    private double crossoverRate;
    public Knapsack population;
    private Random rand;
    private int bestWeight;
    private int bestValue;
    private int bestGeneration;

    public GA(double mutationRate, double crossoverRate, Knapsack population, long seed) {
        // this.populationSize = population.numItems;
        rand = new Random(seed);
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.population = population;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }

    private int reverseGene(int gene) {
        int index = 0;
        while (gene != 0) {
            index = index * 10 + gene % 10;
            gene /= 10;
        }
        return index;
    }

    public void mutate(Knapsack sack) {
        // Implement the mutation function here
        if (mutationRate > rand.nextDouble()) {
            for (int i = 0; i < sack.getItems().size(); i++) {
                int index = rand.nextInt(sack.getItems().size());
                int geneIndex = rand.nextInt(2);
                if (sack.getItemAtIndex(index) != null)
                    sack.getItemAtIndex(index).setGene(geneIndex,
                            reverseGene(sack.getItemAtIndex(index).gene[geneIndex]));
            }
        }
    }

    public Knapsack crossover(Knapsack sack) {
        // Implement the crossover function here
        Knapsack crossover = new Knapsack(sack.getWeightLimit());
        if (sack.getItems().size() == 1) {
            crossover.addItem(new Item(sack.getItemAtIndex(0).getValue(), sack.getItemAtIndex(0).getWeight()));
            return crossover;
        }
        for (int i = 0; i < sack.getItems().size() / 2; i++) {
            Item parentA = sack.getItemAtIndex(i * 2);
            Item parentB = sack.getItemAtIndex(i * 2 + 1);
            if (crossoverRate > rand.nextDouble()) {
                continue;
            }
            if (parentA != null && parentB != null) {
                int temp = parentA.gene[rand.nextInt(2)];
                int index = rand.nextInt(2);
                parentA.gene[index] = parentB.gene[index];
                parentB.gene[index] = temp;
            }
            Item childA = new Item(parentA);
            Item childB = new Item(parentB);
            crossover.addItem(childA);
            crossover.addItem(childB);
        }
        return crossover;
    }

    private void initializePopulation(String file, int weightLimit) {
        int value = 0;
        int weight = 0;
        try {
            File object = new File(file);
            Scanner readFile = new Scanner(object);
            while (readFile.hasNextLine() && population.isFull(weightLimit)) {
                if (readFile.hasNextInt()) {
                    value = readFile.nextInt();
                }
                if (readFile.hasNextInt()) {
                    weight = readFile.nextInt();
                }
                if (readFile.nextLine() == null) {
                    break;
                }
                population.addItem(new Item(value, weight));
            }
            readFile.close();
        } catch (FileNotFoundException error) {
            System.out.println(error.getMessage());
        }
    }

    private Knapsack EvaluateFitness() {
        Knapsack fittest = new Knapsack(population);
        fittest.orderByFitness();
        return fittest;
    }

    private Knapsack tournamentSelection(int numberOfComparions) {
        Knapsack winners = new Knapsack(population.getWeightLimit());
        Item fittest = new Item(0, 0);
        fittest.setFitness(0);
        for (int i = 0; i < population.getItems().size(); i++) {
            for (int j = 0; j < numberOfComparions; j++) {
                int index = rand.nextInt(population.getItems().size());
                Item current = population.getItemAtIndex(index);
                if (current != null) {
                    if (current.calulateFitness() > fittest.getFitness()) {
                        fittest = current;
                    }
                }
            }
            if (winners.getItems().size() == 0) {
                winners.addItem(fittest);
            }
            for (int k = 0; k < winners.getItems().size(); k++) {
                if (winners.contains(fittest) == false) {
                    winners.addItem(fittest);
                }
            }

        }
        return winners;
    }

    private Knapsack topHalf() {
        Knapsack all = new Knapsack(population.getWeightLimit());
        for (int i = 0; i < population.getItems().size() / 2; i++) {
            all.addItem(population.getItemAtIndex(i));
        }

        all.orderByFitness();
        Knapsack top = new Knapsack(population.getWeightLimit());
        for (int i = 0; i < population.getItems().size() / 2; i++) {
            top.addItem(all.getItemAtIndex(i));
        }
        return top;

    }

    public Knapsack Generational() {
        Knapsack sack = new Knapsack(population.getWeightLimit());
        return sack;
    }

    public void SteadyState(Knapsack offspring, Knapsack old_generation) {
        for (int i = 0; i < offspring.getItems().size(); i++) {
            if (offspring.getItemAtIndex(i) != null) {
                Item off_spring = offspring.getItemAtIndex(i);
                for (int j = 0; j < old_generation.getItems().size(); j++) {
                    if (old_generation.getItemAtIndex(j) != null) {
                        Item old = old_generation.getItemAtIndex(j);
                        if (off_spring.calulateFitness() > old.calulateFitness()) {
                            old_generation.setItemAtIndex(j, off_spring);
                            break;
                        }
                    }
                }
            }
        }
        population = old_generation;
    }

    private int calculateTotalWeight(Knapsack sack) {
        int totalWeight = 0;
        for (int i = 0; i < sack.getItems().size(); i++) {
            if (sack.getItemAtIndex(i) != null) {
                totalWeight += sack.getItemAtIndex(i).getWeight();
            }
        }
        return totalWeight;
    }

    private int calculateTotalValue(Knapsack sack) {
        int totalValue = 0;
        for (int i = 0; i < sack.getItems().size(); i++) {
            if (sack.getItemAtIndex(i) != null) {
                totalValue += sack.getItemAtIndex(i).getValue();
            }
        }
        return totalValue;
    }

    public void run(String file) {
        // Random rand = Seed(System.currentTimeMillis());
        // Implement the run function here
        int numberofgenerations = 0;
        initializePopulation(file, population.getWeightLimit());
        while (numberofgenerations < generations) {
            EvaluateFitness();
            Knapsack mating_pool = topHalf();
            Knapsack cross = crossover(mating_pool);
            mutate(cross);
            SteadyState(cross, population);
            bestValue = calculateTotalValue(population);
            bestWeight = calculateTotalWeight(population);
            bestGeneration = numberofgenerations;
            numberofgenerations++;
            population.print();
            System.out
                    .println("Generation: " + bestGeneration + " Value: " + bestValue + " Weight: " + bestWeight+" Best Solution: "+bestValue/bestWeight);
        }

    }

}
