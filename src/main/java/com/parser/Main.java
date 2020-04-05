package com.parser;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String inStr = in.next();
        in.close();

        Checker.check(inStr);
        String outStr = Parser.parse(inStr);

        System.out.println(outStr);
    }

}
