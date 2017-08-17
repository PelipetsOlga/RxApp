package com.example.opelipets.rxapp;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

public class ScreenModel extends BaseObservable {
    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> result = new ObservableField<>("");
    public ObservableBoolean btnEnabled = new ObservableBoolean(false);
    public ObservableBoolean isMan = new ObservableBoolean(true);

    public String getRequest() {
        return name.get()
                + " ("
                + (isMan.get() ? "man" : "woman")
                + ") : "
                + email.get();
    }
}
