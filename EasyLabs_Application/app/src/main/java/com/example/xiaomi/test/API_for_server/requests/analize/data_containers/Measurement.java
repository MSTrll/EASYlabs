package com.example.xiaomi.test.API_for_server.requests.analize.data_containers;

// Измерения для каждой переменной
public class Measurement {
    String variable;
    int[] mments;

    public Measurement(String variable, int[] mments) {
        this.variable = variable;
        this.mments = mments;
    }
}
