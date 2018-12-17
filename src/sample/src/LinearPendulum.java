package sample.src;

public class LinearPendulum implements CalculateAcceleration{

    @Override
    public double a(double x) {
        return -x;
    }


}
