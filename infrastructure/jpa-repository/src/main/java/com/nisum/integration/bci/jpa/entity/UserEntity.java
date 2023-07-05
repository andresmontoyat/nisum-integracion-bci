package com.nisum.integration.bci.jpa.entity;

import com.nisum.integration.bci.jpa.entity.listener.UserEntityListener;
import com.nisum.integration.bci.jpa.entity.support.BaseEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;


@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "users")
@EntityListeners(UserEntityListener.class)
public class UserEntity extends BaseEntity {
    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Lob
    @Column(nullable = false)
    private String token;

    private boolean isActive;

    private LocalDateTime lastLogin;

    @ElementCollection
    @CollectionTable(
            name = "users_phones",
            joinColumns = @JoinColumn(name = "user_id")
    )
    private List<PhoneEntity> phones;
}
