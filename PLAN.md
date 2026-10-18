# CineSonic Studio: Architecture & Technical Plan

## 1. System Architecture

```mermaid
graph TD
    User([Creator / User]) -->|1-Sentence Idea| Producer[AI Producer & Memory Manager]
    Producer --> Router{Mode Selector}
    Router -->|Guided Mode| Stepper[12-Stage Film & 7-Stage Music Stepper]
    Router -->|Auto Mode| AutoRunner[Auto Pipeline with Budget Guard]

    subgraph "Step Assistant Contract Hub (8 Dimensions)"
        SA_F1[F1: Idea & Brief]
        SA_F2[F2: Story & 3-Act Beats]
        SA_F3[F3: Characters & Style Bible]
        SA_F4[F4: Screenplay & Dialogue]
        SA_F5[F5: Storyboard & Shot List]
        SA_F6[F6: Keyframes & Consistency]
        SA_F7[F7: Video Takes & Inpainting]
        SA_F8[F8: Voice TTS & Subtitles]
        SA_F9[F9: Score & Musical Cues]
        SA_F10[F10: SFX, Ambience & Ducking]
        SA_F11[F11: Timeline Multi-track Editing]
        SA_F12[F12: Automated QC & Master Export]
    end

    subgraph "Model & Provider Adapters"
        GeminiFlash[Gemini 3.5 Flash - Script/Reasoning]
        GeminiImage[Gemini 2.5 Flash Image - Keyframes]
        GeminiAudio[Gemini Audio / TTS]
        MockEngine[Local Mock & Procedural Generator]
    end

    Stepper --> SA_F1
    SA_F1 --> SA_F2 --> SA_F3 --> SA_F4 --> SA_F5 --> SA_F6
    SA_F6 --> SA_F7 --> SA_F8 --> SA_F9 --> SA_F10 --> SA_F11 --> SA_F12

    SA_F1 & SA_F2 & SA_F3 & SA_F4 --> GeminiFlash & MockEngine
    SA_F6 & SA_F7 --> GeminiImage & MockEngine
    SA_F8 & SA_F9 & SA_F10 --> GeminiAudio & MockEngine

    subgraph "Local Persistence & Media Rendering"
        RoomDB[(Room SQLite: Project & Assets)]
        MediaCompositor[Compose Canvas + AudioTrack / MediaPlayer]
    end

    SA_F12 --> MediaCompositor
    SA_F12 --> RoomDB
```

## 2. Data Models (Entities & Schemas)

- **Project**: `id`, `title`, `ideaPrompt`, `mode` (Guided/Auto), `createdAt`, `budgetLimit`, `budgetSpent`, `filmStep`, `musicStep`
- **Brief**: `genre`, `targetAudience`, `tone`, `durationSec`, `primaryLanguage`, `budgetCeiling`, `concepts` (List of 3)
- **StoryVersion**: `version`, `logline`, `synopsis`, `threeActBeats` (Setup, Inciting Incident, Midpoint, Climax, Resolution), `critiqueScore`
- **Character**: `id`, `name`, `role`, `appearancePrompt`, `voicePitch`, `costumeNotes`, `consistencyTokens`
- **StyleBible**: `visualStyle`, `colorPalette` (hex codes), `lightingPreset`, `aspectRatio`, `negativePrompt`
- **Scene**: `sceneIndex`, `heading`, `setting`, `timeOfDay`, `summary`
- **Shot**: `shotIndex`, `sceneIndex`, `shotType` (Wide, Medium, Close-up), `cameraAngle`, `cameraMotion`, `durationSec`, `prompt`, `dialogue`, `subtitles`
- **Take**: `takeIndex`, `shotIndex`, `videoUrlOrSyntheticId`, `keyframeBitmap`, `status`, `qualityScore`
- **MusicTrack**: `title`, `bpm`, `musicalKey`, `genre`, `structure` (Intro, Verse, Chorus, Outro), `lyricsThEn`, `stemTypes`
- **TimelineClip**: `trackId` (Video, Dialogue, SFX, BGM), `startTimeMs`, `durationMs`, `volume`, `duckingPriority`
- **UsageLedger**: `timestamp`, `stepId`, `provider`, `modelName`, `tokensUsed`, `estimatedCostUsd`

## 3. Step Assistant Contract (8 Aspects)

Every assistant implements:
1. `explain`: 2–3 plain sentences explaining the objective with an example.
2. `interview`: Up to 3 high-impact questions (user can skip).
3. `propose`: 3 diverse creative options with pros/cons and a recommended pick.
4. `estimate`: Pre-generation time and cost ($0.00 in mock mode).
5. `critique`: Rubric scoring 1–5 with actionable improvement steps.
6. `refine`: Quick modification commands (e.g., "more intense", "slower pace").
7. `check`: Cross-step conflict detection (character traits, tone, timing, budget).
8. `handoff`: Summary of locked parameters passed to the next step.

## 4. Top 3 Risks & Mitigations

1. **Character Drift**: Solved via Style Bible Consistency Pack prepended to all image/video prompts.
2. **Cost Overrun**: Pre-generation estimate gate and $0.00 default cap using procedural mock generators.
3. **Network/CORS/Latency**: Native Android OkHttpClient with 60s timeout, exponential backoff, and 100% offline fallback.
