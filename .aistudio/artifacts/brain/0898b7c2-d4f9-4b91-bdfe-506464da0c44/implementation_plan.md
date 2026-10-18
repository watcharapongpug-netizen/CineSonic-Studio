# CineSonic Studio: AI Film & Music Production Suite

CineSonic Studio is an end-to-end creative suite that transforms a single-sentence idea into a fully realized short film (1–3 minutes) and synchronized soundtrack. It guides creators through structured, multi-model AI pipelines with strict character and tonal consistency, automated QC, and an 8-contract AI Assistant Co-pilot at every step.

---

## User Review & Critical Decisions

> [!IMPORTANT]
> The following parameters were confirmed during clarification and anchor this architecture:

- **AI Provider Strategy**: Hybrid Mock Engine + Gemini API (`gemini-3.5-flash` for narrative & reasoning, `gemini-2.5-flash-image` for keyframe ideation, and Gemini Audio/TTS for voice). Full local synthetic mock generation is included so the entire pipeline runs offline and 100% free of charge without external API keys.
- **Budget Ceiling**: $0.00 / Free tier default with an active Usage Ledger and Pre-generation Cost Gate.
- **Language Support**: Bilingual Thai & English (Thai syllable/tonal aware lyric checker, Thai natural dialogue pacing, English image/video prompt synthesizer).
- **Target Audience & Rights**: Personal, creative, and educational use with zero copyright infringement, anti-deepfake filters, and AI-generation provenance tagging.

---

## 1. Overview & Core Concept

CineSonic Studio provides creators with a virtual film studio in their pocket:
- **No Single Black-Box Model**: Break cinematic production into specialized phases where creators maintain creative agency.
- **Two Interconnected Pipelines**:
  1. **Film Pipeline (F1–F12)**: Idea & Brief &rarr; Story Development &rarr; Style Bible & Characters &rarr; Screenplay &rarr; Storyboard & Shots &rarr; Keyframes &rarr; Video Takes &rarr; Voice & Subtitles &rarr; Score & Sound Design &rarr; Timeline Editing &rarr; Automated QC & Export.
  2. **Music Pipeline (M1–M7)**: Musical Brief &rarr; Cue Structure &rarr; Thai/English Lyrics &rarr; Generation & Stems &rarr; Vocal Synthesis &rarr; Hit-point Video Sync &rarr; Master Mix (-14 LUFS).
- **Step Assistant Contract**: Every stage is powered by a dedicated AI specialist following an 8-part JSON contract (Explain, Interview, Propose, Estimate, Critique, Refine, Check, Handoff) overseen by an AI Producer.

---

## 2. User Experience & Visual Design

### Aesthetic Direction: Cyberpunk Studio Dark Mode
- **Palette**: Deep slate background (`#0A0D14`), obsidian card surfaces (`#121722`), electric neon cyan (`#00F0FF`) for film operations, hyper magenta (`#FF007F`) for audio/music operations, neon amber (`#FFB800`) for cost/QC warnings, and crisp mint (`#00E676`) for approvals.
- **Typography**: High-legibility modern sans with Thai font metrics (Noto Sans Thai compatible), distinct monospace timecodes (`00:01:24.12`), and clean hierarchy.
- **Layout Architecture (3-Zone Studio)**:
  - **Zone 1: Pipeline Navigator & Telemetry**: Progress tracker across F1–F12 and M1–M7, project memory summary, budget meter ($0.00 / Free tier), and current status badge.
  - **Zone 2: Main Stage & Production Workspace**: Responsive multi-view editor (Story Beat Grid, Script Table, Visual Storyboard Cards, Keyframe Comparator, Multi-track Timeline with Audio Ducking Waves, and QC Checklist).
  - **Zone 3: AI Co-pilot & Assistant Drawer**: Interactive conversation pane, structured 3-proposal cards, 1–5 critique radar, quick-refine chip prompts, and Approval Gate button.

