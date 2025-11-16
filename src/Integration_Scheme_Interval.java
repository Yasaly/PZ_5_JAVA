import java.util.function.Function;

public class Integration_Scheme_Interval extends Integration_Scheme {

    public Integration_Scheme_Interval(Integration_Scheme_Type Type) {
        switch (Type) {
            case Gauss2:
                Points.add(new Point(-1.0 / Math.sqrt(3.0), 0, 0));
                Points.add(new Point( 1.0 / Math.sqrt(3.0), 0, 0));
                Weight.add(1.0);
                Weight.add(1.0);
                break;

            case Trapezoid:
                Points.add(new Point(-1.0, 0, 0));
                Points.add(new Point( 1.0, 0, 0));
                Weight.add(1.0);
                Weight.add(1.0);
                break;
        }
    }

    public double Calculate_Integral(
            Point Begin,
            Point End,
            int Number_Segments,
            Function<Point, Double> Func) {

        double Result = 0.0;
        double h = (End.x() - Begin.x()) / Number_Segments;

        for (int i = 0; i < Number_Segments; i++) {
            double X0 = Begin.x() + i * h;

            for (int k = 0; k < Points.size(); k++) {
                double xi = Points.get(k).x();
                double xPhys = X0 + (1.0 + xi) * h / 2.0;
                Point P = new Point(xPhys, 0, 0);

                Result += Weight.get(k) * Func.apply(P);
            }
        }

        return Result * (h / 2.0);
    }
}
