package sample.src;

import static java.lang.Math.cos;

public class Pendulum implements CalculateAcceleration, CalculateEnergy {

    @Override
    public double a(double x) {
        return -Math.sin(x);
    }

    @Override
    public double[] energy(double v, double x) {
        double eKin = 0.5 * v * v;
        double ePot = -cos(x);
        double eMech = 0.5 * v * v - cos(x);
        return new double[]{eKin, ePot, eMech};
    }

}
