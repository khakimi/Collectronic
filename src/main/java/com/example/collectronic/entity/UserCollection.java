package com.example.collectronic.entity;

import com.example.collectronic.entity.enums.ERole;
import com.example.collectronic.entity.enums.ESubject;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class UserCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private ESubject subject;

    @ElementCollection(targetClass = ESubject.class)
    @CollectionTable(name = "UserCollection_subject",
            joinColumns = @JoinColumn(name = "UserCollection_id"))
    private Set<ESubject> subjects = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER,
            mappedBy = "userCollection", orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    @Column(updatable = false)
    private LocalDateTime createdDate;

    @PrePersist
    protected void onCreate(){
        this.createdDate = LocalDateTime.now();
    }
}
