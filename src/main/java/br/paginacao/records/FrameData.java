package br.paginacao.records;

import br.paginacao.common.Frame;

public record FrameData(String pageId, boolean isReferenced, boolean isModified, int duration) {
    public FrameData(Frame frame) {
        this(frame.getPageId(), frame.isReferenced(), frame.isModified(), frame.getDuration());
    }
}
