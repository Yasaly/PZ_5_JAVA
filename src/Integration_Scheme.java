import java.util.ArrayList;
import java.util.List;

public class Integration_Scheme {
    protected List<Point> Points = new ArrayList<>();
    protected List<Double> Weight = new ArrayList<>();

    public enum Integration_Scheme_Type {
        Gauss2,
        Trapezoid
    }
}
