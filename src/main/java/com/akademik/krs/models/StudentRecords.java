package com.akademik.krs.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity(name="StudentRecords")
@Table(name="studentrecords")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class StudentRecords extends Audit<String> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, insertable = false, unique = true)
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "studentId", nullable = false, insertable = true, referencedColumnName = "id")
    private Students students;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "courseId", nullable = false, insertable = true, referencedColumnName = "id")
    private Courses courses;

    @Column(name = "semester", nullable = false, insertable = true, length = 1)
    private Integer semester;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "scoreId", nullable = false, insertable = true, referencedColumnName = "id")
    private ScoreParameters scoreParameters;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "krsParametersId", nullable = false, insertable = true, referencedColumnName = "id")
    private KrsParameters krsParameters;
}
