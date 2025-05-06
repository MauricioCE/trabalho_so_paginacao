package br.paginacao.common;

import java.util.ArrayList;

import br.paginacao.utils.Utils;

public class SimulationArgs {
    private final int memorySize;
    private final int pagesQueueSize;
    private final int pagesCount;
    private final int clockInterruptTime;
    private final ArrayList<String> pagesIdsList;
    private final ArrayList<String> pagesIdsQueue;
    private final ArrayList<String> operationsQueue;
    private final ArrayList<String> memoryInitialState;

    public SimulationArgs(
            int memorySize,
            int pagesQueueSize,
            int pagesCount,
            int clockInterruptTime,
            String pagesIdStr,
            String pagesIdsQueueStr,
            String operationsQueueStr,
            String memoryInitialStateStr) {

        this.memorySize = memorySize;
        this.pagesQueueSize = pagesQueueSize;
        this.pagesCount = pagesCount;
        this.clockInterruptTime = clockInterruptTime;
        this.pagesIdsList = Utils.splitStringSequence(pagesIdStr);
        this.pagesIdsQueue = Utils.splitStringSequence(pagesIdsQueueStr);
        this.operationsQueue = Utils.splitStringSequence(operationsQueueStr);
        this.memoryInitialState = new ArrayList<>();

        Utils.splitStringSequence(memoryInitialStateStr).forEach(page -> {
            page = page.toUpperCase();
            if (!page.equals("0") && !page.equals("")) {
                this.memoryInitialState.add(page);
            }
        });
    }

    public int getMemorySize() {
        return memorySize;
    }

    public int getPagesQueueSize() {
        return pagesQueueSize;
    }

    public int getPagesCount() {
        return pagesCount;
    }

    public int getClockInterruptTime() {
        return clockInterruptTime;
    }

    public ArrayList<String> getMemoryInitialState() {
        return new ArrayList<>(this.memoryInitialState);
    }

    public ArrayList<String> getPagesIdsList() {
        return new ArrayList<>(this.pagesIdsList);
    }

    public ArrayList<String> getPagesIdsQueue() {
        return new ArrayList<>(this.pagesIdsQueue);
    }

    public ArrayList<String> getOperationsQueue() {
        return new ArrayList<>(this.operationsQueue);
    }

}
