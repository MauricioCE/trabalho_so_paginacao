package br.paginacao.common;

import java.util.ArrayList;

import br.paginacao.utils.Utils;

public class SimulationArgs {
    private final int memorySize;
    private final int clockInterruptTime;
    private final ArrayList<String> pagesIdsQueue;
    private final ArrayList<String> memoryInitialState;

    public SimulationArgs(
            int memorySize,
            int clockInterruptTime,
            String pagesIdsQueueStr,
            String memoryInitialStateStr) {

        this.memorySize = memorySize;
        this.clockInterruptTime = clockInterruptTime;
        this.pagesIdsQueue = Utils.splitStringSequence(pagesIdsQueueStr);
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

    public int getClockInterruptTime() {
        return clockInterruptTime;
    }

    public ArrayList<String> getMemoryInitialState() {
        return new ArrayList<>(this.memoryInitialState);
    }

    public ArrayList<String> getPagesIdsQueue() {
        return new ArrayList<>(this.pagesIdsQueue);
    }

}
