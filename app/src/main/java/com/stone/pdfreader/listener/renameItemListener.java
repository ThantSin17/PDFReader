package com.stone.pdfreader.listener;

import com.stone.pdfreader.dto.PdfDto;

public interface renameItemListener {
    void onRenameItem(PdfDto item, int position);
}
