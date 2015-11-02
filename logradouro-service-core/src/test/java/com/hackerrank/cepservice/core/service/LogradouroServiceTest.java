package com.hackerrank.cepservice.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.google.common.collect.Lists;
import com.hackerrank.cepservice.core.dao.LogradouroDAOImpl;
import com.hackerrank.cepservice.core.repository.LogradouroRepository;
import com.hackerrank.cepservice.core.repository.LogradouroRepositoryImpl;
import com.hackerrank.cepservice.core.service.adapter.LogradouroWSClientAdapter;
import com.hackerrank.cepservice.core.service.adapter.LogradouroWSClientAdapterException;
import com.hackerrank.cepservice.core.service.adapter.LogradouroWSClientAdapterImpl;
import com.hackerrank.cepservice.model.Logradouro;
import com.hackerrank.cepservice.model.filter.LogradouroFilter;
import com.hackerrank.cepservice.ws.client.LogradouroWSClient;
import com.hackerrank.cepservice.ws.client.LogradouroWSClientFactory;
import com.hackerrank.cepservice.ws.client.Result;
import com.hackerrank.cepservice.ws.client.Result.Status;

@RunWith(PowerMockRunner.class)
@PrepareForTest(LogradouroWSClientFactory.class)
public class LogradouroServiceTest {

    private LogradouroServiceImpl logradouroServiceImpl = new LogradouroServiceImpl();

    private LogradouroWSClientAdapterImpl logradouroWSClientAdapterImpl = new LogradouroWSClientAdapterImpl();

    private LogradouroRepositoryImpl logradouroRepositoryImpl = new LogradouroRepositoryImpl();
    
    private LogradouroDAOImpl logradouroDAOImpl = new LogradouroDAOImpl();
    
    @Mock
    private EntityManager entityManager;

    @Mock
    private LogradouroWSClient logradouroWSClient;

    @Before
    public void setUp() {

        logradouroDAOImpl.setEntityManager(entityManager);
        logradouroRepositoryImpl.setDao(logradouroDAOImpl);
        logradouroServiceImpl.setRepository(logradouroRepositoryImpl);
        logradouroServiceImpl.setLogradouroWSClientAdapter(logradouroWSClientAdapterImpl);

        mockStatic(LogradouroWSClientFactory.class);

        when(LogradouroWSClientFactory.newLogradouroWSClient(anyString()))
            .thenReturn(logradouroWSClient);
    }

    @Test
    public void deveCriarLogradouroQuandoNaoExistirOutroLogradouroComOMesmoCep() throws ServiceException {

        Result<Logradouro> resultadoValidoSemLogradouro = resultadoValido(null);

        Logradouro logradouro = logradouroValidoSemId();

        when(logradouroWSClient.pesquisarPorCep(logradouro.getCep()))
            .thenReturn(resultadoValidoSemLogradouro);

        logradouroServiceImpl.inserir(logradouro);

        verify(logradouroWSClient).pesquisarPorCep(eq(logradouro.getCep()));
        verify(entityManager).persist(logradouro);
        verify(entityManager).flush();
    }
    
    @Test
    public void deveCriarLogradouroMesmoQueExistaOutroComCepParecido() throws ServiceException {

        Logradouro logradouro = logradouroValidoSemId();
        logradouro.setCep(StringUtils.repeat('9', Logradouro.TAMANHO_CEP));
        Logradouro logradouroComCepParecido = SerializationUtils.clone(logradouro);
        
        logradouroComCepParecido.setCep(StringUtils.substring(logradouroComCepParecido.getCep(), 0, -1) + "0");
        
        when(logradouroWSClient.pesquisarPorCep(logradouro.getCep()))
            .thenReturn(resultadoValido(logradouroComCepParecido));

        logradouroServiceImpl.inserir(logradouro);

        verify(logradouroWSClient).pesquisarPorCep(eq(logradouro.getCep()));
        verify(entityManager).persist(logradouro);
        verify(entityManager).flush();
    }
    
    @Test
    public void deveLancarExcecaoQuandoTentarCadastrarLogradouroComCepJaExistenteEmOutroLogradouro() throws LogradouroWSClientAdapterException {

        Logradouro logradouro = logradouroValidoSemId();
        
        Result<Logradouro> resultadoValido = resultadoValido(logradouro);
        
        when(logradouroWSClient.pesquisarPorCep(logradouro.getCep()))
            .thenReturn(resultadoValido);
     
        try {
            
            logradouroServiceImpl.inserir(logradouro);
            
            fail("Deveria ter lançado exceção por achar logradouro cadastrado com cep igual");
            
        } catch (ServiceException serviceException) {

            verify(logradouroWSClient).pesquisarPorCep(eq(logradouro.getCep()));
            verify(entityManager, never()).persist(any(Logradouro.class));
            
        }        
    }
    
