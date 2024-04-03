import java.util.Arrays;
import java.util.List;

public class Iris {
    private Double sepal_length;
    private Double sepal_width;
    private Double petal_length;
    private Double petal_width;
    private List<Double> attributes;
    private String spec;
    private int realOutput;

    public Iris(Double sepal_length, Double sepal_width, Double petal_length, Double petal_width, String spec) {
        this.sepal_length = sepal_length;
        this.sepal_width = sepal_width;
        this.petal_length = petal_length;
        this.petal_width = petal_width;
        this.attributes = Arrays.asList(sepal_length, sepal_width, petal_length, petal_width, 0d);
        this.spec = spec;
        if(spec.equals("Iris-setosa")) realOutput = 1;
        else realOutput = 0;
    }

    @Override
    public String toString() {
        return "[" + sepal_length + ", " + sepal_width + ", " + petal_length + ", " + petal_width + ", " + spec + "]";
    }
    public List<Double> getAttributes() {
        return attributes;
    }

    public String getSpec() {
        return spec;
    }

    public int getRealOutput() {
        return realOutput;
    }
}
