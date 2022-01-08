package com.tizzy.business.util;

public class ConversionUtil {

    private static final String allowedString = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static char[] allowedCharacters = allowedString.toCharArray();
    private static int base = allowedCharacters.length;

    public static String encode(long input){
        StringBuilder encodedString = new StringBuilder();

        if(input == 0) {
            return String.valueOf(allowedCharacters[0]);
        }

        while (input > 0) {
            encodedString.append(allowedCharacters[(int) (input % base)]);
            input = input / base;
        }

        return encodedString.reverse().toString();
    }

    public static long decode(String input) {
        char[] characters = input.toCharArray();
        int length = characters.length;

        long decoded = 0;

        //counter is used to avoid reversing input string
        int counter = 1;
        for (int i = 0; i < length; i++) {
            decoded += allowedString.indexOf(characters[i]) * Math.pow(base, length - counter);
            counter++;
        }
        return decoded;
    }
}
