import java.util.ArrayList;

public class Population {
    public ArrayList<Individual> individuals = new ArrayList<>();

    public Population(int size) {
        this.individuals = new ArrayList<>();
    }
    public void addIndividual(Individual individual) {
        individuals.add(individual);
    }

    public Population(ArrayList<Individual> individuals) {
        this.individuals = (ArrayList<Individual>) individuals.clone();
    }
    
    public void printPopulation() {
        for (Individual individual : individuals) {
            System.out.println(individual.numbers.toString()+" "+individual.bestResult+" "+individual.getFitness());
        }
    }

    public int getTotalTruePositives() {
        int total = 0;
        for (Individual individual : individuals) {
            total += individual.getTruePositives();
        }
        return total;
    }

    public int getTotalTrueNegatives() {
        int total = 0;
        for (Individual individual : individuals) {
            total += individual.getTrueNegatives();
        }
        return total;
    }

    public int getTotalFalsePositives() {
        int total = 0;
        for (Individual individual : individuals) {
            total += individual.getFalsePositives();
        }
        return total;
    }

    public int getTotalFalseNegatives() {
        int total = 0;
        for (Individual individual : individuals) {
            total += individual.getFalseNegatives();
        }
        return total;
    }

    public double calculateSensitivity() {
         int totalTruePositives = getTotalTruePositives();
        int totalFalseNegatives = getTotalFalseNegatives();
        return (double) totalTruePositives / (totalTruePositives + totalFalseNegatives);
    }

    public double calculateSpecificity() {
        int totalTrueNegatives = getTotalTrueNegatives();
        int totalFalsePositives = getTotalFalsePositives();
        return (double) totalTrueNegatives / (totalTrueNegatives + totalFalsePositives);
    }

    public double calculateFMeasure() {
        double sensitivity = calculateSensitivity();
        double specificity = calculateSpecificity();
        return 2 * (sensitivity * specificity) / (sensitivity + specificity);
    }

   
}
