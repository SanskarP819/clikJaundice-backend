package com.clikjaundice.controller;

import com.clikjaundice.dto.TestDtos.ApiResponse;
import com.clikjaundice.dto.TestDtos.SaveTestResponse;
import com.clikjaundice.dto.TestDtos.TestRecordDto;
import com.clikjaundice.service.TestRecordService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/tests")
@CrossOrigin(origins = "*")
public class TestRecordController {

    @Autowired
    private TestRecordService service;

    @PostMapping(value = "/save", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<SaveTestResponse>> saveTest(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestPart("patientName")                             String patientName,
            @RequestPart(value = "patientId", required = false)     String patientId,
            @RequestPart("bly")                                     String bly,
            @RequestPart("blz")                                     String blz,
            @RequestPart("riskLevel")                               String riskLevel,
            @RequestPart(value = "chosenChannel", required = false) String chosenChannel,
            @RequestPart(value = "image", required = false)         MultipartFile image,
            HttpServletRequest request
    ) {
        try {
            String host = request.getServerName();
            SaveTestResponse response = service.saveTest(
                    userDetails.getUsername(),
                    patientName, patientId,
                    Double.parseDouble(bly),
                    Double.parseDouble(blz),
                    riskLevel, chosenChannel,
                    image, host
            );
            return ResponseEntity.ok(ApiResponse.ok(response));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error("Failed to save: " + e.getMessage()));
        }
    }

    @GetMapping("/history")
    public ResponseEntity<ApiResponse<List<TestRecordDto>>> getHistory(
            @AuthenticationPrincipal UserDetails userDetails,
            HttpServletRequest request
    ) {
        try {
            String host = request.getServerName();
            List<TestRecordDto> records = service.getAllTests(userDetails.getUsername(), host);
            return ResponseEntity.ok(ApiResponse.ok(records));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }

    @GetMapping("/history/{patientId}")
    public ResponseEntity<ApiResponse<List<TestRecordDto>>> getPatientHistory(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable String patientId,
            HttpServletRequest request
    ) {
        try {
            String host = request.getServerName();
            List<TestRecordDto> records = service.getPatientTests(userDetails.getUsername(), patientId, host);
            return ResponseEntity.ok(ApiResponse.ok(records));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error(e.getMessage()));
        }
    }
}