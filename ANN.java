import java.util.ArrayList;
import java.util.Random;


public class ANN {
    private int inputNodes;
    public double error = 0;
    private int hiddenNodes;
    private int outputNodes;
    private double learningRate;
    public  int truePositives = 0;
    public  int trueNegatives = 0;
    public  int falsePositives = 0;
    public  int falseNegatives = 0;
    public ArrayList<ArrayList<Double>> weightsInputHidden;
    public ArrayList<ArrayList<Double>> weightsHiddenOutput;
    public double biasHidden;
    public double biasOutput;
    public double accuracy = 0;

    public ANN(int inputNodes, int hiddenNodes, int outputNodes, double learningRate) {
        this.inputNodes = inputNodes;
        this.hiddenNodes = hiddenNodes;
        this.outputNodes = outputNodes;
        this.learningRate = learningRate;

        // Initialize weights and biases to small random values
        Random rand = new Random();
        weightsInputHidden = new ArrayList<>();
        weightsHiddenOutput = new ArrayList<>();
        for (int i = 0; i < hiddenNodes; i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j < inputNodes; j++) {
                row.add(rand.nextDouble() * 0.01);
            }
            weightsInputHidden.add(row);
        }
        for (int i = 0; i < outputNodes; i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j < hiddenNodes; j++) {
                row.add(rand.nextDouble() * 0.01);
            }
            weightsHiddenOutput.add(row);
        }
        biasHidden = rand.nextDouble() * 0.01;
        biasOutput = rand.nextDouble() * 0.01;
    }

    private double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    private double sigmoidDerivative(double x) {
        double s = sigmoid(x);
        return s * (1 - s);
    }

    public void calculateAccuracy(ArrayList<Double> inputs, ArrayList<Double> targets, int start) {
        // Feedforward
        // Step 1: Calculate n1 for each node in the hidden layer
        ArrayList<Double> hiddenN1 = new ArrayList<>();
        for (int i = 0; i < hiddenNodes; i++) {
            double sum = biasHidden;
            for (int j = 0; j < inputNodes; j++) {
                if (j < inputs.size()) {
                    sum += inputs.get(j) * weightsInputHidden.get(i).get(j);
                } else {
                    break;
                }
            }
            hiddenN1.add(sum);
            // System.out.println(sum);
        }

        // Step 2: Calculate the activation f(n1) for each node in the hidden layer
        ArrayList<Double> hiddenInputs = new ArrayList<>();
        for (Double n1 : hiddenN1) {
            hiddenInputs.add(sigmoid(n1));
        }

        // Step 3: Calculate n2 for each node in the output layer
        ArrayList<Double> outputN2 = new ArrayList<>();
        for (int i = 0; i < outputNodes; i++) {
            double sum = biasOutput;
            for (int j = 0; j < hiddenNodes; j++) {
                sum += hiddenInputs.get(j) * weightsHiddenOutput.get(i).get(j);
            }
            outputN2.add(sum);
        }

        // Step 4: Calculate the activation f(n2) for each node in the output layer
        ArrayList<Double> outputs = new ArrayList<>();
        for (Double n2 : outputN2) {
            outputs.add(sigmoid(n2));
        }

        // Calculate accuracy
        int correctPredictions = 0;
        for (int i = 0; i < targets.size(); i++) {
            if (i >= outputs.size()) {
                break;
            }
            double target = targets.get(i);
            double output = outputs.get(i);

            // If the output is closer to 1 and the target is 1, or if the output is closer
            // to 0 and the target is 0, increment correctPredictions
            if ((output >= 0.5 && target == 1.0)) {
                correctPredictions++;
                truePositives++;
            } else if (output < 0.5 && target == 0.0) {
                correctPredictions++;
                trueNegatives++;
            } else if (output >= 0.5 && target == 0.0) {
                falsePositives++;
            } else if (output < 0.5 && target == 1.0) {
                falseNegatives++;
            }
        }

        accuracy = (double) correctPredictions;
        // System.out.println("Accuracy: " + accuracy);

        // Backpropagation
        // Step 1: Calculate the error information term for each node in the output
        // layer
        ArrayList<Double> outputErrors = new ArrayList<>();
        for (int i = 0; i < outputNodes; i++) {
            outputErrors.add(targets.get(i) - outputs.get(i));
        }

        // Step 2: Calculate the weight correction term for each node in the output
        // layer
        ArrayList<ArrayList<Double>> outputWeightCorrections = new ArrayList<>();
        for (int i = 0; i < outputNodes; i++) {
            ArrayList<Double> corrections = new ArrayList<>();
            for (int j = 0; j < hiddenNodes; j++) {
                double gradient = outputErrors.get(i) * sigmoidDerivative(outputs.get(i));
                corrections.add(learningRate * gradient * hiddenInputs.get(j));
            }
            outputWeightCorrections.add(corrections);
        }

        // Step 3: Calculate the bias correction term for each node in the output layer
        ArrayList<Double> outputBiasCorrections = new ArrayList<>();
        for (int i = 0; i < outputNodes; i++) {
            outputBiasCorrections.add(learningRate * outputErrors.get(i));
        }

        // Step 4: Calculate the sum of delta inputs for each node in the hidden layer
        ArrayList<Double> hiddenDeltaInputs = new ArrayList<>();
        for (int i = 0; i < hiddenNodes; i++) {
            double deltaInput = 0;
            for (int j = 0; j < outputNodes; j++) {
                deltaInput += outputErrors.get(j) * weightsHiddenOutput.get(j).get(i);
            }
            hiddenDeltaInputs.add(deltaInput);
        }

        // Step 5: Calculate the error information term for each node in the hidden
        // layer
        ArrayList<Double> hiddenErrors = new ArrayList<>();
        for (int i = 0; i < hiddenNodes; i++) {
            hiddenErrors.add(hiddenDeltaInputs.get(i) * sigmoidDerivative(hiddenInputs.get(i)));
        }

        // Step 6: Calculate the weight error term for each node in the hidden layer
        ArrayList<ArrayList<Double>> hiddenWeightCorrections = new ArrayList<>();
        for (int i = 0; i < hiddenNodes; i++) {
            ArrayList<Double> corrections = new ArrayList<>();
            for (int j = 0; j < inputNodes; j++) {
                double gradient = hiddenErrors.get(i) * sigmoidDerivative(hiddenInputs.get(i));
                corrections.add(learningRate * gradient * inputs.get(j));
            }
            hiddenWeightCorrections.add(corrections);
        }

        // Step 7: Calculate the bias error term for each node in the hidden layer
        ArrayList<Double> hiddenBiasCorrections = new ArrayList<>();
        for (int i = 0; i < hiddenNodes; i++) {
            hiddenBiasCorrections.add(learningRate * hiddenErrors.get(i));
        }

        for (int i = 0; i < targets.size(); i++) {
            if (i >= outputs.size()) {
                break;
            }
            this.error += Math.pow(targets.get(i) - outputs.get(i), 2);
        }
        this.error = this.error /= 2;
        // System.out.println("Error after this epoch: " + error);

        // Update weights and biases
        for (int i = 0; i < outputNodes; i++) {
            for (int j = 0; j < hiddenNodes; j++) {
                weightsHiddenOutput.get(i).set(j,
                        weightsHiddenOutput.get(i).get(j) + outputWeightCorrections.get(i).get(j));
            }
            biasOutput += outputBiasCorrections.get(i);
        }
        for (int i = 0; i < hiddenNodes; i++) {
            for (int j = 0; j < inputNodes; j++) {
                if (j >= inputs.size()) {
                    break;
                }
                weightsInputHidden.get(i).set(j,
                        weightsInputHidden.get(i).get(j) + hiddenWeightCorrections.get(i).get(j));
            }
            biasHidden += hiddenBiasCorrections.get(i);
        }
    }

    public void calc(ArrayList<Double> inputs, ArrayList<Double> targets, int start) {
        // Feedforward
        // Step 1: Calculate n1 for each node in the hidden layer
        ArrayList<Double> hiddenN1 = new ArrayList<>();
        for (int i = 0; i < hiddenNodes; i++) {
            double sum = biasHidden;
            for (int j = 0; j < inputNodes; j++) {
                if (j < inputs.size()) {
                    sum += inputs.get(j) * weightsInputHidden.get(i).get(j);
                } else {
                    break;
                }
            }
            hiddenN1.add(sum);
            // System.out.println(sum);
        }

        // Step 2: Calculate the activation f(n1) for each node in the hidden layer
        ArrayList<Double> hiddenInputs = new ArrayList<>();
        for (Double n1 : hiddenN1) {
            hiddenInputs.add(sigmoid(n1));
        }

        // Step 3: Calculate n2 for each node in the output layer
        ArrayList<Double> outputN2 = new ArrayList<>();
        for (int i = 0; i < outputNodes; i++) {
            double sum = biasOutput;
            for (int j = 0; j < hiddenNodes; j++) {
                sum += hiddenInputs.get(j) * weightsHiddenOutput.get(i).get(j);
            }
            outputN2.add(sum);
        }

        // Step 4: Calculate the activation f(n2) for each node in the output layer
        ArrayList<Double> outputs = new ArrayList<>();
        for (Double n2 : outputN2) {
            outputs.add(sigmoid(n2));
        }

        // Calculate accuracy
        int correctPredictions = 0;
        for (int i = 0; i < targets.size(); i++) {
            if (i >= outputs.size()) {
                break;
            }
            double target = targets.get(i);
            double output = outputs.get(i);
            // If the output is closer to 1 and the target is 1, or if the output is closer
            // to 0 and the target is 0, increment correctPredictions
            if ((output >= 0.5 && target == 1.0)) {
                correctPredictions++;
                truePositives++;
            } else if (output < 0.5 && target == 0.0) {
                correctPredictions++;
                trueNegatives++;
            } else if (output >= 0.5 && target == 0.0) {
                falsePositives++;
            } else if (output < 0.5 && target == 1.0) {
                falseNegatives++;
            }
        }

        accuracy = (double) correctPredictions;
    }
}
