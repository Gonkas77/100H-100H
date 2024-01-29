package me.gonkas.onehhonehh.util;

import me.gonkas.onehhonehh.OneHHOneHH;

import java.util.Arrays;

public class Array {

    public static String[] arrayPop(String[] array, int index) {
        String[] result = new String[array.length - 1];
        for (int i=0; i < result.length; i++) {
            if (i < index) {result[i] = array[i];}
            else {result[i] = array[i+1];}
        } return result;
    }

    public static String[] arrayAppend(String[] array, String string) {
        String[] result = new String[array.length + 1];
        System.arraycopy(array, 0, result, 0, array.length);
        result[array.length] = string;
        return result;
    }

    public static String[] arrayRemove(String[] array, String string) {
        for (int i=0; i < array.length; i++) {
            if (array[i].equals(string)) {return arrayPop(array, i);}
        } return array;
    }

    public static String[] arrayInsert(String[] array, String string, int index) {
        String[] result = new String[array.length + 1];
        for (int i=0; i < array.length; i++) {
            if (i < index) {result[i] = array[i];}
            else if (index < i) {result[i] = array[i-1];}
            else {result[i] = string;}
        } return result;
    }

    public static String[] arrayClear(String[] array) {
        return new String[array.length];
    }

    public static String[] arrayClear(String[] array, int index) {
        String[] result = new String[array.length-(array.length-index)];
        for (int i=0; i < index; i++) {
            result[i] = array[i];
            OneHHOneHH.CONSOLE.sendMessage(array[i]);
            OneHHOneHH.CONSOLE.sendMessage(String.valueOf(i));
            OneHHOneHH.CONSOLE.sendMessage(Arrays.toString(result));
        } return result;
    }

    public static char[] arrayPop(char[] array, int index) {
        char[] result = new char[array.length - 1];
        for (int i=0; i < result.length; i++) {
            if (i < index) {result[i] = array[i];}
            else {result[i] = array[i+1];}
        } return result;
    }

    public static char[] arrayAppend(char[] array, char character) {
        char[] result = new char[array.length + 1];
        System.arraycopy(array, 0, result, 0, array.length);
        result[array.length] = character;
        return result;
    }

    public static char[] arrayRemove(char[] array, char character) {
        for (int i=0; i < array.length; i++) {
            if (array[i] == character) {return arrayPop(array, i);}
        } return array;
    }

    public static char[] arrayInsert(char[] array, char character, int index) {
        char[] result = new char[array.length + 1];
        for (int i=0; i < array.length; i++) {
            if (i < index) {result[i] = array[i];}
            else if (index < i) {result[i] = array[i-1];}
            else {result[i] = character;}
        } return result;
    }

    public static char[] arrayClear(char[] array) {
        return new char[array.length];
    }

    public static boolean[] arrayPop(boolean[] array, int index) {
        boolean[] result = new boolean[array.length - 1];
        for (int i=0; i < result.length; i++) {
            if (i < index) {result[i] = array[i];}
            else {result[i] = array[i+1];}
        } return result;
    }

    public static boolean[] arrayAppend(boolean[] array, boolean bool) {
        boolean[] result = new boolean[array.length + 1];
        System.arraycopy(array, 0, result, 0, array.length);
        result[array.length] = bool;
        return result;
    }

    public static boolean[] arrayRemove(boolean[] array, boolean bool) {
        for (int i=0; i < array.length; i++) {
            if (array[i] == bool) {return arrayPop(array, i);}
        } return array;
    }

    public static boolean[] arrayInsert(boolean[] array, boolean bool, int index) {
        boolean[] result = new boolean[array.length + 1];
        for (int i=0; i < array.length; i++) {
            if (i < index) {result[i] = array[i];}
            else if (index < i) {result[i] = array[i-1];}
            else {result[i] = bool;}
        } return result;
    }

    public static boolean[] arrayClear(boolean[] array) {
        return new boolean[array.length];
    }
}