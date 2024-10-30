package pl.sulazula.demo.entity;

import jakarta.persistence.*;

@MappedSuperclass
public abstract class EntityWithRelation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "relatedEntityID")
    private Long relatedEntityId;
}