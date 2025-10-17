package com.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Short addressId;
    @Column(name = "address", length = 50, nullable = false)
    private String address;
    @Column(name = "address2", length = 50)
    private String address2;
    @Column(name = "district", length = 20, nullable = false)
    private String district;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City cityId;
    @Column(name = "postal_code", length = 10)
    private String postalCode;
    @Column(name = "phone", length = 20, nullable = false)
    private String phone;
    @Column(name = "last_update", nullable = false, insertable = false, updatable = false)
    private LocalDateTime lastUpdate;

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }
}
