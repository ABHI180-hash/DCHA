package com.dcha.dto;

import lombok.*;

@Getter
@Setter
public class AiResponse {

    private Boolean success;
    private String response;
    private String context;
    private Long timestamp;

    public AiResponse() {}

    public AiResponse(Boolean success, String response, String context, Long timestamp) {
        this.success = success;
        this.response = response;
        this.context = context;
        this.timestamp = timestamp;
    }

    public static AiResponseBuilder builder() {
        return new AiResponseBuilder();
    }

    public Boolean getSuccess() { return success; }
    public void setSuccess(Boolean success) { this.success = success; }

    public String getResponse() { return response; }
    public void setResponse(String response) { this.response = response; }

    public String getContext() { return context; }
    public void setContext(String context) { this.context = context; }

    public Long getTimestamp() { return timestamp; }
    public void setTimestamp(Long timestamp) { this.timestamp = timestamp; }

    public static class AiResponseBuilder {
        private Boolean success;
        private String response;
        private String context;
        private Long timestamp;

        public AiResponseBuilder success(Boolean success) { this.success = success; return this; }
        public AiResponseBuilder response(String response) { this.response = response; return this; }
        public AiResponseBuilder context(String context) { this.context = context; return this; }
        public AiResponseBuilder timestamp(Long timestamp) { this.timestamp = timestamp; return this; }
        public AiResponse build() { return new AiResponse(success, response, context, timestamp); }
    }
}
