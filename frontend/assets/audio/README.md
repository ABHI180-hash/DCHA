# 🎵 DCHA Audio Management & Archival Repository

This directory hosts the authentic aural heritage, regional songs, and acoustic folk instrument recordings for the **Digital Culture Heritage Archive (DCHA)**.

---

## 📁 Directory Structure

```
frontend/assets/audio/
├── README.md
├── songs/                    # Regional Heritage Songs & Vocal Traditions
│   ├── baul_sangeet.mp3      # Card #1: West Bengal (Baul Sangeet)
│   ├── carnatic_kritis.mp3   # Card #2: Tamil Nadu / Andhra Pradesh (Carnatic Classical)
│   ├── manganiyar_folk.mp3   # Card #3: Rajasthan (Manganiyar & Langa Ballads)
│   ├── hindustani_classical.mp3 # Card #4: Uttar Pradesh / Delhi (Khayal & Thumri)
│   ├── assamese_bihu_geet.mp3   # Card #5: Assam (Bihu Geet & Monastic Borgeet)
│   ├── maharashtrian_powada.mp3 # Card #6: Maharashtra (Lavani & Powada Ballads)
│   ├── sufiana_kalam.mp3     # Card #7: Jammu and Kashmir (Sufiana Kalam)
│   └── pandavani_ballad.mp3  # Card #8: Chhattisgarh (Pandavani Oral Ballad)
└── instruments/              # Traditional Musical Instruments Demonstration Audio
    ├── sitar.mp3             # Card #1: Sitar & Sarod (Pan-North India)
    ├── veena.mp3             # Card #2: Saraswati Veena & Rudra Veena (South / Pan-India)
    ├── shehnai.mp3           # Card #3: Shehnai & Nadaswaram (UP / Tamil Nadu)
    ├── tabla.mp3             # Card #4: Tabla & Pakhawaj Drums (Pan-India)
    ├── ektara.mp3            # Card #5: Ektara & Khamak (West Bengal / Odisha)
    ├── kamaicha.mp3          # Card #6: Kamaicha & Ravanahatha (Rajasthan / Gujarat)
    ├── pepa.mp3              # Card #7: Pepa & Gogona (Assam)
    ├── santoor.mp3           # Card #8: Kashmiri Santoor & Himalayan Rubab (J&K)
    └── thavil.mp3            # Card #9: Ghatam & Thavil (Tamil Nadu / Kerala)
```

---

## 🎼 Mapped Cards Reference Table

### Section 1: Regional Heritage Songs (`HERITAGE_MUSIC_ITEMS`)

| ID | Music Card Name | Region / State | Designated Audio File Path |
|---|---|---|---|
| `1` | Baul Sangeet & Mystical Minstrel Ballads | West Bengal | `assets/audio/songs/baul_sangeet.mp3` |
| `2` | Carnatic Classical Kritis & Tyagaraja Aradhana | Tamil Nadu / Andhra Pradesh | `assets/audio/songs/carnatic_kritis.mp3` |
| `3` | Rajasthani Manganiyar & Langa Folk Ballads | Rajasthan | `assets/audio/songs/manganiyar_folk.mp3` |
| `4` | Hindustani Classical Khayal & Thumri | Uttar Pradesh / Delhi | `assets/audio/songs/hindustani_classical.mp3` |
| `5` | Assamese Bihu Geet & Monastic Borgeet | Assam | `assets/audio/songs/assamese_bihu_geet.mp3` |
| `6` | Maharashtrian Lavani & Powada Ballads | Maharashtra | `assets/audio/songs/maharashtrian_powada.mp3` |
| `7` | Sufiana Kalam & Chhakri Ensemble | Jammu and Kashmir | `assets/audio/songs/sufiana_kalam.mp3` |
| `8` | Chhattisgarhi Pandavani Oral Ballad | Chhattisgarh | `assets/audio/songs/pandavani_ballad.mp3` |

### Section 2: Traditional Folk & Classical Instruments (`FOLK_INSTRUMENT_ITEMS`)

| ID | Instrument Name | Region / State | Designated Audio File Path |
|---|---|---|---|
| `1` | Sitar & Sarod | Pan-North India | `assets/audio/instruments/sitar.mp3` |
| `2` | Saraswati Veena & Rudra Veena | Tamil Nadu / Karnataka | `assets/audio/instruments/veena.mp3` |
| `3` | Shehnai & Nadaswaram | Uttar Pradesh / Tamil Nadu | `assets/audio/instruments/shehnai.mp3` |
| `4` | Tabla & Pakhawaj Drums | Pan-India | `assets/audio/instruments/tabla.mp3` |
| `5` | Ektara & Khamak | West Bengal / Odisha | `assets/audio/instruments/ektara.mp3` |
| `6` | Kamaicha & Ravanahatha | Rajasthan / Gujarat | `assets/audio/instruments/kamaicha.mp3` |
| `7` | Pepa & Gogona | Assam | `assets/audio/instruments/pepa.mp3` |
| `8` | Kashmiri Santoor & Himalayan Rubab | Jammu and Kashmir | `assets/audio/instruments/santoor.mp3` |
| `9` | Ghatam & Thavil | Tamil Nadu / Kerala | `assets/audio/instruments/thavil.mp3` |

---

## 🛠️ How to Add New Audio (User Workflow)

When you want to add an audio track to any card:
1. Provide the audio file (`.mp3`, `.wav`, `.ogg`, `.m4a`, `.aac`, or `.flac`) directly in the chat.
2. Specify the card name (e.g., *"Ye Baul Sangeet ke liye hai"* or *"Card #3 Rajasthani Manganiyar"*).
3. The AI assistant will automatically copy the file into the correct location, link it to the card, test playback, and confirm.
