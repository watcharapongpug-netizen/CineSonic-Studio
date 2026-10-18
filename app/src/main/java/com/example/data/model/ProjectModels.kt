package com.example.data.model

data class Project(
    val id: String = "proj_default",
    val title: String = "Untitled Short Film",
    val ideaPrompt: String = "",
    val isAutoMode: Boolean = false,
    val currentFilmStep: FilmStep = FilmStep.F1_BRIEF,
    val currentMusicStep: MusicStep = MusicStep.M1_BRIEF,
    val activePipeline: PipelineType = PipelineType.FILM,
    val budgetLimitUsd: Double = 0.0,
    val budgetSpentUsd: Double = 0.0,
    val createdAt: Long = System.currentTimeMillis()
)

enum class PipelineType {
    FILM,
    MUSIC
}

enum class FilmStep(val stepCode: String, val titleTh: String, val titleEn: String) {
    F1_BRIEF("F1", "ไอเดียและบรีฟ", "Idea & Brief"),
    F2_STORY("F2", "พัฒนาเรื่อง", "Story & Beats"),
    F3_BIBLE("F3", "ตัวละครและสไตล์", "Bible & Characters"),
    F4_SCRIPT("F4", "บทภาพยนตร์", "Screenplay"),
    F5_STORYBOARD("F5", "สตอรี่บอร์ด", "Storyboard & Shots"),
    F6_KEYFRAMES("F6", "คีย์เฟรมภาพ", "Keyframes"),
    F7_VIDEO("F7", "สร้างวิดีโอคลิป", "Video Takes"),
    F8_VOICE("F8", "เสียงพากย์และซับ", "Voice & Subtitles"),
    F9_SCORE("F9", "ดนตรีประกอบภาพ", "Film Score"),
    F10_SFX("F10", "เสียงประกอบและมิกซ์", "Sound Design & SFX"),
    F11_EDITING("F11", "ตัดต่อบนไทม์ไลน์", "Timeline Editing"),
    F12_QC_EXPORT("F12", "ตรวจสอบและส่งออก", "QC & Export")
}

enum class MusicStep(val stepCode: String, val titleTh: String, val titleEn: String) {
    M1_BRIEF("M1", "บรีฟดนตรี", "Music Brief"),
    M2_STRUCTURE("M2", "โครงสร้างท่อน", "Track Structure"),
    M3_LYRICS("M3", "เนื้อเพลงและสัมผัส", "Lyrics & Prosody"),
    M4_MUSIC_GEN("M4", "สร้างดนตรี", "Music Generation"),
    M5_VOCAL_GEN("M5", "สร้างเสียงร้อง", "Vocal Synthesis"),
    M6_HIT_POINTS("M6", "ตัดต่อเข้ากับภาพ", "Hit-point Sync"),
    M7_MASTER_MIX("M7", "มิกซ์และส่งออก", "Mastering & Export")
}

data class BriefData(
    val genre: String = "Cyberpunk Neo-Noir",
    val targetAudience: String = "คอหนังไซไฟและแฟนเพลงซินธ์เวฟ",
    val tone: String = "หม่น ลึกลับ แต่มีความหวัง",
    val durationSec: Int = 60,
    val primaryLanguage: String = "ไทยและอังกฤษ",
    val budgetCap: Double = 0.0,
    val concepts: List<String> = emptyList()
)

data class StoryBeatSheet(
    val logline: String = "",
    val synopsis: String = "",
    val act1Setup: String = "",
    val act1Inciting: String = "",
    val act2Midpoint: String = "",
    val act2Crisis: String = "",
    val act3Climax: String = "",
    val act3Resolution: String = "",
    val protagonistGoal: String = "",
    val stakes: String = ""
)

data class CharacterProfile(
    val id: String,
    val name: String,
    val role: String,
    val visualPrompt: String,
    val voiceType: String,
    val consistencyTokens: String
)

data class StyleBible(
    val visualAesthetic: String = "Cinematic Cyberpunk 35mm Anamorphic",
    val colorPalette: List<String> = listOf("#00F0FF", "#FF007F", "#121722", "#FFB800"),
    val lightingMood: String = "Neon rim light with deep shadows and soft rain reflections",
    val negativePrompt: String = "blurry, distorted faces, duplicate limbs, cartoon, low resolution",
    val consistencySeed: Long = 481092L
)

data class SceneScript(
    val sceneNumber: Int,
    val heading: String,
    val description: String,
    val dialogueLines: List<DialogueLine>
)

data class DialogueLine(
    val speaker: String,
    val lineTh: String,
    val lineEn: String,
    val emotion: String
)

data class ShotItem(
    val shotId: String,
    val sceneIndex: Int,
    val shotNumber: Int,
    val shotSize: String, // Wide, Medium, Close-up, POV
    val cameraAngle: String, // Low angle, Eye level, High angle, Dutch
    val cameraMotion: String, // Static, Pan Left, Tilt Up, Tracking
    val durationSec: Float,
    val visualPromptEn: String,
    val dialogue: String = "",
    val keyframeHue: Int = 180, // For mock procedural visual rendering
    val consistencyScore: Int = 94,
    val takes: List<VideoTake> = emptyList(),
    val selectedTakeIndex: Int = 0
)

data class VideoTake(
    val takeNumber: Int,
    val label: String,
    val status: String, // "Draft", "Approved", "Failed"
    val durationSec: Float,
    val seed: Long,
    val qualityScore: Int = 92
)

data class MusicBrief(
    val intent: String = "Cinematic Emotional Score",
    val genre: String = "Cyber-Synthwave / Orchestral Hybrid",
    val mood: String = "Tense & Uplifting",
    val bpm: Int = 118,
    val musicalKey: String = "D Minor",
    val durationSec: Int = 60,
    val lyricsThEn: String = "",
    val loudnessLufs: Float = -14.0f
)

data class TimelineClip(
    val id: String,
    val trackType: TrackType,
    val title: String,
    val startMs: Long,
    val durationMs: Long,
    val volume: Float = 1.0f,
    val isMuted: Boolean = false
)

enum class TrackType {
    VIDEO,
    DIALOGUE,
    SFX,
    BGM
}

data class QCCheckItem(
    val id: String,
    val category: String,
    val title: String,
    val description: String,
    val passed: Boolean,
    val scoreText: String
)

data class UsageRecord(
    val timestamp: Long = System.currentTimeMillis(),
    val step: String,
    val provider: String,
    val costUsd: Double,
    val tokensOrDuration: String
)
