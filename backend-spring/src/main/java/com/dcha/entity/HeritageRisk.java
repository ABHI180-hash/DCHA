package com.dcha.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "heritage_risks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeritageRisk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "heritage_site_id")
    private HeritageSite heritageSite;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @Column(name = "site_name")
    private String siteName;

    @Column(nullable = false)
    private String threat;

    @Column(name = "risk_level")
    private String riskLevel; // 'LOW', 'MODERATE', 'HIGH', 'CRITICAL'

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "preservation_status")
    private String preservationStatus;

    @Column(name = "suggested_action", columnDefinition = "TEXT")
    private String suggestedAction;

    @Column(name = "source_name")
    private String sourceName;

    @Column(name = "source_url")
    private String sourceUrl;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public HeritageSite getHeritageSite() { return heritageSite; }
    public void setHeritageSite(HeritageSite heritageSite) { this.heritageSite = heritageSite; }

    public State getState() { return state; }
    public void setState(State state) { this.state = state; }

    public String getSiteName() { return siteName; }
    public void setSiteName(String siteName) { this.siteName = siteName; }

    public String getThreat() { return threat; }
    public void setThreat(String threat) { this.threat = threat; }

    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPreservationStatus() { return preservationStatus; }
    public void setPreservationStatus(String preservationStatus) { this.preservationStatus = preservationStatus; }

    public String getSuggestedAction() { return suggestedAction; }
    public void setSuggestedAction(String suggestedAction) { this.suggestedAction = suggestedAction; }

    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }

    public String getSourceUrl() { return sourceUrl; }
    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
