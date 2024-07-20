package org.example.entities;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "order_statuses")
public class OrderStatusEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 150, nullable = false)
    private String name;
}
