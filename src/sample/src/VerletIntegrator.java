package sample.src;

public class VerletIntegrator {
    // pendulum - obiekt klasy ktora implementuje Calculate Acceleration; rożne rodzaje obiektów dynamicznych
    // VerletIntegrator verletIntegrator.intergrate(pendulum, analyzer, 0,6,1,0)
    private double krok;

    public VerletIntegrator(double krok) {
        this.krok = krok;
    }

    public double getKrok() {
        return krok;
    }

    public void setKrok(double krok) {
        this.krok = krok;
    }

    public void intergrate(CalculateAcceleration ca, ODEUpdate analyzer, double tStart, double tStop, double x0, double v0) {
        analyzer.update(tStart, x0, v0);
        double a0 = ca.a(x0);
        for (double t = tStart + krok; t <= tStop; t = t + krok) {
            double x = x0 + krok * (v0 + krok * a0 / 2);
            double a = ca.a(x);
            double v = v0 + krok * (a0+a) / 2;

            analyzer.update(t, x, v);
            x0 = x;
            v0 = v;
            a0 = a;
        }

    }



    public void okres() {

    }


   }
