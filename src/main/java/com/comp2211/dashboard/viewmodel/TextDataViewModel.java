package com.comp2211.dashboard.viewmodel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TextDataViewModel {
    private StringProperty textValue = new SimpleStringProperty("");;

    public StringProperty textValue(){
        return textValue;
    }
}
