package com.dcha.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiRequest {

    @NotBlank(message = "Prompt cannot be empty")
    @Size(max = 1000, message = "Prompt cannot exceed 1000 characters")
    private String prompt;

    private String context; // e.g. "heritage", "story", "state", "general"

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public String getContext() { return context; }
    public void setContext(String context) { this.context = context; }
}
