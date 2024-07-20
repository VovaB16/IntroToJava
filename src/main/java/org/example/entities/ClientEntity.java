package org.example.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "clients")
public class ClientEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 50, nullable = false)
    private String firstName;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(length = 100, nullable = true)
    private String car_model;

    @Column(nullable = true)
    private int car_year;
}
//@OneToMany(mappedBy = "status", cascade = CascadeType.ALL, orphanRemoval = true)
//private Set<OrderEntity> orders = new HashSet<>();
