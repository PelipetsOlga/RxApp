package com.example.opelipets.rxapp.model;

public class Contributor {

    String login;

    int contributions;

    @Override
    public String toString() {
        return login + " (" + contributions + ")\n";
    }
}
