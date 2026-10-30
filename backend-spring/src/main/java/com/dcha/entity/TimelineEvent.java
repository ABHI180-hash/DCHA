package com.dcha.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "timeline_events")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimelineEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id")
    private State state;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "year_start", nullable = false)
    private Integer yearStart;

    @Column(name = "year_end")
    private Integer yearEnd;

    private String era; // 'Ancient', 'Medieval', 'Early Modern', 'Colonial', 'Independence', 'Modern'

    private String category;

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

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Integer getYearStart() { return yearStart; }
    public void setYearStart(Integer yearStart) { this.yearStart = yearStart; }

    public Integer getYearEnd() { return yearEnd; }
    public void setYearEnd(Integer yearEnd) { this.yearEnd = yearEnd; }

    public String getEra() { return era; }
    public void setEra(String era) { this.era = era; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
