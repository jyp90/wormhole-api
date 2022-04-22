package com.jypark.coding.domain.users.entity;

import com.jypark.coding.domain.users.dto.UserCreateRequestDTO;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(
    name = "users",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"username"})
    })
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "userId")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    private String username;

    private String email;

    private String phone;

    private String password;

    private String code;

    private int level;

    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "deleted")
    private boolean deleted;

    @CreationTimestamp
    @Column(name = "register_at")
    private LocalDateTime registerAt = LocalDateTime.now();

    public User(UserCreateRequestDTO userCreateRequestDTO) {
        this.username = userCreateRequestDTO.getUsername();
        this.password = userCreateRequestDTO.getPassword();
        this.email = userCreateRequestDTO.getEmail();
        this.level = 1;
        this.lastLoginAt = LocalDateTime.now();
    }
}
