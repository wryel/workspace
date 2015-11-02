package com.hackerrank.streamreader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

public class StreamTest {

    @Test
    public void deveAvaliarFalsoQuandoNull() {
        Stream stream = StreamFactory.newStream(null);
        assertFalse("Deveria avaliar como false", stream.hasNext());
    }
    
    @Test
    public void deveAvaliarFalsoQuandoVazio() {
        Stream stream = StreamFactory.newStream("");
        assertFalse("Deveria avaliar como true", stream.hasNext());
    }
    
    @Test
    public void stringDeveSerAMesmaPassadaComoParametro() {
               
        String string = "teste";
        
        Stream stream = StreamFactory.newStream(string);
        
        StringBuilder stringBuilder = new StringBuilder();
       
        while (stream.hasNext()) {
            stringBuilder.append(stream.getNext());
        }
        
        assertEquals("String diferente do esperado", string, stringBuilder.toString());
    }
}
