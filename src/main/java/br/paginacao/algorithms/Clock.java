package br.paginacao.algorithms;

import java.util.ArrayList;
import br.paginacao.common.Memory;
import br.paginacao.common.SimulationArgs;
import br.paginacao.interfaces.IAlgorithm;
import br.paginacao.records.SimulationResults;

public class Clock implements IAlgorithm {

    private ArrayList<PageData> initCache(ArrayList<String> initialState) {
        ArrayList<PageData> cache = new ArrayList<>();
        initialState.forEach(page -> cache.add(new PageData(page)));
        return cache;
    }

    private void resetReferencesBits(ArrayList<PageData> cache) {
        cache.forEach(data -> data.setReferred(false));
    }

    private void updateRef(String page, boolean isRefered, ArrayList<PageData> cache) {
        cache.forEach(pageData -> {
            if (pageData.getId().equals(page)) {
                pageData.setReferred(isRefered);
                return;
            }
        });
    }

    private PageData freeSpaceFromCache(ArrayList<PageData> cache) {
        if (cache.size() == 0)
            return null;

        while (true) {
            PageData page = cache.removeFirst();
            if (page.getReferred()) {
                page.setReferred(false);
                cache.add(page);
            } else {
                return page;
            }
        }
    }

    public SimulationResults run(SimulationArgs args) {
        Memory memory = new Memory(args.getMemorySize(), args.getMemoryInitialState());
        ArrayList<PageData> pagesCache = initCache(args.getMemoryInitialState());
        ArrayList<String> pagesIdQueue = args.getPagesIdsQueue();
        ArrayList<String> steps = new ArrayList<>();
        int maxTime = args.getClockInterruptTime();
        int currentTime = 0;
        long startTime = System.currentTimeMillis();
        long duration;
        int faults = 0;

        while (pagesIdQueue.size() > 0) {
            String nextPageId = pagesIdQueue.removeFirst();
            PageData pageToDeallocate = null;
            currentTime += 1;

            if (memory.isAllocated(nextPageId)) {
                updateRef(nextPageId, true, pagesCache);
                steps.add("A página " + nextPageId + " está na memória.");
            } else {
                faults++;
                if (!memory.isFull()) {
                    // Page not in memory, but memory is not full. Allocate page
                    steps.add("A página " + nextPageId + " foi inserida em uma posição livre da memória.");
                } else {
                    // Page not in memory and memory is full. Swap pages
                    pageToDeallocate = freeSpaceFromCache(pagesCache);
                    memory.deallocate(pageToDeallocate.getId());
                    steps.add("A página " + nextPageId + " foi inserida no lugar da página " + pageToDeallocate.getId()
                            + ".");
                }
                pagesCache.add(new PageData(nextPageId));
                memory.allocate(nextPageId);
            }

            if (maxTime != 0 && currentTime >= maxTime) {
                resetReferencesBits(pagesCache);
                currentTime = 0;
                steps.add("Bit R resetado.");
            }
        }

        duration = System.currentTimeMillis() - startTime;

        return new SimulationResults(faults, duration, steps);
    }

    private class PageData {
        private String id;
        private boolean isReferred;

        public PageData(String id) {
            this.id = id;
            this.isReferred = true;
        }

        public String getId() {
            return id;
        }

        public boolean getReferred() {
            return this.isReferred;
        }

        public void setReferred(boolean value) {
            this.isReferred = value;
        }

    }
}
