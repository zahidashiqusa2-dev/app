# View Once Downloader & Deleted Message Logger

Architectural plan to integrate a **View Once Media Downloader & Vault** and an **Anti-Delete Message Logger** into the Urooj Choudhary WhatsApp Automation Android application.

---

### User Review & Critical Decisions

> [!IMPORTANT]
> The following user preferences were confirmed during the Phase 1 interview and form the baseline for this implementation:

- **Navigation Integration**: Dedicated navigation tabs in the application navigation bar for **One View** (`AppScreen.ONE_VIEW`) and **Anti-Delete** (`AppScreen.DELETED_LOGS`), giving immediate 1-tap access.
- **Interception Alerts**: Real-time system notification alerts will trigger whenever a View Once media item is decrypted/intercepted or when a contact revokes a message with "Delete for Everyone".
- **Storage Strategy**: Preserved media and deleted message payloads are kept strictly within the local device sandbox (`/data/user/0/.../sandbox/`) with encrypted metadata and user-initiated manual export to public storage / gallery.

---

### 1. Overview & Core Concept

1. **One View (View Once) Downloader & Vault**:
   - Intercepts incoming WhatsApp ephemeral messages flagged with `viewOnceMessage: true`.
   - Prevents media destruction upon opening; caches high-resolution images, video notes, and audio notes in the secure device vault.
   - Provides a media viewer with metadata (sender name, phone number, capture timestamp, file size, media type) and 1-tap "Save to Gallery" and "Share" affordances.

2. **Anti-Delete / Revoked Message Archive**:
   - Detects WhatsApp protocol message revocation events (`protocolMessage` with `type: REVOKE`).
   - Retains the full original text message or media caption, sender profile, group context, original arrival time, and exact revocation time.
   - Displays a dedicated timeline with visual "Revoked by Sender" red warning badges and search/filter controls.

---

### 2. User Experience & Visual Design

#### Visual Direction & Styling
- **Color Tokens**:
  - Vault & Downloader Accent: Emerald Green (`#00A884`) and Cyan Glow (`#38BDF8`).
  - Anti-Delete Revocation Badge: Crimson Red (`#EF4444`) and Coral (`#F87171`).
  - Surface Background: Deep Luxury Slate (`#0B101B`) and Elevated Panels (`#111B27`).
- **Typography & Layout**:
  - Monospace timestamps and phone JIDs for technical clarity.
  - Semi-transparent cards with subtle gradient borders.
  - Minimum 48dp touch targets for accessibility.

#### User Flows
1. **View Once Downloader Flow**:
   - User navigates to the **One View** tab.
   - Screen displays metrics: Total Intercepted, Storage Used, Active Interception Toggle.
   - Grid/List of captured View Once media cards showing thumbnail previews, sender badge, and timestamp.
   - Tapping a media card opens the **View Once Media Inspector Dialog** allowing full-size viewing, media playback, and "Save to Gallery".
   - "Simulate View Once Capture" button allows immediate on-device testing.

2. **Anti-Delete Logger Flow**:
   - User navigates to the **Anti-Delete** tab.
   - Shows active anti-revoke protection status banner with message count.
   - Timeline list of revoked messages displaying:
     - Sender Avatar & Name/Number.
     - Original message bubble with strikethrough deletion watermark.
     - "Deleted at [HH:mm:ss] • [X] seconds after delivery" telemetry badge.
   - Search bar to filter deleted messages by contact or keyword.
   - "Simulate Message Revocation" button for instant CUJ validation.

---

### 3. Key Product Decisions & Trade-Offs

- **Decision 1: Direct Bottom Navigation vs Nested Hub**:
  - *Chosen Approach*: Expand navigation bar to incorporate dedicated `One View` and `Anti-Delete` tabs with clean icon badges.
  - *Why*: User explicitly requested dedicated tabs in the navigation bar for rapid access.
- **Decision 2: Device-Bound Sandbox Storage**:
  - *Chosen Approach*: Store intercepted media and revoked text within the isolated app sandbox with manual export rather than polluting the public camera roll automatically.
  - *Why*: Preserves user privacy and prevents accidental exposure of sensitive intercepted media in common gallery apps.

---

### 4. Technical Architecture & Data Strategy

```
┌────────────────────────────────────────────────────────────────────────┐
│                        Urooj Choudhary App Shell                       │
│                        (MainActivity + Theme)                          │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │
          ┌─────────────────────────┴─────────────────────────┐
          ▼                                                   ▼
┌───────────────────────────────┐           ┌────────────────────────────────┐
│   OneViewDownloaderScreen     │           │    DeletedMessageLoggerScreen  │
│  - Storage & Intercept stats  │           │  - Anti-revoke status banner   │
│  - Media grid / list          │           │  - Revocation event timeline   │
│  - Fullscreen media preview   │           │  - Search & contact filter     │
│  - Export / Save to Gallery   │           │  - Diff & metadata inspector   │
└───────────────┬───────────────┘           └────────────────┬───────────────┘
                │                                            │
                └─────────────────────┬──────────────────────┘
                                      ▼
                        ┌───────────────────────────┐
                        │    AutomationViewModel    │
                        │  - uiState StateFlow      │
                        │  - Interception handlers  │
                        └─────────────┬─────────────┘
                                      ▼
                        ┌───────────────────────────┐
                        │   AutomationRepository    │
                        │  - viewOnceItems list     │
                        │  - deletedMessages list   │
                        │  - Notification trigger   │
                        └───────────────────────────┘
```

#### New Data Models (`Models.kt`)
```kotlin
enum class ViewOnceMediaType { IMAGE, VIDEO, AUDIO_NOTE }

data class ViewOnceMediaItem(
    val id: String,
    val senderName: String,
    val senderNumber: String,
    val mediaType: ViewOnceMediaType,
    val caption: String,
    val timestamp: String,
    val fileSize: String,
    val isSavedToGallery: Boolean = false
)

data class DeletedMessageItem(
    val id: String,
    val senderName: String,
    val senderNumber: String,
    val messageContent: String,
    val originalTimestamp: String,
    val deletedTimestamp: String,
    val chatName: String, // Individual or Group name
    val hasMedia: Boolean = false
)
```

#### Verification & Testing
1. Verify clean compilation with `compile_applet`.
2. Test One View capture simulation: verify media item appears, displays correct metadata, and can be exported.
3. Test Anti-Delete simulation: verify revoked message appears in the timeline with deletion watermark and timestamps.
4. Verify navigation transitions and edge-to-edge layout padding.