    @Test
    public void deveLancarExcecaoParaLogradouroSemRua() {
       
        Logradouro logradouro = logradouroValidoSemId();
        logradouro.setRua(null);
        
        try {
            
            logradouroServiceImpl.inserir(logradouro);
            
            fail("Deveria ter lançado exceção por rua estar vazia");
            
        } catch (ServiceException serviceException) {
           
            verify(entityManager, never()).persist(any(Logradouro.class));
            
        }
    }

    @Test
    public void deveLancarExcecaoParaLogradouroSemNumero() {
       
        Logradouro logradouro = logradouroValidoSemId();
        logradouro.setNumero(null);
        
        try {
            
            logradouroServiceImpl.inserir(logradouro);
            
            fail("Deveria ter lançado exceção por numero estar vazio");
            
        } catch (ServiceException serviceException) {

            verify(entityManager, never()).persist(any(Logradouro.class));
            
        }        
    }

    @Test
    public void deveLancarExcecaoParaLogradouroSemCep() {
        
        Logradouro logradouro = logradouroValidoSemId();
        logradouro.setCep(null);
        
        try {
            
            logradouroServiceImpl.inserir(logradouro);
            
            fail("Deveria ter lançado exceção por cep estar vazio");
            
        } catch (ServiceException serviceException) {

            verify(entityManager, never()).persist(any(Logradouro.class));
            
        }
    }

    @Test
    public void deveLancarExcecaoParaLogradouroSemCidade() {
        
        Logradouro logradouro = logradouroValidoSemId();
        logradouro.setCidade(null);
        
        try {
            
            logradouroServiceImpl.inserir(logradouro);
            
            fail("Deveria ter lançado exceção por cidade estar vazia");
            
        } catch (ServiceException serviceException) {

            verify(entityManager, never()).persist(any(Logradouro.class));
            
        }
    }

    @Test
    public void deveLancarExcecaoParaLogradouroSemEstado() throws ServiceException {
        
        Logradouro logradouro = logradouroValidoSemId();
        logradouro.setEstado(null);
        
        try {
            
            logradouroServiceImpl.inserir(logradouro);
            
            fail("Deveria ter lançado exceção por estado estar vazio");
            
        } catch (ServiceException serviceException) {

            verify(entityManager, never()).persist(any(Logradouro.class));
            
        }
    }

    @Test
    public void deveRetornarMenorCepPossivel() throws ServiceException {

        String menorCep = "10000000";
        String segundoMenorCep = "11000000";

        Logradouro logradouroComMenorCep = logradouroValidoSemId();
        logradouroComMenorCep.setCep(menorCep);

        Logradouro logradouroComSegundoMenorCep = logradouroValidoSemId();
        logradouroComSegundoMenorCep.setCep(segundoMenorCep);
        
        // mockando repositório para simular comportamento de logradouros com cep parecidos
        
        LogradouroRepository logradouroRepository = mock(LogradouroRepository.class);
        
        when(logradouroRepository.obterPorCep(menorCep))
            .thenReturn(logradouroComMenorCep);
        
        when(logradouroRepository.obterPorCep(segundoMenorCep))
            .thenReturn(logradouroComSegundoMenorCep);
        
        logradouroServiceImpl.setRepository(logradouroRepository);
        
        // fim

        assertEquals("Retornou logradouro diferente do esperado", logradouroComSegundoMenorCep, logradouroServiceImpl.pesquisarPorCep("11999999"));
        
        verify(logradouroRepository, never()).obterPorCep(logradouroComMenorCep.getCep());
    }

    @Test
    public void deveRemoverLogradouro() throws ServiceException {
       
        Logradouro logradouro = logradouroValidoSemId();
        logradouro.setId(idAleatorio());
       
        when(entityManager.find(Logradouro.class, logradouro.getId()))
            .thenReturn(logradouro);
        
        logradouroServiceImpl.remover(logradouro);
        
        verify(entityManager).remove(eq(logradouro));
        verify(entityManager).flush();
    }

    @Test
    public void deveObterLogradouroPeloId() throws ServiceException {
        
        Logradouro logradouro = logradouroValidoSemId();
        logradouro.setId(idAleatorio());
        
        when(entityManager.find(Logradouro.class, logradouro.getId()))
            .thenReturn(logradouro);
        
        assertEquals("Logradouro diferente do esperado", logradouro, logradouroServiceImpl.obter(logradouro.getId()));
        verify(entityManager).find(Logradouro.class, logradouro.getId());
    }
    
