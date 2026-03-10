package com.clikjaundice.repository;

import com.clikjaundice.model.Doctor;
import com.clikjaundice.model.TestRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRecordRepository extends JpaRepository<TestRecord, Long> {

    // Get all tests for a specific doctor — newest first
    List<TestRecord> findByDoctorOrderByTestedAtDesc(Doctor doctor);

    // Get all tests for a patient under a specific doctor
    List<TestRecord> findByDoctorAndPatientIdOrderByTestedAtDesc(Doctor doctor, String patientId);
}