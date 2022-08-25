package com.akademik.krs.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="ScoreParameters")
@Table(name="scoreparameters")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ScoreParameters extends Audit<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, unique = true)
    private Integer id;

    @Column(name = "scoreGrade", nullable = false, insertable = true, length = 1)
    private String scoreGrade;

    @Column(name = "bottomThreshold", nullable = false, insertable = true)
    private Integer bottomThreshold;

    @Column(name = "upperThreshold", nullable = false, insertable = true)
    private Integer upperThreshold;
}
