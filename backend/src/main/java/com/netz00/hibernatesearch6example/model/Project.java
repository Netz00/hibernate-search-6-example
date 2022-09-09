package com.netz00.hibernatesearch6example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "project",
        uniqueConstraints = {
                @UniqueConstraint(
                        columnNames = "name",
                        name = "unq_name")
        }
)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Setter(AccessLevel.NONE)
    @Column(name = "date_created")
    private Date dateCreated = new Date();

    @ManyToMany(mappedBy = "projects")
    @JsonIgnoreProperties(value = {"projects"})
    private Set<Freelancer> freelancers = new HashSet<>();
}
