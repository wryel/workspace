package com.hackerrank.cepservice.ws.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.hackerrank.cepservice.model.Logradouro;

@Path(value = "/logradouroWS")
@Produces(MediaType.APPLICATION_JSON)
public interface LogradouroWSClient {

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
     * @param cep
     * @return Result<Logradouro> s
     * @throws javax.ws.rs.ProcessingException caso ocorra algum problema na comunicação com o serviço.
     */
    @GET
    @Path(value = "/pesquisar-por-cep/{cep}")
    public Result<Logradouro> pesquisarPorCep(@PathParam("cep") String cep);
}