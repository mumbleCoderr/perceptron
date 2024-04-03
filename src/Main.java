import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        Perceptron perceptron = new Perceptron(Arrays.asList(0.2,0.2,0.2,0.2,-1.), 0.3);
        Trainer trainer = new Trainer(perceptron, db);

        Scanner scanner = new Scanner(System.in);
        db.loadMainData("Iris-setosa", "Iris-virginica");
        System.out.println("do you want to predict spec from your inserted data? [y/n]");
        String choose = scanner.nextLine();
        boolean isRunning = false;
        while (!choose.equals("y") && !choose.equals("n")) {
            System.out.println("unknown command");
            System.out.println("do you want to predict spec from your inserted data? [y/n]");
            choose = scanner.nextLine();
        }

        trainer.train();

        if (choose.equals("y")) isRunning = true;

        while (isRunning) {
            System.out.println("insert sepal_length");
            Double sepal_length = scanner.nextDouble();
            System.out.println("insert sepal_width");
            Double sepal_width = scanner.nextDouble();
            System.out.println("insert petal_length");
            Double petal_length = scanner.nextDouble();
            System.out.println("insert petal_width");
            Double petal_width = scanner.nextDouble();
            db.loadInputData(sepal_length, sepal_width, petal_length, petal_width);
            System.out.println("do you want to predict more species? [y/n]");
            choose = scanner.nextLine();
            while(!choose.equals("y") && !choose.equals("n")){
                System.out.println("unknown command");
                System.out.println("do you want to predict spec from your inserted data? [y/n]");
                choose = scanner.nextLine();
            }
            if (choose.equals("n"))
                isRunning = false;
        }
    }
}