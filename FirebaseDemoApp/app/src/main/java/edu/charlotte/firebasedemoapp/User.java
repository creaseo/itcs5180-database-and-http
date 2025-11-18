package edu.charlotte.firebasedemoapp;

public class User {
    String name;
    String cell;

    public User() {
    }

    public User(String name, String cell) {
        this.name = name;
        this.cell = cell;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        this.cell = cell;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", cell='" + cell + '\'' +
                '}';
    }
}
