package com.netz00.hibernatesearch6example.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "freelancer",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = "username",
                        name = "unq_username")
        }
)
public class Freelancer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @OneToMany(orphanRemoval = true)
    private Set<Comment> comments = new HashSet<>();

    // Unidirectional
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "freelancer_categories", joinColumns = @JoinColumn(name = "freelancer_id"), inverseJoinColumns = @JoinColumn(name = "categories_id"))
    private Set<Category> categories = new HashSet<>();

    // Bidirectional
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "freelancer_clients", joinColumns = @JoinColumn(name = "freelancer_id"), inverseJoinColumns = @JoinColumn(name = "client_id"))
    @JsonIgnoreProperties(value = {"freelancers"}, allowSetters = true)
    private Set<Client> clients = new HashSet<>();

}

