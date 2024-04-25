public class Item {

    public int[] gene = new int[2];
    private double fitness;

    public Item(int weight, int value) {
        this.gene[0] = weight;
        this.gene[1] = value;
    }

    public Item(Item item) {
        this.gene[0] = item.getWeight();
        this.gene[1] = item.getValue();
    }

    public void setGene(int index, int v) {
        if (index == 0) {
            this.gene[0] = v;
        }else if (index == 1) {
            this.gene[1] = v;
        }
    }

    public int getWeight() {
        return this.gene[0];
    }

    public int getValue() {
        return this.gene[1];
    }

    public double calulateFitness() {
        return this.fitness = (double) this.gene[1] / this.gene[0];
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getFitness() {
        return fitness;
    }

}
