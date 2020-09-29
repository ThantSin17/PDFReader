package com.stone.pdfreader.listener;

import com.stone.pdfreader.dto.PdfDto;

public interface OnDeleteItemListener {
    void deleteItemListener(PdfDto item, int position);
}
