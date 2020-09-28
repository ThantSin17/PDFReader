package com.stone.pdfreader.viewModel;

import androidx.lifecycle.ViewModel;

public class ViewerActivityModel extends ViewModel {
    private int currentPageNumber=0;
    private boolean orientation=true;
    private int orientationIndex=0;

    public int getOrientationIndex() {
        return orientationIndex;
    }

    public void setOrientationIndex(int orientationIndex) {
        this.orientationIndex = orientationIndex;
    }

    public boolean isOrientation() {
        return orientation;
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }
}
