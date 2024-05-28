import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Individual {
    private StringBuilder syntaxTree; // string of the expression
    private double fitness; // the result of the expression/expected result of the expression
    public ArrayList<Double> numbers;
    public ArrayList<Character> operators;
    public double bestResult = 0;
    public double expectedResult;
    public int correct = 0;
    private ArrayList<String> syntaxTreeStrings;
    private int truePositives = 0;
    private int trueNegatives = 0;
    private int falsePositives = 0;
    private int falseNegatives = 0;

    public Individual(ArrayList<Double> numbers, ArrayList<Character> operators) {
        this.syntaxTree = new StringBuilder();
        this.fitness = 0;
        this.numbers = numbers;
        this.operators = operators;
        this.syntaxTreeStrings = new ArrayList<>();
    }

    public Individual(Individual individual) {
        this.syntaxTree = new StringBuilder(individual.syntaxTree);
        this.fitness = individual.fitness;
        this.numbers = new ArrayList<>(individual.numbers);
        this.operators = new ArrayList<>(individual.operators);
        this.syntaxTreeStrings = new ArrayList<>(individual.syntaxTreeStrings);
        this.expectedResult = individual.expectedResult;
    }

    public StringBuilder getSyntaxTree() {
        return syntaxTree;
    }

    public void setNumbers(ArrayList<Double> numbers) {
        this.numbers = numbers;
    }

    public void setOperators(ArrayList<Character> operators) {
        this.operators = operators;
    }

    public void setSyntaxTree(StringBuilder syntaxTree) {
        this.syntaxTree = syntaxTree;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public int getTruePositives() {
        return truePositives;
    }

    public int getTrueNegatives() {
        return trueNegatives;
    }

    public int getFalsePositives() {
        return falsePositives;
    }

    public int getFalseNegatives() {
        return falseNegatives;
    }

    public double calculateFitness() {
        // The closer the bestResult is to the expectedResult, the higher the fitness
        double fitness = 1 / (1 + Math.abs(bestResult - expectedResult));

        if (expectedResult == 1) {
            if (bestResult > 0.5) {
                truePositives = 1;
                correct = 1;
            } else {
                falseNegatives = 1;
            }
        } else { // expectedResult == 0
            if (bestResult <= 0.5) {
                trueNegatives = 1;
                correct = 1;
            } else {
                falsePositives = 1;
            }
        }

        return fitness;
    }

    public Double evaluateExpression(ArrayList<Double> numbers, ArrayList<Character> operators, Random rand) {
        // Random rand = new Random(seed);
        int noImprovement = 0;
        Double result = 0.0;
        expectedResult = numbers.get(numbers.size() - 1);
        bestResult = Double.MAX_VALUE;
        // StringBuilder bestStackString = new StringBuilder();

        while (noImprovement < 100) {
            Stack<Double> stack = new Stack<>();
            StringBuilder stackString = new StringBuilder();
            ArrayList<String> TreeStrings = new ArrayList<>();

            for (int i = 0; i < numbers.size() - 1; i++) {
                double num = numbers.get(i);
                char op = operators.get(rand.nextInt(operators.size()));

                switch (op) {
                    case '+':
                        stack.push(num);
                        stackString.append("+ ").append(num).append(" ");
                        TreeStrings.add("+");
                        TreeStrings.add(num + "");
                        break;
                    case '-':
                        stack.push(-num);
                        stackString.append("- ").append(num).append(" ");
                        TreeStrings.add("-");
                        TreeStrings.add(num + "");
                        break;
                    case '*':
                        if (!stack.isEmpty()) {
                            stack.push(stack.pop() * num);
                        }
                        stackString.append("* ").append(num).append(" ");
                        TreeStrings.add("*");
                        TreeStrings.add(num + "");
                        break;
                    case '/':
                        if (!stack.isEmpty()) {
                            stack.push(stack.pop() / num);
                        }
                        stackString.append("/ ").append(num).append(" ");
                        TreeStrings.add("/");
                        TreeStrings.add(num + "");
                        break;
                }
            }

            Double currentResult = 0.0;
            while (!stack.isEmpty()) {
                currentResult += stack.pop();
            }

            if (Math.abs(currentResult - expectedResult) < 0.0001) {
                result = currentResult;
                break;
            } else {
                noImprovement++;
                if (Math.abs(currentResult - expectedResult) < Math.abs(bestResult - expectedResult)) {
                    syntaxTree = stackString;
                    syntaxTreeStrings = TreeStrings;
                    bestResult = currentResult;
                }
            }
        }
        return bestResult;
    }

    public void setSyntaxTreeStrings() {
        for (int i = 0; i < syntaxTree.length(); i++) {
            syntaxTreeStrings.add(syntaxTree.substring(i, i + 1));
        }
    }

    public ArrayList<String> getSyntaxTreeStrings() {
        return syntaxTreeStrings;
    }

    public double evaluate() {
        // Parse the best stack string
        Stack<Double> newStack = new Stack<>();

        for (int i = 0; i < syntaxTreeStrings.size(); i += 2) {
            char op = syntaxTreeStrings.get(i).charAt(0);
            double num = Double.parseDouble(syntaxTreeStrings.get(i + 1));

            switch (op) {
                case '+':
                    if (!newStack.isEmpty()) {
                        newStack.push(newStack.pop() + num);
                    } else {
                        newStack.push(num);
                    }
                    break;
                case '-':
                    if (!newStack.isEmpty()) {
                        newStack.push(newStack.pop() - num);
                    } else {
                        newStack.push(-num);
                    }
                    break;
                case '*':
                    if (!newStack.isEmpty()) {
                        newStack.push(newStack.pop() * num);
                    }
                    break;
                case '/':
                    if (!newStack.isEmpty() && num != 0) {
                        newStack.push(newStack.pop() / num);
                    }
                    break;
            }
        }

        // Return the result
        return newStack.isEmpty() ? 0 : newStack.pop();
    }

    public void print() {
        System.out.println("Syntax Tree: " + syntaxTreeStrings.toString() + " Best Result: " + bestResult + " "
                + " Expected Result: " + expectedResult + " Fitness: " + fitness);
    }
}
