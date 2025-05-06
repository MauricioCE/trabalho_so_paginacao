package br.paginacao.algorithms;

import java.util.ArrayList;
import br.paginacao.common.Memory;
import br.paginacao.common.SimulationArgs;
import br.paginacao.interfaces.IAlgorithm;
import br.paginacao.records.SimulationResults;

public class LRU implements IAlgorithm {

    public SimulationResults run(SimulationArgs args) {
        Memory memory = new Memory(args.getMemorySize(), args.getMemoryInitialState());
        ArrayList<String> lruPagesList = args.getMemoryInitialState();
        ArrayList<String> pagesIdQueue = args.getPagesIdsQueue();
        ArrayList<String> steps = new ArrayList<>();
        int faults = 0;
        long startTime = System.currentTimeMillis();
        long duration;

        while (pagesIdQueue.size() > 0) {
            String nextPageId = pagesIdQueue.removeFirst();
            String pageToDeallocate = "";

            if (memory.isAllocated(nextPageId)) {
                lruPagesList.remove(nextPageId);
                lruPagesList.add(nextPageId);
                steps.add("A página " + nextPageId + " está na memória.");
            } else {
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
                memory.allocate(nextPageId);
                lruPagesList.add(nextPageId);
            }
        }

        duration = System.currentTimeMillis() - startTime;

        return new SimulationResults(faults, duration, steps);
    }

}
