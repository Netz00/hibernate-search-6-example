package com.netz00.hibernatesearch6example.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "value")
    private String value;

    @Setter(AccessLevel.NONE)
    @Column(name = "date_created")
    private Date dateCreated = new Date();

    @Setter(AccessLevel.NONE)
    @Column(name = "date_modified")
    private Date dateModified = new Date();

    @PreUpdate
    protected void preUpdate() {
        this.dateModified = new Date();
    }
}
