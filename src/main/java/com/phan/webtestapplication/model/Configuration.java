package com.phan.webtestapplication.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.phan.webtestapplication.util.validation.ValidBinary;
import com.phan.webtestapplication.util.validation.ValidStorage;

@Entity
public class Configuration extends AbstractEntity<Long> {

    private static final long serialVersionUID = 1L;

    @NotNull(message = "Webtest binary is required")
    @NotBlank(message = "Webtest binary is required")
    @ValidBinary(message = "Webtest binary is invalid")
    private String binary;

    @ValidStorage(message = "Webtest storage is invalid")
    private String storage;

    private String proxyHost;

    private String proxyPort;

    public String getBinary() {
        return binary;
    }

    public void setBinary(String binary) {
        this.binary = binary;
    }

    public String getStorage() {
        return storage;
    }

    public void setStorage(String storage) {
        this.storage = storage;
    }

    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }

    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

}
