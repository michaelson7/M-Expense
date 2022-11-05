package com.example.m_expense;

import java.io.Serializable;

public class tripModel implements Serializable {
    private String  title,date,name,destination,risk,description,imageLink,expenseType,expenseAmount,expenseTime,expenseComments,id;

    public tripModel(String title, String date, String name, String destination, String risk, String description, String imageLink, String expenseType, String expenseAmount, String expenseTime, String expenseComments, String id) {
        this.title = title;
        this.date = date;
        this.name = name;
        this.destination = destination;
        this.risk = risk;
        this.description = description;
        this.imageLink = imageLink;
        this.expenseType = expenseType;
        this.expenseAmount = expenseAmount;
        this.expenseTime = expenseTime;
        this.expenseComments = expenseComments;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }
    public String getName() {
        return name;
    }

    public String getDestination() {
        return destination;
    }

    public String getRisk() {
        return risk;
    }

    public String getDescription() {
        return description;
    }

    public String getImageLink() {
        return imageLink;
    }

    public String getExpenseType() {
        return expenseType;
    }

    public String getExpenseAmount() {
        return expenseAmount;
    }

    public String getExpenseTime() {
        return expenseTime;
    }

    public String getExpenseComments() {
        return expenseComments;
    }

    public String getId() {
        return id;
    }
}
