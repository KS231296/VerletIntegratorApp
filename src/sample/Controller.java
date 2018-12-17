package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sample.src.Analyzer;
import sample.src.Pendulum;
import sample.src.VerletIntegrator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    private double krok;
    private double tStart;
    private double tStop;
    private double x0;
    private double v0;

    @FXML
    private LineChart<Number, Number> xChart;

    @FXML
    private LineChart<Number, Number> vChart;

    @FXML
    private LineChart<Number, Number> eChart;

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
        }catch (IllegalArgumentException e) {
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

        xChart.getData().clear();
        vChart.getData().clear();
        eChart.getData().clear();
        ArrayList energyValues = analyzer.energyTest(pendulum);
        for (int i = 0; i < analyzer.gettValues().size(); i++) {
            vVal.getData().add(new XYChart.Data<>((double) analyzer.gettValues().get(i), (double) analyzer.getvValues().get(i)));
            xVal.getData().add(new XYChart.Data<>((double) analyzer.gettValues().get(i), (double) analyzer.getxValues().get(i)));
            energy.getData().add(new XYChart.Data<>((double) analyzer.gettValues().get(i), (double) energyValues.get(i)));
        }
        vChart.getData().add(vVal);
        xChart.getData().add(xVal);
        eChart.getData().add(energy);

    }

}
