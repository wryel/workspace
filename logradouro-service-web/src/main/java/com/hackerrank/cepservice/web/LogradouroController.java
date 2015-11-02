package com.hackerrank.cepservice.web;

import static com.hackerrank.cepservice.web.ControllerHelper.addErrorMessage;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hackerrank.cepservice.core.service.LogradouroService;
import com.hackerrank.cepservice.core.service.ServiceException;
import com.hackerrank.cepservice.model.Logradouro;
import com.hackerrank.cepservice.web.view.LogradouroEntradaView;
import com.hackerrank.cepservice.web.view.LogradouroListagemView;

@ManagedBean
@ViewScoped
public class LogradouroController extends AbstractController {

    private static final long serialVersionUID = -5627326656722662067L;

    private static final Logger LOGGER = LoggerFactory.getLogger(LogradouroController.class);

    private LogradouroListagemView logradouroListagemView;

    private LogradouroEntradaView logradouroEntradaView;

    public String actionListar() {
        getLogradouroListagemView().setLogradouros(getService(LogradouroService.class).listar());
        setView(VIEW_LISTAR);
        return StringUtils.EMPTY;
    }

    public String actionAdicionar() {
        setView(VIEW_ENTRADA);
        setAction(ACTION_ADICIONAR);
        getLogradouroEntradaView().setLogradouro(new Logradouro());
        return StringUtils.EMPTY;
    }

    public String actionSalvarAdicionar() {
        try {
            getService(LogradouroService.class).inserir(getLogradouroEntradaView().getLogradouro());
            return actionListar();
        } catch (ServiceException serviceException) {
            LOGGER.debug("Erro ao tentar salvar logradouro", serviceException);
            addErrorMessage(serviceException.getMessage());
        }
        return StringUtils.EMPTY;
    }

    public String actionRemover() {
        try {
            getService(LogradouroService.class).remover(getLogradouroEntradaView().getLogradouro());
            return actionListar();
        } catch (ServiceException serviceException) {
            LOGGER.debug("Erro ao tentar remover logradouro", serviceException);
            addErrorMessage(serviceException.getMessage());
        }
        return StringUtils.EMPTY;
    }

    public String actionEditar() {
        setView(VIEW_ENTRADA);
        setAction(ACTION_EDITAR);
        getLogradouroEntradaView().setLogradouro(getService(LogradouroService.class).obter(getLogradouroEntradaView().getLogradouro().getId()));
        return StringUtils.EMPTY;
    }

    public String actionSalvarEditar() {
        try {
            getService(LogradouroService.class).atualizar(getLogradouroEntradaView().getLogradouro());
            return actionListar();
        } catch (ServiceException serviceException) {
            LOGGER.debug("Erro ao tentar editar logradouro", serviceException);
            addErrorMessage(serviceException.getMessage());
        }
        return StringUtils.EMPTY;
    }

    public LogradouroListagemView getLogradouroListagemView() {
        if (logradouroListagemView == null) {
            logradouroListagemView = new LogradouroListagemView();
        }
        return logradouroListagemView;
    }

    public void setLogradouroListagemView(LogradouroListagemView logradouroListagemView) {
        this.logradouroListagemView = logradouroListagemView;
    }

    public LogradouroEntradaView getLogradouroEntradaView() {
        if (logradouroEntradaView == null) {
            logradouroEntradaView = new LogradouroEntradaView();
        }
        return logradouroEntradaView;
    }

    public void setLogradouroEntradaView(LogradouroEntradaView logradouroEntradaView) {
        this.logradouroEntradaView = logradouroEntradaView;
    }
}