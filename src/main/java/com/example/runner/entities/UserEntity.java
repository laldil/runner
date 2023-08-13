package com.example.runner.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Collection;
import java.util.List;

@Data
@Table(name = "users")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "isMale")
    private Boolean isMale;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "weight")
    private Integer weight;

    @Column(name = "height")
    private Integer height;

    @Column(name = "fit_percentage")
    private Double fitPercentage;

    @Column(name = "coins")
    private Integer coins;

    @Column(name = "location")
    private String location;

    @Column(name = "total_km")
    private Double totalKm;

    @ElementCollection
    @Column(name = "selected_goals_indexes")
    private List<Integer> selectedGoalsIndexes;

    @ElementCollection
    @Column(name = "selected_split_indexes")
    private List<Integer> selectedSplitIndexes;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<RoleEntity> roles;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private GroupEntity group;
}

// todo
//const List goals = [  'Eat better',
//        'Improve mental strength',  'Build muscle',
//        'Burn fat',  'Relieve stress'
//        ];
//const Map splits = {
//        runningIconSvg: 'Running',  barbellIconSvg: 'Barbell',
//        bodyWeightIconSvg: 'Bodyweight'};
