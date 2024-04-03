import java.util.List;

public class Trainer {
    private Perceptron perceptron;
    private Database db;
    private boolean repeat;

    public Trainer(Perceptron perceptron, Database db) {
        this.perceptron = perceptron;
        this.db = db;
    }

    public void train() {
        List<Iris> trainingData = db.getTrainingData();

        do {
            this.repeat = false;
            for (Iris iris : trainingData) {
                perceptron.learn(iris);
                if (perceptron.isRepeat()) this.repeat = true;
            }
        } while (this.repeat);
    }


}
