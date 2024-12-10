package com.example.myapp.controller;

// DTO 클래스
public class RealtimeRequest {
    private String stockName;
    private String close;

    // Getter와 Setter
    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }
}
