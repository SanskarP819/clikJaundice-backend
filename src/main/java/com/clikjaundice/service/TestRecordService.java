package com.clikjaundice.service;

import com.clikjaundice.dto.TestDtos.SaveTestResponse;
import com.clikjaundice.dto.TestDtos.TestRecordDto;
import com.clikjaundice.model.Doctor;
import com.clikjaundice.model.TestRecord;
import com.clikjaundice.repository.DoctorRepository;
import com.clikjaundice.repository.TestRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class TestRecordService {

    @Autowired
    private TestRecordRepository testRepo;

    @Autowired
    private DoctorRepository doctorRepo;

    @Value("${app.image.upload-dir}")
    private String uploadDir;

    @Value("${server.port:8080}")
    private String serverPort;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public SaveTestResponse saveTest(
            String username,
            String patientName,
            String patientId,
            Double bly,
            Double blz,
            String riskLevel,
            String chosenChannel,
            MultipartFile image,
            String serverHost
    ) throws IOException {

        Doctor doctor = doctorRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        String pid = (patientId == null || patientId.isBlank())
                ? "CJ-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase()
                : patientId;

        String imagePath = saveImage(image, pid);
        String imageUrl  = buildImageUrl(imagePath, serverHost);

        TestRecord record = new TestRecord();
        record.setDoctor(doctor);
        record.setPatientId(pid);
        record.setPatientName(patientName);
        record.setBly(bly);
        record.setBlz(blz);
        record.setRiskLevel(riskLevel);
        record.setChosenChannel(chosenChannel);
        record.setImagePath(imagePath);

        TestRecord saved = testRepo.save(record);
        return toSaveResponse(saved, imageUrl);
    }

    public List<TestRecordDto> getAllTests(String username, String serverHost) {
        Doctor doctor = doctorRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return testRepo.findByDoctorOrderByTestedAtDesc(doctor)
                .stream()
                .map(r -> toDto(r, serverHost))
                .collect(Collectors.toList());
    }

    public List<TestRecordDto> getPatientTests(String username, String patientId, String serverHost) {
        Doctor doctor = doctorRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return testRepo.findByDoctorAndPatientIdOrderByTestedAtDesc(doctor, patientId)
                .stream()
                .map(r -> toDto(r, serverHost))
                .collect(Collectors.toList());
    }

    private String saveImage(MultipartFile image, String patientId) throws IOException {
        if (image == null || image.isEmpty()) return null;
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
        String ext      = getExtension(image.getOriginalFilename());
        String filename = patientId + "_" + System.currentTimeMillis() + ext;
        Path filePath   = uploadPath.resolve(filename);
        Files.write(filePath, image.getBytes());
        return filePath.toString();
    }

    private String getExtension(String filename) {
        if (filename != null && filename.contains("."))
            return filename.substring(filename.lastIndexOf("."));
        return ".jpg";
    }

    private String buildImageUrl(String imagePath, String serverHost) {
        if (imagePath == null) return null;
        return "http://" + serverHost + ":" + serverPort + "/images/" + new File(imagePath).getName();
    }

    private SaveTestResponse toSaveResponse(TestRecord r, String imageUrl) {
        return new SaveTestResponse(
                r.getId(), r.getPatientId(), r.getPatientName(),
                r.getBly(), r.getBlz(), r.getRiskLevel(), r.getChosenChannel(),
                imageUrl,
                r.getTestedAt() != null ? r.getTestedAt().format(FORMATTER) : null
        );
    }

    private TestRecordDto toDto(TestRecord r, String serverHost) {
        return new TestRecordDto(
                r.getId(), r.getPatientId(), r.getPatientName(),
                r.getBly(), r.getBlz(), r.getRiskLevel(), r.getChosenChannel(),
                buildImageUrl(r.getImagePath(), serverHost),
                r.getTestedAt() != null ? r.getTestedAt().format(FORMATTER) : null
        );
    }
}