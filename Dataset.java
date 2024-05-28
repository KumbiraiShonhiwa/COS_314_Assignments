import java.util.ArrayList;

import java.util.Random;
import java.util.Collections;

public class Dataset {
    public ArrayList<ArrayList<Double>> dataPoints;

    public Dataset() {
        this.dataPoints = new ArrayList<>();
    }

    public void addDataPoint(ArrayList<Double> dataPoint) {
        this.dataPoints.add(dataPoint);
    }

    public ArrayList<Double> getDataPoint(int index) {
        return this.dataPoints.get(index);
    }

    public int size() {
        return this.dataPoints.size();
    }

    public void clear() {
        this.dataPoints.clear();
    }

    public void removeDataPoint(int index) {
        this.dataPoints.remove(index);
    }

    public ArrayList<ArrayList<Double>> inputs() {
        ArrayList<ArrayList<Double>> inputs = new ArrayList<>();
        for (int i = 0; i < this.dataPoints.size(); i++) {
            for (int j = 0; j < this.dataPoints.get(i).size() - 1; j++) {
                inputs.add(this.dataPoints.get(i));
            }
        }
        return inputs;
    }

    public ArrayList<ArrayList<Double>>outputs() {
        ArrayList<ArrayList<Double>> outputs = new ArrayList<>();
        for (int i = 0; i < this.dataPoints.size(); i++) {
            ArrayList<Double> output = new ArrayList<>();
            output.add(this.dataPoints.get(i).get(this.dataPoints.get(i).size() - 1));
            outputs.add(output);
        }
        return outputs;
    }

    public void print() {
        for (int i = 0; i < this.dataPoints.size(); i++) {
            for (int j = 0; j < this.dataPoints.get(i).size(); j++) {
                System.out.print(this.dataPoints.get(i).get(j) + " ");
            }
            System.out.println();
        }
    }

    public void shuffleDataPoints(Random random) {
        Collections.shuffle(this.dataPoints,random);
    }
}