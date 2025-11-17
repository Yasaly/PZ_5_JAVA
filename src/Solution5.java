import java.util.function.Function;

public class Solution5 {
    public static void main(String[] args) {

        double a = 0.09;
        double b = 0.96;

        Function<Point, Double> phi = P -> Math.sin(P.x());

        Function<Point, Double> F = P -> -Math.cos(P.x());

        Point Begin = new Point(a, 0, 0);
        Point End   = new Point(b, 0, 0);

        double I_true = F.apply(End) - F.apply(Begin);

        int N  = 10;
        int N2 = 2 * N;
        int N4 = 4 * N;
        int N8 = 8 * N;

        double len = b - a;
        double h   = len / N;
        double h2  = len / N2;
        double h4 = len / N4;


        Integration_Scheme_Interval gauss2 =
                new Integration_Scheme_Interval(
                        Integration_Scheme.Integration_Scheme_Type.Gauss2);

        analyzeScheme("Гаусс-2:", gauss2, Begin, End, phi, I_true,
                N, N2, N4, N8, h, h2, h4, 4);


        Integration_Scheme_Interval trapezoid =
                new Integration_Scheme_Interval(
                        Integration_Scheme.Integration_Scheme_Type.Trapezoid);

        analyzeScheme("метод трапеции:", trapezoid, Begin, End, phi, I_true,
                N, N2, N4, N8, h, h2, h4, 2);
    }

    private static void analyzeScheme(
            String name,
            Integration_Scheme_Interval scheme,
            Point Begin,
            Point End,
            java.util.function.Function<Point, Double> phi,
            double I_true,
            int N, int N2, int N4, int N8,
            double h, double h2, double h4,
            int k) {


        double Ih   = scheme.Calculate_Integral(Begin, End, N,  phi);   // h
        double Ih2  = scheme.Calculate_Integral(Begin, End, N2, phi);   // h/2
        double Ih4  = scheme.Calculate_Integral(Begin, End, N4, phi);   // h/4
        double Ih8  = scheme.Calculate_Integral(Begin, End, N8, phi);   // h/8


        double Eh   = I_true - Ih;
        double Eh2  = I_true - Ih2;
        double Eh4  = I_true - Ih4;
        double Eh8  = I_true - Ih8;


        double ratio1 = Eh / Eh2;
        double corr1  = (Ih2 - Ih) / (Math.pow(2.0, k) - 1.0);
        double IR1    = Ih2 + corr1;
        double ER1    = I_true - IR1;


        double ratio2 = Eh2 / Eh4;
        double corr2  = (Ih4 - Ih2) / (Math.pow(2.0, k) - 1.0);
        double IR2    = Ih4 + corr2;
        double ER2    = I_true - IR2;


        double ratio3 = Eh4 / Eh8;
        double corr3  = (Ih8 - Ih4) / (Math.pow(2.0, k) - 1.0);
        double IR3    = Ih8 + corr3;
        double ER3    = I_true - IR3;

        System.out.println(name);
        System.out.printf(
                "%-10s\t%-18s\t%-18s\t%-18s\t%-18s\t%-24s%n",
                "h",
                "    I* - Ih",
                "    (I*-Ih)/(I*-Ih2)",
                "(Ih2-Ih)/(2^k-1)",
                "IR",
                "    I* - IR"
        );

        //h
        System.out.printf(
                "%10.6e\t%18.12e\t%18.12e\t%18.12e\t%18.16e\t%18.12e%n",
                h, Eh,  ratio1, corr1, IR1, ER1
        );

        //h/2
        System.out.printf(
                "%10.6e\t%18.12e\t%18.12e\t%18.12e\t%18.16e\t%18.12e%n",
                h2, Eh2, ratio2, corr2, IR2, ER2
        );

        //h/4
        System.out.printf(
                "%10.6e\t%18.12e\t%18.12e\t%18.12e\t%18.16e\t%18.12e%n",
                h4, Eh4, ratio3, corr3, IR3, ER3
        );
        System.out.println();
    }



}
