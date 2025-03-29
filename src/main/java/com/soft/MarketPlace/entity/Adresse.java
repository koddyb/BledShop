package com.soft.MarketPlace.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "adresse")
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDADRESSE")
    private int idAdresse;

    @Column(name = "LOCALISATION")
    private String localisation;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "adresseuser",
            joinColumns = @JoinColumn(name = "IDADRESSE", referencedColumnName = "idAdresse"),
            inverseJoinColumns = @JoinColumn(name = "IDUSER", referencedColumnName = "idUser")
    )
    private List<User> user;

    @Override
    public String toString() {
        return "Adresse{" +
                "idAdresse=" + idAdresse +
                ", user=" + user +
                ", localisation='" + localisation + '\'' +
                '}';
    }
}
