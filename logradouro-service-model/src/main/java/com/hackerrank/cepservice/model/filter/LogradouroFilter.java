package com.hackerrank.cepservice.model.filter;

public class LogradouroFilter extends AbstractFilter {

    private static final long serialVersionUID = -7647877513777952953L;

    public static final String CEP_EQUALS = "cepEquals";

    private String cepEquals;

    public String getCepEquals() {
        return cepEquals;
    }

    public void setCepEquals(String cepEquals) {
        this.cepEquals = cepEquals;
    }
}
