package com.example.ygc.test.entity;

/**
 * Created by YGC on 16/11/16.
 */

public class Singleton {
    private Singleton instance;

    private Singleton() {
    }

    public Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }
}
