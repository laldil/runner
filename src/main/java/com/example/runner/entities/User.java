package com.example.runner.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collection;

@Data
@Table(name = "users")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
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

//    @Column(name = "selected_goals_indexes") todo
//    private List<Integer> selectedGoalsIndexes;
//
//    @Column(name = "selected_split_indexes") todo
//    private List<Integer> selectedSplitIndexes;

    @ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;

//    private Set<Group> groups;
}

// todo
//const List goals = [  'Eat better',
//        'Improve mental strength',  'Build muscle',
//        'Burn fat',  'Relieve stress'
//        ];
//const Map splits = {
//        runningIconSvg: 'Running',  barbellIconSvg: 'Barbell',
//        bodyWeightIconSvg: 'Bodyweight'};
