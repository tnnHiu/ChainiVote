package com.chainivote.chainivoteserver.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "poll")
public class PollEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private LocalDateTime startTime;

    @Column(nullable = false)
    private LocalDateTime endTime;

//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//            name = "poll_category",
//            joinColumns = @JoinColumn(name = "poll_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id")
//    )

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false)
    private CategoryEntity category;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity creator;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
//    @ToString.Exclude
    private List<CandidateEntity> candidates = new ArrayList<>();

    @Column(unique = true, nullable = false)
    private String chainId;

    @Column(nullable = false)
    private int status;

    private String urlImage;
}

