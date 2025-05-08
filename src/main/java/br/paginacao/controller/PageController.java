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
import javafx.application.Platform;

import br.paginacao.common.SimulationArgs;
import br.paginacao.records.SimulationResults;
import br.paginacao.test_cases.TestCases;
import br.paginacao.utils.Utils;
import br.paginacao.algorithms.FIFO;
import br.paginacao.algorithms.LRU;
import br.paginacao.algorithms.NFU;
import br.paginacao.algorithms.Clock;

/**
 * Controller for the page replacement simulation view.
 * Handles user input, runs simulations, and updates the UI with results.
 */
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
    private Label lbPagesCount;

    @FXML
    private Button btSimulate;

    @FXML
    private BarChart<String, Number> chart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    private final String[] colorList = { "#62b095", "#e29a6f", "#c0dae1", "#c87b77" };

    /**
     * Initializes the controller after the FXML file has been loaded.
     * Sets up listeners for input validation and configures the bar chart.
     */
    @FXML
    public void initialize() {

        tfMemorySize.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.equals("") && !Utils.isValidInteger(newValue)) {
                    tfMemorySize.setText(oldValue);
                }
            }
        });

        tfClockInterruptionCount.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.equals("") && !Utils.isValidInteger(newValue)) {
                    tfClockInterruptionCount.setText(oldValue);
                }
            }
        });

        tfPagesIdQueue.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                lbPagesCount.setText(formatInt(newValue.split(" ").length));
            }
        });

        // Chart initialization
        chart.setLegendVisible(false);
        xAxis.setLabel("Algoritmo");
        ObservableList<String> algoritmos = FXCollections.observableArrayList("FIFO", "LRU", "NFU", "Segunda Chance");
        xAxis.setCategories(algoritmos);
        yAxis.setLabel("Faltas");
        yAxis.setLowerBound(0);
        yAxis.setTickUnit(10);
    }

    /**
     * Fills all TextFields with the data of the selected test case.
     *
     * @param caseId a String that represent a valid test case (caso 1, caso 2, caso
     *               3, caso 4)
     */
    private void fillWithTestCase(String caseId) {
        SimulationArgs args;
        switch (caseId.toLowerCase()) {
            case "caso 1":
                args = TestCases.case01;
                break;
            case "caso 2":
                args = TestCases.case02;
                break;
            case "caso 3":
                args = TestCases.case03;
                break;
            case "caso 4":
                args = TestCases.randomCase(1000);
                break;
            case "caso 5":
                args = TestCases.randomCase(10000);
                break;
            case "caso 6":
                args = TestCases.randomCase(100000);
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

    /**
     * Fills the chart with data from the results of the simulation.
     *
     * @param fifoResults  results from FIFO simulation
     * @param lruResults   results from LRU simulation
     * @param nfuResults   results from NFU simulation
     * @param clockResults results from Clock simulation
     */
    private void fillChartData(
            SimulationResults fifoResults,
            SimulationResults lruResults,
            SimulationResults nfuResults,
            SimulationResults clockResults) {

        ObservableList<XYChart.Data<String, Number>> faultsData = FXCollections.observableArrayList();
        faultsData.add(new XYChart.Data<>("FIFO", fifoResults.faults()));
        faultsData.add(new XYChart.Data<>("LRU", lruResults.faults()));
        faultsData.add(new XYChart.Data<>("NFU", nfuResults.faults()));
        faultsData.add(new XYChart.Data<>("Segunda Chance", clockResults.faults()));

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

    /**
     * Retrieves the simulation arguments from the input fields.
     *
     * @return a {@link SimulationArgs} object containing the user-provided
     *         simulation parameters.
     */
    private SimulationArgs getSimulationArgs() {
        String memorySizeStr = tfMemorySize.getText();
        String clockTimerStr = tfClockInterruptionCount.getText();
        String pagesQueue = tfPagesIdQueue.getText();
        String memoryInitalState = tfMemoryinitialState.getText();
        int memorySize = memorySizeStr.equals("") ? 0 : Integer.parseInt(memorySizeStr);
        int clockTimer = clockTimerStr.equals("") ? 0 : Integer.parseInt(clockTimerStr);

        return new SimulationArgs(memorySize, clockTimer, pagesQueue, memoryInitalState);
    }

    /**
     * Handles potential errors in the simulation arguments.
     * Displays an error message to the user if any invalid input is detected.
     *
     * @param args the {@link SimulationArgs} object to validate.
     * @return {@code true} if any error is found, {@code false} otherwise.
     */
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

    /**
     * Shows an error message dialog to the user.
     *
     * @param title the title of the error dialog.
     * @param msg   the message content of the error dialog.
     */
    private void showErrorMsg(String title, String msg) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private String formatInt(int numero) {
        return String.format("%,d", numero).replace(',', '.');
    }

    private String formatLong(long numero) {
        return String.format("%,d", numero).replace(',', '.');
    }

    // Events =====================================================================

    /**
     * Executes the page replacement simulations for FIFO, LRU, NFU, and Clock
     * algorithms, than updates the UI and chart with the results.
     */
    public void simulate() {
        SimulationArgs args = getSimulationArgs();

        if (handleErros(args)) {
            return;
        }

        btSimulate.setText("Aguarde...");
        btSimulate.setDisable(true);

        Thread thread = new Thread(() -> {
            FIFO fifo = new FIFO();
            SimulationResults fifoResults = fifo.run(args);

            LRU lru = new LRU();
            SimulationResults lruResults = lru.run(args);

            NFU nfu = new NFU();
            SimulationResults nfuResults = nfu.run(args);

            Clock clock = new Clock();
            SimulationResults clockResults = clock.run(args);

            Platform.runLater(() -> {
                lbFifoFaultsResult.setText(formatInt(fifoResults.faults()));
                lbFifoDurationResult.setText(formatLong(fifoResults.duration()) + " mls");
                lbLruFaultsResult.setText(formatInt(lruResults.faults()));
                lbLruDurationResult.setText(formatLong(lruResults.duration()) + " mls");
                lbNfuFaultsResult.setText(formatInt(nfuResults.faults()));
                lbNfuDurationResult.setText(formatLong(nfuResults.duration()) + " mls");
                lbClockFaultsResult.setText(formatInt(clockResults.faults()));
                lbClockDurationResult.setText(formatLong(clockResults.duration()) + " mls");
                fillChartData(fifoResults, lruResults, nfuResults, clockResults);

                btSimulate.setText("Simular");
                btSimulate.setDisable(false);
            });
        });

        thread.start();

    }

    /**
     * Handles the action for loading test case 1.
     * Calls {@link #fillWithTestCase(String)} with the case ID "caso 1".
     */
    public void handleCase1() {
        fillWithTestCase("caso 1");
    }

    /**
     * Handles the action for loading test case 2.
     * Calls {@link #fillWithTestCase(String)} with the case ID "caso 2".
     */
    public void handleCase2() {
        fillWithTestCase("caso 2");
    }

    /**
     * Handles the action for loading test case 3.
     * Calls {@link #fillWithTestCase(String)} with the case ID "caso 3".
     */
    public void handleCase3() {
        fillWithTestCase("caso 3");
    }

    /**
     * Handles the action for loading test case 4.
     * Calls {@link #fillWithTestCase(String)} with the case ID "caso 4".
     */
    public void handleCase4() {
        fillWithTestCase("caso 4");
    }

    /**
     * Handles the action for loading test case 5.
     * Calls {@link #fillWithTestCase(String)} with the case ID "caso 5".
     */
    public void handleCase5() {
        fillWithTestCase("caso 5");
    }

    /**
     * Handles the action for loading test case 6.
     * Calls {@link #fillWithTestCase(String)} with the case ID "caso 6".
     */
    public void handleCase6() {
        fillWithTestCase("caso 6");
    }

}