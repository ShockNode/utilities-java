package com.shocknode.utilities;

public class Strings {

    public static String capitalize(String original){
        return original.length() == 0 ? original : original.substring(0, 1).toUpperCase() + original.substring(1);
    }

}