```
┌────────────────────────────────────────────────────────────────────────┐
│  CINESONIC STUDIO   [F5: Storyboard & Shot List]   [Budget: $0.00 Free]│
├──────────────┬──────────────────────────────────────────┬──────────────┤
│ PIPELINE     │ WORKSPACE: Shot 3 of 8 (Scene 1)         │ AI CO-PILOT  │
│ [x] F1 Brief │ ┌──────────────────┐ ┌─────────────────┐ │ Step Ass't   │
│ [x] F2 Story │ │ Shot #3: Close-up│ │ Consistency: 96%│ │ F5 Director  │
│ [x] F3 Bible │ │ Angle: Dutch Low │ │ Char: 'Kaew'    │ │              │
│ [x] F4 Script│ │ Motion: Pan L->R │ │ Seed: #481092   │ │ "Proposal:   │
│ [>] F5 Shots │ └──────────────────┘ └─────────────────┘ │  A) Wide Est │
│ [ ] F6 Frame │ [Take 1: Draft]  [Take 2: Active] [+]   │  B) Low Pan  │
│ [ ] F7 Video │ Prompt: "Cyberpunk Bangkok rainy neon.."│  C) OTS Track│
│ [ ] F8 Voice │ Duration: 4.5s | Audio Cue: Heavy Synth │              │
│ [ ] F9 Music │ ─────────────────────────────────────── │ [Critique 4/5│
│ [ ] F10 SFX  │ [<< Rewind]  [ Play Shot ]  [ Next Shot]│ [Refine Chip]│
│ [ ] F11 Edit │ ─────────────────────────────────────── │              │
│ [ ] F12 QC   │ [Timeline Track 1: Video | Track 2: SFX]│ [APPROVE &   │
│              │ [Track 3: Dialogue       | Track 4: BGM]│  PROCEED ->] │
└──────────────┴──────────────────────────────────────────┴──────────────┘
```

---

## 3. Key Product Decisions & Trade-Offs

1. **Dual Execution Engine (Mock Simulator + Gemini Direct REST)**:
   - *Decision*: Include an offline canvas/audio procedural generation engine as the default, with seamless switching to Google Gemini API (`gemini-3.5-flash` for reasoning/text, `gemini-2.5-flash-image` for visuals).
   - *Why*: Allows zero-friction evaluation, rapid iteration, and immediate testability without incurring API fees or network latency, while delivering true AI power when keys are provided.
2. **Native Android Compose vs. Web**:
   - *Decision*: Native Jetpack Compose with Material 3, Room Database, Kotlin Coroutines/Flow, Android Canvas graphics rendering, and Android MediaPlayer / AudioTrack synthesis.
   - *Why*: Delivers 60fps performance on mobile, robust offline storage in SQLite, native Android audio/video capabilities, and adheres strictly to Android runtime guidelines.
3. **Structured JSON Validation for All AI Assistants**:
   - *Decision*: Every AI assistant invocation uses strictly typed Kotlin Serialization schemas ensuring `Explain`, `Interview`, `Propose`, `Estimate`, `Critique`, `Refine`, `Check`, and `Handoff` fields are parsed deterministically.
   - *Why*: Prevents unstructured hallucination, keeps UI components cleanly reactive, and protects pipeline gates.
4. **Approval Gate & Budget Ceiling**:
   - *Decision*: The pipeline cannot advance past any step until the user clicks "Approve Step" (Guided Mode) or reviews the Auto-run summary (Auto Mode).
   - *Why*: Guarantees user sovereignty over creative choices and prevents unexpected costs.

---

## 4. Technical Architecture & Data Strategy

