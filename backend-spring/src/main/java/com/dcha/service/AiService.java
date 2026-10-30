package com.dcha.service;

import com.dcha.dto.AiRequest;
import com.dcha.dto.AiResponse;
import com.dcha.entity.*;
import com.dcha.repository.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AiService {

    private static final Logger logger = LoggerFactory.getLogger(AiService.class);

    @Value("${ai.api-key:}")
    private String apiKey;

    @Value("${ai.base-url:https://generativelanguage.googleapis.com/v1beta/openai/}")
    private String baseUrl;

    @Value("${ai.model:gemini-1.5-flash}")
    private String model;

    private final StateRepository stateRepository;
    private final HeritageSiteRepository heritageSiteRepository;
    private final FestivalRepository festivalRepository;
    private final FoodRepository foodRepository;
    private final DanceRepository danceRepository;
    private final TraditionalDressRepository dressRepository;
    private final LanguageRepository languageRepository;
    private final FolkStoryRepository folkStoryRepository;
    private final MuseumArtifactRepository museumArtifactRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public AiService(StateRepository stateRepository,
                     HeritageSiteRepository heritageSiteRepository,
                     FestivalRepository festivalRepository,
                     FoodRepository foodRepository,
                     DanceRepository danceRepository,
                     TraditionalDressRepository dressRepository,
                     LanguageRepository languageRepository,
                     FolkStoryRepository folkStoryRepository,
                     MuseumArtifactRepository museumArtifactRepository) {
        this.stateRepository = stateRepository;
        this.heritageSiteRepository = heritageSiteRepository;
        this.festivalRepository = festivalRepository;
        this.foodRepository = foodRepository;
        this.danceRepository = danceRepository;
        this.dressRepository = dressRepository;
        this.languageRepository = languageRepository;
        this.folkStoryRepository = folkStoryRepository;
        this.museumArtifactRepository = museumArtifactRepository;
    }

    public AiResponse processAiQuery(AiRequest request) {
        if (request == null || request.getPrompt() == null || request.getPrompt().isBlank()) {
            return AiResponse.builder()
                    .success(true)
                    .response("🙏 **Namaste! Welcome to the DCHA AI Heritage Assistant.**\n\nI am your comprehensive archival AI guide for Indian civilization, preserving records across all 28 States & 8 UTs, 42+ UNESCO monuments, musical instruments, museums, classical arts, and culinary traditions. How may I assist your exploration today?")
                    .context("general")
                    .timestamp(System.currentTimeMillis())
                    .build();
        }

        String prompt = request.getPrompt().trim();
        String contextType = request.getContext() != null ? request.getContext() : "general";

        // 1. Build database context from indexed entities
        String retrievedContext = buildDatabaseContext(prompt);

        // 2. Check if valid external AI key (e.g. Gemini / OpenAI) is configured
        boolean hasValidKey = apiKey != null && !apiKey.isBlank()
                && !apiKey.startsWith("[") && !apiKey.contains("copy") && !apiKey.contains("YOUR_");

        if (hasValidKey) {
            try {
                AiResponse externalResponse = callExternalAiProvider(prompt, retrievedContext, contextType);
                if (externalResponse != null && Boolean.TRUE.equals(externalResponse.getSuccess())
                        && externalResponse.getResponse() != null
                        && !externalResponse.getResponse().isBlank()) {
                    return externalResponse;
                }
            } catch (Exception e) {
                logger.warn("External AI call failed, falling back to Native DCHA Intelligence: {}", e.getMessage());
            }
        }

        // 3. Native DCHA Encyclopedic Heritage Intelligence Engine
        String intelligentResponse = buildIntelligentHeritageResponse(prompt, retrievedContext);
        return AiResponse.builder()
                .success(true)
                .response(intelligentResponse)
                .context(contextType)
                .timestamp(System.currentTimeMillis())
                .build();
    }

    /**
     * Extracts meaningful keyword tokens from prompt by stripping common filler and question words.
     */
    private String cleanQuery(String prompt) {
        String clean = prompt.toLowerCase();
        clean = clean.replaceAll("(?i)\\b(tell\\s*me\\s*about|what\\s*is|what\\s*are|where\\s*is|can\\s*you\\s*tell|explain|give\\s*me|show\\s*me|details\\s*of|history\\s*of|information\\s*about|ke\\s*bare\\s*me|ke\\s*baare\\s*mein|kya\\s*hai|batao|bataiye|dikhao|kuch\\s*batao|in\\s*india|of\\s*india|famous|popular|best|top|the|about|hai|ka|ki|ke|me|mein)\\b", " ");
        clean = clean.replaceAll("[^a-zA-Z0-9\\s]", " ").trim().replaceAll("\\s+", " ");
        return clean;
    }

    private String buildDatabaseContext(String prompt) {
        StringBuilder sb = new StringBuilder();
        String lower = prompt.toLowerCase();
        String cleaned = cleanQuery(prompt);
        String[] tokens = cleaned.split("\\s+");

        // 1. Match States
        try {
            List<State> states = stateRepository.findAll();
            for (State state : states) {
                String stateName = state.getName().toLowerCase();
                boolean matches = lower.contains(stateName);
                if (!matches) {
                    for (String token : tokens) {
                        if (token.length() >= 4 && stateName.contains(token)) {
                            matches = true;
                            break;
                        }
                    }
                }

                if (matches) {
                    sb.append("📍 **State Archive Record**: ").append(state.getName())
                            .append(" (Capital: ").append(state.getCapital()).append(", Region: ").append(state.getRegion()).append(")\n")
                            .append("   • Cultural Identity: ").append(state.getDescription()).append("\n");

                    List<HeritageSite> sites = heritageSiteRepository.findByStateId(state.getId());
                    if (sites != null && !sites.isEmpty()) {
                        sb.append("   • Heritage Monuments: ")
                                .append(sites.stream().map(HeritageSite::getName).limit(4).collect(Collectors.joining(", ")))
                                .append("\n");
                    }

                    List<Festival> festivals = festivalRepository.findByStateId(state.getId());
                    if (festivals != null && !festivals.isEmpty()) {
                        sb.append("   • Celebrated Festivals: ")
                                .append(festivals.stream().map(Festival::getName).limit(4).collect(Collectors.joining(", ")))
                                .append("\n");
                    }

                    List<Food> foods = foodRepository.findByStateId(state.getId());
                    if (foods != null && !foods.isEmpty()) {
                        sb.append("   • Signature Cuisines: ")
                                .append(foods.stream().map(Food::getName).limit(4).collect(Collectors.joining(", ")))
                                .append("\n");
                    }

                    List<Dance> dances = danceRepository.findByStateId(state.getId());
                    if (dances != null && !dances.isEmpty()) {
                        sb.append("   • Performing Arts & Dance: ")
                                .append(dances.stream().map(Dance::getName).limit(4).collect(Collectors.joining(", ")))
                                .append("\n");
                    }
                    sb.append("\n");
                }
            }
        } catch (Exception e) {
            logger.debug("Database state context search skipped: {}", e.getMessage());
        }

        // 2. Match Monuments directly
        try {
            for (String token : tokens) {
                if (token.length() >= 4) {
                    List<HeritageSite> sites = heritageSiteRepository.findByNameContainingIgnoreCase(token);
                    if (sites != null && !sites.isEmpty()) {
                        sb.append("🏛️ **Matched Archaeological Sites**:\n");
                        for (HeritageSite s : sites.stream().limit(3).toList()) {
                            sb.append("   • **").append(s.getName()).append("**: ").append(s.getDescription()).append("\n");
                        }
                        break;
                    }
                }
            }
        } catch (Exception ignored) {}

        return sb.toString().trim();
    }

    private AiResponse callExternalAiProvider(String userPrompt, String retrievedContext, String contextType) {
        try {
            SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
            requestFactory.setConnectTimeout(8000);
            requestFactory.setReadTimeout(12000);
            RestTemplate restTemplate = new RestTemplate(requestFactory);

            String endpointUrl = baseUrl.endsWith("/") ? baseUrl + "chat/completions" : baseUrl + "/chat/completions";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            String systemMessage = "You are the DCHA AI Heritage Assistant, an expert scholar and conversational curator for the Digital Culture Heritage Archive of India. " +
                    "Your mission is to provide deeply knowledgeable, elegant, authentic, and inspiring answers about Indian heritage, monuments, traditions, musical instruments, museums, and historical periods. " +
                    "Reference the DCHA platform features (Explore Archive, 36 States & UTs Directory, Heritage Sites, Cultural Music Archive, Museums & Virtual Vault, Subcontinent Timeline, and Interactive Map) when helpful.\n\n" +
                    "Database Context:\n" + (retrievedContext.isBlank() ? "No direct DB entity matched; synthesize from comprehensive Indian civilizational knowledge." : retrievedContext);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            requestBody.put("messages", List.of(
                    Map.of("role", "system", "content", systemMessage),
                    Map.of("role", "user", "content", userPrompt)
            ));
            requestBody.put("temperature", 0.7);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(endpointUrl, entity, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful() && responseEntity.getBody() != null) {
                JsonNode rootNode = objectMapper.readTree(responseEntity.getBody());
                JsonNode choices = rootNode.path("choices");
                if (choices.isArray() && !choices.isEmpty()) {
                    String aiText = choices.get(0).path("message").path("content").asText();
                    if (!aiText.isBlank()) {
                        return AiResponse.builder()
                                .success(true)
                                .response(aiText)
                                .context(contextType)
                                .timestamp(System.currentTimeMillis())
                                .build();
                    }
                }
            }
        } catch (Exception ex) {
            logger.warn("External AI call encountered exception: {}", ex.getMessage());
        }
        return null;
    }

    /**
     * Comprehensive Knowledge Brain covering DCHA Platform, Musical Instruments, Museums & Relics,
     * All 36 States & UTs, Classical Dances, Festivals, Textiles, and History.
     */
    private String buildIntelligentHeritageResponse(String userPrompt, String retrievedContext) {
        String lower = userPrompt.toLowerCase().trim();

        // 1. GREETINGS & CASUAL INTERACTION
        if (lower.matches("^(hi|hello|namaste|hey|greetings|hola|pranam|ram ram|sat sri akal|adaab)(\\s+.*|$)")
                || lower.matches("^good\\s*(morning|afternoon|evening).*")) {
            return "🙏 **Namaste! Welcome to the Indian Cultural Heritage Archive (DCHA).**\n\n" +
                   "I am your dedicated **AI Heritage Curator**, equipped with encyclopedic knowledge across India's 5,000-year living civilization.\n\n" +
                   "**Here is what you can ask me right now:**\n" +
                   "• 🏛️ **All 28 States & 8 UTs** — *(e.g. 'Punjab ke bare me batao', 'Culture of Tamil Nadu', 'Arunachal Pradesh heritage')*\n" +
                   "• 🎵 **Musical Instruments & Traditions** — *(e.g. 'Traditional instruments of India', 'Sitar and Veena', 'Carnatic vs Hindustani music')*\n" +
                   "• 🏺 **Museums & Virtual Vault** — *(e.g. 'Museums of India', 'Salar Jung Museum', 'Chola Nataraja Bronze', 'Dancing Girl relic')*\n" +
                   "• 🏰 **Monuments & UNESCO Sites** — *(e.g. 'Taj Mahal', 'Sun Temple Konark', 'Hampi ruins', 'Ajanta & Ellora caves')*\n" +
                   "• 🎭 **Classical & Folk Dances** — *(e.g. '8 classical dance forms', 'Kathakali', 'Bharatanatyam', 'Garba & Bhangra')*\n" +
                   "• ⏳ **Subcontinent Timeline** — *(e.g. 'Indus Valley Civilization', 'Gupta Golden Age', 'Chola Empire')*\n" +
                   "• 🧠 **DCHA Platform Brain** — *(e.g. 'What is DCHA?', 'How does the interactive map work?', 'Audio archive features')*\n\n" +
                   "What would you like to discover today?";
        }

        // 2. DCHA PROJECT BRAIN & PLATFORM CAPABILITIES
        if (lower.contains("dcha") || lower.contains("project") || lower.contains("platform") || lower.contains("website")
                || lower.contains("what can you do") || lower.contains("features") || lower.contains("kaam karta hai")
                || lower.contains("who are you") || lower.contains("aap kaun ho") || lower.contains("tum kaun ho")) {
            return "🏛️ **Digital Culture Heritage Archive (DCHA) — Complete Platform Dossier**\n\n" +
                   "**DCHA** is India's premier digital cultural repository, architected to safeguard, document, and celebrate the civilizational wealth of India across all 28 States and 8 Union Territories.\n\n" +
                   "✨ **Key Platform Modules & Features**:\n" +
                   "1. 🌐 **Explore Archive (`explore.html`)**: Instant multi-attribute search across 100+ monuments, historical periods, and architectural styles.\n" +
                   "2. 🗺️ **36 States & UTs Directory (`states.html`)**: Detailed cultural encyclopedias for every Indian state—covering capitals, languages, folk traditions, signature dishes, and monuments.\n" +
                   "3. 🏛️ **Heritage Sites (`heritage.html`)**: Deep architectural dossiers on 42+ UNESCO World Heritage Sites with geographic GIS mapping.\n" +
                   "4. 🎭 **Culture & Traditions (`culture.html`)**: Master showcases of performing arts, folk paintings (Madhubani, Pattachitra), and ancient languages.\n" +
                   "5. 🎵 **Audio & Living Traditions (`audio.html`)**: An integrated sound vault featuring classical Carnatic and Hindustani ragas, sacred chants, and folk instruments.\n" +
                   "6. 🏺 **Museums of India & Virtual Vault (`museum.html`)**: Directory of 12 national museums and 3D/2D digitized antiquities (including the Chola Nataraja and Harappan Dancing Girl).\n" +
                   "7. ⏳ **Subcontinent Timeline (`timeline.html`)**: Interactive 5,000-year chronological spine from the Indus Valley Civilization (2500 BCE) to Modern India.\n" +
                   "8. 🗺️ **Interactive Geographic Map (`map.html`)**: Spatial mapping of temples, forts, caves, and monasteries across the subcontinent.\n" +
                   "9. 📅 **Cultural Calendar (`calendar.html`)**: Seasonal calendar tracking living festivals like Durga Puja, Hornbill, Pushkar, and Onam.\n" +
                   "10. ⚠️ **Heritage at Risk (`at-risk.html`)**: Conservation telemetry documenting endangered monument structures and artisan crafts.\n" +
                   "11. ✍️ **Community Contributions (`contribute.html`)**: Scholarly crowdsourcing allowing registered members to submit regional folk discoveries.\n" +
                   "12. 👤 **Member Portal & Vault (`profile.html` & `login.html`)**: Personal archival bookmarking, saved collections, and research notes.\n\n" +
                   "---\n*💡 You can navigate to any of these modules using the top navigation bar or ask me specific questions about any region!*";
        }

        // 3. SPECIFIC STATES & REGIONS (Prioritized over generic topic words)

        // PUNJAB
        if (lower.contains("punjab") || lower.contains("amritsar") || lower.contains("sikh") || lower.contains("golden temple")) {
            return "🏛️ **Archival Dossier: Punjab Cultural Heritage**\n\n" +
                   "**Punjab** ('Land of Five Rivers') is a vibrant cultural frontier known for its heroic martial traditions, Sikh spiritual legacy, energetic folk arts, and hearty agricultural cuisine.\n\n" +
                   "🏰 **Iconic Heritage Landmarks**:\n" +
                   "• **Sri Harmandir Sahib (Golden Temple, Amritsar)**: Sacred spiritual center of Sikhism founded by Guru Ram Das in 1577, covered in 24-karat gold leaf, housing the eternal *Guru Granth Sahib* and serving the world's largest free community kitchen (*Langar*).\n" +
                   "• **Jallianwala Bagh Memorial**: National freedom memorial documenting the 1919 massacre.\n" +
                   "• **Qila Mubarak (Bathinda & Patiala)**: Ancient brick and Mughal fortresses associated with Razia Sultana.\n\n" +
                   "🎭 **Folk Performing Arts**:\n" +
                   "• **Bhangra**: High-energy celebratory harvest dance performed to the booming rhythmic patterns of the *Dhol*.\n" +
                   "• **Giddha & Sammi**: Expressive female folk dance accompanied by rhythmic clapping (*tali*) and poetic couplets (*boliyan*).\n\n" +
                   "🍲 **Gastronomic Traditions**:\n" +
                   "• **Makki di Roti & Sarson da Saag**: Signature winter flatbread of maize flour paired with mustard greens and dollops of white butter (*makhan*).\n" +
                   "• **Amritsari Kulcha, Chole & Lassi**: Tandoor-baked flaky stuffed leavened bread paired with chilled spiced churned buttermilk.\n\n" +
                   "🎨 **Handicrafts & Attire**:\n" +
                   "• **Phulkari Embroidery**: Geometric floral silk thread embroidery handcrafted onto coarse hand-spun cotton (*khaddar*).\n" +
                   "• **Jandiala Guru Thathera Metalwork**: UNESCO Intangible Cultural Heritage brass and copper utensil crafting.\n\n" +
                   "---\n*💡 Explore full galleries in DCHA's Punjab State Archive (`state-detail.html?state=punjab`)!*";
        }

        // TAMIL NADU
        if (lower.contains("tamil nadu") || lower.contains("chennai") || lower.contains("madurai") || lower.contains("thanjavur") || lower.contains("chola temple") || lower.contains("brihadeeswarar")) {
            return "🏛️ **Archival Dossier: Tamil Nadu Cultural Heritage**\n\n" +
                   "**Tamil Nadu** represents over 2,500 years of unbroken Dravidian literature, towering temple architecture (*Gopurams*), Carnatic music, and classical dance.\n\n" +
                   "🏰 **UNESCO World Heritage Monuments**:\n" +
                   "• **Great Living Chola Temples**: Brihadeeswarar Temple (Thanjavur), Gangaikonda Cholapuram, and Airavatesvara (Darasuram)—soaring granite pyramidal towers (*Vimanas*).\n" +
                   "• **Monuments at Mamallapuram**: 7th-century Pallava coastal rock-cut monolithic chariots (*Pancha Rathas*) and Shore Temple.\n" +
                   "• **Madurai Meenakshi Amman Temple**: Majestic 14-tower complex dedicated to Meenakshi and Sundareswarar.\n\n" +
                   "🎭 **Classical Performing Arts**:\n" +
                   "• **Bharatanatyam**: Ancient classical temple dance celebrating devotion (*bhakti*) and rhythmic footwork (*adavu*).\n" +
                   "• **Carnatic Music & Margazhi Season**: Annual winter musical festival in Chennai honoring saint-composers.\n\n" +
                   "🍲 **Gastronomic Traditions**:\n" +
                   "• **Chettinad Cuisine**: Fragrant spice-rich cooking with star anise, fennel, and freshly crushed pepper.\n" +
                   "• **Filter Coffee & Idli-Dosa**: Traditional morning staples served with sambar and coconut chutneys.\n\n" +
                   "🎨 **Textiles & Handlooms**:\n" +
                   "• **Kanchipuram Silk Sarees**: Woven with pure mulberry silk and pure gold zari using three-shuttle interlocking borders.\n" +
                   "• **Toda Tribal Embroidery**: Reversible geometric threadwork of the Nilgiri pastoral community.\n\n" +
                   "---\n*💡 Explore full records in DCHA's Tamil Nadu State Archive (`state-detail.html?state=tamil-nadu`)!*";
        }

        // GUJARAT
        if (lower.contains("gujarat") || lower.contains("ahmedabad") || lower.contains("rann of kutch") || lower.contains("rani ki vav") || lower.contains("patola")) {
            return "🏛️ **Archival Dossier: Gujarat Cultural Heritage**\n\n" +
                   "**Gujarat** is India's Western maritime gateway, renowned for ancient Harappan port cities, Mahatma Gandhi's non-violent legacy, colorful folk dances, and master silk weaving.\n\n" +
                   "🏰 **UNESCO Heritage Monuments**:\n" +
                   "• **Rani ki Vav (The Queen's Stepwell, Patan)**: 11th-century inverted subterranean temple stepwell carved with 800+ intricate Hindu deities.\n" +
                   "• **Historic City of Ahmedabad**: India's first UNESCO World Heritage City, featuring medieval wooden pols and Sidi Saiyyed Jali.\n" +
                   "• **Lothal & Dholavira**: World's oldest known tidal dockyard and Harappan water reservoir engineering.\n\n" +
                   "🎭 **Festivals & Folk Dance**:\n" +
                   "• **Navratri & Garba**: The world's longest dance festival—nine consecutive nights of circular devotion around the sacred lamp (*Garbha Deep*).\n" +
                   "• **Rann Utsav**: White salt desert cultural carnival under the full moon in Kutch.\n\n" +
                   "🍲 **Gastronomic Traditions**:\n" +
                   "• **Gujarati Thali**: Perfectly balanced sweet, salty, and sour vegetarian meal featuring Dhokla, Thepla, Undhiyu, Kadhi, and Shrikhand.\n\n" +
                   "🎨 **Handcrafts & Textiles**:\n" +
                   "• **Patan Patola**: World-renowned double-ikat silk weaving where warp and weft are tied and dyed before weaving.\n" +
                   "• **Bandhani & Rogan Art**: Castor oil pigment painting on cloth preserved exclusively by the Khatri family of Nirona.\n\n" +
                   "---\n*💡 Explore full records in DCHA's Gujarat State Archive (`state-detail.html?state=gujarat`)!*";
        }

        // MAHARASHTRA
        if (lower.contains("maharashtra") || lower.contains("mumbai") || lower.contains("ajanta") || lower.contains("ellora") || lower.contains("kailasa temple") || lower.contains("shivaji")) {
            return "🏛️ **Archival Dossier: Maharashtra Cultural Heritage**\n\n" +
                   "**Maharashtra** is a land of rock-cut mountain caves, formidable Maratha forts, devotional Varkari saint poetry, and grand public festivals.\n\n" +
                   "🏰 **UNESCO Heritage Monuments**:\n" +
                   "• **Ajanta & Ellora Caves**: 2nd century BCE to 10th century CE rock-cut caves; Ajanta features exquisite Buddhist mural frescoes, and Ellora houses the monolithic rock-cut **Kailasa Temple**.\n" +
                   "• **Chhatrapati Shivaji Maharaj Terminus (CSMT)**: Victorian Gothic masterpiece in Mumbai.\n" +
                   "• **Maratha Hill Forts**: Raigad, Sinhagad, and Pratapgad built by Chhatrapati Shivaji Maharaj.\n\n" +
                   "🎉 **Festivals & Performing Arts**:\n" +
                   "• **Ganesh Chaturthi**: Grand 10-day community festival introduced in modern form by Lokmanya Tilak.\n" +
                   "• **Lavani & Povada**: Dynamic rhythmic folk dance and heroic ballad singing of Maratha warriors.\n" +
                   "• **Pandharpur Varkari Palkhi**: Devotional foot pilgrimage singing the abhangs of Saints Tukaram and Dnyaneshwar.\n\n" +
                   "🍲 **Gastronomic Traditions**:\n" +
                   "• **Puran Poli & Modak**: Sweet lentil stuffed flatbread and Lord Ganesha's steamed rice-flour coconut dumplings.\n" +
                   "• **Misal Pav & Pithla Bhakri**: Spicy sprouted lentil curry and rural gram flour stew with millet bread.\n\n" +
                   "🎨 **Textiles**:\n" +
                   "• **Paithani Saree**: Pure silk handloom featuring solid gold zari borders and kaleidoscopic peacock (*Mor*) pallu motifs.\n\n" +
                   "---\n*💡 Explore full records in DCHA's Maharashtra State Archive (`state-detail.html?state=maharashtra`)!*";
        }

        // UTTAR PRADESH
        if (lower.contains("uttar pradesh") || lower.contains("varanasi") || lower.contains("lucknow") || lower.contains("kashi") || lower.contains("sarnath") || lower.contains("mathura") || lower.contains("ayodhya")) {
            return "🏛️ **Archival Dossier: Uttar Pradesh Cultural Heritage**\n\n" +
                   "**Uttar Pradesh** is the spiritual heart of the Indo-Gangetic plains, cradle of Hinduism, Buddhism, and Jainism, home to the Taj Mahal and royal Awadhi courtly elegance.\n\n" +
                   "🏰 **UNESCO World Heritage Monuments**:\n" +
                   "• **Taj Mahal (Agra)**: 1631–1648 CE ivory-white marble mausoleum built by Shah Jahan for Mumtaz Mahal.\n" +
                   "• **Agra Fort & Fatehpur Sikri**: Red sandstone Mughal imperial capitals built by Emperor Akbar.\n" +
                   "• **Varanasi (Kashi Ghats)**: The world's oldest continually inhabited spiritual city along the sacred Ganga.\n" +
                   "• **Sarnath**: The Deer Park where Gautama Buddha delivered his first sermon (*Dharmachakra Pravartana*).\n\n" +
                   "🎭 **Classical Arts & Music**:\n" +
                   "• **Kathak**: Lucknow and Banaras Gharanas known for elegant emotional expressions (*bhava*) and rhythmic footwork.\n" +
                   "• **Benares Gharana Music**: Famed for Shehnai (Ustad Bismillah Khan) and classical Tabla maestros.\n\n" +
                   "🍲 **Gastronomic Traditions**:\n" +
                   "• **Awadhi Dum Pukht**: Royal Lucknowi slow-cooking method under sealed dough, producing tender Biryani and Galouti Kebabs.\n" +
                   "• **Banarasi Paan & Malaiyo**: Fragrant betel leaf preparation and delicate winter saffron milk froth.\n\n" +
                   "🎨 **Handcrafts & Textiles**:\n" +
                   "• **Banarasi Brocade Silk**: Opulent bridal sarees woven with pure gold and silver Zari floral foliage (*Jhallar*).\n" +
                   "• **Chikan Embroidery**: Delicate shadow needlework handcrafted on fine muslin in Lucknow.\n\n" +
                   "---\n*💡 Explore full records in DCHA's Uttar Pradesh State Archive (`state-detail.html?state=uttar-pradesh`)!*";
        }

        // WEST BENGAL
        if (lower.contains("bengal") || lower.contains("kolkata") || lower.contains("durga puja") || lower.contains("shantiniketan") || lower.contains("bishnupur")) {
            return "🏛️ **Archival Dossier: West Bengal Cultural Heritage**\n\n" +
                   "West Bengal is renowned as India's cultural capital, celebrated for its literature, performing arts, handloom weaving, and vibrant festivals.\n\n" +
                   "🎉 **Major Festivals & Rituals**:\n" +
                   "• **Durga Puja** (UNESCO Intangible Cultural Heritage): A 10-day grand festival worshipping Goddess Durga, celebrated with ornate pandals, traditional *Dhaka Dhak* drumming, and *Dhunuchi Naach*.\n" +
                   "• **Poila Baisakh**: Bengali New Year celebrated with cultural processions (*Shobhajatra*) and festive feasting.\n\n" +
                   "🏛️ **Heritage Sites & Architecture**:\n" +
                   "• **Victoria Memorial & Marble Palace**: Iconic colonial and neoclassical architectural landmarks in Kolkata.\n" +
                   "• **Sundarbans National Park**: UNESCO World Heritage mangrove forest and Royal Bengal Tiger reserve.\n" +
                   "• **Bishnupur Terracotta Temples**: 17th-century Malla dynasty temples carved with intricate terracotta brickwork.\n\n" +
                   "🎨 **Handcrafts & Textiles**:\n" +
                   "• **Baluchari & Jamdani Silk Sarees**: GI-tagged weaving featuring mythological motifs woven into pure silk.\n" +
                   "• **Kantha Embroidery**: Traditional rural folk quilting technique reusing layered vintage silks.\n\n" +
                   "🍲 **Culinary Heritage**:\n" +
                   "• **Shorshe Ilish & Chingri Malai Curry**: Signature mustard hilsa and coconut prawn delicacies.\n" +
                   "• **Mishti Doi & Rosogolla**: World-famous Bengali dairy confectionery.\n\n" +
                   "---\n*💡 Visit the West Bengal State Archive on DCHA for full photo collections and historical records.*";
        }

        // RAJASTHAN
        if (lower.contains("rajasthan") || lower.contains("jaipur") || lower.contains("jodhpur") || lower.contains("udaipur") || lower.contains("jaisalmer") || lower.contains("hawa mahal")) {
            return "🏛️ **Archival Dossier: Rajasthan Cultural Heritage**\n\n" +
                   "Rajasthan, the 'Land of Kings', represents imperial Rajput architecture, vibrant desert folk traditions, and world-renowned handicrafts.\n\n" +
                   "🏛️ **Key UNESCO Monuments & Forts**:\n" +
                   "• **Hawa Mahal (Palace of Winds)**: 1799 pink sandstone palace built by Maharaja Sawai Pratap Singh with 953 lattice windows (*jharokhas*).\n" +
                   "• **Hill Forts of Rajasthan**: Chittorgarh, Kumbhalgarh, Ranthambore, Amber, Jaisalmer, and Gagron Forts.\n" +
                   "• **Jantar Mantar Jaipur**: 18th-century UNESCO astronomical observatory with stone instruments.\n\n" +
                   "🎭 **Folk Performing Arts**:\n" +
                   "• **Ghoomar & Kalbelia Dance**: Traditional swirling folk dance and nomadic serpent-charmer folk dance (UNESCO Intangible Heritage).\n" +
                   "• **Manganiyar & Langa Ballads**: Desert folk minstrels performing with Sarangi and Kamaicha instruments.\n\n" +
                   "🍲 **Culinary Heritage**:\n" +
                   "• **Dal Baati Churma**: Baked wheat balls served with spiced lentils and clarified ghee.\n" +
                   "• **Laal Maas & Ghevar**: Royal spiced mutton curry and disc-shaped festive honeyed dessert.\n\n" +
                   "🎨 **Handcrafts & Attire**:\n" +
                   "• **Bandhani Tie-Dye & Jaipur Blue Pottery**: Heritage quartz pottery and vibrant tie-dyed textiles.\n\n" +
                   "---\n*💡 Visit the Rajasthan State Archive on DCHA for full photo collections and historical records.*";
        }

        // ODISHA
        if (lower.contains("odisha") || lower.contains("orissa") || lower.contains("puri") || lower.contains("konark") || lower.contains("jagannath")) {
            return "🏛️ **Archival Dossier: Odisha Cultural Heritage**\n\n" +
                   "Odisha is famous for its ancient Kalinga temple architecture, classical Odissi dance, maritime heritage, and sacred festivals.\n\n" +
                   "🏛️ **Monuments & UNESCO Sites**:\n" +
                   "• **Konark Sun Temple**: 13th-century UNESCO World Heritage stone chariot dedicated to Surya, the Sun God, carved with 24 wheels.\n" +
                   "• **Jagannath Temple Puri**: Sacred 12th-century Kalinga architecture spiritual pilgrimage center.\n\n" +
                   "🎭 **Performing Arts & Crafts**:\n" +
                   "• **Odissi Dance**: One of India's 8 classical dance forms rooted in ancient temple sculpture poses (*tribhangi*).\n" +
                   "• **Pattachitra Painting**: Traditional cloth-based scroll painting depicting Krishna-Leela and mythological legends.\n" +
                   "• **Sambalpuri Ikat Silk**: Handloom tie-and-dye weaving technique with intricate curvilinear motifs.\n\n" +
                   "🎉 **Major Festivals**:\n" +
                   "• **Ratha Yatra Puri**: World-famous chariot festival drawing millions of devotees.\n\n" +
                   "---\n*💡 Visit the Odisha State Archive on DCHA for full photo collections and historical records.*";
        }

        // KERALA
        if (lower.contains("kerala") || lower.contains("cochin") || lower.contains("trivandrum") || lower.contains("onam") || lower.contains("kathakali")) {
            return "🏛️ **Archival Dossier: Kerala Cultural Heritage**\n\n" +
                   "Kerala ('God's Own Country') is renowned for classical dance-drama, Ayurveda, palm-fringed backwaters, and ancient maritime trade history.\n\n" +
                   "🎭 **Performing Arts & Rituals**:\n" +
                   "• **Kathakali**: World-renowned classical dance-drama with vibrant face painting, elaborate headgear, and mudra hand gestures.\n" +
                   "• **Theyyam**: Sacred ritualistic dance performance worshiping ancestral deities in North Malabar.\n" +
                   "• **Kalaripayattu**: One of the world's oldest surviving martial arts systems.\n\n" +
                   "🎉 **Festivals & Gastronomy**:\n" +
                   "• **Onam & Vallam Kali**: Harvest festival featuring traditional Snake Boat Races (*Snake Boat Regatta*) and *Sadya* feast served on banana leaves.\n\n" +
                   "🎨 **Handloom & Crafts**:\n" +
                   "• **Kasavu Saree & Aranmula Kannadi**: Traditional gold-bordered cotton sarees and hand-polished metal mirrors.\n\n" +
                   "---\n*💡 Visit the Kerala State Archive on DCHA for full photo collections and historical records.*";
        }

        // KARNATAKA
        if (lower.contains("karnataka") || lower.contains("bengaluru") || lower.contains("hampi") || lower.contains("mysore") || lower.contains("mysuru") || lower.contains("badami") || lower.contains("pattadakal")) {
            return "🏛️ **Archival Dossier: Karnataka Cultural Heritage**\n\n" +
                   "**Karnataka** is a treasure trove of Chalukyan, Hoysala, and Vijayanagara architecture, Carnatic musical traditions, and lush sandalwood forests.\n\n" +
                   "🏰 **UNESCO World Heritage Monuments**:\n" +
                   "• **Group of Monuments at Hampi**: Capital of the Vijayanagara Empire with stone chariot, Virupaksha temple, and musical pillars.\n" +
                   "• **Sacred Ensembles of the Hoysalas (Belur, Halebidu, Somanathapura)**: 12th-century soapstone star-shaped temples with micro-carved friezes.\n" +
                   "• **Pattadakal & Badami Caves**: 7th-century rock-cut cave temples blending Nagara and Dravidian architecture.\n\n" +
                   "🎭 **Performing Arts**:\n" +
                   "• **Yakshagana**: Ancient theatrical folk dance-drama featuring vibrant makeup, elaborate turbans, and loud percussion.\n\n" +
                   "🍲 **Gastronomic Traditions**:\n" +
                   "• **Bisi Bele Bath, Mysore Pak & Filter Coffee**: Spiced lentil rice, gram-flour ghee fudge, and frothy chicory-blended coffee.\n\n" +
                   "🎨 **Textiles & Crafts**:\n" +
                   "• **Mysore Silk & Channapatna Wooden Toys**: Pure silk brocades and vegetable-dyed lacquerware toys.\n\n" +
                   "---\n*💡 Explore full records in DCHA's Karnataka State Archive (`state-detail.html?state=karnataka`)!*";
        }

        // ANDHRA PRADESH & TELANGANA
        if (lower.contains("andhra") || lower.contains("telangana") || lower.contains("hyderabad") || lower.contains("kuchipudi") || lower.contains("charminar") || lower.contains("tirupati")) {
            return "🏛️ **Archival Dossier: Andhra Pradesh & Telangana Heritage**\n\n" +
                   "The Telugu Deccan plateau represents ancient Buddhist stupas, diamond trading dynasties (Golconda), Qutb Shahi architecture, and classical Kuchipudi dance.\n\n" +
                   "🏰 **Iconic Heritage Landmarks**:\n" +
                   "• **Charminar & Golconda Fort (Hyderabad)**: 1591 CE victory monument and the acoustic fortress of the world's richest diamond trade.\n" +
                   "• **Ramappa Temple (UNESCO, Palampet)**: 13th-century Kakatiya temple built with lightweight floating bricks and sandbox seismic technology.\n" +
                   "• **Lepakshi Temple (Andhra)**: 16th-century Vijayanagara hanging pillar and colossal monolithic Nandi bull.\n" +
                   "• **Tirumala Venkateswara Temple**: Ancient spiritual center in the Seshachalam hills.\n\n" +
                   "🎭 **Performing Arts**:\n" +
                   "• **Kuchipudi**: Classical dance-drama originating in the Krishna district, famous for balancing atop a brass plate (*Tarangam*).\n\n" +
                   "🍲 **Gastronomic Traditions**:\n" +
                   "• **Hyderabadi Dum Biryani & Gongura Pachadi**: Fragrant saffron basmati rice layered with spiced meat, and tangy sorrel leaf chutney.\n\n" +
                   "🎨 **Textiles**:\n" +
                   "• **Pochampally Ikat & Kalamkari**: Geometric tie-dye silks and natural mineral block-printed temple cloths.\n\n" +
                   "---\n*💡 Explore DCHA's Andhra Pradesh and Telangana Archives (`states.html`) for full details!*";
        }

        // BIHAR & JHARKHAND
        if (lower.contains("bihar") || lower.contains("jharkhand") || lower.contains("nalanda") || lower.contains("bodh gaya") || lower.contains("madhubani") || lower.contains("chhath")) {
            return "🏛️ **Archival Dossier: Bihar & Jharkhand Cultural Heritage**\n\n" +
                   "The ancient Magadha empire lands gave the world Nalanda University, Buddhism, Jainism, sacred tribal mud wall art, and ancient martial dance.\n\n" +
                   "🏰 **UNESCO World Heritage Sites**:\n" +
                   "• **Nalanda Mahavihara Ruins (Bihar)**: 5th-century CE ancient university that hosted 10,000 scholars from across Asia.\n" +
                   "• **Mahabodhi Temple Complex (Bodh Gaya)**: Built around the sacred Bodhi Tree where Prince Siddhartha attained enlightenment.\n" +
                   "• **Ashokan Pillars**: Polished sandstone monoliths bearing Emperor Ashoka's Dhamma edicts.\n\n" +
                   "🎨 **Folk Arts & Traditions**:\n" +
                   "• **Madhubani (Mithila) Painting**: Geometric folk paintings made with twigs, fingers, and natural dyes depicting nature and epics.\n" +
                   "• **Sohrai & Khovar Murals (Jharkhand)**: Indigenous mud wall murals created by tribal women during harvest and marriage seasons.\n" +
                   "• **Chhau Dance**: Masked martial dance depicting mythological battles.\n\n" +
                   "🎉 **Sacred Festivals & Food**:\n" +
                   "• **Chhath Puja**: Rigorous 4-day Vedic sun worship offering gratitude to Surya and Usha on the banks of rivers.\n" +
                   "• **Litti Chokha**: Roasted whole wheat flour balls stuffed with spiced roasted gram flour (*sattu*), served with roasted brinjal.\n\n" +
                   "---\n*💡 Explore full records in DCHA's Bihar and Jharkhand State Archives (`states.html`)!*";
        }

        // MADHYA PRADESH & CHHATTISGARH
        if (lower.contains("madhya pradesh") || lower.contains("chhattisgarh") || lower.contains("khajuraho") || lower.contains("sanchi") || lower.contains("bhimbetka") || lower.contains("bastar")) {
            return "🏛️ **Archival Dossier: Central India (MP & Chhattisgarh) Heritage**\n\n" +
                   "The heart of India preserves 30,000-year-old prehistoric rock shelters, Emperor Ashoka's Great Stupa, Chandela temple carvings, and Bastar tribal metallurgy.\n\n" +
                   "🏰 **UNESCO World Heritage Sites**:\n" +
                   "• **Khajuraho Group of Monuments**: 10th-century Chandela Nagara sandstone temples with celestial sculptures.\n" +
                   "• **Great Stupa at Sanchi**: 3rd-century BCE Buddhist stone hemisphere commissioned by Emperor Ashoka with carved Torana gateways.\n" +
                   "• **Bhimbetka Rock Shelters**: 30,000-year-old Stone Age cave paintings of prehistoric hunters and animals.\n\n" +
                   "🎨 **Indigenous Tribal Arts**:\n" +
                   "• **Bastar Dhokra (Bell Metal)**: 4,000-year-old lost-wax metal casting technique preserved by Bastar tribal artisans.\n" +
                   "• **Gond Art & Chanderi Weaving**: Vibrant dot-and-line tribal paintings and featherweight silk-cotton handlooms.\n\n" +
                   "---\n*💡 Explore DCHA's Central India Archives (`states.html?region=Central`) for full collections!*";
        }

        // JAMMU, KASHMIR & LADAKH
        if (lower.contains("kashmir") || lower.contains("ladakh") || lower.contains("srinagar") || lower.contains("leh") || lower.contains("pashmina")) {
            return "🏛️ **Archival Dossier: Jammu, Kashmir & Ladakh Heritage**\n\n" +
                   "The Himalayan northern frontier encompasses breathtaking alpine valley traditions, Sufi mysticism, and high-altitude Tibetan Buddhist monastic culture.\n\n" +
                   "🏰 **Monasteries & Sacred Landmarks**:\n" +
                   "• **Thiksey & Hemis Monasteries (Ladakh)**: Majestic multi-story Mahayana Buddhist gompas housing gold Maitreya Buddha statues.\n" +
                   "• **Martand Sun Temple**: 8th-century stone temple ruins constructed by King Lalitaditya Muktapida.\n" +
                   "• **Mughal Gardens of Srinagar**: Nishat Bagh and Shalimar Bagh designed on Persian Charbagh geometric concepts.\n\n" +
                   "🎭 **Traditions & Rituals**:\n" +
                   "• **Hemis Cham Dance**: Sacred masked lama dance portraying the triumph of spiritual wisdom over ignorance.\n" +
                   "• **Rouf Dance & Sufiana Kalam**: Graceful female Kashmiri folk dance accompanied by the 100-string Santoor.\n\n" +
                   "🎨 **Handicrafts & Textiles**:\n" +
                   "• **Pashmina & Kani Shawls**: Fine undercoat wool of the Changthangi mountain goat, woven with wooden needles.\n" +
                   "• **Paper-Mache & Walnut Wood Carvings**: Intricate floral lacquered artwork introduced from Central Asia.\n\n" +
                   "🍲 **Gastronomic Traditions**:\n" +
                   "• **Kashmiri Wazwan**: Royal 36-course banquet featuring Rogan Josh, Gushtaba, and saffron Kahwa tea.\n" +
                   "• **Ladakhi Thukpa & Khambir**: Whole wheat hearth bread paired with salty butter tea (*Gur-Gur Chai*).\n\n" +
                   "---\n*💡 Explore DCHA's Ladakh and Jammu & Kashmir Archives (`states.html`) for full photography!*";
        }

        // NORTHEAST INDIA (Assam, Arunachal, Meghalaya, Manipur, Nagaland, Mizoram, Tripura, Sikkim)
        if (lower.contains("northeast") || lower.contains("assam") || lower.contains("arunachal") || lower.contains("meghalaya")
                || lower.contains("manipur") || lower.contains("nagaland") || lower.contains("mizoram") || lower.contains("tripura") || lower.contains("sikkim")) {
            return "🏛️ **Archival Dossier: The Eight Sisters of Northeast India**\n\n" +
                   "Northeast India represents a biodiversity hotspot preserving ancient tribal self-governance, sacred nature worship, living root bridges, and distinctive weaving traditions.\n\n" +
                   "🏰 **Key Cultural Highlights Across the 8 States**:\n" +
                   "• **Assam**: Golden **Muga Silk** (found only in Assam), Saint Sankardev's classical **Sattriya Dance**, Bihu harvest festival, and the royal **Charaideo Moidam** burial mounds.\n" +
                   "• **Arunachal Pradesh**: 400-year-old **Tawang Monastery** (second largest globally), Monpa woodblock printing, and indigenous Donyi-Polo sun-moon worship.\n" +
                   "• **Meghalaya**: Khasi and Jaintia **Living Root Bridges** (*Jingkieng Jri*) trained across jungle rivers over centuries, and matrilineal kinship systems.\n" +
                   "• **Manipur**: Devotional classical **Manipuri Dance** (*Raas Leela*), indigenous polo (*Sagol Kangjei*), and the world's only floating national park on Loktak Lake.\n" +
                   "• **Nagaland**: The world-famous **Hornbill Festival**, traditional Morung village dormitories, and intricate warrior beadwork.\n" +
                   "• **Mizoram**: Bamboo **Cheraw Dance** requiring lightning footwork, and bamboo basket weaving.\n" +
                   "• **Tripura**: Rock-cut giant reliefs at **Unakoti** and royal Indo-Saracenic Ujjayanta Palace.\n" +
                   "• **Sikkim**: Sacred Buddhist Himalayan monasteries (Rumtek & Pemayangtse), masked Cham dances, and Losar celebrations.\n\n" +
                   "---\n*💡 Visit DCHA's States Directory (`states.html?region=Northeast`) for comprehensive dossiers on every northeastern state!*";
        }

        // GOA
        if (lower.contains("goa") || lower.contains("panaji") || lower.contains("bom jesus") || lower.contains("shigmo") || lower.contains("feni")) {
            return "🏛️ **Archival Dossier: Goa Cultural Heritage**\n\n" +
                   "**Goa** is a unique cultural bridge where ancient Konkani Hindu traditions harmoniously blend with 450 years of Portuguese architectural and culinary legacy.\n\n" +
                   "🏰 **UNESCO World Heritage Monuments**:\n" +
                   "• **Churches and Convents of Goa (Old Goa)**: Basilica of Bom Jesus (enshrining the relics of St. Francis Xavier) and Se Cathedral (one of the largest churches in Asia with the Golden Bell).\n" +
                   "• **Shanta Durga & Mangueshi Temples**: Deeply revered Konkani Hindu temple architecture featuring distinctive multi-story lamp towers (*Deepstambhas*).\n\n" +
                   "🎭 **Performing Arts & Festivals**:\n" +
                   "• **Shigmo Festival**: Spring harvest carnival with vibrant folk dances like Fugdi, Ghode Modni (warrior horse dance), and huge mythological floats.\n" +
                   "• **Mando & Dulpod**: Romantic Konkani musical ballads accompanied by the Gumott percussion drum.\n\n" +
                   "🍲 **Gastronomic Traditions**:\n" +
                   "• **Goan Fish Curry & Bebinca**: Spiced coconut kokum seafood curry, and the iconic 7-layered traditional Indo-Portuguese dessert cake.\n" +
                   "• **Heritage Feni**: GI-tagged indigenous distilled spirit made from cashew apples or coconut palm sap.\n\n" +
                   "---\n*💡 Explore full records in DCHA's Goa State Archive (`state-detail.html?state=goa`)!*";
        }

        // HIMACHAL PRADESH & UTTARAKHAND (Himalayan Devbhoomi)
        if (lower.contains("himachal") || lower.contains("uttarakhand") || lower.contains("shimla") || lower.contains("manali")
                || lower.contains("dehradun") || lower.contains("rishikesh") || lower.contains("haridwar") || lower.contains("kedarnath")
                || lower.contains("badrinath") || lower.contains("pahari") || lower.contains("kullu") || lower.contains("devbhoomi")) {
            return "🏛️ **Archival Dossier: Himalayan Devbhoomi (Himachal Pradesh & Uttarakhand)**\n\n" +
                   "The Western Himalayan corridor is venerated as the 'Abode of Gods' (*Devbhoomi*), renowned for sacred river sources, timber-stone temple architecture (*Kath-Kuni*), and vibrant Pahari folk culture.\n\n" +
                   "🏰 **Sacred Sanctuaries & Architectural Heritage**:\n" +
                   "• **Char Dham of Uttarakhand**: Kedarnath, Badrinath, Gangotri, and Yamunotri—ancient mountain shrines founded by Adi Shankaracharya.\n" +
                   "• **Kalka-Shimla Mountain Railway (UNESCO)**: Spectacular 1903 engineering marvel winding through 102 tunnels.\n" +
                   "• **Kath-Kuni Architecture (Himachal)**: Interlocking stone and wood timber framing without mortar, engineered to withstand major earthquakes (e.g. Hidimba Devi Temple, Manali).\n\n" +
                   "🎭 **Folk Traditions & Crafts**:\n" +
                   "• **Kullu & Kinnauri Shawls**: Geometric woolen handlooms woven with traditional vibrant border patterns.\n" +
                   "• **Pahari Miniature Paintings (Kangra & Basohli)**: Exquisite lyrical miniatures capturing Radha-Krishna lore with delicate brushwork and natural mineral pigments.\n" +
                   "• **Aipan Art (Uttarakhand)**: Ritualistic folk floor and wall paintings made by Kumaoni women using red ochre (*geru*) and white rice paste (*biswar*).\n\n" +
                   "🎉 **Sacred Fairs & Festivals**:\n" +
                   "• **Kullu Dussehra**: Week-long international congregation of 200+ village deities (*Devtas*) at Dhalpur Maidan.\n" +
                   "• **Kumbh Mela (Haridwar)**: UNESCO Intangible Cultural Heritage gathering of millions at the Ganga ghats.\n\n" +
                   "---\n*💡 Explore DCHA's Western Himalayan Archives (`states.html?region=North`) for full details!*";
        }

        // HARYANA
        if (lower.contains("haryana") || lower.contains("kurukshetra") || lower.contains("surajkund") || lower.contains("panipat")) {
            return "🏛️ **Archival Dossier: Haryana Cultural Heritage**\n\n" +
                   "**Haryana** is an ancient cradle of Vedic civilization, home to the sacred battlefield of Kurukshetra where the *Bhagavad Gita* was spoken, and legendary historical battlegrounds of Panipat.\n\n" +
                   "🏰 **Heritage Landmarks**:\n" +
                   "• **Brahma Sarovar & Jyotisar (Kurukshetra)**: Holy water tank and the sacred banyan tree marking the revelation of the *Bhagavad Gita*.\n" +
                   "• **Rakhigarhi Archaeological Site**: One of the largest Harappan (Indus Valley) metropolises, revealing 5,000-year-old planned drainage and cemetery relics.\n\n" +
                   "🎭 **Folk Arts & Fairs**:\n" +
                   "• **Surajkund International Crafts Mela**: World's largest annual craft fair hosted against the backdrop of 10th-century Tomar king Suraj Pal's reservoir.\n" +
                   "• **Saang (Swang) Folk Theatre**: Traditional musical open-air folk theatre blending storytelling, dialogue, and rustic poetry.\n\n" +
                   "---\n*💡 Explore full records in DCHA's Haryana State Archive (`state-detail.html?state=haryana`)!*";
        }

        // 4. TRADITIONAL MUSICAL INSTRUMENTS & CULTURAL MUSIC ARCHIVE
        if (lower.contains("instrument") || lower.contains("music") || lower.contains("sangeet") || lower.contains("sitar") || lower.contains("veena")
                || lower.contains("tabla") || lower.contains("mridangam") || lower.contains("shehnai") || lower.contains("sarod")
                || lower.contains("santoor") || lower.contains("bansuri") || lower.contains("flute") || lower.contains("sarangi")
                || lower.contains("pakhawaj") || lower.contains("ghatam") || lower.contains("raga") || lower.contains("carnatic")
                || lower.contains("hindustani") || lower.contains("gana") || lower.contains("geet") || lower.contains("sound") || lower.contains("audio")) {
            return "🎵 **Indian Classical & Folk Musical Instruments Archive**\n\n" +
                   "Indian musical heritage is grounded in the ancient Vedic *Natya Shastra* (compiled by Bharata Muni), which classifies all musical instruments into four fundamental acoustical families (*Chaturvidha Vadya*):\n\n" +
                   "1. 🎻 **Tata Vadya (Chordophones / Stringed Instruments)**:\n" +
                   "   • **Saraswati Veena & Rudra Veena**: India's oldest fretted lutes, sacred to goddess Saraswati; cornerstone of Carnatic classical and Dhrupad meditation.\n" +
                   "   • **Sitar**: 18–20 stringed plucked lute with movable frets and sympathetic resonance strings (*tarab*), celebrated worldwide by Pt. Ravi Shankar.\n" +
                   "   • **Sarod**: Fretless string instrument carved from a single block of teak with a goat-skin belly and chrome fingerboard, famous for continuous sliding notes (*meend*).\n" +
                   "   • **Santoor**: Ancient 100-string hammered dulcimer from the Kashmir valley, played with delicate curved walnut mallets (*mezrab*).\n" +
                   "   • **Sarangi**: Bowed fretless instrument carved with 35+ sympathetic strings, acclaimed for closely imitating the nuanced microtones of the human singing voice.\n\n" +
                   "2. 🥁 **Avanaddha Vadya (Membranophones / Percussion Instruments)**:\n" +
                   "   • **Mridangam**: Primary rhythmic pillar of South Indian Carnatic concerts, a double-headed barrel drum tuned with black paste (*karanai*).\n" +
                   "   • **Tabla (Dayan & Bayan)**: Twin hand drums of Hindustani music producing precise acoustic syllables (*bols*) across complex rhythmic cycles (*taals*).\n" +
                   "   • **Pakhawaj**: Grand barrel drum associated with ancient temple Dhrupad recitals.\n\n" +
                   "3. 🪈 **Sushira Vadya (Aerophones / Wind Instruments)**:\n" +
                   "   • **Bansuri**: Natural bamboo flute with 6–7 finger holes, bringing pastoral Krishna spirituality to classical concert halls.\n" +
                   "   • **Shehnai**: Quadruple-reed auspicious wind instrument made world-famous by Ustad Bismillah Khan, played at royal ceremonies and temple dawn prayers.\n" +
                   "   • **Nadaswaram**: Majestic South Indian double-reed wind instrument, revered as one of the world's most powerful non-brass acoustic instruments.\n\n" +
                   "4. 🔔 **Ghana Vadya (Idiophones / Solid Resonance Instruments)**:\n" +
                   "   • **Ghatam**: Earthen clay pot baked with copper and iron filings, producing bell-like ringing percussion.\n" +
                   "   • **Manjira & Kartal**: Brass hand cymbals used in spiritual Bhakti kirtans and Sufi Qawwali gatherings.\n\n" +
                   "🎶 **Classical Traditions on DCHA**:\n" +
                   "• **Hindustani Tradition (North)**: Khayal, Dhrupad, Tarana, and Thumri rooted in morning, evening, and seasonal ragas.\n" +
                   "• **Carnatic Tradition (South)**: Highly structured compositions celebrating the Trinity (Saint Tyagaraja, Muthuswami Dikshitar, Syama Sastri).\n\n" +
                   "---\n*💡 Visit DCHA's **`audio.html`** page to listen to archival audio samples of classical ragas and folk instruments!*";
        }

        // 5. MUSEUMS OF INDIA & VIRTUAL ARTIFACT VAULT
        if (lower.contains("museum") || lower.contains("salar jung") || lower.contains("csmvs") || lower.contains("calico")
                || lower.contains("artifact") || lower.contains("relic") || lower.contains("chola bronze") || lower.contains("nataraja")
                || lower.contains("dancing girl") || lower.contains("veiled rebecca") || lower.contains("manuscript")
                || lower.contains("katar") || lower.contains("vault") || lower.contains("antiquity")) {
            return "🏺 **Museums of India & Digitized Virtual Artifact Vault**\n\n" +
                   "India houses some of the world's most prestigious cultural repositories, preserving millions of antiquities, bronzes, illuminated codices, and imperial regalia.\n\n" +
                   "🏛️ **Premier National Museums**:\n" +
                   "1. **National Museum (New Delhi)**: India's flagship institution housing 200,000+ relics across 5,000 years, including the Harappan Gallery, Buddhist relic caskets, and Mughal miniature codices.\n" +
                   "2. **Indian Museum (Kolkata, Estd. 1814)**: The ninth oldest museum in the world, featuring an Egyptian mummy, Barhut Buddhist railings, and rare meteorite collections.\n" +
                   "3. **Salar Jung Museum (Hyderabad)**: World-famous single-man antique collection by Mir Yousuf Ali Khan (Salar Jung III), housing European marbles, jade daggers, and the 19th-century Musical Clock.\n" +
                   "4. **CSMVS (Mumbai)**: Indo-Saracenic jewel preserving Indus artifacts, Himalayan bronzes, and Mughal weaponry.\n" +
                   "5. **Government Museum (Egmore, Chennai)**: Renowned for the world's finest collection of Chola dynasty bronzes and Amaravati Buddhist marble friezes.\n" +
                   "6. **Calico Museum of Textiles (Ahmedabad)**: The pinnacle of global textile archives, preserving 500 years of court patolas, Pichwai temple cloths, and gold brocades.\n" +
                   "7. **Rampur Raza Library (Uttar Pradesh)**: Incomparable repository of royal Islamic manuscripts, Naskh calligraphy, and Mughal imperial court albums.\n\n" +
                   "✨ **Digitized Relics in DCHA Virtual Vault (`museum.html`)**:\n" +
                   "• **Dancing Girl of Mohenjo-daro (c. 2300 BCE)**: Lost-wax cast bronze depicting a confident Harappan woman wearing shell bangles.\n" +
                   "• **Chola Dynasty Nataraja Bronze (10th Century CE)**: Masterpiece of Lord Shiva performing the cosmic dance (*Ananda Tandava*) encircled in a ring of flames.\n" +
                   "• **Veiled Rebecca Marble (Salar Jung, 1876)**: Giovanni Benzoni's breathtaking marble sculpture featuring a seemingly transparent veil carved from solid stone.\n" +
                   "• **Royal Mughal Illuminated Manuscript (1620 CE)**: Pure gold leaf and lapis lazuli embellished calligraphy from the royal imperial courts.\n" +
                   "• **Damascened Wootz Steel Rajput Katar**: 17th-century thrusting dagger forged with watered Wootz steel and gold koftgari floral inlays.\n\n" +
                   "---\n*💡 Explore these treasures interactively in DCHA's **`museum.html`** directory and Virtual Vault!*";
        }

        // 6. CLASSICAL DANCES & PERFORMING ARTS
        if (lower.contains("dance") || lower.contains("nritya") || lower.contains("kathak") || lower.contains("bharatanatyam")
                || lower.contains("kathakali") || lower.contains("odissi") || lower.contains("kuchipudi") || lower.contains("manipuri")
                || lower.contains("mohiniyattam") || lower.contains("sattriya") || lower.contains("garba") || lower.contains("bhangra")
                || lower.contains("chhau") || lower.contains("ghoomar") || lower.contains("lavani") || lower.contains("bihu dance")) {
            return "🎭 **Classical & Folk Dance Traditions of India**\n\n" +
                   "Indian dance is a spiritual synthesis of drama (*Natya*), pure rhythmic movement (*Nritta*), and emotional expression (*Nritya/Abhinaya*), formalized in Bharata Muni's *Natya Shastra*.\n\n" +
                   "✨ **The 8 Classical Dance Forms (Sangeet Natak Akademi)**:\n" +
                   "1. **Bharatanatyam (Tamil Nadu)**: Sacred temple dance characterized by precise geometric postures, crisp footwork (*adavus*), and expressive eye gestures (*abhinaya*).\n" +
                   "2. **Kathak (Uttar Pradesh & North India)**: The art of the nomadic storyteller (*Kathaka*), famed for lightning pirouettes (*chakkars*) and rhythmic footwork (*tatkar*).\n" +
                   "3. **Kathakali (Kerala)**: Grand stylized dance-drama featuring elaborate facial makeup (*Paccha, Kathi*), majestic headdresses, and silent mudra storytelling.\n" +
                   "4. **Odissi (Odisha)**: Sculptural temple dance known for the sensual three-bend posture (*Tribhangi*) celebrating Jayadeva's *Gita Govinda*.\n" +
                   "5. **Kuchipudi (Andhra Pradesh)**: Dynamic dance-drama rooted in Krishna bhakti, famous for dancing on the rim of a brass plate (*Tarangam*).\n" +
                   "6. **Manipuri (Manipur)**: Gentle, serpentine devotional dance celebrating the divine *Raas Leela* of Radha and Lord Krishna.\n" +
                   "7. **Mohiniyattam (Kerala)**: 'Dance of the Enchantress', featuring graceful swaying wave-like motions performed in white-and-gold Kasavu attire.\n" +
                   "8. **Sattriya (Assam)**: 15th-century monastic dance created by Vaishnavite reformer Saint Srimanta Sankardev in the river-island monasteries (*Sattras*).\n\n" +
                   "💃 **Celebrated Regional Folk Dances**:\n" +
                   "• **Bhangra & Giddha (Punjab)**: Energetic harvest celebration driven by the pounding dhol.\n" +
                   "• **Garba & Dandiya Raas (Gujarat)**: Circular devotion dance honoring Goddess Durga.\n" +
                   "• **Ghoomar & Kalbelia (Rajasthan)**: Graceful swirling royal dance and nomadic serpent-charmer movements.\n" +
                   "• **Chhau Dance (Jharkhand / Bengal / Odisha)**: Martial masked dance depicting mythological warfare (UNESCO Intangible Heritage).\n" +
                   "• **Bihu Dance (Assam)**: Springtime fertility and harvest dance marked by rapid hand movements and hip sways.\n" +
                   "• **Lavani (Maharashtra)**: High-energy rhythmic folk dance set to the powerful beats of the Dholki.\n\n" +
                   "---\n*💡 Visit DCHA's **`culture.html`** page to explore performing arts dossiers and multimedia records!*";
        }

        // 7. SUBCONTINENT HERITAGE TIMELINE (5,000 YEARS)
        if (lower.contains("timeline") || lower.contains("history") || lower.contains("ancient") || lower.contains("civilization")
                || lower.contains("harappa") || lower.contains("vedic") || lower.contains("maurya") || lower.contains("ashoka")
                || lower.contains("gupta") || lower.contains("mughal") || lower.contains("chola empire") || lower.contains("vijayanagara")
                || lower.contains("independence") || lower.contains("british") || lower.contains("chronology")) {
            return "⏳ **Subcontinent Heritage Chronology (5,000 Years of Indian Civilization)**\n\n" +
                   "India's civilizational continuum spans five millennia of architectural mastery, philosophical inquiry, and cultural integration:\n\n" +
                   "1. 🏛️ **2500 BCE – 1500 BCE: Indus Valley / Harappan Civilization**\n" +
                   "   • World's earliest urban planned cities (Harappa, Mohenjo-daro, Dholavira, Lothal dockyard).\n" +
                   "   • Lost-wax bronze casting (*Dancing Girl*), terracotta seals, and advanced drainage systems.\n\n" +
                   "2. 📜 **1500 BCE – 500 BCE: Vedic Era & The Great Epics**\n" +
                   "   • Composition of the Rigveda, Samaveda, Yajurveda, and Atharvaveda; Upanishadic philosophy.\n" +
                   "   • Emergence of the 16 *Mahajanapadas* across the Indo-Gangetic plains.\n\n" +
                   "3. 🦁 **322 BCE – 185 BCE: Mauryan Empire & Emperor Ashoka**\n" +
                   "   • Chandragupta Maurya unifies the subcontinent; Emperor Ashoka embraces Ahimsa and Buddhist diplomacy.\n" +
                   "   • Sarnath Lion Capital (now India's National Emblem) and the Great Stupa at Sanchi.\n\n" +
                   "4. 🎨 **320 CE – 550 CE: Gupta Empire (Golden Age of Classical Arts)**\n" +
                   "   • Flowering of Sanskrit literature (Kalidasa's *Shakuntala*), astronomy, and mathematics (Aryabhata).\n" +
                   "   • Exquisite rock-cut Buddhist mural frescoes in the Ajanta Caves.\n\n" +
                   "5. ⛵ **9th – 13th Century CE: Imperial Chola Maritime Empire**\n" +
                   "   • Maritime dominance across the Bay of Bengal, Java, and Sumatra under Rajaraja & Rajendra Chola.\n" +
                   "   • Monolithic granite temples (Brihadeeswarar Thanjavur) and divine lost-wax bronze sculptures (*Nataraja*).\n\n" +
                   "6. 🏰 **1336 CE – 1646 CE: Vijayanagara Empire (City of Victory)**\n" +
                   "   • Vast cultural empire founded on the Tungabhadra river at Hampi.\n" +
                   "   • Ornate thousand-pillared temple mandapas, stone chariots, and Carnatic music patronage.\n\n" +
                   "7. 🕌 **1526 CE – 1707 CE: Mughal Architectural Epoch**\n" +
                   "   • Fusion of Persian, Central Asian, and indigenous Indian architecture under Akbar, Jahangir, and Shah Jahan.\n" +
                   "   • Taj Mahal, Red Fort, Fatehpur Sikri, Shalimar Gardens, and illuminated miniature paintings.\n\n" +
                   "8. 🇮🇳 **1857 CE – 1947 CE: Freedom Struggle & Modern Republic**\n" +
                   "   • The 1857 First War of Independence leading to nationwide anti-colonial renaissance.\n" +
                   "   • Mahatma Gandhi's Satyagraha, Subhash Chandra Bose's INA, and August 15, 1947 Independence.\n\n" +
                   "---\n*💡 Explore the architectural spine interactively in DCHA's **`timeline.html`** page!*";
        }

        // 8. HERITAGE SITES / MONUMENTS / TEMPLES
        if (lower.contains("monument") || lower.contains("temple") || lower.contains("fort") || lower.contains("palace")
                || lower.contains("caves") || lower.contains("unesco") || lower.contains("hampi") || lower.contains("konark")
                || lower.contains("taj mahal") || lower.contains("khajuraho") || lower.contains("qutub")) {
            return "🏛️ **Architectural Legacy: UNESCO World Heritage Monuments of India**\n\n" +
                   "India is home to **42 UNESCO World Heritage Sites**, documenting architectural marvels across Hindu, Buddhist, Jain, Islamic, and colonial traditions:\n\n" +
                   "1. 🏛️ **Taj Mahal (Agra, Uttar Pradesh)**: 1631–1648 CE ivory-white marble mausoleum with Pietra Dura gemstone inlays.\n" +
                   "2. ☀️ **Sun Temple Konark (Odisha)**: 13th-century Kalinga stone chariot with 24 carved stone wheels serving as astronomical sundials.\n" +
                   "3. 🏛️ **Monuments of Hampi (Karnataka)**: 14th-century capital of the Vijayanagara Empire with stone chariots, monolithic Narasimha, and Virupaksha Temple.\n" +
                   "4. 🎨 **Ajanta & Ellora Caves (Maharashtra)**: 2nd century BCE Buddhist mural frescoes at Ajanta and the monolithic rock-hewn Kailasa Temple at Ellora.\n" +
                   "5. 🛕 **Brihadeeswarar Temple (Thanjavur, Tamil Nadu)**: Built by Rajaraja Chola I in 1010 CE; possesses an 80-tonne single granite capstone atop its 66m vimana.\n" +
                   "6. 🏰 **Hill Forts of Rajasthan**: Six monumental forts—Chittorgarh, Kumbhalgarh, Ranthambore, Amber, Jaisalmer, and Gagron.\n" +
                   "7. 🗼 **Qutub Minar Complex (Delhi)**: 73-meter brick victory minaret and the 1,600-year-old rust-resistant Iron Pillar of Chandragupta II.\n" +
                   "8. 🛕 **Khajuraho Group of Monuments (Madhya Pradesh)**: 10th-century Chandela dynasty Nagara-style sandstone temples with intricate celestial carvings.\n\n" +
                   "---\n*💡 Explore all 42+ monuments with high-resolution imagery and GPS locations in DCHA's **`heritage.html`** directory!*";
        }

        // 9. IF DATABASE CONTEXT HAS MATCHED SPECIFIC ENTITIES
        if (retrievedContext != null && !retrievedContext.isBlank()) {
            return "🏛️ **DCHA Archival Intelligence Dossier**\n\n" +
                   "Here are the curated records retrieved from the Indian Cultural Heritage Archive for **\"" + userPrompt + "\"**:\n\n" +
                   retrievedContext + "\n\n" +
                   "---\n*💡 You can delve deeper into full photo archives, oral audio, and scholarly records directly on the DCHA platform!*";
        }

        // 10. GENERAL SYNTHESIS FOR ANY OTHER CULTURAL QUERY
        return "🏛️ **Archival Overview: Indian Cultural Heritage**\n\n" +
               "India is one of the world's most enduring civilizational matrixes, uniting 5,000 years of philosophy, monumental architecture, classical and folk arts, textiles, and living traditions across all 28 States and 8 Union Territories.\n\n" +
               "✨ **DCHA Core Knowledge Hubs to Explore for \"" + userPrompt + "\"**:\n" +
               "• 🗺️ **36 States & UTs Directory (`states.html`)**: Deep encyclopedic profiles of every Indian state's culture, monuments, languages, and food.\n" +
               "• 🎵 **Cultural Music & Instruments (`audio.html`)**: Listen to traditional string, wind, and percussion instruments from Carnatic & Hindustani masters.\n" +
               "• 🏺 **Museums & Virtual Relics Vault (`museum.html`)**: High-resolution 3D & 2D artifacts like the Chola Nataraja, Dancing Girl, and illuminated manuscripts.\n" +
               "• ⏳ **Subcontinent Timeline (`timeline.html`)**: 5,000-year chronology spanning the Indus Valley, Mauryas, Guptas, Cholas, Mughals, and Modern India.\n" +
               "• 🗺️ **Interactive Geographic Map (`map.html`)**: Spatial mapping of monuments, forts, and cultural landmarks across India.\n\n" +
               "Try asking me specifically about any Indian state, monument (e.g. *'Taj Mahal'*, *'Konark'*), dance form (*'Kathakali'*, *'Odissi'*), or instrument (*'Veena'*, *'Sitar'*, *'Mridangam'*)!";
    }
}
