package com.demo.walletservice.common;

import java.util.List;

public class ResponseWrapper {

    private Object data;
    private Object metadata;
    private List<ErrorMessage> errors;

    public ResponseWrapper(Object data, Object metadata) {
        super();
        this.data = data;
        this.metadata = metadata;
    }

    public ResponseWrapper(Object metadata, List<ErrorMessage> errors) {
        super();
        this.metadata = metadata;
        this.errors = errors;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getMetadata() {
        return metadata;
    }

    public void setMetadata(Object metadata) {
        this.metadata = metadata;
    }

    public List<ErrorMessage> getErrors() {
        return errors;
    }

    public void setErrors(List<ErrorMessage> errors) {
        this.errors = errors;
    }
}
