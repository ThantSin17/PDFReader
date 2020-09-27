package com.stone.pdfreader.viewModel;

import androidx.lifecycle.ViewModel;

public class ViewerActivityModel extends ViewModel {
    private int currentPageNumber=0;

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }
}
