package com.netz00.hibernatesearch6example.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.search.engine.backend.types.Aggregable;
import org.hibernate.search.engine.backend.types.Projectable;
import org.hibernate.search.engine.backend.types.Searchable;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "freelancer", uniqueConstraints = {@UniqueConstraint(columnNames = "username", name = "unq_username")})
@Indexed(index = "idx_freelancer")
public class Freelancer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    @GenericField(name = "id", projectable = Projectable.YES, sortable = Sortable.YES, searchable = Searchable.NO, aggregable = Aggregable.NO)
    private Long id;

    @Column(name = "username")
    @FullTextField(name = "username", analyzer = "edgeNgram", searchAnalyzer = "standard", projectable = Projectable.YES, searchable = Searchable.YES)
    @KeywordField(name = "username_sort", normalizer = "lowercase", sortable = Sortable.YES)
    private String username;

    @Column(name = "first_name")
    @FullTextField(name = "first_name", analyzer = "edgeNgram", searchAnalyzer = "standard", projectable = Projectable.YES, searchable = Searchable.YES)
    @KeywordField(name = "first_name_sort", normalizer = "lowercase", sortable = Sortable.YES)
    private String firstName;

    @Column(name = "last_name")
    @FullTextField(name = "last_name", analyzer = "edgeNgram", searchAnalyzer = "standard", projectable = Projectable.YES, searchable = Searchable.YES)
    @KeywordField(name = "last_name_sort", normalizer = "lowercase", sortable = Sortable.YES)
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<>();

    // Unidirectional
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "freelancer_categories", joinColumns = @JoinColumn(name = "freelancer_id"), inverseJoinColumns = @JoinColumn(name = "categories_id"))
    @IndexedEmbedded(name = "categories", includePaths = {"title"})
    private Set<Category> categories = new HashSet<>();

    // Bidirectional
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "freelancer_projects", joinColumns = @JoinColumn(name = "freelancer_id"), inverseJoinColumns = @JoinColumn(name = "project_id"))
    @JsonIgnoreProperties(value = {"freelancers"}, allowSetters = true)
    private Set<Project> projects = new HashSet<>();

    public Freelancer addComment(Comment comment) {
        comments.add(comment);
        return this;
    }

    public Freelancer removeComment(Comment comment) {
        comments.remove(comment);
        return this;
    }

    public Freelancer addCategory(Category category) {
        categories.add(category);
        return this;
    }

    public Freelancer removeCategory(Category category) {
        categories.remove(category);
        return this;
    }

    public Freelancer addProject(Project project) {
        projects.add(project);
        return this;
    }

    public Freelancer removeProject(Project project) {
        projects.remove(project);
        return this;
    }
}