```
┌────────────────────────────────────────────────────────────────────────┐
│                        Jetpack Compose UI Layer                        │
│  ┌───────────────────────┐  ┌────────────────────┐  ┌────────────────┐ │
│  │ PipelineNavigationRow │  │ WorkspaceContainer │  │ CoPilotDrawer  │ │
│  │ (F1-F12 / M1-M7 Tabs) │  │ (Editors & Canvas) │  │ (Chat & Gates) │ │
│  └───────────────────────┘  └────────────────────┘  └────────────────┘ │
└───────────────────────────────────┬────────────────────────────────────┘
                                    │ UI State & User Events
┌───────────────────────────────────▼────────────────────────────────────┐
│                    ProductionViewModel & AI Producer                   │
│  - Pipeline State Machine (Step Transitions & Active Stage)            │
│  - Project Memory Manager (Style Bible, Characters, Narrative Lore)    │
│  - Budget & Token Ledger ($0.00 Cap Monitor)                           │
└──────────────┬──────────────────────────────────────────┬──────────────┘
               │                                          │
┌──────────────▼──────────────────────────┐ ┌─────────────▼──────────────┐
│        AI Assistant & Adapter Hub       │ │     Room Database Layer    │
│ ┌──────────────┐     ┌────────────────┐ │ │ - ProjectEntity            │
│ │ Mock Engine  │     │ Gemini REST    │ │ │ - SceneEntity & ShotEntity │
│ │ - Procedural │     │ - Flash 3.5    │ │ │ - CharacterEntity & Bible  │
│ │   Frames/WAV │     │ - Flash Image  │ │ │ - TimelineClipEntity       │
│ └──────────────┘     └────────────────┘ │ │ - AssistantThreadEntity    │
└─────────────────────────────────────────┘ └────────────────────────────┘
```

### Core Pipeline Steps Detail:
- **F1 (Brief & Concept)**: Prompt input &rarr; Generates genre, tone, target audience, 3 high-concept loglines.
- **F2 (Story Development)**: 3-Act beat sheet (Setup, Inciting Incident, Plot Points 1 & 2, Climax, Resolution).
- **F3 (Style Bible & Characters)**: Visual consistency seed, color palette hex codes, lighting style, character profiles.
- **F4 (Screenplay)**: Formatted script with scene headings, character tags, parentheticals, and Thai/English dialogue.
- **F5 (Storyboard & Shots)**: Shot cards with framing (Wide, Medium, Close-up), camera movement, and duration.
- **F6 (Keyframes)**: High-resolution visual frame per shot adhering to the Style Bible.
- **F7 (Video Takes)**: Multi-take video preview clips, motion speed controls, and take selector.
- **F8 (Voice & Subtitles)**: Character TTS speech synthesis, audio waveforms, and synchronized SRT/VTT captions.
- **F9 (Music & Score)**: Multi-cue soundtrack with BPM, key, and mood transition markers.
- **F10 (Sound Design & SFX)**: Ambience beds, Foley effects, and automatic speech ducking.
- **F11 (Timeline & Editing)**: Multi-track video/audio compositor, cross-dissolve/cut transitions, and LUT color filters.
- **F12 (QC & Master Export)**: Automated continuity checker, audio loudness normalization (-14 LUFS), and project package exporter.

---

## 5. Major Risks & Mitigation Strategies

1. **Character & Visual Consistency Drift Across Shots**:
   - *Mitigation*: The Style Bible locks a Character Consistency Pack containing fixed descriptive tokens (clothing, hairstyle, facial markers, lighting scheme) and fixed negative prompts. Every shot prompt automatically prepends the consistency tokens.
2. **Generation Cost & Quota Overruns**:
   - *Mitigation*: Hard budget ceiling set to $0.00 by default. Every action provides an `Estimate` before submission. The Mock Engine delivers high-fidelity procedural results without consuming API tokens.
3. **CORS, Network & Latency Failures in Multi-Model Pipeline**:
   - *Mitigation*: Native Android HTTP client (`OkHttpClient` with 60s timeouts) bypasses browser CORS completely. All network calls feature exponential backoff, error normalization into Thai explanatory messages, and full offline fallback.
