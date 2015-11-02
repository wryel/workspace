package com.hackerrank.cepservice.web;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public final class ControllerHelper {

    private ControllerHelper() {

    }
    
    public static void addErrorMessage(String message) {
        FacesMessage facesMessage = new FacesMessage();
        facesMessage.setSeverity(FacesMessage.SEVERITY_ERROR);
        facesMessage.setSummary(message);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }
}
