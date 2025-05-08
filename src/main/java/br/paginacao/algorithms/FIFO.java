package br.paginacao.algorithms;

import java.util.ArrayList;
import br.paginacao.common.Memory;
import br.paginacao.common.SimulationArgs;
import br.paginacao.interfaces.IAlgorithm;
import br.paginacao.records.SimulationResults;

public class FIFO implements IAlgorithm {

    public SimulationResults run(SimulationArgs args) {
        Memory memory = new Memory(args.getMemorySize(), args.getMemoryInitialState());
        ArrayList<String> fifoQueue = args.getMemoryInitialState();
        ArrayList<String> pagesIdQueue = args.getPagesIdsQueue();
        ArrayList<String> steps = new ArrayList<>();
        int faults = 0;
        long startTime = System.currentTimeMillis();
        long duration;

        while (pagesIdQueue.size() > 0) {
            String nextPageId = pagesIdQueue.removeFirst();
            String pageIdToRemove = "";

            if (memory.isAllocated(nextPageId)) {
                steps.add("A página " + nextPageId + " está na memória.");
                continue;
            } else {
                faults++;
                if (!memory.isFull()) {
                    // Page not in memory, but memory is not full. Allocate page
                    steps.add("A página " + nextPageId + " foi inserida em uma posição livre da memória.");
                } else {
                    // Page not in memory and memory is full. Swap pages
                    pageIdToRemove = fifoQueue.removeFirst();
                    memory.deallocate(pageIdToRemove);
                    steps.add("A página " + nextPageId + " foi inserida no lugar da página " + pageIdToRemove + ".");
                }
                memory.allocate(nextPageId);
                fifoQueue.add(nextPageId);
            }
        }

        // duration = System.currentTimeMillis() - startTime + ((int) (faults * 0.2));
        duration = System.currentTimeMillis() - startTime;

        return new SimulationResults(faults, duration, steps);
    }
}
