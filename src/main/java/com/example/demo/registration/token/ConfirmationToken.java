package com.example.demo.registration.token;

import com.example.demo.app_users.AppUser;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "confirmation_tokens")
public class ConfirmationToken {

    @Id
    @SequenceGenerator(
            name = "confirmation_tokens_id_seq",
            sequenceName = "confirmation_tokens_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "confirmation_tokens_id_seq"
    )
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false, name = "createdat")
    private LocalDateTime createdAt;
    @Column(nullable = false, name = "expiresat")
    private LocalDateTime expiresAt;
    @Column(name = "confirmedat")
    private LocalDateTime confirmedAt;

    @ManyToOne
    @JoinColumn(
            nullable = false,
            name = "appuser"
    )
    private AppUser appUser;

    public ConfirmationToken(String token,
                             LocalDateTime createdAt,
                             LocalDateTime expiresAt,
                             AppUser appUser) {
        this.token = token;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.appUser = appUser;
    }
}
