package com.example.gui;

import java.util.ArrayList;

public class Observable {
    ArrayList<Observer> observers;

    public Observable(){
        observers = new ArrayList<>();
    }

    public void addObserver(Observer observer){
        observers.add(observer);
    }

    public void updateAll(){
        observers.forEach(Observer::update);
    }
}
