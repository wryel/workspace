package com.hackerrank;

import java.util.Arrays;

import com.hackerrank.streamreader.Stream;

public final class Solution {

    private Solution() {
        
    }
    
    public static char firstChar(Stream stream) {        
        char[] caracteres = new char[0];
        while (stream.hasNext()) {           
            caracteres = Arrays.copyOf(caracteres, caracteres.length + 1);
            caracteres[caracteres.length - 1] = stream.getNext();
        }
        return primeiroNaoRepetido(caracteres);
    }
    
    private static char primeiroNaoRepetido(char[] caracterArray) {
        char caracter = Character.MIN_VALUE;
        for (int x = 0; x < caracterArray.length; x++) {
            if (primeiraPosicao(caracterArray[x], caracterArray) == ultimaPosicao(caracterArray[x], caracterArray)) {
                caracter = caracterArray[x];
                break;
            }
        }
        return caracter;
    }

    private static int primeiraPosicao(char caracter , char[] caracterArray) {
        int posicao = -1;
        for (int x = 0; x < caracterArray.length; x++) {
            if (caracter == caracterArray[x]) { 
                posicao = x;
                break;
            }
        }
        return posicao;
    }
    
    private static int ultimaPosicao(char caracter , char[] caracterArray) {
        int posicao = -1;
        for (int x = caracterArray.length - 1; x >= 0; x--) {
            if (caracter == caracterArray[x]) { 
                posicao = x;
                break;
            }
        }
        return posicao;
    }
}