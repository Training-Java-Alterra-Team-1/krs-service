package com.akademik.krs.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="KrsParameters")
@Table(name="krsparameters")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KrsParameters extends Audit<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, unique = true)
    private Integer id;

    @Column(name = "parameterCode", nullable = false, insertable = true)
    private String parameterCode;

    @Column(name = "value", nullable = false, insertable = true)
    private String value;
}
