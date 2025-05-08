package br.paginacao.common;

import java.util.ArrayList;
import java.util.HashMap;

import br.paginacao.exceptions.NotExpectedSizeException;
import br.paginacao.records.FrameData;

public class Memory {
    private HashMap<String, Frame> framesMap;
    private int maxSize;
    private int size;

    // TODO: Revisar esse throw de erro
    public Memory(int maxSize, ArrayList<String> initialPages) {
        if (initialPages.size() > maxSize) {
            throw new NotExpectedSizeException(
                    "O tamanho do estado inicial da memória é maior que o tamanho máximo da memória princiapl");
        }

        this.framesMap = new HashMap<>();
        this.maxSize = maxSize;

        for (int i = 0; i < initialPages.size(); i++) {
            String id = initialPages.get(i);

            if (!id.equals("") && !id.equals("0")) {
                this.framesMap.put(id, new Frame(id));
                this.size++;
            }

        }
    }

    /*
    * 
    */
    private boolean allocate(Frame frame) {
        if (!isFull()) {
            this.framesMap.put(frame.getPageId(), frame);
            size++;
            return true;
        }

        return false;
    }

    /*
    * 
    */
    public boolean allocate(String pageId) {
        return this.allocate(new Frame(pageId));
    }

    /*
    * 
    */
    public boolean deallocate(String pageId) {
        if (isAllocated(pageId)) {
            this.framesMap.remove(pageId);
            this.size--;
            return true;
        }

        return false;
    }

    /*
     * Return
     */
    public FrameData getFrameData(String pageId) {
        if (isAllocated(pageId))
            return new FrameData(this.framesMap.get(pageId));

        return null;
    }

    /*
     * Return
     */
    public boolean isFull() {
        return size == maxSize;
    }

    /*
     * Return
     */
    public boolean isAllocated(String pageId) {
        return framesMap.containsKey(pageId);
    }

}
