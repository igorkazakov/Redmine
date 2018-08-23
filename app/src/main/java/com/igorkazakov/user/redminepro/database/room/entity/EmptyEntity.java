package com.igorkazakov.user.redminepro.database.room.entity;

public abstract class EmptyEntity {

    private boolean isEmpty = false;

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
