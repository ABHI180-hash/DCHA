# Indian Cultural Heritage Archive (DCHA)
## Privacy Risk Register & Impact Assessment (DPIA)

**Compliance Benchmark:** Digital Personal Data Protection Act, 2023 (DPDP Act) & ISO/IEC 27701  
**Assessment Date:** October 2026  
**Review Frequency:** Annual or upon material architectural changes  

---

### 1. Risk Scoring Methodology
- **Likelihood:** Low (1), Medium (2), High (3)
- **Impact:** Low (1), Medium (2), High (3)
- **Risk Score:** Likelihood × Impact (1–3: Low, 4–6: Medium, 7–9: High)

---

### 2. Privacy Risk Register

| Risk ID | Threat Scenario / Processing Activity | Initial Likelihood | Initial Impact | Initial Score | Implemented Technical & Policy Controls | Residual Likelihood | Residual Impact | Residual Score | Risk Status |
| :--- | :--- | :---: | :---: | :---: | :--- | :---: | :---: | :---: | :--- |
| **PR-01** | **Unbundled or Coerced Consent**<br>Requiring newsletter or promotional subscription as a mandatory condition of account creation. | 2 | 3 | **6 (Med)** | Separated core service consent (Terms/Privacy) from optional newsletter marketing. Mandatory checkbox decoupled from marketing toggle in `register.html`. | 1 | 1 | **1 (Low)** | Tolerable / Closed |
| **PR-02** | **Inability to Exercise Statutory Rights**<br>User requests data export or account erasure without technical mechanism to fulfill statutory timeline. | 3 | 3 | **9 (High)** | Built self-service data export (`GET /api/auth/export-data`) and automated permanent account erasure (`DELETE /api/auth/account`) on `profile.html`. | 1 | 1 | **1 (Low)** | Tolerable / Closed |
| **PR-03** | **Uninformed Cookie / Local Storage Tracking**<br>Silent placement of persistent tokens and preferences without user knowledge. | 2 | 2 | **4 (Med)** | Implemented non-deceptive Cookie Consent Banner & Granular Preferences Modal with persistent footer trigger and complete key documentation in `cookies.html`. | 1 | 1 | **1 (Low)** | Tolerable / Closed |
| **PR-04** | **Third-Party Surveillance / Ad Networks**<br>Inclusion of Google Analytics, Facebook Pixel, or ad remarketing SDKs tracking heritage visitors. | 3 | 3 | **9 (High)** | Zero commercial ad networks or third-party trackers integrated. Strict Content Security Policy prohibits unauthorized domain beacons. | 1 | 1 | **1 (Low)** | Tolerable / Closed |
| **PR-05** | **AI Attribution & Disinformation Risk**<br>AI assistant outputs false historical dates or unauthorized cultural sacred secrets presented as official archive doctrine. | 2 | 2 | **4 (Med)** | Added prominent AI Provenance notice in assistant dialog, disclaimer linking to `terms.html#ai-disclaimer`, and explicit clarification that AI synthesis is advisory. | 1 | 1 | **1 (Low)** | Tolerable / Closed |
| **PR-06** | **Uncredited Cultural Media Exploitation**<br>Use of community or ASI photographic relics without open licensing attribution. | 2 | 2 | **4 (Med)** | Established comprehensive Media Attribution Register (`attributions.html`) crediting ASI, UNESCO, Wikimedia Commons, and CC BY-SA 4.0 terms. | 1 | 1 | **1 (Low)** | Tolerable / Closed |

---

### 3. Data Protection Officer (DPO) & Redressal Escalation
Any Data Principal experiencing concerns or requesting manual intervention may contact:
- **Designation:** Archival Data Protection Officer & Grievance Custodian
- **Grievance Desk:** `privacy@dcha.org`
- **Statutory Resolution Window:** 30 calendar days under DPDP Act 2023 regulations.
