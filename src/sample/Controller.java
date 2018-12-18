package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sample.src.Analyzer;
import sample.src.Pendulum;
import sample.src.VerletIntegrator;


import java.util.ArrayList;

import static java.lang.Math.abs;

public class Controller {

    private double krok;
    private double tStart;
    private double tStop;
    private double x0;
    private double v0;

    @FXML
    private Text txtOkres;


    @FXML
    private LineChart<Number, Number> xChart;

    @FXML
    private LineChart<Number, Number> vChart;

    @FXML
    private LineChart<Number, Number> eChart;

    @FXML
    private LineChart<Number, Number> chartFazowa;

    @FXML
    private TextField txtKrok;

    @FXML
    private TextField txtStart;

    @FXML
    private TextField txtStop;

    @FXML
    private TextField txtX0;

    @FXML
    private TextField txtV0;

    @FXML
    private Button btnStart;

    @FXML
    void start(ActionEvent event) {

        try {
            krok = Double.parseDouble(txtKrok.getText());
            tStart = Double.parseDouble(txtStart.getText());
            tStop = Double.parseDouble(txtStop.getText());
            x0 = Double.parseDouble(txtX0.getText());
            v0 = Double.parseDouble(txtV0.getText());
        } catch (IllegalArgumentException e) {
            new Alert(Alert.AlertType.ERROR, "Przynajmniej jedna z podanych wartości nie jest liczbą").showAndWait();
            return;
        }
        Analyzer analyzer = new Analyzer();
        Pendulum pendulum = new Pendulum();
        VerletIntegrator integrator = new VerletIntegrator(krok);

        integrator.intergrate(pendulum, analyzer, tStart, tStop, x0, v0);
        XYChart.Series<Number, Number> vVal = new XYChart.Series<>();
        XYChart.Series<Number, Number> xVal = new XYChart.Series<>();
        XYChart.Series<Number, Number> energy = new XYChart.Series<>();
        XYChart.Series<Number, Number> fazowa = new XYChart.Series<>();

        xChart.getData().clear();
        vChart.getData().clear();
        eChart.getData().clear();
        chartFazowa.getData().clear();

        ArrayList energyValues = analyzer.energyTest(pendulum);
        for (int i = 0; i < analyzer.gettValues().size(); i++) {
            vVal.getData().add(new XYChart.Data<>((double) analyzer.gettValues().get(i), (double) analyzer.getvValues().get(i)));
            xVal.getData().add(new XYChart.Data<>((double) analyzer.gettValues().get(i), (double) analyzer.getxValues().get(i)));
            energy.getData().add(new XYChart.Data<>((double) analyzer.gettValues().get(i), (double) energyValues.get(i)));
            fazowa.getData().add(new XYChart.Data<>((double) analyzer.getxValues().get(i), (double) analyzer.getvValues().get(i)));
        }
        vChart.getData().add(vVal);
        xChart.getData().add(xVal);
        eChart.getData().add(energy);
        chartFazowa.getData().add(fazowa);
        try {
            double period = calcPeriod(analyzer.getxValues(), analyzer.getvValues(), analyzer.gettValues());
            txtOkres.setText(String.format("%.3f", period));
        } catch (PeriodNotFoundException e) {
            txtOkres.setText("Nie znaleziono okresu");
        }


    }

    public double findEps(ArrayList list) {
        double diff = Math.abs((double) list.get(1) - (double) list.get(0));
        for (int i = 2; i < list.size(); i++) {
            double dif = Math.abs((double) list.get(i) - (double) list.get(i - 1));
            if (dif > diff) {
                diff = dif;
            }
        }
        return diff * 0.5;
    }

    public double calcPeriod(ArrayList x, ArrayList v, ArrayList t) throws PeriodNotFoundException {
        double epsX = findEps(x);
        double epsV = findEps(v);
        for (int i = 2; i < x.size(); i++) {
            if ((double) x.get(0) - epsX <= (double) x.get(i) && (double) x.get(i) <= (double) x.get(0) + epsX && (double) v.get(0) - epsV <= (double) v.get(i) && (double) v.get(i) <= (double) v.get(0) + epsV) {
                return (double) t.get(i);
            }
        }

        throw new PeriodNotFoundException("nie udało się znaleźć okresu");
    }

}
