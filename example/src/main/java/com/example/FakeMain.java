package com.example;

public class FakeMain { // method required to 'trick' java into running the program, since it does not
                        // extende Application
    public static void main(String[] args) {
        App.main(args);
    }
}
