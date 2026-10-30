package com.dcha.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "states")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String slug;

    private String capital;

    private String region;

    @Column(nullable = false)
    private String type; // 'state' or 'union_territory'

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "cultural_significance", columnDefinition = "TEXT")
    private String culturalSignificance;

    @Column(columnDefinition = "TEXT")
    private String languages;

    private Integer area;

    private Long population;

    @Column(name = "established_year")
    private Integer establishedYear;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "map_lat")
    private Double mapLat;

    @Column(name = "map_lng")
    private Double mapLng;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSlug() { return slug; }
    public void setSlug(String slug) { this.slug = slug; }

    public String getCapital() { return capital; }
    public void setCapital(String capital) { this.capital = capital; }

    public String getRegion() { return region; }
    public void setRegion(String region) { this.region = region; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCulturalSignificance() { return culturalSignificance; }
    public void setCulturalSignificance(String culturalSignificance) { this.culturalSignificance = culturalSignificance; }

    public String getLanguages() { return languages; }
    public void setLanguages(String languages) { this.languages = languages; }

    public Integer getArea() { return area; }
    public void setArea(Integer area) { this.area = area; }

    public Long getPopulation() { return population; }
    public void setPopulation(Long population) { this.population = population; }

    public Integer getEstablishedYear() { return establishedYear; }
    public void setEstablishedYear(Integer establishedYear) { this.establishedYear = establishedYear; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public Double getMapLat() { return mapLat; }
    public void setMapLat(Double mapLat) { this.mapLat = mapLat; }

    public Double getMapLng() { return mapLng; }
    public void setMapLng(Double mapLng) { this.mapLng = mapLng; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
