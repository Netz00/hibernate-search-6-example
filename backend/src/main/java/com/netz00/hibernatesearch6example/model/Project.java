package com.netz00.hibernatesearch6example.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.engine.backend.types.Searchable;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

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
@Indexed(index = "idx_project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    @GenericField(name = "id", projectable = Projectable.YES, sortable = Sortable.YES, searchable = Searchable.NO, aggregable = Aggregable.NO)
    private Long id;

    @Column(name = "name")
    @FullTextField(name = "name", analyzer = "edgeNgram", searchAnalyzer = "standard", projectable = Projectable.YES, searchable = Searchable.YES)
    @KeywordField(name = "name_sort", normalizer = "lowercase", sortable = Sortable.YES)
    private String name;

    @Column(name = "description")
    private String description;

    @Setter(AccessLevel.NONE)
    @Column(name = "date_created")
    @GenericField(name = "date_created", projectable = Projectable.YES, sortable = Sortable.YES)
    private Date dateCreated = new Date();

    @ManyToMany(mappedBy = "projects")
    @JsonIgnoreProperties(value = {"projects"})
    private Set<Freelancer> freelancers = new HashSet<>();
}
