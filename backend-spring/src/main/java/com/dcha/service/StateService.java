package com.dcha.service;

import com.dcha.entity.*;
import com.dcha.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class StateService {

    private final StateRepository stateRepository;
    private final HeritageSiteRepository heritageSiteRepository;
    private final FestivalRepository festivalRepository;
    private final FoodRepository foodRepository;
    private final TraditionalDressRepository dressRepository;
    private final DanceRepository danceRepository;
    private final LanguageRepository languageRepository;
    private final FolkStoryRepository storyRepository;
    private final MuseumArtifactRepository museumRepository;

    public StateService(StateRepository stateRepository,
                        HeritageSiteRepository heritageSiteRepository,
                        FestivalRepository festivalRepository,
                        FoodRepository foodRepository,
                        TraditionalDressRepository dressRepository,
                        DanceRepository danceRepository,
                        LanguageRepository languageRepository,
                        FolkStoryRepository storyRepository,
                        MuseumArtifactRepository museumRepository) {
        this.stateRepository = stateRepository;
        this.heritageSiteRepository = heritageSiteRepository;
        this.festivalRepository = festivalRepository;
        this.foodRepository = foodRepository;
        this.dressRepository = dressRepository;
        this.danceRepository = danceRepository;
        this.languageRepository = languageRepository;
        this.storyRepository = storyRepository;
        this.museumRepository = museumRepository;
    }

    @Transactional(readOnly = true)
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<State> getStateByIdOrSlug(String idOrSlug) {
        if (idOrSlug == null || idOrSlug.isBlank()) return Optional.empty();
        String clean = idOrSlug.trim().toLowerCase();

        try {
            Long id = Long.parseLong(clean);
            Optional<State> state = stateRepository.findById(id);
            if (state.isPresent()) return state;
        } catch (NumberFormatException ignored) {}

        Optional<State> stateBySlug = stateRepository.findBySlug(clean);
        if (stateBySlug.isPresent()) return stateBySlug;

        // Map 2-letter SVG map codes to state slugs
        java.util.Map<String, String> codeToSlug = java.util.Map.ofEntries(
            java.util.Map.entry("wb", "west-bengal"),
            java.util.Map.entry("od", "odisha"),
            java.util.Map.entry("or", "odisha"),
            java.util.Map.entry("mh", "maharashtra"),
            java.util.Map.entry("rj", "rajasthan"),
            java.util.Map.entry("kl", "kerala"),
            java.util.Map.entry("ap", "andhra-pradesh"),
            java.util.Map.entry("tg", "telangana"),
            java.util.Map.entry("ar", "arunachal-pradesh"),
            java.util.Map.entry("as", "assam"),
            java.util.Map.entry("br", "bihar"),
            java.util.Map.entry("cg", "chhattisgarh"),
            java.util.Map.entry("dl", "delhi"),
            java.util.Map.entry("ga", "goa"),
            java.util.Map.entry("gj", "gujarat"),
            java.util.Map.entry("hr", "haryana"),
            java.util.Map.entry("hp", "himachal-pradesh"),
            java.util.Map.entry("jk", "jammu-and-kashmir"),
            java.util.Map.entry("jh", "jharkhand"),
            java.util.Map.entry("ka", "karnataka"),
            java.util.Map.entry("mp", "madhya-pradesh"),
            java.util.Map.entry("mn", "manipur"),
            java.util.Map.entry("ml", "meghalaya"),
            java.util.Map.entry("mz", "mizoram"),
            java.util.Map.entry("nl", "nagaland"),
            java.util.Map.entry("pb", "punjab"),
            java.util.Map.entry("sk", "sikkim"),
            java.util.Map.entry("tn", "tamil-nadu"),
            java.util.Map.entry("tr", "tripura"),
            java.util.Map.entry("up", "uttar-pradesh"),
            java.util.Map.entry("uk", "uttarakhand"),
            java.util.Map.entry("ut", "uttarakhand"),
            java.util.Map.entry("py", "puducherry"),
            java.util.Map.entry("an", "andaman-and-nicobar-islands"),
            java.util.Map.entry("ch", "chandigarh"),
            java.util.Map.entry("dh", "dadra-and-nagar-haveli-and-daman-and-diu"),
            java.util.Map.entry("ld", "lakshadweep"),
            java.util.Map.entry("la", "ladakh")
        );

        if (codeToSlug.containsKey(clean)) {
            Optional<State> fromCode = stateRepository.findBySlug(codeToSlug.get(clean));
            if (fromCode.isPresent()) return fromCode;
        }

        return stateRepository.findAll().stream()
                .filter(s -> s.getName().equalsIgnoreCase(clean) || s.getSlug().equalsIgnoreCase(clean))
                .findFirst();
    }

    @Transactional(readOnly = true)
    public Optional<State> getStateBySlug(String slug) {
        return stateRepository.findBySlug(slug);
    }

    @Transactional(readOnly = true)
    public List<State> getStatesByRegion(String region) {
        return stateRepository.findByRegion(region);
    }

    @Transactional(readOnly = true)
    public List<State> searchStatesByName(String query) {
        return stateRepository.findByNameContainingIgnoreCase(query);
    }

    @Transactional(readOnly = true)
    public List<HeritageSite> getStateHeritageSites(Long stateId) {
        return heritageSiteRepository.findByStateId(stateId);
    }

    @Transactional(readOnly = true)
    public List<Festival> getStateFestivals(Long stateId) {
        return festivalRepository.findByStateId(stateId);
    }

    @Transactional(readOnly = true)
    public List<Food> getStateFoods(Long stateId) {
        return foodRepository.findByStateId(stateId);
    }

    @Transactional(readOnly = true)
    public List<TraditionalDress> getStateDresses(Long stateId) {
        return dressRepository.findByStateId(stateId);
    }

    @Transactional(readOnly = true)
    public List<Dance> getStateDances(Long stateId) {
        return danceRepository.findByStateId(stateId);
    }

    @Transactional(readOnly = true)
    public List<Language> getStateLanguages(Long stateId) {
        return languageRepository.findByStateId(stateId);
    }

    @Transactional(readOnly = true)
    public List<FolkStory> getStateStories(Long stateId) {
        return storyRepository.findByStateId(stateId);
    }

    @Transactional(readOnly = true)
    public List<MuseumArtifact> getStateMuseumArtifacts(Long stateId) {
        return museumRepository.findByStateId(stateId);
    }
}
