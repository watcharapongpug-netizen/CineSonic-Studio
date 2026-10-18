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
