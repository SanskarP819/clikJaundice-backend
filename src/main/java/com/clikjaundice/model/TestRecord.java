package com.clikjaundice.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Entity
@Table(name = "test_records")
public class TestRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @Column(name = "patient_id", nullable = false)
    private String patientId;

    @Column(name = "patient_name", nullable = false)
    private String patientName;

    @Column(name = "bly", nullable = false)
    private Double bly;

    @Column(name = "blz", nullable = false)
    private Double blz;

    @Column(name = "risk_level", nullable = false)
    private String riskLevel;

    @Column(name = "chosen_channel")
    private String chosenChannel;

    @Column(name = "image_path")
    private String imagePath;

    @Column(name = "tested_at", nullable = false)
    private LocalDateTime testedAt;

    @PrePersist
    public void prePersist() {
        this.testedAt = LocalDateTime.now(ZoneOffset.UTC);
    }

    public TestRecord() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Doctor getDoctor() { return doctor; }
    public void setDoctor(Doctor doctor) { this.doctor = doctor; }

    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public Double getBly() { return bly; }
    public void setBly(Double bly) { this.bly = bly; }

    public Double getBlz() { return blz; }
    public void setBlz(Double blz) { this.blz = blz; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public String getChosenChannel() { return chosenChannel; }
    public void setChosenChannel(String chosenChannel) { this.chosenChannel = chosenChannel; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public LocalDateTime getTestedAt() { return testedAt; }
    public void setTestedAt(LocalDateTime testedAt) { this.testedAt = testedAt; }
}