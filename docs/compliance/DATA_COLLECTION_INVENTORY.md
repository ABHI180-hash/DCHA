# Indian Cultural Heritage Archive (DCHA)
## Comprehensive Data Collection & Processing Inventory

**Regulatory Alignment:** Digital Personal Data Protection Act, 2023 (Republic of India) — Sections 4, 5, 6, 8, 11, 12.  
**Classification:** Cultural & Educational Public Repository  
**Last Audited:** October 2026  
**Document Status:** Approved Production Baseline  

---

### 1. Overview & Data Minimization Principles
The Digital Culture Heritage Archive (DCHA) adheres strictly to the principle of purpose limitation and data minimization. We only collect the minimal personal information necessary to deliver authentic historical archives, user collection curation, and community contribution workflows.

No sensitive financial data, biometrics, government identifiers (Aadhaar, PAN), or commercial behavioral tracking profiles are collected, processed, or stored.

---

### 2. Detailed Data Inventory Table

| Data Element | Point of Collection | Purpose / Legal Basis | Storage Location | Retention Period | Third-Party Transfer | User Control Mechanism |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Full Name** | Account Registration (`register.html`) | User authentication, contribution attribution (DPDP Sec 4 & 5) | `users.name` (H2 / PostgreSQL database) | Until account erasure | None | Self-service update on `profile.html` |
| **Email Address** | Account Registration & Login (`register.html`, `login.html`) | Primary account identifier, password recovery (DPDP Sec 5) | `users.email` (Unique index in database) | Until account erasure | None | Self-service export or permanent erasure |
| **Password (Hashed)** | Registration & Password Update | Cryptographic authentication credential | `users.password_hash` (BCrypt salt-hashed, cost 10) | Until account erasure / update | Never exposed or transferred | Change via profile / institutional recovery |
| **Archival Bio / Notes** | Profile Editing (`profile.html`) | Community engagement & research domain interests | `users.bio` (Database) | Until account erasure | None | Free self-service edit on `profile.html` |
| **Favorites & Bookmarks** | Interactive Heart icons on monuments/states | Archival bookmarking and user vault | `favorites` table & browser `localStorage` | Retained with account | None | Direct toggle or self-service account erasure |
| **Custom Collections** | Collection Manager (`collections.html`) | User-curated thematic heritage groups | `collections` & `collection_items` tables | Retained with account | None | Delete item, delete collection, or account erasure |
| **Community Contributions** | Contribution Form (`contribute.html`) | Crowd-sourced preservation under CC BY-SA 4.0 | `contributions` table (Title, desc, state, media link) | Archival permanence under open license | Published under CC BY-SA 4.0 upon curator approval | Self-service erasure or grievance desk review |
| **Session JWT Token** | Auth API (`/api/auth/login`) | Stateless session authentication | Browser `localStorage` (`dcha_token`) | 24 hours expiry or until logout | None | Immediate purge on logout or erasure |
| **Theme Preference** | Theme Toggle Button (`☀️ / 🌙`) | Visual accessibility & contrast preference | Browser `localStorage` (`dcha_theme`) | Until cleared by user | Client-only, never sent to server | Toggle button in navigation bar |
| **Cookie Consent Record** | Cookie Consent Banner / Modal | Documenting user consent choices (DPDP Sec 6) | Browser `localStorage` (`dcha_cookie_consent`) | 12 months or until modified | Client-only | Footer button "⚙️ Cookie Preferences" |

---

### 3. Special Categories & Child Data
In accordance with Section 9 of the DPDP Act 2023:
- The Indian Cultural Heritage Archive **does not** knowingly engage in tracking, behavioral monitoring, or targeted advertising directed at children or minors.
- No detrimental processing affecting children's well-being is permitted across any archive monographs or interactive systems.

---

### 4. Statutory Rights Verification
Data Principals exercising rights under DPDP Act 2023 Sec 11 (Access/Export), Sec 12 (Correction/Erasure), and Sec 13 (Grievances) can verify execution via:
1. **Self-Service Export:** Instant download of `dcha-archive-data-export.json` on `profile.html`.
2. **Self-Service Erasure:** One-click irreversible deletion of all records via `DELETE /api/auth/account`.
3. **Institutional Contact:** Escalation to `privacy@dcha.org` and Grievance Officer desk.
