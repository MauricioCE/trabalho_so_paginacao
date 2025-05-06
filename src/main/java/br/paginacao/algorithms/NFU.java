package br.paginacao.algorithms;

import java.util.ArrayList;
import br.paginacao.common.ErrorLogs;
import br.paginacao.common.Memory;
import br.paginacao.common.SimulationArgs;
import br.paginacao.interfaces.IAlgorithm;
import br.paginacao.records.SimulationResults;

public class NFU implements IAlgorithm {

    private ArrayList<PageData> initMemory(ArrayList<String> initialState) {
        ArrayList<PageData> pagesData = new ArrayList<>();

        initialState.forEach(page -> {
            PageData pageData = new PageData(page);
            pagesData.add(pageData);
        });

        return pagesData;
    }

    private boolean incrementRefValue(String page, ArrayList<PageData> pagesList) {
        for (PageData pageData : pagesList) {
            if (pageData.getId().equals(page)) {
                pageData.incrementRef();
                return true;
            }
        }

        return false;
    }

    private String removeNfuPage(ArrayList<PageData> pagesList) {
        if (pagesList.isEmpty()) {
            return null;
        }

        PageData pageToRemove = null;
        String keyToRemove = null;
        Integer minValue = Integer.MAX_VALUE;

        for (int i = pagesList.size() - 1; i >= 0; i--) {
            PageData pageData = pagesList.get(i);
            if (pageData.getRefCount() <= minValue) {
                minValue = pageData.getRefCount();
                keyToRemove = pageData.getId();
                pageToRemove = pageData;
            }
        }

        pagesList.remove(pageToRemove);
        return keyToRemove;
    }

    public SimulationResults run(SimulationArgs args) {
        Memory memory = new Memory(args.getMemorySize(), args.getMemoryInitialState());
        ArrayList<PageData> nfuPagesList = initMemory(args.getMemoryInitialState());
        ArrayList<String> pagesIdQueue = args.getPagesIdsQueue();
        ArrayList<String> steps = new ArrayList<>();
        int faults = 0;
        long startTime = System.currentTimeMillis();
        long duration;

        while (pagesIdQueue.size() > 0) {
            String nextPageId = pagesIdQueue.removeFirst();
            String pageToDeallocate = "";

            if (memory.isAllocated(nextPageId)) {
                incrementRefValue(nextPageId, nfuPagesList);
                steps.add("A página " + nextPageId + " está na memória.");
            } else {
                faults++;
                if (!memory.isFull()) {
                    // Page not in memory, but memory is not full. Allocate page
                    steps.add("A página " + nextPageId + " foi inserida em uma posição livre da memória.");
                } else {
                    // Page not in memory and memory is full. Swap pages
                    pageToDeallocate = removeNfuPage(nfuPagesList);
                    memory.deallocate(pageToDeallocate);
                    steps.add("A página " + nextPageId + " foi inserida no lugar da página " + pageToDeallocate
                            + ".");
                }
                memory.allocate(nextPageId);
                nfuPagesList.add(new PageData(nextPageId));
            }

        }

        duration = System.currentTimeMillis() - startTime;

        return new SimulationResults(faults, duration, steps);
    }

    private class PageData {
        String id;
        int refCount;

        public PageData(String id) {
            this.id = id;
            this.refCount = 1;
        }

        public String getId() {
            return id;
        }

        public int getRefCount() {
            return refCount;
        }

        public void incrementRef() {
            ++this.refCount;
        }
    }

}
