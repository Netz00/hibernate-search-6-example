package com.netz00.hibernatesearch6example.model;

import lombok.*;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import javax.persistence.*;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    @KeywordField(name = "title", normalizer = "lowercase", projectable = Projectable.YES)
    private String title;

    @ManyToMany(mappedBy = "categories")    // Added only for Hibernate Search needs, otherwise can be omitted, and relation set to unidirectional
    Set<Freelancer> freelancers;

}
