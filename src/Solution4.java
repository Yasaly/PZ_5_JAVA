import java.util.function.Function;

public class Solution4 {
    public static void main(String[] args) {

        double a = 0.09;
        double b = 0.96;
        double len = b - a;

        Function<Point, Double> phi = P -> Math.sin(P.x());

        Function<Point, Double> F = P -> -Math.cos(P.x());

        Point Begin = new Point(a, 0, 0);
        Point End   = new Point(b, 0, 0);

        double I_true = F.apply(End) - F.apply(Begin);
        System.out.printf("I_true = %.15e%n%n", I_true);

        int N  = 5;
        int N2 = 2 * N;
        int N4 = 4 * N;

        double h = len / N;
        double h2 = len / N2;
        double h4 = len / N4;

        Integration_Scheme_Interval gauss2 =
                new Integration_Scheme_Interval(
                        Integration_Scheme.Integration_Scheme_Type.Gauss2);

        analyzeScheme("Гаусс-2", gauss2, Begin, End, phi, I_true,
                N, N2, N4, h, h2, h4);

        Integration_Scheme_Interval trapezoid =
                new Integration_Scheme_Interval(
                        Integration_Scheme.Integration_Scheme_Type.Trapezoid);

        analyzeScheme("метод трапеций", trapezoid, Begin, End, phi, I_true,
                N, N2, N4, h, h2, h4);
    }

    private static void analyzeScheme(
            String name,
            Integration_Scheme_Interval scheme,
            Point Begin,
            Point End,
            Function<Point, Double> phi,
            double I_true,
            int N, int N2, int N4,
            double h, double h2, double h4) {

        double Ih  = scheme.Calculate_Integral(Begin, End, N,  phi);
        double Ih2 = scheme.Calculate_Integral(Begin, End, N2, phi);
        double Ih4 = scheme.Calculate_Integral(Begin, End, N4, phi);

        double Eh  = Math.abs(I_true - Ih);
        double Eh2 = Math.abs(I_true - Ih2);
        double Eh4 = Math.abs(I_true - Ih4);

        double p12 = Eh / Eh2;
        double p23 = Eh2 / Eh4;

        System.out.println("Схема: " + name);
        System.out.printf("  h = %.6e,  |I-Ih|  = %.10e%n", h,  Eh);
        System.out.printf("  h/2 = %.6e,  |I-Ih2| = %.10e%n", h2, Eh2);
        System.out.printf("  h/4 = %.6e,  |I-Ih4| = %.10e%n", h4, Eh4);
        System.out.printf("  h -> h/2 ≈ %.2f%n", p12);
        System.out.printf("  h/2 -> h/4 ≈ %.2f%n%n", p23);
    }
}
