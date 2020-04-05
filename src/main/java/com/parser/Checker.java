package com.parser;

public class Checker {
    public static String str;

    public static boolean check(String inStr) {
        str = inStr;

        try {
            callChain();
        } catch (MySyntaxException ex) {
            System.out.println("SYNTAX ERROR");
            System.out.println(ex.getMessage());
//            System.out.println(str);
            return false;
        } catch (MyTypeException ex) {
            System.out.println("Type ERROR");
            System.out.println(ex.getMessage());
//            System.out.println(str);
            return  false;
        }

        return true;
    }


    public static void callChain() throws MySyntaxException, MyTypeException {
        call();
        if (!str.equals("")) {
            nextCall();
            callChain();
        }
    }


    public static void nextCall() throws MySyntaxException, MyTypeException {
        if (!str.startsWith("%>%")) {
            throw new MySyntaxException("should be %>%");
        }
        str = str.substring("%>%".length());
    }


    public static void call() throws MySyntaxException, MyTypeException {
        if (str.startsWith("map")) {
            str = str.substring("map".length());
        } else if (str.startsWith("filter")) {
            str = str.substring("filter".length());
        } else {
            throw new MyTypeException("should be filter of map");
        }

        if (str.startsWith("{")) {
            str = str.substring("{".length());
        } else {
            throw new MySyntaxException("should be {");
        }

        expr();

        if (str.startsWith("}")) {
            str = str.substring("}".length());
        } else {
            throw new MySyntaxException("should be }");
        }
    }


    public static void expr() throws MySyntaxException, MyTypeException {
        if (str.startsWith("element")) {
            str = str.substring("element".length());
        } else if (str.startsWith("(")) {
            binExpr();
        } else {
            constExpr();
        }
    }


    public static void binExpr() throws MySyntaxException, MyTypeException {
        if (str.startsWith("(")) {
            str = str.substring("(".length());
        } else {
            throw new MySyntaxException("should be (");
        }

        expr();
        operation();
        expr();

        if (str.startsWith(")")) {
            str = str.substring(")".length());
        } else {
            throw new MySyntaxException("should be )");
        }
    }


    public static void constExpr() throws MySyntaxException, MyTypeException {
        if (str.startsWith("-")) {
            str = str.substring("-".length());
        }
        number();
    }


    public static void number() throws MySyntaxException, MyTypeException {
        int i = 0;
        while (Character.isDigit(str.charAt(i))) {
            i++;
        }

        if (i == 0) {
            throw new MyTypeException("should be a number");
        } else {
            str = str.substring(i);
        }
    }


    public static void operation() throws MySyntaxException, MyTypeException {
        if (
                str.startsWith("+") ||
                str.startsWith("-") ||
                str.startsWith("*") ||
                str.startsWith(">") ||
                str.startsWith("<") ||
                str.startsWith("=") ||
                str.startsWith("&") ||
                str.startsWith("|")
        ) {
            str = str.substring(1);
        } else {
            throw new MySyntaxException("should be an operation");
        }
    }

}
