package com.dcha.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "museum_artifacts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MuseumArtifact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "historical_context", columnDefinition = "TEXT")
    private String historicalContext;

    @Column(name = "cultural_significance", columnDefinition = "TEXT")
    private String culturalSignificance;

    private String period;

    private String category;

    private String section;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "source_name")
    private String sourceName;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public State getState() { return state; }
    public void setState(State state) { this.state = state; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getHistoricalContext() { return historicalContext; }
    public void setHistoricalContext(String historicalContext) { this.historicalContext = historicalContext; }

    public String getCulturalSignificance() { return culturalSignificance; }
    public void setCulturalSignificance(String culturalSignificance) { this.culturalSignificance = culturalSignificance; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getSection() { return section; }
    public void setSection(String section) { this.section = section; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
