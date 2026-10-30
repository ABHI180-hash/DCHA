package com.dcha.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "heritage_sites")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HeritageSite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    private String category;

    private String location;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "historical_significance", columnDefinition = "TEXT")
    private String historicalSignificance;

    @Column(name = "cultural_significance", columnDefinition = "TEXT")
    private String culturalSignificance;

    private String period;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "map_lat")
    private Double mapLat;

    @Column(name = "map_lng")
    private Double mapLng;

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

    public State getState() { return state; }
    public void setState(State state) { this.state = state; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getHistoricalSignificance() { return historicalSignificance; }
    public void setHistoricalSignificance(String historicalSignificance) { this.historicalSignificance = historicalSignificance; }

    public String getCulturalSignificance() { return culturalSignificance; }
    public void setCulturalSignificance(String culturalSignificance) { this.culturalSignificance = culturalSignificance; }

    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Double getMapLat() { return mapLat; }
    public void setMapLat(Double mapLat) { this.mapLat = mapLat; }

    public Double getMapLng() { return mapLng; }
    public void setMapLng(Double mapLng) { this.mapLng = mapLng; }

    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }

    public String getSourceUrl() { return sourceUrl; }
    public void setSourceUrl(String sourceUrl) { this.sourceUrl = sourceUrl; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
