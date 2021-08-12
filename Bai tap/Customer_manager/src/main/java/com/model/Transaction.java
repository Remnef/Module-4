package com.model;

public class Transaction {
    private int id =0;
    private String name;
    private String method;
    private float money;
    private float fee;
    private String time;

    public Transaction(){

    }

    public Transaction(int id, String name, String method, float money, float fee, String time) {
        this.id = id;
        this.name = name;
        this.method = method;
        this.money = money;
        this.fee = fee;
        this.time = time;
    }

    public Transaction(String name, String method, float money, float fee, String time) {
        this.name = name;
        this.method = method;
        this.money = money;
        this.fee = fee;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public float getFee() {
        return fee;
    }

    public void setFee(float fee) {
        this.fee = fee;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", method='" + method + '\'' +
                ", money=" + money +
                ", fee=" + fee +
                ", time='" + time + '\'' +
                '}';
    }
}
