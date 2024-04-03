import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Perceptron {
    private List<Double> weights;
    private double alpha;
    private boolean weightsImproved;
    private int calculatedOutput;
    private boolean repeat;

    public Perceptron(List<Double> weights, double alpha){
        this.weights = weights;
        this.alpha = alpha;
        this.weightsImproved = true;
    }

    public void learn(Iris iris) {
        int realOutput = iris.getRealOutput();
        List<Double> vector = iris.getAttributes();
        repeat = false;

        do{
            double dotProduct = dotProduct(vector);
            if (dotProduct >= 0) calculatedOutput = 1;
            else calculatedOutput = 0;
            List<Double> newWeights = deltaRule(vector, realOutput, calculatedOutput);
            printResults(iris, dotProduct, newWeights);
            if (weights.equals(newWeights)) {
                weightsImproved = false;
            }
            else {
                weights = newWeights;
                weightsImproved = true;
                repeat = true;
            }

        }while(weightsImproved);

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

    private void printResults(Iris iris, double dotProduct, List<Double> newWeights){
        dotProduct = Math.floor(dotProduct * 10) / 10;
        System.out.println("\t\t  VECTOR:\t\t\t\t\t\t\t\tWEIGHTS:");
        printList(iris.getAttributes());
        System.out.print("\t\tx\t  ");
        printList(weights);
        System.out.println();
        System.out.println();
        System.out.print("spec: " + iris.getSpec() + ", d = ");
        if (iris.getSpec().equals("Iris-setosa")) System.out.println(1);
        else System.out.println(0);
        System.out.print("dot product = " + dotProduct);
        if(dotProduct >= 0) System.out.println(" >= 0, y = 1");
        else System.out.println(" < 0, y = 0");
        System.out.print("W': ");
        printList(newWeights);
        System.out.println();
        System.out.println("---------------------------------------------------------------------------------------");
    }

    private void printList(List<Double> values){
        System.out.print("[ ");
        for (int i = 0; i < values.size(); i++) {
            System.out.print(Math.floor(values.get(i) * 10) / 10);
            if (i != values.size()-1) System.out.print(", ");
        }
        System.out.print(" ]");
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