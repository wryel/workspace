package com.hackerrank;

import org.junit.Assert;
import org.junit.Test;

import com.hackerrank.streamreader.StreamFactory;

public class SolutionTest {

    @Test
    public void deveRetornarPrimeiroCaractereNaoRepetitivoProposto() {
        Assert.assertEquals('b', Solution.firstChar(StreamFactory.newStream("aAbBABac")));
    }
    
    @Test
    public void deveRetornarPrimeiroCaractereNaoRepetitivoPropostoQueEstaNaUltimaPosicao() {
        Assert.assertEquals('A', Solution.firstChar(StreamFactory.newStream("abcdabcdA")));
    }
    
    @Test
    public void deveRetornarPrimeiroCaractereNaoRepetitivoPropostoQueEstaNaPrimeiraPosicao() {
        Assert.assertEquals('A', Solution.firstChar(StreamFactory.newStream("Aabcdabcd")));
    }
    
    @Test
    public void deveRetornarVazioQuandoStringVazia() {
        Assert.assertEquals(Character.MIN_VALUE, Solution.firstChar(StreamFactory.newStream("")));
        Assert.assertEquals(Character.MIN_VALUE, Solution.firstChar(StreamFactory.newStream(null)));
    }
    
    @Test
    public void deveRetornarVazioQuandoStringTiverApenasLetrasIguais() {
        Assert.assertEquals(Character.MIN_VALUE, Solution.firstChar(StreamFactory.newStream("aaaaaaa")));
    }
}
