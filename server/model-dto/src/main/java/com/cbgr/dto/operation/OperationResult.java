package com.cbgr.dto.operation;

public class OperationResult {

    String id;

    String description;

    public OperationResult(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public OperationResult() {
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
