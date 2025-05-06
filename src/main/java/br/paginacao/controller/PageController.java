package br.paginacao.controller;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.beans.value.ChangeListener;

import br.paginacao.common.SimulationArgs;
import br.paginacao.exemple.Exemples;
import br.paginacao.records.SimulationResults;
import br.paginacao.algorithms.FIFO;
import br.paginacao.algorithms.LRU;
import br.paginacao.algorithms.NFU;
import br.paginacao.algorithms.Clock;

public class PageController {

    @FXML
    private TextField tfMemorySize;

    @FXML
    private TextField tfPagesIdQueue;

    @FXML
    private TextField tfMemoryinitialState;

    @FXML
    private TextField tfClockInterruptionCount;

    @FXML
    private Label lbFifoFaultsResult;

    @FXML
    private Label lbFifoDurationResult;

    @FXML
    private Label lbLruFaultsResult;

    @FXML
    private Label lbLruDurationResult;

    @FXML
    private Label lbNfuFaultsResult;

    @FXML
    private Label lbNfuDurationResult;

    @FXML
    private Label lbClockFaultsResult;

    @FXML
    private Label lbClockDurationResult;

    @FXML
    private Button simular;

    @FXML
    private BarChart<String, Number> chart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private final String[] colorList = { "#1f77b4", "#ff7f0e", "#2ca02c", "#d62728" };

    @FXML
    public void initialize() {
        tfMemorySize.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tfMemorySize.setText(formatToNumber(oldValue, newValue));
            }
        });

        tfClockInterruptionCount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                tfClockInterruptionCount.setText(formatToNumber(oldValue, newValue));
            }
        });

        chart.setLegendVisible(false);

        xAxis.setLabel("Algoritmo");
        ObservableList<String> algoritmos = FXCollections.observableArrayList("FIFO", "LRU", "NFU", "Segunda Chance");
        xAxis.setCategories(algoritmos);

        yAxis.setLabel("Faltas");
        yAxis.setLowerBound(0);
        yAxis.setTickUnit(1);
    }

    private String formatToNumber(String oldText, String newText) {
        if (newText == null || newText.isEmpty()) {
            return "";
        }
        try {
            return "" + Integer.parseInt(newText);
        } catch (NumberFormatException e) {
            return oldText;
        }
    }

    public void handleAction() {
        System.out.println("11111111111111111111");
    }

    private void fillWithTestCase(String caseId) {
        SimulationArgs args;
        switch (caseId.toLowerCase()) {
            case "caso 1":
                args = Exemples.test01;
                break;
            case "caso 2":
                args = Exemples.test02;
                break;
            case "caso 3":
                args = Exemples.test03;
                break;

            default:
                System.out.println("Case ID: " + caseId + " não bate com casos padrões.");
                return;
        }

        if (args != null) {
            tfMemorySize.setText("" + args.getMemorySize());
            tfPagesIdQueue.setText(String.join(" ", args.getPagesIdsQueue()));
            tfMemoryinitialState.setText(String.join(" ", args.getMemoryInitialState()));
            tfClockInterruptionCount.setText("" + args.getClockInterruptTime());
        }
    }

    public void handleCase1() {
        fillWithTestCase("caso 1");
    }

    public void handleCase2() {
        fillWithTestCase("caso 2");
    }

    public void handleCase3() {
        fillWithTestCase("caso 3");
    }

    public void simulate() {
        SimulationArgs args = getSimulationArgs();

        if (handleErros(args)) {
            return;
        }

        FIFO fifo = new FIFO();
        SimulationResults fifoResults = fifo.run(args);
        lbFifoFaultsResult.setText("" + fifoResults.faults());
        lbFifoDurationResult.setText("" + fifoResults.duration() + " mls");

        LRU lru = new LRU();
        SimulationResults lruResults = lru.run(args);
        lbLruFaultsResult.setText("" + lruResults.faults());
        lbLruDurationResult.setText("" + lruResults.duration() + " mls");

        NFU nfu = new NFU();
        SimulationResults nfuResults = nfu.run(args);
        lbNfuFaultsResult.setText("" + nfuResults.faults());
        lbNfuDurationResult.setText("" + nfuResults.duration() + " mls");

        Clock clock = new Clock();
        SimulationResults clockResults = clock.run(args);
        lbClockFaultsResult.setText("" + clockResults.faults());
        lbClockDurationResult.setText("" + clockResults.duration() + " mls");

        handleChart(fifoResults, lruResults, nfuResults, clockResults);
    }

    public void handleChart(SimulationResults fifo, SimulationResults lru, SimulationResults nfu,
            SimulationResults clock) {

        ObservableList<XYChart.Data<String, Number>> faultsData = FXCollections.observableArrayList();
        faultsData.add(new XYChart.Data<>("FIFO", fifo.faults()));
        faultsData.add(new XYChart.Data<>("LRU", lru.faults()));
        faultsData.add(new XYChart.Data<>("NFU", nfu.faults()));
        faultsData.add(new XYChart.Data<>("Segunda Chance", clock.faults()));

        XYChart.Series<String, Number> serieFaults = new XYChart.Series<>();
        serieFaults.setName("Faltas");
        serieFaults.setData(faultsData);

        chart.getData().clear();
        chart.getData().add(serieFaults);

        for (int i = 0; i < serieFaults.getData().size(); i++) {
            XYChart.Data<String, Number> data = serieFaults.getData().get(i);
            Tooltip tooltip = new Tooltip();
            tooltip.setText(data.getXValue() + ": " + data.getYValue() + " faltas");
            Tooltip.install(data.getNode(), tooltip);

            String cor = colorList[i % colorList.length];
            data.getNode().setStyle("-fx-background-color: " + cor + ";");
        }
    }

    private void showErrorMsg(String title, String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private SimulationArgs getSimulationArgs() {
        String memorySizeStr = tfMemorySize.getText();
        String clockTimerStr = tfClockInterruptionCount.getText();
        String pagesQueue = tfPagesIdQueue.getText();
        String memoryInitalState = tfMemoryinitialState.getText();
        int memorySize = memorySizeStr.equals("") ? 0 : Integer.parseInt(memorySizeStr);
        int clockTimer = clockTimerStr.equals("") ? 0 : Integer.parseInt(clockTimerStr);

        return new SimulationArgs(memorySize, clockTimer, pagesQueue, memoryInitalState);
    }

    private boolean handleErros(SimulationArgs args) {
        if (args == null) {
            System.out.println("Parâmetos de simulação vaizos");
            return true;
        }

        if (args.getMemorySize() < args.getMemoryInitialState().size()) {
            showErrorMsg("Erro", "O estado inicial da mémoria é maior que a capacidade da memória");
            return true;
        }

        if (args.getMemorySize() == 0) {
            showErrorMsg("Erro", "Informe o tamanho da memória");
            return true;
        }

        if (args.getPagesIdsQueue().size() == 0) {
            showErrorMsg("Erro", "Informe pelo menos uma página");
            return true;
        }

        return false;
    }
}