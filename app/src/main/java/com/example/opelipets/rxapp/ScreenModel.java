package com.example.opelipets.rxapp;

import android.databinding.BaseObservable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ScreenModel extends BaseObservable {
    public ObservableField<String> name = new ObservableField<>("");
    public ObservableField<String> email = new ObservableField<>("");
    public ObservableField<String> result = new ObservableField<>("");
    public ObservableBoolean btnEnabled = new ObservableBoolean(false);
    public ObservableBoolean isMan = new ObservableBoolean(true);
    public ObservableField<String> day = new ObservableField<>("");
    public ObservableArrayList<String> days = new ObservableArrayList<>();

    ScreenModel(String[] daysArray) {
        for (String d : daysArray) {
            days.add(d);
        }
    }

    public String getRequest() {
        return "Search results:\n\n"
                + name.get()
                + " ("
                + (isMan.get() ? "man" : "woman")
                + ") : "
                + email.get()
                + "\n"
                + day.get();
    }
}
