package main;

import functional.MyLinkedList;
import testData.Person;

public class MainClass {
    public static void main(String[] args) {
        MyLinkedList<Person> list = new MyLinkedList<>();

        for (int i = 0; i < 10; i++) {
            Person person = new Person(i, "Petr" + i, "221677" + i);
            list.add(person);
        }
        System.out.println(list);

    }

}
