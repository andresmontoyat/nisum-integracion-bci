package com.nisum.integration.bci.jpa.entity.listener;

import com.nisum.integration.bci.jpa.entity.UserEntity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

public class UserEntityListener {

    @PrePersist
    void onPrePersist(UserEntity entity) {
        entity.setLastLogin(entity.getCreated());
    }

    @PreUpdate
    void onPreUpdate(UserEntity entity) {
        entity.setLastLogin(entity.getModified());
    }
}
