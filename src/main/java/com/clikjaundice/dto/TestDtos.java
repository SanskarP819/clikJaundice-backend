package com.clikjaundice.dto;

public class TestDtos {

    public static class SaveTestResponse {
        private Long   id;
        private String patientId;
        private String patientName;
        private Double bly;
        private Double blz;
        private String riskLevel;
        private String chosenChannel;
        private String imageUrl;
        private String testedAt;

        public SaveTestResponse() {}

        public SaveTestResponse(Long id, String patientId, String patientName,
                                Double bly, Double blz, String riskLevel,
                                String chosenChannel, String imageUrl, String testedAt) {
            this.id = id;
            this.patientId = patientId;
            this.patientName = patientName;
            this.bly = bly;
            this.blz = blz;
            this.riskLevel = riskLevel;
            this.chosenChannel = chosenChannel;
            this.imageUrl = imageUrl;
            this.testedAt = testedAt;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
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
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
        public String getTestedAt() { return testedAt; }
        public void setTestedAt(String testedAt) { this.testedAt = testedAt; }
    }

    public static class TestRecordDto {
        private Long   id;
        private String patientId;
        private String patientName;
        private Double bly;
        private Double blz;
        private String riskLevel;
        private String chosenChannel;
        private String imageUrl;
        private String testedAt;

        public TestRecordDto() {}

        public TestRecordDto(Long id, String patientId, String patientName,
                             Double bly, Double blz, String riskLevel,
                             String chosenChannel, String imageUrl, String testedAt) {
            this.id = id;
            this.patientId = patientId;
            this.patientName = patientName;
            this.bly = bly;
            this.blz = blz;
            this.riskLevel = riskLevel;
            this.chosenChannel = chosenChannel;
            this.imageUrl = imageUrl;
            this.testedAt = testedAt;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
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
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
        public String getTestedAt() { return testedAt; }
        public void setTestedAt(String testedAt) { this.testedAt = testedAt; }
    }

    public static class ApiResponse<T> {
        private boolean success;
        private String  message;
        private T       data;

        public ApiResponse() {}

        public ApiResponse(boolean success, String message, T data) {
            this.success = success;
            this.message = message;
            this.data = data;
        }

        public static <T> ApiResponse<T> ok(T data) {
            return new ApiResponse<>(true, "OK", data);
        }

        public static <T> ApiResponse<T> error(String message) {
            return new ApiResponse<>(false, message, null);
        }

        public boolean isSuccess() { return success; }
        public void setSuccess(boolean success) { this.success = success; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
        public T getData() { return data; }
        public void setData(T data) { this.data = data; }
    }
}