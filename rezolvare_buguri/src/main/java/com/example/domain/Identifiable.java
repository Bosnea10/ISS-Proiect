package com.example.domain;

public interface Identifiable<ID> {
    void setId(ID id);
    ID getId();
}
