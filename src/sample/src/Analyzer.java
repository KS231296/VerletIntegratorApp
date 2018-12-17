package sample.src;

import java.util.ArrayList;

public class Analyzer implements ODEUpdate {
    private ArrayList tValues;
    private ArrayList xValues;
    private ArrayList vValues;

    public ArrayList gettValues() {
        return tValues;
    }

    public ArrayList getxValues() {
        return xValues;
    }

    public ArrayList getvValues() {
        return vValues;
    }

    public Analyzer() {
        this.tValues = new ArrayList();
        this.xValues = new ArrayList();
        this.vValues = new ArrayList();
    }

    @Override
    public void update(double t, double x, double v) {
        tValues.add(t);
        xValues.add(x);
        vValues.add(v);
    }

    public ArrayList<Double> energyTest(CalculateEnergy object) {
        ArrayList<Double> energy = new ArrayList();
        for (int i = 0; i<tValues.size();i++){
            energy.add(object.energy((Double)vValues.get(i), (Double) xValues.get(i))[2]);
        }
return energy;
    }
}
