package com.dcha.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "languages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "state_id", nullable = false)
    private State state;

    @Column(nullable = false)
    private String name;

    private String script;

    @Column(name = "speakers_approx")
    private Long speakersApprox;

    @Column(name = "is_official")
    @Builder.Default
    private Boolean isOfficial = false;

    @Column(columnDefinition = "TEXT")
    private String description;

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

    public String getScript() { return script; }
    public void setScript(String script) { this.script = script; }

    public Long getSpeakersApprox() { return speakersApprox; }
    public void setSpeakersApprox(Long speakersApprox) { this.speakersApprox = speakersApprox; }

    public Boolean getIsOfficial() { return isOfficial; }
    public void setIsOfficial(Boolean isOfficial) { this.isOfficial = isOfficial; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getSourceName() { return sourceName; }
    public void setSourceName(String sourceName) { this.sourceName = sourceName; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
