package com.dcha.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContributionStatusRequest {

    @NotBlank(message = "Status is required")
    private String status; // PENDING, APPROVED, REJECTED

    private String reviewerNotes;

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getReviewerNotes() { return reviewerNotes; }
    public void setReviewerNotes(String reviewerNotes) { this.reviewerNotes = reviewerNotes; }
}
