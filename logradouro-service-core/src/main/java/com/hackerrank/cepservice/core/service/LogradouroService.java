package com.hackerrank.cepservice.core.service;

import com.hackerrank.cepservice.model.Logradouro;
import com.hackerrank.cepservice.model.filter.LogradouroFilter;

public interface LogradouroService extends Service<Logradouro, Long, LogradouroFilter> {

    /**
     * Obtem o primeiro logradouro encontrado conforme as regras abaixo:
     * 
     * <ol>
     * <li>Dado um CEP válido, deve retornar o endereço correspondente.</li>
     * <li>Dado um CEP válido, que não exista o endereço, deve substituir um
     * digito da direita para a esquerda por zero até que o endereço seja
     * localizado (Exemplo: Dado 22333999 tentar com 22333990, 22333900 ...).</li>
     * <li>Dado um CEP inválido, deve retornar uma mensagem reportando o erro:
     * "CEP inválido".</li>
     * </ol>
     * 
     * @param cep - cep a ser pesquisado
     * @return o primeiro logradouro econtrando
     * @throws ServiceException quando o cep é inválido.
     */
    Logradouro pesquisarPorCep(String cep) throws ServiceException;
}
