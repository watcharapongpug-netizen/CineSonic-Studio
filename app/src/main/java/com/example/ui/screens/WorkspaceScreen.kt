package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.*
import com.example.ui.components.TimelineEditorView
import com.example.ui.components.VisualCanvasPreview
import com.example.ui.theme.*

@Composable
fun WorkspaceScreen(
    project: Project,
    brief: BriefData,
    storyBeats: StoryBeatSheet,
    styleBible: StyleBible,
    characters: List<CharacterProfile>,
    screenplay: List<SceneScript>,
    shots: List<ShotItem>,
    selectedShotIndex: Int,
    musicBrief: MusicBrief,
    timelineClips: List<TimelineClip>,
    qcChecklist: List<QCCheckItem>,
    isPlaying: Boolean,
    timelinePositionMs: Long,
    onIdeaSubmit: (String) -> Unit,
    onSelectShot: (Int) -> Unit,
    onSelectTake: (Int, Int) -> Unit,
    onTogglePlay: () -> Unit,
    onSeek: (Long) -> Unit,
    onApproveCurrentStep: () -> Unit,
    onOpenExport: () -> Unit,
    onOpenShotEdit: (ShotItem) -> Unit,
    modifier: Modifier = Modifier
) {
    var ideaInput by remember { mutableStateOf(project.ideaPrompt) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Stage 1-Sentence Idea Input Bar (Always accessible for starting fresh or tuning)
        Card(
            colors = CardDefaults.cardColors(containerColor = CyberSurface),
            shape = RoundedCornerShape(10.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = ideaInput,
                    onValueChange = { ideaInput = it },
                    placeholder = {
                        Text(
                            text = "ใส่ไอเดียประโยคเดียว (เช่น นักสืบไซบอร์กตามหาความทรงจำในกรุงเทพฯ ปี 2088)",
                            fontSize = 11.sp,
                            color = TextTertiary
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = CyberBorder,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .testTag("idea_input_field")
                )

                Button(
                    onClick = { onIdeaSubmit(ideaInput) },
                    colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.testTag("generate_from_idea_button")
                ) {
                    Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "สร้างเลย", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Live Simulated Viewport Preview
        VisualCanvasPreview(
            activeShot = shots.getOrNull(selectedShotIndex),
            isPlaying = isPlaying,
            currentTimeMs = timelinePositionMs,
            totalDurationMs = 60000L,
            onTogglePlay = onTogglePlay,
            onSeek = onSeek
        )

        // Dynamic Workspace Content per Stage
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (project.activePipeline == PipelineType.FILM) {
                when (project.currentFilmStep) {
                    FilmStep.F1_BRIEF -> item {
                        BriefView(brief = brief)
                    }
                    FilmStep.F2_STORY -> item {
                        StoryBeatsView(storyBeats = storyBeats)
                    }
                    FilmStep.F3_BIBLE -> item {
                        StyleBibleAndCharactersView(styleBible = styleBible, characters = characters)
                    }
                    FilmStep.F4_SCRIPT -> item {
                        ScreenplayView(screenplay = screenplay)
                    }
                    FilmStep.F5_STORYBOARD -> item {
                        StoryboardView(
                            shots = shots,
                            selectedShotIndex = selectedShotIndex,
                            onSelectShot = onSelectShot,
                            onOpenShotEdit = onOpenShotEdit
                        )
                    }
                    FilmStep.F6_KEYFRAMES -> item {
                        KeyframesView(
                            shots = shots,
                            selectedShotIndex = selectedShotIndex,
                            onSelectShot = onSelectShot,
                            onOpenShotEdit = onOpenShotEdit
                        )
                    }
                    FilmStep.F7_VIDEO -> item {
                        VideoTakesView(
                            shots = shots,
                            selectedShotIndex = selectedShotIndex,
                            onSelectShot = onSelectShot,
                            onSelectTake = onSelectTake
                        )
                    }
                    FilmStep.F8_VOICE -> item {
                        VoiceAndSubtitlesView(screenplay = screenplay)
                    }
                    FilmStep.F9_SCORE -> item {
                        MusicScoreView(musicBrief = musicBrief)
                    }
                    FilmStep.F10_SFX -> item {
                        SoundDesignView()
                    }
                    FilmStep.F11_EDITING -> item {
                        TimelineEditorView(
                            clips = timelineClips,
                            currentPositionMs = timelinePositionMs,
                            onSeek = onSeek
                        )
                    }
                    FilmStep.F12_QC_EXPORT -> item {
                        QCAndExportView(qcChecklist = qcChecklist, onOpenExport = onOpenExport)
                    }
                }
            } else {
                // Music Pipeline Views (M1 to M7)
                item {
                    MusicPipelineMasterView(
                        musicStep = project.currentMusicStep,
                        musicBrief = musicBrief
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// SUBVIEWS FOR EACH STAGE
// -------------------------------------------------------------

@Composable
fun BriefView(brief: BriefData) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "F1: พิมพ์เขียวบรีฟภาพยนตร์ (Production Brief)",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = NeonCyan
            )
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoTag(label = "แนวภาพยนตร์", value = brief.genre, modifier = Modifier.weight(1f))
                InfoTag(label = "ความยาว", value = "${brief.durationSec} วินาที", modifier = Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoTag(label = "โทนอารมณ์", value = brief.tone, modifier = Modifier.weight(1f))
                InfoTag(label = "ภาษา", value = brief.primaryLanguage, modifier = Modifier.weight(1f))
            }
            InfoTag(label = "กลุ่มผู้ชมเป้าหมาย", value = brief.targetAudience, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "3 แนวคิดคอนเซ็ปต์เริ่มต้น:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = NeonAmber)
            brief.concepts.forEachIndexed { idx, concept ->
                Surface(
                    color = CyberSurfaceVariant,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = concept,
                        fontSize = 11.sp,
                        color = TextPrimary,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun StoryBeatsView(storyBeats: StoryBeatSheet) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(text = "F2: โครงสร้างเรื่องและจังหวะ 3 องก์ (Beat Sheet)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeonCyan)
            
            // Logline
            Surface(
                color = CyberSurfaceVariant,
                shape = RoundedCornerShape(6.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "LOGLINE:", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = NeonCyan)
                    Text(text = storyBeats.logline, fontSize = 12.sp, color = TextPrimary)
                }
            }

            // 3-Act Beats
            BeatItem(actTitle = "องก์ที่ 1: การปูเรื่อง (Setup & Inciting)", content = storyBeats.act1Setup + " " + storyBeats.act1Inciting)
            BeatItem(actTitle = "องก์ที่ 2: วิกฤตและการเผชิญหน้า (Midpoint & Crisis)", content = storyBeats.act2Midpoint + " " + storyBeats.act2Crisis)
            BeatItem(actTitle = "องก์ที่ 3: ไคลแมกซ์และบทสรุป (Climax & Resolution)", content = storyBeats.act3Climax + " " + storyBeats.act3Resolution)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoTag(label = "เป้าหมายตัวเอก", value = storyBeats.protagonistGoal, modifier = Modifier.weight(1f))
                InfoTag(label = "เดิมพัน", value = storyBeats.stakes, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun BeatItem(actTitle: String, content: String) {
    Column {
        Text(text = actTitle, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NeonAmber)
        Text(text = content, fontSize = 11.sp, color = TextSecondary)
    }
}

@Composable
fun StyleBibleAndCharactersView(styleBible: StyleBible, characters: List<CharacterProfile>) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(text = "F3: Style Bible & Consistency Pack", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeonCyan)

            Text(text = "สไตล์ภาพ: ${styleBible.visualAesthetic}", fontSize = 12.sp, color = TextPrimary)
            Text(text = "แสงและบรรยากาศ: ${styleBible.lightingMood}", fontSize = 11.sp, color = TextSecondary)

            // Color Palette Swatches
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(text = "คู่สีหลัก:", fontSize = 11.sp, color = TextSecondary)
                styleBible.colorPalette.forEach { hex ->
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(Color(android.graphics.Color.parseColor(hex)))
                            .border(1.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                    )
                }
            }

            HorizontalDivider(color = CyberBorder)

            Text(text = "ตัวละครที่ล็อกความต่อเนื่อง (Character Profiles):", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = NeonMagenta)
            characters.forEach { char ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = char.name, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NeonCyan)
                            Text(text = char.role, fontSize = 10.sp, color = NeonAmber)
                        }
                        Text(text = "รูปลักษณ์: ${char.visualPrompt}", fontSize = 11.sp, color = TextSecondary)
                        Text(text = "เสียง: ${char.voiceType}", fontSize = 10.sp, color = TextTertiary)
                        Text(
                            text = "Consistency Tokens: ${char.consistencyTokens}",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = NeonMint
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ScreenplayView(screenplay: List<SceneScript>) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(text = "F4: บทภาพยนตร์ฉากต่อฉาก (Screenplay)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeonCyan)

            screenplay.forEach { scene ->
                Card(
                    colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = scene.heading,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = NeonCyan,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(text = scene.description, fontSize = 11.sp, color = TextSecondary)

                        HorizontalDivider(color = CyberBorder.copy(alpha = 0.5f))

                        scene.dialogueLines.forEach { line ->
                            Column(modifier = Modifier.padding(start = 12.dp)) {
                                Text(
                                    text = "${line.speaker} (${line.emotion}):",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NeonAmber
                                )
                                Text(text = "TH: \"${line.lineTh}\"", fontSize = 11.sp, color = Color.White)
                                Text(text = "EN: \"${line.lineEn}\"", fontSize = 10.sp, color = TextSecondary, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StoryboardView(
    shots: List<ShotItem>,
    selectedShotIndex: Int,
    onSelectShot: (Int) -> Unit,
    onOpenShotEdit: (ShotItem) -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "F5: สตอรี่บอร์ดและช็อตลิสต์ (Shot List)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeonCyan)
                Text(text = "8 Shots | 180° Rule Verified", fontSize = 10.sp, color = NeonMint, fontFamily = FontFamily.Monospace)
            }

            shots.forEachIndexed { index, shot ->
                val isSelected = index == selectedShotIndex
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = if (isSelected) NeonCyan.copy(alpha = 0.12f) else CyberSurfaceVariant
                    ),
                    shape = RoundedCornerShape(8.dp),
                    border = androidx.compose.foundation.BorderStroke(
                        width = if (isSelected) 1.5.dp else 1.dp,
                        color = if (isSelected) NeonCyan else CyberBorder
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelectShot(index) }
                        .testTag("shot_item_${shot.shotNumber}")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Surface(
                            color = if (isSelected) NeonCyan else CyberBorder,
                            shape = CircleShape,
                            modifier = Modifier.size(28.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Text(
                                    text = "#${shot.shotNumber}",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isSelected) Color.Black else Color.White
                                )
                            }
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                                Text(text = shot.shotSize, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = NeonCyan)
                                Text(text = "• ${shot.cameraAngle}", fontSize = 11.sp, color = TextSecondary)
                                Text(text = "• ${shot.durationSec}s", fontSize = 11.sp, color = NeonAmber, fontFamily = FontFamily.Monospace)
                            }
                            Text(text = shot.dialogue, fontSize = 10.sp, color = TextPrimary, maxLines = 1)
                        }

                        IconButton(
                            onClick = { onOpenShotEdit(shot) },
                            modifier = Modifier.size(28.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = "แก้ไขช็อต",
                                tint = NeonCyan,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KeyframesView(
    shots: List<ShotItem>,
    selectedShotIndex: Int,
    onSelectShot: (Int) -> Unit,
    onOpenShotEdit: (ShotItem) -> Unit
) {
    val activeShot = shots.getOrNull(selectedShotIndex) ?: shots.firstOrNull()
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "F6: คีย์เฟรมภาพต้นแบบ (Keyframe Inspector)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeonCyan)

            if (activeShot != null) {
                Surface(
                    color = CyberSurfaceVariant,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "ช็อตที่ ${activeShot.shotNumber}: ${activeShot.shotSize}", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NeonCyan)
                            Text(text = "Consistency: ${activeShot.consistencyScore}%", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NeonMint)
                        }
                        Text(text = "Visual Prompt (EN):", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = NeonAmber)
                        Text(text = activeShot.visualPromptEn, fontSize = 11.sp, color = TextPrimary)

                        Spacer(modifier = Modifier.height(4.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { onOpenShotEdit(activeShot) },
                                colors = ButtonDefaults.buttonColors(containerColor = CyberBorder),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "แก้ไข / Inpaint ช็อต", fontSize = 11.sp)
                            }
                            Button(
                                onClick = { onOpenShotEdit(activeShot) },
                                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "สร้างตัวเลือกใหม่", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VideoTakesView(
    shots: List<ShotItem>,
    selectedShotIndex: Int,
    onSelectShot: (Int) -> Unit,
    onSelectTake: (Int, Int) -> Unit
) {
    val activeShot = shots.getOrNull(selectedShotIndex) ?: shots.firstOrNull()
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "F7: จัดการคลิปวิดีโอ (Video Takes Manager)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeonCyan)

            if (activeShot != null) {
                Text(text = "ช็อต #${activeShot.shotNumber} (${activeShot.durationSec}s): มี ${activeShot.takes.size} เทค", fontSize = 11.sp, color = TextSecondary)
                activeShot.takes.forEachIndexed { takeIdx, take ->
                    val isTakeSelected = takeIdx == activeShot.selectedTakeIndex
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (isTakeSelected) NeonCyan.copy(alpha = 0.15f) else CyberSurfaceVariant
                        ),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (isTakeSelected) NeonCyan else CyberBorder
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelectTake(selectedShotIndex, takeIdx) }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(text = take.label, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = if (isTakeSelected) NeonCyan else TextPrimary)
                                Text(text = "Seed: #${take.seed} | สถานะ: ${take.status}", fontSize = 10.sp, color = TextSecondary)
                            }
                            Surface(
                                color = if (take.status == "Approved") NeonMint.copy(alpha = 0.2f) else CyberBorder,
                                shape = RoundedCornerShape(4.dp)
                            ) {
                                Text(
                                    text = if (isTakeSelected) "✓ ACTIVE" else "SELECT",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isTakeSelected) NeonCyan else TextSecondary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VoiceAndSubtitlesView(screenplay: List<SceneScript>) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "F8: เสียงพากย์และซับไตเติล (Voice & Timed Subtitles)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeonCyan)
            Text(text = "TTS Phonetic Matching: ตรวจสอบ ร/ล และวรรณยุกต์ไทย 100% ตรงเวลา", fontSize = 11.sp, color = NeonMint)

            screenplay.flatMap { it.dialogueLines }.forEach { line ->
                Surface(
                    color = CyberSurfaceVariant,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.Mic, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(18.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "${line.speaker} • ${line.emotion}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = NeonAmber)
                            Text(text = line.lineTh, fontSize = 11.sp, color = TextPrimary)
                            Text(text = line.lineEn, fontSize = 10.sp, color = TextSecondary)
                        }
                        Surface(color = CyberBackground, shape = RoundedCornerShape(4.dp)) {
                            Text(text = "TTS OK", fontSize = 9.sp, color = NeonMint, modifier = Modifier.padding(4.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MusicScoreView(musicBrief: MusicBrief) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "F9: ดนตรีประกอบภาพยนตร์ (Film Score Cue Sheet)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeonMagenta)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoTag(label = "BPM", value = "${musicBrief.bpm}", modifier = Modifier.weight(1f))
                InfoTag(label = "Musical Key", value = musicBrief.musicalKey, modifier = Modifier.weight(1f))
                InfoTag(label = "เป้าหมาย LUFS", value = "${musicBrief.loudnessLufs}", modifier = Modifier.weight(1f))
            }

            Surface(
                color = CyberSurfaceVariant,
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "เนื้อเพลงและท่อนดนตรี (Lyrics & Cues):", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = NeonMagenta)
                    Text(
                        text = musicBrief.lyricsThEn,
                        fontSize = 11.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TextPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun SoundDesignView() {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "F10: ออกแบบเสียงและมิกซ์ (Sound Design & Ducking)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeonCyan)
            Text(text = "ระบบปรับลดเสียงอัตโนมัติ (Audio Ducking Hierarchy):", fontSize = 11.sp, color = NeonAmber)

            Surface(color = CyberSurfaceVariant, shape = RoundedCornerShape(6.dp), modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "1. บทพูด (Dialogue): ลำดับสูงสุด 0 dB (ไม่ถูกกดเสียง)", fontSize = 11.sp, color = NeonMint)
                    Text(text = "2. ซาวด์เอฟเฟกต์ (SFX): ลด 3 dB เมื่อมีบทพูด", fontSize = 11.sp, color = TextPrimary)
                    Text(text = "3. ดนตรีประกอบ (Score): ลด 5 dB เมื่อมีบทพูด", fontSize = 11.sp, color = TextSecondary)
                }
            }
        }
    }
}

@Composable
fun QCAndExportView(qcChecklist: List<QCCheckItem>, onOpenExport: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "F12: ตรวจสอบคุณภาพและส่งออก (QC & Delivery)", fontWeight = FontWeight.Bold, fontSize = 13.sp, color = NeonMint)
                Surface(color = NeonMint.copy(alpha = 0.2f), shape = RoundedCornerShape(4.dp)) {
                    Text(text = "QC 100% PASSED", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = NeonMint, modifier = Modifier.padding(4.dp))
                }
            }

            qcChecklist.forEach { item ->
                Surface(
                    color = CyberSurfaceVariant,
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = NeonMint, modifier = Modifier.size(16.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = "${item.category}: ${item.title}", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            Text(text = item.description, fontSize = 10.sp, color = TextSecondary)
                        }
                        Text(text = item.scoreText, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = NeonMint)
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))
            Text(text = "ตัวเลือกการส่งออกผลงาน:", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = NeonCyan)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(
                    onClick = onOpenExport,
                    colors = ButtonDefaults.buttonColors(containerColor = NeonMint, contentColor = Color.Black),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "ส่งออกวิดีโอ MP4 / ซับ", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
                Button(
                    onClick = onOpenExport,
                    colors = ButtonDefaults.buttonColors(containerColor = CyberBorder),
                    shape = RoundedCornerShape(6.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    Text(text = "ส่งออกโปรเจกต์ JSON", fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
fun MusicPipelineMasterView(musicStep: MusicStep, musicBrief: MusicBrief) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
                text = "CineSonic Music Pipeline [${musicStep.stepCode}: ${musicStep.titleTh}]",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = NeonMagenta
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoTag(label = "เป้าหมายดนตรี", value = musicBrief.intent, modifier = Modifier.weight(1f))
                InfoTag(label = "แนวเพลง", value = musicBrief.genre, modifier = Modifier.weight(1f))
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoTag(label = "ความเร็ว BPM", value = "${musicBrief.bpm} BPM", modifier = Modifier.weight(1f))
                InfoTag(label = "คีย์เพลง", value = musicBrief.musicalKey, modifier = Modifier.weight(1f))
                InfoTag(label = "ระดับ LUFS", value = "${musicBrief.loudnessLufs} LUFS", modifier = Modifier.weight(1f))
            }

            Surface(
                color = CyberSurfaceVariant,
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(text = "เนื้อเพลงและจังหวะพยางค์ (Prosody & Thai Tonal Match):", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = NeonMagenta)
                    Text(text = musicBrief.lyricsThEn, fontSize = 11.sp, fontFamily = FontFamily.Monospace, color = TextPrimary)
                }
            }
        }
    }
}

@Composable
fun InfoTag(label: String, value: String, modifier: Modifier = Modifier) {
    Surface(
        color = CyberSurfaceVariant,
        shape = RoundedCornerShape(6.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = label, fontSize = 9.sp, color = TextTertiary)
            Text(text = value, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        }
    }
}
