package com.hackerrank.streamreader;

public class StreamFactory {

    private StreamFactory() {
        
    }
    
    public static Stream newStream(String string) {
        return new StreamImpl(string);
    }
}
