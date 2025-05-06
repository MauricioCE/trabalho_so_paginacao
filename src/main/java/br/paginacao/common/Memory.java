package br.paginacao.common;

import java.util.ArrayList;
import java.util.HashMap;

import br.paginacao.erros.BiggerSizeThanExpectedError;
import br.paginacao.records.FrameData;

public class Memory {
    private HashMap<String, Frame> framesMap;
    private int maxSize;
    private int size;

    // TODO: Revisar esse throw de erro
    public Memory(int maxSize, ArrayList<String> initialPages) {
        if (initialPages.size() > maxSize) {
            throw new BiggerSizeThanExpectedError(
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
     * Realiza uma operação de memória
     */
    public boolean doOperation(String operation, String pageId) {
        switch (operation.toUpperCase()) {
            case "L":
                return read(pageId);

            case "E":
                return write(pageId);

            default:
                return read(pageId);
        }
    }

    /*
    * 
    */
    private boolean allocate(Frame frame, boolean isModified) {
        if (!isFull()) {
            frame.setModified(isModified);
            this.framesMap.put(frame.getPageId(), frame);
            size++;
            return true;
        }

        return false;
    }

    /*
    * 
    */
    public boolean allocate(String pageId, boolean isModified) {
        return this.allocate(new Frame(pageId), isModified);
    }

    /*
    * 
    */
    public boolean deallocate(String pageId) {
        if (isInMemory(pageId)) {
            this.framesMap.remove(pageId);
            this.size--;
            return true;
        }

        return false;
    }

    /*
     * 
     */
    private boolean read(String pageId) {
        if (isInMemory(pageId)) {
            return true;
        }

        return false;
    }

    /*
     * 
     */
    private boolean write(String pageId) {
        if (isInMemory(pageId)) {
            getFrame(pageId).setModified(true);
            return true;
        }

        return false;
    }

    /*
     * Return
     */
    public FrameData getFrameData(String pageId) {
        if (isInMemory(pageId))
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
    public boolean isInMemory(String pageId) {
        return framesMap.containsKey(pageId);
    }

    /*
     * Return
     */
    private Frame getFrame(String pageId) {
        if (isInMemory(pageId))
            return this.framesMap.get(pageId);

        return null;
    }

}
