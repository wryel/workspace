package com.hackerrank.streamreader;

class StreamImpl implements Stream {
    
    private String string;
    
    private int index;
    
    public StreamImpl(String string) {
        this.string = string;
    }

    @Override
    public char getNext() {
        return string.charAt(index++);
    }

    @Override
    public boolean hasNext() {        
        return string != null && index < string.length();
    }
}