    @Test
    public void deveLancarExcecaoQuandoServicoDeLogradouroEstiverFora() throws LogradouroWSClientAdapterException {
       
        String cepAletatorio = RandomStringUtils.randomNumeric(Logradouro.TAMANHO_CEP);
        
        String mensagemEsperada = "Uma mensagem qualquer retornada pelo adapter";
        
        LogradouroWSClientAdapter logradouroWSClientAdapter = mock(LogradouroWSClientAdapter.class);
        
        when(logradouroWSClientAdapter.pesquisarPorCep(anyString()))
            .thenThrow(new LogradouroWSClientAdapterException(mensagemEsperada));
        
        logradouroServiceImpl.setLogradouroWSClientAdapter(logradouroWSClientAdapter);
        
        try {
            
            logradouroServiceImpl.pesquisarPorCepPassandoPeloWS(cepAletatorio);
            
            fail("Deveria lançar exceção por serviço estar fora do ar");
            
        } catch (ServiceException serviceException) {

            assertEquals("Mensagem retornada do adapter diferente do esperado", mensagemEsperada, serviceException.getMessage());
            verify(logradouroWSClientAdapter).pesquisarPorCep(eq(cepAletatorio));
            
        }
    }
    
    @Test
    public void deveAtualizarLogradouroNormalmente() throws ServiceException {
        
        Logradouro logradouro = logradouroValidoSemId();
        logradouro.setId(idAleatorio());
        
        Result<Logradouro> resultado = resultadoValido(logradouro);
        
        when(logradouroWSClient.pesquisarPorCep(logradouro.getCep()))
            .thenReturn(resultado);
        
        when(entityManager.find(Logradouro.class, logradouro.getId()))
            .thenReturn(logradouro);

        logradouroServiceImpl.atualizar(logradouro);
        
        verify(entityManager).merge(eq(logradouro));
    }
    
    @Test
    public void deveLancarExcecaoQuandoTentarAtualizarLogradouroAlterandoCepParaUmMesmoDeOutroLogradouroExistente() {
        
        Logradouro logradouro = logradouroValidoSemId();
        logradouro.setId(1L);
        
        Logradouro logradouroComIdDiferenteECepIgual = SerializationUtils.clone(logradouro);
        logradouroComIdDiferenteECepIgual.setId(2L);
        
        Result<Logradouro> resultado = resultadoValido(logradouroComIdDiferenteECepIgual);
        
        when(logradouroWSClient.pesquisarPorCep(logradouro.getCep()))
            .thenReturn(resultado);

        try {
            
            logradouroServiceImpl.atualizar(logradouro);
            
            fail("Deveria lançar excecao ao tentar atualizar cep para o mesmo cep de outro logradouro existente");
            
        } catch (ServiceException serviceException) {
            
            verify(entityManager, never()).merge(any(Logradouro.class));
            
        }        
    }
    
    @Test
    public void deveAtualizarLogradouroMesmoComServicoInformandoCepParecidoEmOutroLogradouro() throws ServiceException {
        
        Logradouro logradouro = logradouroValidoSemId();
        logradouro.setId(1L);
        
        Logradouro logradouroComCepParecido = SerializationUtils.clone(logradouro);
        logradouroComCepParecido.setId(2L);
        logradouroComCepParecido.setCep(StringUtils.substring(logradouroComCepParecido.getCep(), 0, -1) + "0");
        
        Result<Logradouro> resultado = resultadoValido(logradouroComCepParecido);
        
        when(logradouroWSClient.pesquisarPorCep(logradouro.getCep()))
            .thenReturn(resultado);
        
        when(entityManager.find(Logradouro.class, logradouro.getId()))
            .thenReturn(logradouro);
            
        logradouroServiceImpl.atualizar(logradouro);
        
        verify(logradouroWSClient).pesquisarPorCep(eq(logradouro.getCep()));
        verify(entityManager).merge(eq(logradouro));
        verify(entityManager).flush();
    }
    
    @Test
    public void deveLancarExcecaoParaCepMaiorQuePermitido() {
        
        try {
            
            logradouroServiceImpl.pesquisarPorCep(RandomStringUtils.randomNumeric(Logradouro.TAMANHO_CEP + 1));
            
            fail("Deveria lançar excecao por cep maior que permitido");
            
        } catch (ServiceException serviceException) {
            
            verifyZeroInteractions(entityManager);
            
        }
    }

    @Test
    public void deveLancarExcecaoParaCepMenorQuePermitido() throws ServiceException {
        
        try {
            
            logradouroServiceImpl.pesquisarPorCep(RandomStringUtils.randomNumeric(Logradouro.TAMANHO_CEP - 1));
            
            fail("Deveria lançar excecao por cep menor que permitido");
            
        } catch (ServiceException serviceException) {
            
            verifyZeroInteractions(entityManager);
            
        }        
    }
    
