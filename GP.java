import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GP {
    private static final int POPULATION_SIZE = 100;
    private static final int MAX_GENERATIONS = 50;
    private static final double MUTATION_RATE = 0.7;
    private static final double CROSSOVER_RATE = 0.9;
    private Population individuals;
    private Random random = new Random();
    private Dataset dataset = new Dataset();
    private Dataset testDataset = new Dataset();

    public GP(Long seed) {
        individuals = new Population(POPULATION_SIZE);
        random = new Random(seed);
    }

    public void run() {

        Scanner scanner;
        try {
            scanner = new Scanner(new File("mushroom_train.csv"));
            scanner.useDelimiter(",");
            // Skip header
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String[] nextLine = scanner.nextLine().split(",");
                double[] row = new double[9];
                ArrayList<Double> rowList = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    row[i] = Double.parseDouble(nextLine[i]);
                    rowList.add(row[i]);
                }
                dataset.addDataPoint(rowList);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            scanner = new Scanner(new File("mushroom_test.csv"));
            scanner.useDelimiter(",");
            if (scanner.hasNextLine()) {
                scanner.nextLine();
            }

            while (scanner.hasNextLine()) {
                String[] nextLine = scanner.nextLine().split(",");
                double[] row = new double[9];
                ArrayList<Double> rowList = new ArrayList<>();
                for (int i = 0; i < 9; i++) {
                    row[i] = Double.parseDouble(nextLine[i]);
                    rowList.add(row[i]);
                }
                testDataset.addDataPoint(rowList);
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int evolution = 0;
        double accuracy = 0;
        double internalAccuracy = 0;
        double totalAccuracy = 0;
        double testingAccuracy = 0;
        while (evolution < 1) {
            double sum = 0;
            dataset.shuffleDataPoints(random);
            for (int i = 0; i < 2; i++) {
                initializePopulation(dataset.getDataPoint(i),
                        new ArrayList<>(Arrays.asList('+', '-', '*', '/')));
                double internalSum = 0;
                for (int g = 0; g < MAX_GENERATIONS; g++) {

                    individuals = organisePopulation();
                    ArrayList<Individual> parents = select(individuals);
                    ArrayList<Individual> children = crossover(parents);
                    mutate(children);
                    generationReplacement(individuals.individuals, children);
                    for (Individual nd : individuals.individuals) {
                        internalSum += nd.correct;
                    }
                    internalAccuracy = internalSum / MAX_GENERATIONS;
                    System.out.println("Evolution: " + g + "\nTraining Accuracy: " + internalAccuracy + "%");
                }
                totalAccuracy += internalAccuracy;
                
                // getBestIndividual().print();
                sum += getBestIndividual().correct;
            }
            accuracy = sum / dataset.size() * 100;
            // System.out.println("Evolution: " + evolution + "\nTraining Accuracy: " +
            // accuracy + "%");
            evolution++;
        }

        double sum = 0;
        testDataset.shuffleDataPoints(random);
        for (int i = 0; i < testDataset.size(); i++) {
            initializePopulation(testDataset.getDataPoint(i),
                    new ArrayList<>(Arrays.asList('+', '-', '*', '/')));
            for (int g = 0; g < MAX_GENERATIONS; g++) {
                individuals = organisePopulation();
                ArrayList<Individual> parents = select(individuals);
                ArrayList<Individual> children = crossover(parents);
                mutate(children);
                generationReplacement(individuals.individuals, children);
                for (Individual nd : individuals.individuals) {
                    sum += nd.correct;
                }
            }
            // sum += getBestIndividual().correct;
        }
        testingAccuracy = sum / (testDataset.size() * MAX_GENERATIONS);
        System.out.println("Testing Accuracy: " + testingAccuracy + "%");
        System.out.println("Sensitivity: " + individuals.calculateSensitivity());
        System.out.println("Specificity: " + individuals.calculateSpecificity());
        System.out.println("F-Measure: " + individuals.calculateFMeasure());
    }

    private void initializePopulation(ArrayList<Double> numbers, ArrayList<Character> operators) {
        individuals = new Population(POPULATION_SIZE);
        for (int i = 0; i < POPULATION_SIZE; i++) {
            Individual individual = new Individual(numbers, operators);
            individual.evaluateExpression(numbers, operators, random);
            individuals.addIndividual(individual);
            individual.setFitness(individual.calculateFitness());
        }

    }

    private Population organisePopulation() {
        Population organisedPopulation = individuals;
        List<Individual> individualsList = individuals.individuals;

        for (int i = 0; i < POPULATION_SIZE; i++) {
            individualsList.get(i).setFitness(organisedPopulation.individuals.get(i).calculateFitness());
        }

        Collections.sort(individualsList, new Comparator<Individual>() {
            @Override
            public int compare(Individual i1, Individual i2) {
                return Double.compare(i2.getFitness(), i1.getFitness());
            }
        });

        for (int i = 0; i < POPULATION_SIZE; i++) {
            organisedPopulation.individuals.set(i, individualsList.get(i));
        }
        return organisedPopulation;
    }

    private ArrayList<Individual> select(Population population) {
        ArrayList<Individual> parents = new ArrayList<>();
        int halfPopulationSize = population.individuals.size() / 2;

        for (int i = 0; i < halfPopulationSize; i++) {
            parents.add(population.individuals.get(i));
        }
        return parents;
    }

    private ArrayList<Individual> crossover(ArrayList<Individual> parents) {
        ArrayList<Individual> children = new ArrayList<>();

        for (int j = 0; j + 1 < parents.size(); j += 2) {
            if (random.nextDouble() < CROSSOVER_RATE) {
                Individual child1 = new Individual(parents.get(j));
                Individual child2 = new Individual(parents.get(j + 1));

                for (int i = 0; i < child1.getSyntaxTreeStrings().size(); i += 2) {
                    String temp = child1.getSyntaxTreeStrings().get(i);
                    child1.getSyntaxTreeStrings().set(i, child2.getSyntaxTreeStrings().get(i));
                    child2.getSyntaxTreeStrings().set(i, temp);
                    child1.bestResult = child1.evaluate();
                    child2.bestResult = child2.evaluate();
                    child1.setFitness(child1.calculateFitness());
                    child2.setFitness(child2.calculateFitness());
                }

                children.add(child1);
                children.add(child2);
            } else {
                children.add(parents.get(j));
                children.add(parents.get(j + 1));
            }
        }
        return children;
    }

    private void mutate(ArrayList<Individual> individuals) {

        int randomIndex = random.nextInt(individuals.size());
        Individual individual = individuals.get(randomIndex);
        if (random.nextDouble() < MUTATION_RATE) {
            int index1 = random.nextInt(individual.getSyntaxTreeStrings().size() / 2) * 2;
            int index2;
            do {
                index2 = random.nextInt(individual.getSyntaxTreeStrings().size() / 2) * 2;
            } while (index1 == index2);

            for (int i : new int[] { index1, index2 }) {
                String op = individual.getSyntaxTreeStrings().get(i);
                switch (op) {
                    case "+":
                        individual.getSyntaxTreeStrings().set(i, "-");
                        break;
                    case "-":
                        individual.getSyntaxTreeStrings().set(i, "+");
                        break;
                    case "*":
                        individual.getSyntaxTreeStrings().set(i, "/");
                        break;
                    case "/":
                        individual.getSyntaxTreeStrings().set(i, "*");
                        break;
                }
            }
            individual.bestResult = individual.evaluate();
            individual.setFitness(individual.calculateFitness());
        }

    }

    public void generationReplacement(ArrayList<Individual> previousPopulation, ArrayList<Individual> children) {
        for (Individual child : children) {
            for (int i = 0; i < previousPopulation.size(); i++) {
                if (child.calculateFitness() >= previousPopulation.get(i).calculateFitness()) {
                    previousPopulation.set(i, child);
                    break;
                }
            }
        }

    }

    public Individual getBestIndividual() {
        ArrayList<Individual> individualsList = organisePopulation().individuals;
        return individualsList.get(0);
    }
}