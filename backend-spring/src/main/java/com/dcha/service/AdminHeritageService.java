package com.dcha.service;

import com.dcha.entity.*;
import com.dcha.repository.*;
import com.dcha.exception.ResourceNotFoundException;
import com.dcha.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AdminHeritageService {

    private final StateRepository stateRepository;
    private final HeritageSiteRepository heritageSiteRepository;
    private final MuseumArtifactRepository museumArtifactRepository;
    private final FestivalRepository festivalRepository;
    private final FoodRepository foodRepository;
    private final DanceRepository danceRepository;
    private final TraditionalDressRepository dressRepository;
    private final LanguageRepository languageRepository;
    private final FolkStoryRepository storyRepository;
    private final CulturalEventRepository eventRepository;
    private final AudioItemRepository audioRepository;
    private final HeritageRiskRepository riskRepository;

    public AdminHeritageService(StateRepository stateRepository,
                                HeritageSiteRepository heritageSiteRepository,
                                MuseumArtifactRepository museumArtifactRepository,
                                FestivalRepository festivalRepository,
                                FoodRepository foodRepository,
                                DanceRepository danceRepository,
                                TraditionalDressRepository dressRepository,
                                LanguageRepository languageRepository,
                                FolkStoryRepository storyRepository,
                                CulturalEventRepository eventRepository,
                                AudioItemRepository audioRepository,
                                HeritageRiskRepository riskRepository) {
        this.stateRepository = stateRepository;
        this.heritageSiteRepository = heritageSiteRepository;
        this.museumArtifactRepository = museumArtifactRepository;
        this.festivalRepository = festivalRepository;
        this.foodRepository = foodRepository;
        this.danceRepository = danceRepository;
        this.dressRepository = dressRepository;
        this.languageRepository = languageRepository;
        this.storyRepository = storyRepository;
        this.eventRepository = eventRepository;
        this.audioRepository = audioRepository;
        this.riskRepository = riskRepository;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getAllAdminHeritageContent() {
        List<Map<String, Object>> result = new ArrayList<>();

        heritageSiteRepository.findAll().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("name", item.getName());
            map.put("category", "monument");
            map.put("categoryName", "Monument / Heritage Site");
            map.put("stateId", item.getState() != null ? item.getState().getId() : null);
            map.put("stateName", item.getState() != null ? item.getState().getName() : "All India");
            map.put("location", item.getLocation());
            map.put("imageUrl", item.getImageUrl());
            map.put("description", item.getDescription());
            map.put("significance", item.getHistoricalSignificance() != null ? item.getHistoricalSignificance() : item.getCulturalSignificance());
            result.add(map);
        });

        museumArtifactRepository.findAll().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("name", item.getName());
            map.put("category", "museum");
            map.put("categoryName", "Museum");
            map.put("stateId", item.getState() != null ? item.getState().getId() : null);
            map.put("stateName", item.getState() != null ? item.getState().getName() : "All India");
            map.put("location", item.getSection() != null ? item.getSection() : "Main Section");
            map.put("imageUrl", item.getImageUrl());
            map.put("description", item.getDescription());
            map.put("significance", item.getCulturalSignificance() != null ? item.getCulturalSignificance() : item.getHistoricalContext());
            result.add(map);
        });

        festivalRepository.findAll().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("name", item.getName());
            map.put("category", "festival");
            map.put("categoryName", "Festival");
            map.put("stateId", item.getState() != null ? item.getState().getId() : null);
            map.put("stateName", item.getState() != null ? item.getState().getName() : "All India");
            map.put("location", item.getCategory() != null ? item.getCategory() : "Festival");
            map.put("imageUrl", item.getImageUrl());
            map.put("description", item.getDescription());
            map.put("significance", item.getCulturalSignificance());
            result.add(map);
        });

        foodRepository.findAll().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("name", item.getName());
            map.put("category", "food");
            map.put("categoryName", "Traditional Food");
            map.put("stateId", item.getState() != null ? item.getState().getId() : null);
            map.put("stateName", item.getState() != null ? item.getState().getName() : "All India");
            map.put("location", item.getCategory());
            map.put("imageUrl", item.getImageUrl());
            map.put("description", item.getDescription());
            map.put("significance", item.getCulturalSignificance());
            result.add(map);
        });

        danceRepository.findAll().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("name", item.getName());
            map.put("category", "dance");
            map.put("categoryName", "Traditional Dance");
            map.put("stateId", item.getState() != null ? item.getState().getId() : null);
            map.put("stateName", item.getState() != null ? item.getState().getName() : "All India");
            map.put("location", item.getCategory());
            map.put("imageUrl", item.getImageUrl());
            map.put("description", item.getDescription());
            map.put("significance", item.getCulturalSignificance());
            result.add(map);
        });

        dressRepository.findAll().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("name", item.getName());
            map.put("category", "dress");
            map.put("categoryName", "Traditional Dress");
            map.put("stateId", item.getState() != null ? item.getState().getId() : null);
            map.put("stateName", item.getState() != null ? item.getState().getName() : "All India");
            map.put("location", item.getGender());
            map.put("imageUrl", item.getImageUrl());
            map.put("description", item.getDescription());
            map.put("significance", item.getCulturalSignificance());
            result.add(map);
        });

        languageRepository.findAll().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("name", item.getName());
            map.put("category", "language");
            map.put("categoryName", "Language");
            map.put("stateId", item.getState() != null ? item.getState().getId() : null);
            map.put("stateName", item.getState() != null ? item.getState().getName() : "All India");
            map.put("location", item.getScript());
            map.put("imageUrl", null);
            map.put("description", item.getDescription());
            map.put("significance", item.getSpeakersApprox() != null ? item.getSpeakersApprox() + " Speakers" : "Regional Language");
            result.add(map);
        });

        storyRepository.findAll().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("name", item.getTitle());
            map.put("category", "story");
            map.put("categoryName", "Folk Story");
            map.put("stateId", item.getState() != null ? item.getState().getId() : null);
            map.put("stateName", item.getState() != null ? item.getState().getName() : "All India");
            map.put("location", item.getCategory());
            map.put("imageUrl", null);
            map.put("description", item.getStoryText());
            map.put("significance", item.getCulturalSignificance());
            result.add(map);
        });

        eventRepository.findAll().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("name", item.getName());
            map.put("category", "cultural-event");
            map.put("categoryName", "Cultural Event");
            map.put("stateId", item.getState() != null ? item.getState().getId() : null);
            map.put("stateName", item.getState() != null ? item.getState().getName() : "All India");
            map.put("location", item.getCategory());
            map.put("imageUrl", null);
            map.put("description", item.getDescription());
            map.put("significance", item.getCulturalSignificance());
            result.add(map);
        });

        audioRepository.findAll().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("name", item.getTitle());
            map.put("category", "audio");
            map.put("categoryName", "Audio / Media");
            map.put("stateId", item.getState() != null ? item.getState().getId() : null);
            map.put("stateName", item.getState() != null ? item.getState().getName() : "All India");
            map.put("location", item.getCategory());
            map.put("imageUrl", item.getAudioUrl());
            map.put("description", item.getDescription());
            map.put("significance", item.getCredit());
            result.add(map);
        });

        riskRepository.findAll().forEach(item -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("name", item.getSiteName() != null ? item.getSiteName() : "Endangered Heritage");
            map.put("category", "at-risk");
            map.put("categoryName", "Living Tradition / At Risk");
            map.put("stateId", item.getState() != null ? item.getState().getId() : null);
            map.put("stateName", item.getState() != null ? item.getState().getName() : "All India");
            map.put("location", item.getRiskLevel());
            map.put("imageUrl", null);
            map.put("description", item.getDescription());
            map.put("significance", item.getThreat());
            result.add(map);
        });

        return result;
    }

    @Transactional
    public Map<String, Object> createHeritageRecord(String category, Map<String, Object> body) {
        String cat = category.toLowerCase().trim();
        Long stateId = body.get("stateId") != null ? Long.parseLong(body.get("stateId").toString()) : null;
        State state = stateId != null ? stateRepository.findById(stateId)
                .orElseThrow(() -> new ResourceNotFoundException("State not found with id: " + stateId)) : null;

        String name = (String) body.getOrDefault("name", body.get("title"));
        if (name == null || name.isBlank()) {
            throw new BadRequestException("Name or Title is required");
        }

        String description = (String) body.getOrDefault("description", body.get("content"));
        String location = (String) body.getOrDefault("location", body.get("region"));
        String imageUrl = (String) body.getOrDefault("imageUrl", body.get("audioUrl"));
        String significance = (String) body.getOrDefault("significance", body.get("culturalSignificance"));

        String slug = name.toLowerCase().replaceAll("[^a-z0-9]+", "-").replaceAll("^-|-$", "");
        if (slug.isBlank()) slug = "item-" + System.currentTimeMillis();

        switch (cat) {
            case "monument":
            case "heritage-site": {
                HeritageSite site = new HeritageSite();
                site.setName(name);
                site.setSlug(slug + "-" + UUID.randomUUID().toString().substring(0, 4));
                site.setState(state);
                site.setLocation(location != null ? location : (state != null ? state.getName() : "India"));
                site.setDescription(description);
                site.setHistoricalSignificance(significance);
                site.setCulturalSignificance((String) body.get("culturalSignificance"));
                site.setPeriod((String) body.getOrDefault("period", "Historical"));
                site.setImageUrl(imageUrl);
                HeritageSite saved = heritageSiteRepository.save(site);
                return Map.of("id", saved.getId(), "category", "monument", "name", saved.getName());
            }
            case "museum": {
                MuseumArtifact museum = new MuseumArtifact();
                museum.setName(name);
                museum.setState(state);
                museum.setDescription(description);
                museum.setHistoricalContext(significance);
                museum.setCulturalSignificance(significance);
                museum.setCategory((String) body.getOrDefault("type", "Museum Artifact"));
                museum.setSection(location != null ? location : "Main Gallery");
                museum.setImageUrl(imageUrl);
                MuseumArtifact saved = museumArtifactRepository.save(museum);
                return Map.of("id", saved.getId(), "category", "museum", "name", saved.getName());
            }
            case "festival": {
                Festival festival = new Festival();
                festival.setName(name);
                festival.setState(state);
                festival.setMonth(10);
                festival.setCategory(location != null ? location : "Cultural Festival");
                festival.setDescription(description);
                festival.setCulturalSignificance(significance);
                festival.setImageUrl(imageUrl);
                Festival saved = festivalRepository.save(festival);
                return Map.of("id", saved.getId(), "category", "festival", "name", saved.getName());
            }
            case "food": {
                Food food = new Food();
                food.setName(name);
                food.setState(state);
                food.setCategory(location != null ? location : "Traditional Dish");
                food.setDescription(description);
                food.setIngredients((String) body.get("ingredients"));
                food.setCulturalSignificance(significance);
                food.setImageUrl(imageUrl);
                Food saved = foodRepository.save(food);
                return Map.of("id", saved.getId(), "category", "food", "name", saved.getName());
            }
            case "dance": {
                Dance dance = new Dance();
                dance.setName(name);
                dance.setState(state);
                dance.setCategory(location != null ? location : "Classical/Folk");
                dance.setDescription(description);
                dance.setCulturalSignificance(significance);
                dance.setImageUrl(imageUrl);
                Dance saved = danceRepository.save(dance);
                return Map.of("id", saved.getId(), "category", "dance", "name", saved.getName());
            }
            case "dress":
            case "traditional-dress": {
                TraditionalDress dress = new TraditionalDress();
                dress.setName(name);
                dress.setState(state);
                dress.setGender(location != null ? location : "Unisex");
                dress.setDescription(description);
                dress.setCulturalSignificance(significance);
                dress.setImageUrl(imageUrl);
                TraditionalDress saved = dressRepository.save(dress);
                return Map.of("id", saved.getId(), "category", "dress", "name", saved.getName());
            }
            case "language": {
                Language language = new Language();
                language.setName(name);
                language.setState(state);
                language.setScript(location != null ? location : "Primary Script");
                language.setSpeakersApprox(1000000L);
                language.setDescription(description);
                Language saved = languageRepository.save(language);
                return Map.of("id", saved.getId(), "category", "language", "name", saved.getName());
            }
            case "story":
            case "folk-story": {
                FolkStory story = new FolkStory();
                story.setTitle(name);
                story.setState(state);
                story.setCategory(location != null ? location : "Folk Legend");
                story.setStoryText(description != null ? description : "A traditional folk tale passed down through generations.");
                story.setCulturalSignificance(significance);
                FolkStory saved = storyRepository.save(story);
                return Map.of("id", saved.getId(), "category", "story", "name", saved.getTitle());
            }
            case "cultural-event": {
                CulturalEvent event = new CulturalEvent();
                event.setName(name);
                event.setState(state);
                event.setCategory(location != null ? location : "Cultural Gathering");
                event.setDescription(description);
                event.setMonth(10);
                event.setCulturalSignificance(significance);
                CulturalEvent saved = eventRepository.save(event);
                return Map.of("id", saved.getId(), "category", "cultural-event", "name", saved.getName());
            }
            case "audio": {
                AudioItem audio = new AudioItem();
                audio.setTitle(name);
                audio.setState(state);
                audio.setCategory(location != null ? location : "Oral History");
                audio.setDescription(description);
                audio.setAudioUrl(imageUrl);
                audio.setCredit(significance);
                AudioItem saved = audioRepository.save(audio);
                return Map.of("id", saved.getId(), "category", "audio", "name", saved.getTitle());
            }
            case "at-risk":
            case "living-tradition": {
                HeritageRisk risk = new HeritageRisk();
                risk.setSiteName(name);
                risk.setState(state);
                risk.setRiskLevel(location != null ? location : "MODERATE");
                risk.setThreat(significance != null ? significance : "Modernization & Urbanization");
                risk.setDescription(description);
                risk.setSuggestedAction((String) body.getOrDefault("actionNeeded", "Documentation & Cultural Patronage"));
                HeritageRisk saved = riskRepository.save(risk);
                return Map.of("id", saved.getId(), "category", "at-risk", "name", saved.getSiteName());
            }
            default:
                throw new BadRequestException("Unsupported category: " + category);
        }
    }

    @Transactional
    public void deleteHeritageRecord(String category, Long id) {
        String cat = category.toLowerCase().trim();
        switch (cat) {
            case "monument":
            case "heritage-site":
                heritageSiteRepository.deleteById(id);
                break;
            case "museum":
                museumArtifactRepository.deleteById(id);
                break;
            case "festival":
                festivalRepository.deleteById(id);
                break;
            case "food":
                foodRepository.deleteById(id);
                break;
            case "dance":
                danceRepository.deleteById(id);
                break;
            case "dress":
            case "traditional-dress":
                dressRepository.deleteById(id);
                break;
            case "language":
                languageRepository.deleteById(id);
                break;
            case "story":
            case "folk-story":
                storyRepository.deleteById(id);
                break;
            case "cultural-event":
                eventRepository.deleteById(id);
                break;
            case "audio":
                audioRepository.deleteById(id);
                break;
            case "at-risk":
            case "living-tradition":
                riskRepository.deleteById(id);
                break;
            default:
                throw new BadRequestException("Unsupported category: " + category);
        }
    }
}
