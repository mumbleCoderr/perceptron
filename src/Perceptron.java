import java.util.ArrayList;
import java.util.List;

public class Perceptron {
    private List<Double> weights;
    private double alpha;
    private boolean weightsImproved;
    private int calculatedOutput;
    private boolean repeat;
    private double accuracy;
    private double totalAttemps;
    private double positiveAttemps;

    public Perceptron(List<Double> weights, double alpha) {
        this.weights = weights;
        this.alpha = alpha;
        this.weightsImproved = true;
        this.accuracy = 0d;
        this.totalAttemps = 0d;
        this.positiveAttemps = 0d;
    }

    private void compute(List<Iris> testingData) {
        for (int i = 0; i < testingData.size(); i++) {
            Iris iris = testingData.get(i);
            List<Double> vector = iris.getAttributes();
            double dotProduct = dotProduct(vector);
            int realOutput = iris.getRealOutput();
            if (dotProduct >= 0) calculatedOutput = 1;
            else calculatedOutput = 0;
            double diff = (realOutput - calculatedOutput);
            printTestingResults(iris, dotProduct, realOutput, calculatedOutput);
            evaluate(diff);
        }
    }

    public void computeTestingData(List<Iris> testingData) {
        System.out.println("=======================================================================================");
        System.out.println("\t\t\t\t\t\t\tCOMPUTING TESTING DATA");
        System.out.println("=======================================================================================");
        compute(testingData);
        System.out.print("\t\t\t\t\taccuracy of testing data: ");
        System.out.println(calculateAccuracy() + " %");
        cleanCache();
    }

    public void computeUserData(List<Iris> userData) {
        System.out.println("=======================================================================================");
        System.out.println("\t\t\t\t\t\t\tCOMPUTING USER DATA");
        System.out.println("=======================================================================================");
        compute(userData);
        System.out.print("\t\t\t\t\taccuracy of user data: ");
        System.out.println(calculateAccuracy() + " %");
        cleanCache();
    }

    public void computeInputData(List<Iris> inputData, String class1, String class2) {
        Iris iris = inputData.get(0);
        List<Double> vector = iris.getAttributes();
        double dotProduct = dotProduct(vector);
        printInputResults(dotProduct, iris, class1, class2);
        inputData.remove(0);
    }

    public void evaluate(double diff) {
        if (diff == 0) {
            positiveAttemps++;
            totalAttemps++;
        } else {
            totalAttemps++;
        }
    }

    private double calculateAccuracy() {
        accuracy = (positiveAttemps / totalAttemps) * 100;
        return accuracy;
    }

    public void learn(Iris iris) {
        int realOutput = iris.getRealOutput();
        List<Double> vector = iris.getAttributes();
        repeat = false;

        do {
            double dotProduct = dotProduct(vector);
            if (dotProduct >= 0) calculatedOutput = 1;
            else calculatedOutput = 0;
            List<Double> newWeights = deltaRule(vector, realOutput, calculatedOutput);
            printTrainingResults(iris, dotProduct, newWeights);
            if (weights.equals(newWeights)) {
                weightsImproved = false;
            } else {
                weights = newWeights;
                weightsImproved = true;
                repeat = true;
            }

        } while (weightsImproved);

    }

    private double dotProduct(List<Double> vector) {
        double dotProduct = 0;
        for (int i = 0; i < vector.size(); i++) {
            dotProduct += weights.get(i) * vector.get(i);
        }
        return dotProduct;
    }

    private List<Double> deltaRule(List<Double> vector, int realOutput, int calculatedOutput) {
        List<Double> tmpVector = new ArrayList<>();
        List<Double> newWeights = new ArrayList<>();
        double tmp = (realOutput - calculatedOutput) * alpha;
        for (int i = 0; i < vector.size(); i++) {
            tmpVector.add(vector.get(i) * tmp);
        }
        for (int i = 0; i < tmpVector.size(); i++) {
            newWeights.add(weights.get(i) + tmpVector.get(i));
        }
        return newWeights;
    }

    private void printInputResults(double dotProduct, Iris iris, String class1, String class2){
        dotProduct = Math.floor(dotProduct * 100) / 100;
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("\t\t  VECTOR:\t\t\t\t\t\t\t\t\t\tWEIGHTS:");
        printList(iris.getAttributes());
        System.out.print("\t\tx\t\t");
        printList(weights);
        System.out.println();
        System.out.println();
        System.out.print("dot product = " + dotProduct);
        if (dotProduct >= 0) System.out.println(" >= 0, y = 1");
        else System.out.println(" < 0, y = 0");
        if (dotProduct >= 0) {
            System.out.println("PREDICTION: " + class1);
        } else {
            System.out.println("PREDICTION: " + class2);
        }
        System.out.println("---------------------------------------------------------------------------------------");
    }

    private void printTestingResults(Iris iris, double dotProduct, int realOutput, int calculatedOutput) {
        dotProduct = Math.floor(dotProduct * 100) / 100;
        System.out.println("\t\t  VECTOR:\t\t\t\t\t\t\t\t\t\tWEIGHTS:");
        printList(iris.getAttributes());
        System.out.print("\t\tx\t\t");
        printList(weights);
        System.out.println();
        System.out.println();
        System.out.print("spec: " + iris.getSpec() + ", d = ");
        if (iris.getSpec().equals("Iris-setosa")) System.out.println(1);
        else System.out.println(0);
        System.out.print("dot product = " + dotProduct);
        if (dotProduct >= 0) System.out.println(" >= 0, y = 1");
        else System.out.println(" < 0, y = 0");
        System.out.println("diff = d - y = " + realOutput + " - " + calculatedOutput + " = " + (realOutput - calculatedOutput));
        if ((realOutput - calculatedOutput) == 0)
            System.out.println("CORRECT PREDICTION");
        else
            System.out.println("WRONG PREDICTION");
        System.out.println("---------------------------------------------------------------------------------------");
    }

    private void printTrainingResults(Iris iris, double dotProduct, List<Double> newWeights) {
        dotProduct = Math.floor(dotProduct * 100) / 100;
        System.out.println("\t\t  VECTOR:\t\t\t\t\t\t\t\t\t\tWEIGHTS:");
        printList(iris.getAttributes());
        System.out.print("\t\tx\t\t");
        printList(weights);
        System.out.println();
        System.out.println();
        System.out.print("spec: " + iris.getSpec() + ", d = ");
        if (iris.getSpec().equals("Iris-setosa")) System.out.println(1);
        else System.out.println(0);
        System.out.print("dot product = " + dotProduct);
        if (dotProduct >= 0) System.out.println(" >= 0, y = 1");
        else System.out.println(" < 0, y = 0");
        System.out.print("W': ");
        printList(newWeights);
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------");
    }

    private void printList(List<Double> values) {
        System.out.print("[ ");
        for (int i = 0; i < values.size(); i++) {
            System.out.print(Math.floor(values.get(i) * 100) / 100);
            if (i != values.size() - 1) System.out.print(", ");
        }
        System.out.print(" ]");
    }

    private void cleanCache() {
        accuracy = 0d;
        totalAttemps = 0d;
        positiveAttemps = 0d;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public double getAlpha() {
        return alpha;
    }

    public boolean isWeightsImproved() {
        return weightsImproved;
    }

    public boolean isRepeat() {
        return repeat;
    }
}