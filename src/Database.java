import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private List<Iris> trainingData;
    private List<Iris> testingData;
    private List<Iris> userData;
    private List<Iris> inputData;

    public Database() {
        this.trainingData = new ArrayList<>();
        this.testingData = new ArrayList<>();
        this.userData = new ArrayList<>();
        this.inputData = new ArrayList<>();
    }

    private void loadData(String filename, List<Iris> data, String class1, String class2){
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line = "";
            while((line = br.readLine()) != null){
                if(Character.isDigit(line.charAt(0))){
                    String[] split = line.split(",");
                    Double sepal_length = Double.parseDouble(split[0]);
                    Double sepal_width = Double.parseDouble(split[1]);
                    Double petal_length = Double.parseDouble(split[2]);
                    Double petal_width = Double.parseDouble(split[3]);
                    String spec = split[4];
                    if(class1.equals(spec) || class2.equals(spec))
                        data.add(new Iris(sepal_length, sepal_width, petal_length, petal_width, spec));
                }
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadMainData(String class1, String class2){
        loadData("IRIS.csv", trainingData, class1, class2);
        loadData("USER.csv", userData, class1, class2);
        splitData(trainingData);
    }
    private void splitData(List<Iris> irises){
        int dataCounter = 0;
        for (Iris iris : irises)
            dataCounter++;
        dataCounter *= 0.33;

        for (int i = 0; i < dataCounter; i++) {
            int index = (int) (Math.random() * irises.size());
            testingData.add(irises.get(index));
            irises.remove(index);
        }
    }

    public void loadInputData(Double sepal_length, Double sepal_width, Double petal_length, Double petal_width){
        inputData.add(new Iris(sepal_length, sepal_width, petal_length, petal_width, null));
    }

    public List<Iris> getTrainingData() {
        return trainingData;
    }

    public List<Iris> getTestingData() {
        return testingData;
    }

    public List<Iris> getUserData() {
        return userData;
    }

    public List<Iris> getInputData() {
        return inputData;
    }
}
