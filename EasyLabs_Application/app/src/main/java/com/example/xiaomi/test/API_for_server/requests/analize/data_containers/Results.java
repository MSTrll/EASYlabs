package com.example.xiaomi.test.API_for_server.requests.analize.data_containers;

// Результаты анализа данных
public class Results {
    String variable;
    int[] measurements = new int[8];
    int[] errors = new int[8];
    int mTarget;
    int eTarget;

    public Results(String variable, int[] measurements, int[] errors, int mTarget, int eTarget) {
        this.variable = variable;
        this.measurements = measurements;
        this.errors = errors;
        this.mTarget = mTarget;
        this.eTarget = eTarget;
    }
}
