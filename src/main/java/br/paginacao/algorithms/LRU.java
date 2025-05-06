package br.paginacao.algorithms;

import java.util.ArrayList;
import br.paginacao.common.ErrorLogs;
import br.paginacao.common.Memory;
import br.paginacao.common.SimulationArgs;
import br.paginacao.interfaces.IAlgorithm;
import br.paginacao.records.SimulationResults;

public class LRU implements IAlgorithm {

    public SimulationResults run(SimulationArgs args) {
        Memory memory = new Memory(args.getMemorySize(), args.getMemoryInitialState());
        ArrayList<String> lruPagesList = args.getMemoryInitialState();
        ArrayList<String> pagesIdQueue = args.getPagesIdsQueue();
        ArrayList<String> operationQueue = args.getOperationsQueue();
        ArrayList<String> steps = new ArrayList<>();
        int faults = 0;
        long startTime = System.currentTimeMillis();
        long duration;

        if (pagesIdQueue.size() != operationQueue.size()) {
            ErrorLogs.pagesQueueSizeDifferFromActionsQueueSize("LRU");
            return null;
        }

        while (pagesIdQueue.size() > 0) {
            String nextPageId = pagesIdQueue.removeFirst();
            String pageToDeallocate = "";
            String operation = operationQueue.removeFirst();
            boolean operationSuccessful = memory.doOperation(operation, nextPageId);
            boolean modified = operation.toUpperCase() == "E";

            // Page in memory
            if (operationSuccessful) {
                lruPagesList.remove(nextPageId);
                lruPagesList.add(nextPageId);
                steps.add("A página " + nextPageId + " está na memória.");
            } else {
                // Page fault
                faults++;
                if (!memory.isFull()) {
                    // Page not in memory, but memory is not full. Allocate page
                    steps.add("A página " + nextPageId + " foi inserida em uma posição livre da memória.");
                } else {
                    // Page not in memory and memory is full. Swap pages
                    pageToDeallocate = lruPagesList.removeFirst();
                    memory.deallocate(pageToDeallocate);
                    steps.add("A página " + nextPageId + " foi inserida no lugar da página " + pageToDeallocate
                            + ".");
                }
                memory.allocate(nextPageId, modified);
                lruPagesList.add(nextPageId);
            }
        }

        duration = System.currentTimeMillis() - startTime;

        return new SimulationResults(faults, duration, steps);
    }

}
