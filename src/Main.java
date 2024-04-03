import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Database db = new Database();
        Perceptron perceptron = new Perceptron(Arrays.asList(0.2, 0.2, 0.2, 0.2, 0d), 0.3);
        Trainer trainer = new Trainer(perceptron, db);
        Scanner scanner = new Scanner(System.in);
        String class1 = "Iris-setosa";
        String class2 = "Iris-virginica";

        db.loadMainData(class1, class2);
        System.out.println("do you want to predict spec from your inserted data? [y/n]");
        String choose = scanner.nextLine();
        boolean isRunning = false;
        while (!choose.equals("y") && !choose.equals("n")) {
            System.out.println("unknown command");
            System.out.println("do you want to predict spec from your inserted data? [y/n]");
            choose = scanner.nextLine();
        }

        trainer.train();
        if (choose.equals("n")) {
            perceptron.computeTestingData(db.getTestingData());
            perceptron.computeUserData(db.getUserData());
        }

        if (choose.equals("y")) isRunning = true;
        while (isRunning) {
            System.out.println("=======================================================================================");
            System.out.println("\t\t\t\t\t\t\tCOMPUTING INPUT DATA");
            System.out.println("=======================================================================================");
            Double sepal_length;
            Double sepal_width;
            Double petal_length;
            Double petal_width;
            do {
                try {
                    System.out.println("insert sepal_length");
                    sepal_length = scanner.nextDouble();
                    System.out.println("insert sepal_width");
                    sepal_width = scanner.nextDouble();
                    System.out.println("insert petal_length");
                    petal_length = scanner.nextDouble();
                    System.out.println("insert petal_width");
                    petal_width = scanner.nextDouble();
                } catch (InputMismatchException e) {
                    System.err.println("INCORRECT NUMBER FORMAT");
                    scanner.nextLine();
                    continue;
                }
                break;
            } while (true);

            db.loadInputData(sepal_length, sepal_width, petal_length, petal_width);
            perceptron.computeInputData(db.getInputData(), class1, class2);
            System.out.println();
            System.out.println("do you want to predict more species? [y/n]");
            choose = scanner.next();
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