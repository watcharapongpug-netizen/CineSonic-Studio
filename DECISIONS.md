# CineSonic Studio: Architecture Decisions Record (ADR)

## ADR-001: Hybrid Mock Engine + Gemini API
- **Decision**: The studio operates out-of-the-box using an internal procedural generator (Mock Provider) that simulates AI video frames, audio synthesizer, and creative proposals at $0.00 cost, while supporting direct Google Gemini API for real AI inference.
- **Rationale**: Enables instant local testing, offline workflows, zero user financial risk, and satisfies user desire for a free starter tier.

## ADR-002: Native Jetpack Compose & Room Persistence
- **Decision**: Built natively with Jetpack Compose (M3) and Room SQLite database instead of a WebView single-page app.
- **Rationale**: The platform container is an Android OS runtime; native Compose provides superior touch handling, hardware-accelerated Canvas rendering, smoother timeline animations, and native Android media playback.

## ADR-003: Cyberpunk Dark Theme (#0A0D14, #00F0FF, #FF007F)
- **Decision**: High-contrast dark cyberpunk aesthetic with electric cyan (film), magenta (music), and mint (approvals).
- **Rationale**: Cinematic atmosphere suited for creative media editing environments, reducing eye strain during video/audio production.

## ADR-004: Left-to-Right (L ➔ R) Sequential Studio Architecture
- **Decision**: Restructured windows, functions, UI icons, and production components to eliminate long vertical scrolling in favor of a horizontal Left-to-Right progression:
  1. Horizontal step pipeline stepper (F1 ➔ F12, M1 ➔ M7) with directional arrows and instant ◀ Previous / Next ▶ controls.
  2. 3-Stage horizontal workflow tabs: `[ 🎬 1. จอมอนิเตอร์ & ช็อต ]` ➔ `[ 🛠️ 2. เครื่องมือสร้างสรรค์ ]` ➔ `[ 🤖 3. ผู้ช่วย AI Co-Pilot ]`.
  3. Horizontal Filmstrip Reel (`LazyRow`) displaying shots sequentially from left to right with live thumbnail preview, camera angles, and durations.
  4. 3-Act story structure laid out horizontally (Act 1 Setup ➔ Act 2 Crisis ➔ Act 3 Climax).
  5. Co-Pilot 3 Creative Proposals compared side-by-side (Option A ➔ Option B ➔ Option C).
  6. Persistent bottom Studio Transport Bar with prominent Gate approval and bidirectional navigation.
- **Rationale**: Follows professional digital audio workstation (DAW) and NLE film editing conventions (Premiere, Resolve, Final Cut). Eliminates confusing vertical page jumping, enabling users to effortlessly navigate and understand the end-to-end production pipeline.

