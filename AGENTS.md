# CineSonic Studio: Agent Roles & Step Assistant Contracts

## 1. Role Definitions

- **AI Producer (Orchestrator)**: Manages global Project Memory (Brief, Style Bible, Character Sheets, Budget Ledger). Keeps track of overall timeline continuity, budget caps, and routes context to specialized Step Assistants.
- **Step Assistants (F1–F12, M1–M7)**: Domain-specific creative directors fulfilling the 8-part contract.

## 2. Step Assistant Contract (8 Requirements)

Every Step Assistant returns typed responses adhering to:
1. **Explain**: Clear, jargon-free overview of the step with practical examples.
2. **Interview**: 1–3 optional clarifying questions to hone the vision.
3. **Propose**: 3 distinctly different creative alternatives with trade-offs.
4. **Estimate**: Projected latency and compute cost ($0.00 mock / API token cost).
5. **Critique**: Multi-rubric rating (1–5) and concrete tips for improvement.
6. **Refine**: Single-command non-destructive adjustments.
7. **Check**: Cross-checking against prior steps (characters, tone, rules, timecode).
8. **Handoff**: Summary of locked assets forwarded to the subsequent step.

## 3. Ethics, Privacy & Safety Rules

- No unauthorized likenesses, deepfakes, or copyrighted celebrity imitations.
- Local-first encryption for user keys; no telemetry leaks.
- Clear AI generation provenance tags embedded in exported project bundles.
- PDPA compliant with full local data purge capability.
