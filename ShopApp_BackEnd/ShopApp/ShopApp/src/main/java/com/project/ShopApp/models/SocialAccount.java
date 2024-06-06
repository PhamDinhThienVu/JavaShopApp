package com.project.ShopApp.models;

import jakarta.persistence.*;
import lombok.*;


@Table(name = "social_accounts")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id;

    @Column(name = "provider", nullable = true, length = 20)
    private String provider;

    @Column(name = "provider_id", nullable = false, length = 20)
    private String providerId;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "email", nullable = false, length = 150)
    private String email;
}
