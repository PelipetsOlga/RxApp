package com.example.opelipets.rxapp;

public class Contributor {

    String login;

    int contributions;

    @Override
    public String toString() {
        return login + " (" + contributions + ")\n";
    }
}
