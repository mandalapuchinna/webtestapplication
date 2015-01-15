package com.phan.webtestapplication.model;

public class ReportResult {

    private Long scriptId;

    private String scriptName;

    private Double total;

    private Double success;

    private Double failure;

    public Long getScriptId() {
        return scriptId;
    }

    public void setScriptId(Long scriptId) {
        this.scriptId = scriptId;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getSuccess() {
        return success;
    }

    public void setSuccess(Double success) {
        this.success = success;
    }

    public Double getFailure() {
        return failure;
    }

    public void setFailure(Double failure) {
        this.failure = failure;
    }

    public Double getRunning() {
        return running;
    }

    public void setRunning(Double running) {
        this.running = running;
    }

    private Double running;

}
