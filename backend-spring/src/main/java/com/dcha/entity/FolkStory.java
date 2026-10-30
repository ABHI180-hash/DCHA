package com.dcha.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "folk_stories")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FolkStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @Column(nullable = false)
    private String title;

    @Column(name = "story_text", columnDefinition = "TEXT", nullable = false)
    private String storyText;

    private String moral;

    private String category;

    @Column(name = "cultural_significance", columnDefinition = "TEXT")
    private String culturalSignificance;

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

    public String getStoryText() { return storyText; }
    public void setStoryText(String storyText) { this.storyText = storyText; }

    public String getMoral() { return moral; }
    public void setMoral(String moral) { this.moral = moral; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getCulturalSignificance() { return culturalSignificance; }
    public void setCulturalSignificance(String culturalSignificance) { this.culturalSignificance = culturalSignificance; }

    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
