package com.example.kotlinpractice;

public class Cat {
    private int age;
    private String name;

    public Cat(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public static void main(String[] args) {
        Cat franklin = new Cat(12, "franklin");
        Cat sally = new Cat(6, "sally");

        System.out.println(franklin.getAge());

        System.out.println(franklin == sally);
        System.out.println(franklin);
    }
}
