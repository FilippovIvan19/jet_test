package com.parser;

import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String inStr = in.next();
        in.close();

        String outStr = "";
        if (Checker.check(inStr)) {
            outStr = Parser.parse(inStr);
        }

        System.out.println(outStr);
    }

}