    @Test
    public void deveLancarExcecaoParaCepNaoNumerico() {
        
        try {
            
            logradouroServiceImpl.pesquisarPorCep(RandomStringUtils.randomAlphabetic(Logradouro.TAMANHO_CEP));
        
            fail("Deveria lançar excecao por cep nao numerico");
            
        } catch (ServiceException serviceException) {
            
            verifyZeroInteractions(entityManager);
            
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void deveRetornarNullCasoNaoEncontreCepValido() throws ServiceException {
        
        TypedQuery<Logradouro> typedQuery = mock(TypedQuery.class);
        
        when(entityManager.createQuery(anyString(), (Class<Logradouro>) any()))
            .thenReturn(typedQuery); 
        
        when(typedQuery.getResultList())
            .thenReturn(Lists.<Logradouro>newArrayList()); 
        
        assertNull("Não deveria ter encontrado registros", logradouroServiceImpl.pesquisarPorCep(RandomStringUtils.randomNumeric(Logradouro.TAMANHO_CEP)));
        verify(typedQuery, times(Logradouro.TAMANHO_CEP)).getResultList();
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void deveListarTodosLogradouros() {
        
        List<Logradouro> logradourosEsperados = Arrays.asList(logradouroValidoSemId(), logradouroValidoSemId(), logradouroValidoSemId());
        
        TypedQuery<Logradouro> typedQuery = mock(TypedQuery.class);
        
        when(entityManager.createQuery(anyString(), (Class<Logradouro>) any()))
            .thenReturn(typedQuery);
        
        when(typedQuery.getResultList())
            .thenReturn(logradourosEsperados); 
        
        assertEquals("Deveria devolver a mesma quantidade de registros encontrados", logradourosEsperados.size(), logradouroServiceImpl.listar().size());
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void deveListarLogradourosComFiltro() {
        
        List<Logradouro> logradourosEsperados = Arrays.asList(logradouroValidoSemId(), logradouroValidoSemId(), logradouroValidoSemId());
        
        TypedQuery<Logradouro> typedQuery = mock(TypedQuery.class);
        
        when(entityManager.createQuery(anyString(), (Class<Logradouro>) any()))
            .thenReturn(typedQuery);
        
        when(typedQuery.getResultList())
            .thenReturn(logradourosEsperados); 
        
        assertEquals("Deveria devolver a mesma quantidade de registros encontrados", logradourosEsperados.size(), logradouroServiceImpl.listar(new LogradouroFilter()).size());
    }
    
    @Test
    @SuppressWarnings("unchecked")
    public void deveListarLogradourosComFiltroPaginado() {
        
        List<Logradouro> logradourosEsperados = Arrays.asList(logradouroValidoSemId(), logradouroValidoSemId(), logradouroValidoSemId());
        
        LogradouroFilter logradouroFilter = new LogradouroFilter();
        
        logradouroFilter.setFirstResult(BigDecimal.ZERO.intValue());
        logradouroFilter.setMaxResults(BigDecimal.TEN.intValue());
        
        TypedQuery<Logradouro> typedQuery = mock(TypedQuery.class);
        
        when(entityManager.createQuery(anyString(), (Class<Logradouro>) any()))
            .thenReturn(typedQuery);
        
        when(typedQuery.getResultList())
            .thenReturn(logradourosEsperados);

        assertEquals("Deveria devolver a mesma quantidade de registros encontrados", logradourosEsperados.size(), logradouroServiceImpl.listar(logradouroFilter).size());
        
        verify(typedQuery).setFirstResult(logradouroFilter.getFirstResult());
        verify(typedQuery).setMaxResults(logradouroFilter.getMaxResults());
    }
    
    /**
     * nunca usar estar função mais de uma vez no mesmo teste
     */
    private Long idAleatorio() {
        return RandomUtils.nextLong(BigDecimal.ONE.longValue(), BigDecimal.TEN.longValue());
    }
    
    private Result<Logradouro> resultadoValido(Logradouro logradouro) {
        Result<Logradouro> result = new Result<Logradouro>();
        result.setObject(logradouro);
        result.setStatus(Status.OK);
        return result;
    }

    private Logradouro logradouroValidoSemId() {
        Logradouro logradouro = new Logradouro();
        logradouro.setBairro(RandomStringUtils.randomAlphabetic(10));
        logradouro.setCep(RandomStringUtils.randomNumeric(8));
        logradouro.setCidade(RandomStringUtils.randomAlphabetic(10));
        logradouro.setEstado(RandomStringUtils.randomAlphabetic(2));
        logradouro.setNumero(RandomUtils.nextInt(1, 999));
        logradouro.setRua(RandomStringUtils.randomAlphabetic(10));
        return logradouro;
    }
}