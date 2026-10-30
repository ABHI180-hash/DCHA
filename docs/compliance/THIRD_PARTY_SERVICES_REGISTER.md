# Indian Cultural Heritage Archive (DCHA)
## Third-Party Services, CDNs & External Embeds Register

**Audit Date:** October 2026  
**Status:** Clean — Zero Commercial Ad/Analytics Trackers  

---

### 1. External Third-Party Assets Audit

| Provider / Resource | Domain / Origin | Exact Asset Used | Purpose | Data Transmitted | Privacy Assessment & Risk Controls |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **Google Fonts** | `fonts.googleapis.com`<br>`fonts.gstatic.com` | `Cinzel`, `Cinzel Decorative`, `Marcellus`, `Plus Jakarta Sans` | Institutional typography & typography accessibility | Client IP address and HTTP referrer sent during font file stylesheet requests | Legitimate design resource. No tracking cookies set by Google Fonts. Permitted via CSP `style-src` and `font-src`. |
| **Unsplash** | `images.unsplash.com` | High-resolution cultural photography for monuments & states | Visual heritage exhibit presentation | Client IP address and User-Agent on image fetch | Images loaded over HTTPS with `crossorigin="anonymous"` and standard caching headers. Permitted via CSP `img-src`. |
| **Wikimedia Commons** | `upload.wikimedia.org` | Open-access photographic archives of monuments and artifacts | Historical visual evidence under CC BY-SA / Public Domain | Client IP and User-Agent on image fetch | Non-commercial open knowledge repository. Permitted via CSP `img-src`. |
| **OpenStreetMap / Leaflet (Optional Map layer)** | `unpkg.com`<br>`tile.openstreetmap.org` | Interactive geographic state and monument locator | Geospatial cultural exploration | Tile coordinate requests, client IP | No user location tracking (GPS) requested without explicit browser prompt. Permitted via CSP. |

---

### 2. Prohibited Services & Negative Affirmations
The Indian Cultural Heritage Archive explicitly affirms the **absence** of:
- **Zero Advertising Trackers:** No Google AdSense, DoubleClick, Facebook Pixel, TikTok Pixel, or Outbrain trackers.
- **Zero Commercial Analytics:** No Google Analytics (GA4), Mixpanel, Segment, or Amplitude telemetry.
- **Zero Session Recording:** No Hotjar, FullStory, or mouse-tracking session replay software.
- **Zero Third-Party Payment Gateways:** No payment processors or transaction tracking scripts exist, as 100% of the archive is free public access.
