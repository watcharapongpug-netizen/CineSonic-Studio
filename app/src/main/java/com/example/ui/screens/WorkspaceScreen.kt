package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.automirrored.filled.NavigateNext
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

enum class StudioWorkspaceTab(val titleTh: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
    MONITOR("1. จอมอนิเตอร์ & ช็อต", Icons.Default.Tv),
    STEP_CANVAS("2. เครื่องมือประจำขั้น", Icons.Default.Layers),
    COPILOT("3. ผู้ช่วย AI Co-pilot", Icons.Default.SmartToy)
}

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
    currentStepContract: StepAssistantResponse?,
    isPlaying: Boolean,
    timelinePositionMs: Long,
    onIdeaSubmit: (String) -> Unit,
    onSelectShot: (Int) -> Unit,
    onSelectTake: (Int, Int) -> Unit,
    onTogglePlay: () -> Unit,
    onSeek: (Long) -> Unit,
    onApproveCurrentStep: () -> Unit,
    onPreviousStep: () -> Unit,
    onOpenExport: () -> Unit,
    onOpenShotEdit: (ShotItem) -> Unit,
    onSelectProposal: (ProposalOption) -> Unit,
    onQuickRefine: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var ideaInput by remember { mutableStateOf(project.ideaPrompt) }
    var activeTab by remember { mutableStateOf(StudioWorkspaceTab.MONITOR) }

    BoxWithConstraints(modifier = modifier.fillMaxSize()) {
        val isWideScreen = maxWidth >= 760.dp

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // =========================================================================
            // TOP DOCK: 1-Sentence Idea Bar & Quick Generator (Left to Right)
            // =========================================================================
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(NeonCyan.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Lightbulb,
                            contentDescription = null,
                            tint = NeonCyan,
                            modifier = Modifier.size(18.dp)
                        )
                    }

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
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier
                            .height(40.dp)
                            .testTag("generate_from_idea_button")
                    ) {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(14.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "สร้างเลย", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }

            // =========================================================================
            // HORIZONTAL STAGE WORKFLOW SELECTOR (ซ้ายไปขวา: 1. มอนิเตอร์ ➔ 2. เครื่องมือ ➔ 3. ผู้ช่วย)
            // =========================================================================
            if (!isWideScreen) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberSurface)
                        .border(1.dp, CyberBorder, RoundedCornerShape(8.dp))
                        .padding(3.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    StudioWorkspaceTab.values().forEach { tab ->
                        val isSelected = activeTab == tab
                        val tabBg = if (isSelected) NeonCyan.copy(alpha = 0.18f) else Color.Transparent
                        val tabBorder = if (isSelected) NeonCyan else Color.Transparent

                        Row(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(6.dp))
                                .background(tabBg)
                                .border(1.dp, tabBorder, RoundedCornerShape(6.dp))
                                .clickable { activeTab = tab }
                                .padding(vertical = 8.dp, horizontal = 4.dp)
                                .testTag("workspace_tab_${tab.name}"),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = null,
                                tint = if (isSelected) NeonCyan else TextSecondary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = tab.titleTh,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                color = if (isSelected) NeonCyan else TextSecondary
                            )
                        }
                    }
                }
            }

            // =========================================================================
            // MAIN WORKSPACE CONTENT AREA (Responsive Left-to-Right)
            // =========================================================================
            Box(modifier = Modifier.weight(1f).fillMaxWidth()) {
                if (isWideScreen) {
                    // Wide screen: 2-Pane Side-by-Side (Monitor on Left, Step Canvas & Tools on Right)
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        // Left Column (Monitor + Film Reel)
                        Box(modifier = Modifier.weight(0.48f).fillMaxHeight()) {
                            MonitorAndFilmstripView(
                                shots = shots,
                                selectedShotIndex = selectedShotIndex,
                                isPlaying = isPlaying,
                                timelinePositionMs = timelinePositionMs,
                                onSelectShot = onSelectShot,
                                onSelectTake = onSelectTake,
                                onTogglePlay = onTogglePlay,
                                onSeek = onSeek,
                                onOpenShotEdit = onOpenShotEdit
                            )
                        }

                        // Right Column (Step Canvas & Co-Pilot side-by-side or tabbed)
                        Box(modifier = Modifier.weight(0.52f).fillMaxHeight()) {
                            StepCanvasView(
                                project = project,
                                brief = brief,
                                storyBeats = storyBeats,
                                styleBible = styleBible,
                                characters = characters,
                                screenplay = screenplay,
                                shots = shots,
                                selectedShotIndex = selectedShotIndex,
                                musicBrief = musicBrief,
                                timelineClips = timelineClips,
                                qcChecklist = qcChecklist,
                                timelinePositionMs = timelinePositionMs,
                                onSelectShot = onSelectShot,
                                onSelectTake = onSelectTake,
                                onOpenExport = onOpenExport,
                                onOpenShotEdit = onOpenShotEdit,
                                onSeek = onSeek
                            )
                        }
                    }
                } else {
                    // Portrait / Standard Screen: Switch according to Left-to-Right Tab
                    when (activeTab) {
                        StudioWorkspaceTab.MONITOR -> {
                            MonitorAndFilmstripView(
                                shots = shots,
                                selectedShotIndex = selectedShotIndex,
                                isPlaying = isPlaying,
                                timelinePositionMs = timelinePositionMs,
                                onSelectShot = onSelectShot,
                                onSelectTake = onSelectTake,
                                onTogglePlay = onTogglePlay,
                                onSeek = onSeek,
                                onOpenShotEdit = onOpenShotEdit
                            )
                        }
                        StudioWorkspaceTab.STEP_CANVAS -> {
                            StepCanvasView(
                                project = project,
                                brief = brief,
                                storyBeats = storyBeats,
                                styleBible = styleBible,
                                characters = characters,
                                screenplay = screenplay,
                                shots = shots,
                                selectedShotIndex = selectedShotIndex,
                                musicBrief = musicBrief,
                                timelineClips = timelineClips,
                                qcChecklist = qcChecklist,
                                timelinePositionMs = timelinePositionMs,
                                onSelectShot = onSelectShot,
                                onSelectTake = onSelectTake,
                                onOpenExport = onOpenExport,
                                onOpenShotEdit = onOpenShotEdit,
                                onSeek = onSeek
                            )
                        }
                        StudioWorkspaceTab.COPILOT -> {
                            CoPilotEmbeddedView(
                                contract = currentStepContract,
                                onSelectProposal = onSelectProposal,
                                onQuickRefine = onQuickRefine,
                                onApproveStep = onApproveCurrentStep
                            )
                        }
                    }
                }
            }

            // =========================================================================
            // BOTTOM STUDIO TRANSPORT BAR (Left to Right Sequential Stepping)
            // =========================================================================
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Left: Previous Step
                    OutlinedButton(
                        onClick = onPreviousStep,
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = NeonCyan),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        modifier = Modifier.testTag("bottom_prev_step_button")
                    ) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.NavigateBefore, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "ก่อนหน้า", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    // Center: Active Step Badge & Name
                    val currentStepName = if (project.activePipeline == PipelineType.FILM) {
                        "${project.currentFilmStep.stepCode}: ${project.currentFilmStep.titleTh}"
                    } else {
                        "${project.currentMusicStep.stepCode}: ${project.currentMusicStep.titleTh}"
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = if (project.activePipeline == PipelineType.FILM) "ภาพยนตร์ 12 ขั้นตอน (L➔R)" else "ดนตรี 7 ขั้นตอน (L➔R)",
                            fontSize = 9.sp,
                            color = TextTertiary
                        )
                        Text(
                            text = currentStepName,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan
                        )
                    }

                    // Right: Approve Gate & Next Step
                    Button(
                        onClick = onApproveCurrentStep,
                        colors = ButtonDefaults.buttonColors(containerColor = NeonMint, contentColor = Color.Black),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier.testTag("bottom_next_approve_step_button")
                    ) {
                        Text(text = "อนุมัติ & ถัดไป", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(imageVector = Icons.AutoMirrored.Filled.NavigateNext, contentDescription = null, modifier = Modifier.size(16.dp))
                    }
                }
            }
        }
    }
}

// =========================================================================
// 1. MONITOR AND HORIZONTAL FILMSTRIP (จอภาพมอนิเตอร์ + ม้วนฟิล์มซ้ายไปขวา)
// =========================================================================
@Composable
fun MonitorAndFilmstripView(
    shots: List<ShotItem>,
    selectedShotIndex: Int,
    isPlaying: Boolean,
    timelinePositionMs: Long,
    onSelectShot: (Int) -> Unit,
    onSelectTake: (Int, Int) -> Unit,
    onTogglePlay: () -> Unit,
    onSeek: (Long) -> Unit,
    onOpenShotEdit: (ShotItem) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeShot = shots.getOrNull(selectedShotIndex) ?: shots.firstOrNull()

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // Live Procedural Canvas Viewport
        VisualCanvasPreview(
            activeShot = activeShot,
            isPlaying = isPlaying,
            currentTimeMs = timelinePositionMs,
            totalDurationMs = 60000L,
            onTogglePlay = onTogglePlay,
            onSeek = onSeek
        )

        // Film Reel Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(imageVector = Icons.Default.Movie, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(16.dp))
                Text(
                    text = "ม้วนฟิล์มช็อตตามลำดับ (Film Reel: ซ้าย ➔ ขวา)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = NeonCyan
                )
            }
            Text(
                text = "${shots.size} ช็อต | กฎแกน 180° OK",
                fontSize = 10.sp,
                fontFamily = FontFamily.Monospace,
                color = NeonMint
            )
        }

        // Horizontal Filmstrip Reel (ซ้ายไปขวา)
        Card(
            colors = CardDefaults.cardColors(containerColor = CyberSurface),
            shape = RoundedCornerShape(10.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(shots) { index, shot ->
                    val isSelected = index == selectedShotIndex
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) NeonCyan.copy(alpha = 0.15f) else CyberSurfaceVariant
                        ),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) NeonCyan else CyberBorder
                        ),
                        modifier = Modifier
                            .width(135.dp)
                            .clickable { onSelectShot(index) }
                            .testTag("filmstrip_shot_${shot.shotNumber}")
                    ) {
                        Column(modifier = Modifier.padding(8.dp)) {
                            // Top row: Shot # and Duration
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    color = if (isSelected) NeonCyan else CyberBorder,
                                    shape = CircleShape,
                                    modifier = Modifier.size(20.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Text(
                                            text = "#${shot.shotNumber}",
                                            fontSize = 9.sp,
                                            fontWeight = FontWeight.Black,
                                            color = if (isSelected) Color.Black else Color.White
                                        )
                                    }
                                }
                                Text(
                                    text = "${shot.durationSec}s",
                                    fontSize = 10.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = NeonAmber,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            // Simulated Thumbnail Box
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(55.dp)
                                    .clip(RoundedCornerShape(4.dp))
                                    .background(Color(0xFF07090F))
                                    .border(0.5.dp, if (isSelected) NeonCyan.copy(alpha = 0.6f) else CyberBorder, RoundedCornerShape(4.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Icon(
                                        imageVector = Icons.Default.CameraAlt,
                                        contentDescription = null,
                                        tint = if (isSelected) NeonCyan else TextTertiary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = shot.shotSize,
                                        fontSize = 9.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) NeonCyan else TextSecondary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(4.dp))

                            // Camera movement and action
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = shot.cameraAngle,
                                    fontSize = 9.sp,
                                    color = TextSecondary,
                                    maxLines = 1
                                )
                                IconButton(
                                    onClick = { onOpenShotEdit(shot) },
                                    modifier = Modifier.size(20.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Tune,
                                        contentDescription = "ปรับแต่งช็อต",
                                        tint = NeonCyan,
                                        modifier = Modifier.size(13.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        // Active Shot Quick Inspector Strip (Takes Selector & Dialogue)
        if (activeShot != null) {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                shape = RoundedCornerShape(8.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ช็อต #${activeShot.shotNumber}: ${activeShot.shotSize} (${activeShot.cameraAngle})",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonCyan
                        )
                        Text(
                            text = "Consistency: ${activeShot.consistencyScore}%",
                            fontSize = 10.sp,
                            fontFamily = FontFamily.Monospace,
                            color = NeonMint,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Text(
                        text = "บทพูด: \"${activeShot.dialogue}\"",
                        fontSize = 10.sp,
                        color = TextPrimary
                    )

                    // Horizontal Takes Selector (Take 1 | Take 2 | Take 3)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "เทค:", fontSize = 10.sp, color = TextTertiary)
                        activeShot.takes.forEachIndexed { takeIdx, take ->
                            val isTakeActive = takeIdx == activeShot.selectedTakeIndex
                            Surface(
                                color = if (isTakeActive) NeonCyan else CyberSurfaceVariant,
                                shape = RoundedCornerShape(4.dp),
                                modifier = Modifier
                                    .clickable { onSelectTake(selectedShotIndex, takeIdx) }
                            ) {
                                Text(
                                    text = "${take.label} (${take.seed})",
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isTakeActive) Color.Black else TextSecondary,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// =========================================================================
// 2. STEP CANVAS VIEW (เครื่องมือสร้างสรรค์ F1–F12 หรือ M1–M7 ในแนว ซ้ายไปขวา)
// =========================================================================
@Composable
fun StepCanvasView(
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
    timelinePositionMs: Long,
    onSelectShot: (Int) -> Unit,
    onSelectTake: (Int, Int) -> Unit,
    onOpenExport: () -> Unit,
    onOpenShotEdit: (ShotItem) -> Unit,
    onSeek: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (project.activePipeline == PipelineType.FILM) {
            when (project.currentFilmStep) {
                FilmStep.F1_BRIEF -> item { BriefViewHorizontal(brief = brief) }
                FilmStep.F2_STORY -> item { StoryBeatsViewHorizontal(storyBeats = storyBeats) }
                FilmStep.F3_BIBLE -> item { StyleBibleAndCharactersViewHorizontal(styleBible = styleBible, characters = characters) }
                FilmStep.F4_SCRIPT -> item { ScreenplayViewHorizontal(screenplay = screenplay) }
                FilmStep.F5_STORYBOARD -> item {
                    StoryboardViewHorizontal(
                        shots = shots,
                        selectedShotIndex = selectedShotIndex,
                        onSelectShot = onSelectShot,
                        onOpenShotEdit = onOpenShotEdit
                    )
                }
                FilmStep.F6_KEYFRAMES -> item {
                    KeyframesViewHorizontal(
                        shots = shots,
                        selectedShotIndex = selectedShotIndex,
                        onSelectShot = onSelectShot,
                        onOpenShotEdit = onOpenShotEdit
                    )
                }
                FilmStep.F7_VIDEO -> item {
                    VideoTakesViewHorizontal(
                        shots = shots,
                        selectedShotIndex = selectedShotIndex,
                        onSelectShot = onSelectShot,
                        onSelectTake = onSelectTake
                    )
                }
                FilmStep.F8_VOICE -> item { VoiceAndSubtitlesViewHorizontal(screenplay = screenplay) }
                FilmStep.F9_SCORE -> item { MusicScoreViewHorizontal(musicBrief = musicBrief) }
                FilmStep.F10_SFX -> item { SoundDesignViewHorizontal() }
                FilmStep.F11_EDITING -> item {
                    TimelineEditorView(
                        clips = timelineClips,
                        currentPositionMs = timelinePositionMs,
                        onSeek = onSeek
                    )
                }
                FilmStep.F12_QC_EXPORT -> item { QCAndExportViewHorizontal(qcChecklist = qcChecklist, onOpenExport = onOpenExport) }
            }
        } else {
            item {
                MusicPipelineMasterViewHorizontal(
                    musicStep = project.currentMusicStep,
                    musicBrief = musicBrief
                )
            }
        }
    }
}

// -------------------------------------------------------------
// HORIZONTAL-FIRST STEP IMPLEMENTATIONS (F1-F12, M1-M7)
// -------------------------------------------------------------

@Composable
fun BriefViewHorizontal(brief: BriefData) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(
                    text = "F1: พิมพ์เขียวบรีฟภาพยนตร์ (Production Brief)",
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = NeonCyan
                )
                Text(
                    text = "เป้าหมาย: 1–3 นาที | ลิขสิทธิ์ส่วนตัว",
                    fontSize = 10.sp,
                    color = NeonMint,
                    fontFamily = FontFamily.Monospace
                )
            }

            // Quick Info Chips Row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                InfoChip(label = "แนวภาพยนตร์", value = brief.genre)
                InfoChip(label = "ความยาว", value = "${brief.durationSec} วินาที")
                InfoChip(label = "โทนอารมณ์", value = brief.tone)
                InfoChip(label = "ภาษา", value = brief.primaryLanguage)
                InfoChip(label = "ผู้ชมเป้าหมาย", value = brief.targetAudience)
            }

            Spacer(modifier = Modifier.height(2.dp))
            Text(text = "3 แนวคิดคอนเซ็ปต์เริ่มต้น (เลื่อน ซ้าย ➔ ขวา):", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = NeonAmber)

            // Horizontal Concept Cards (ซ้ายไปขวา)
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(brief.concepts) { idx, concept ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (idx == 0) NeonCyan.copy(alpha = 0.5f) else CyberBorder),
                        modifier = Modifier.width(260.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "คอนเซ็ปต์ #${idx + 1}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = NeonCyan)
                                if (idx == 0) {
                                    Surface(color = NeonCyan, shape = RoundedCornerShape(4.dp)) {
                                        Text(text = "แนะนำ", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                    }
                                }
                            }
                            Text(text = concept, fontSize = 11.sp, color = TextPrimary)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StoryBeatsViewHorizontal(storyBeats: StoryBeatSheet) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "F2: โครงสร้างเรื่องและจังหวะ 3 องก์ (3-Act Beat Sequence: ซ้าย ➔ ขวา)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NeonCyan)

            // Logline Bar
            Surface(
                color = CyberSurfaceVariant,
                shape = RoundedCornerShape(6.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "LOGLINE:", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = NeonCyan)
                    Text(text = storyBeats.logline, fontSize = 11.sp, color = TextPrimary)
                }
            }

            // 3 Acts in Horizontal Sequence (องก์ 1 ➔ องก์ 2 ➔ องก์ 3)
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                item {
                    ActCard(
                        actNumber = "องก์ที่ 1",
                        actTitle = "การปูเรื่อง (Setup & Inciting)",
                        content = storyBeats.act1Setup + " " + storyBeats.act1Inciting,
                        color = NeonCyan
                    )
                }
                item {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = NeonMint, modifier = Modifier.size(16.dp))
                }
                item {
                    ActCard(
                        actNumber = "องก์ที่ 2",
                        actTitle = "การเผชิญหน้า (Crisis & Climax Point)",
                        content = storyBeats.act2Midpoint + " " + storyBeats.act2Crisis,
                        color = NeonAmber
                    )
                }
                item {
                    Icon(imageVector = Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, tint = NeonMint, modifier = Modifier.size(16.dp))
                }
                item {
                    ActCard(
                        actNumber = "องก์ที่ 3",
                        actTitle = "ไคลแมกซ์ & บทสรุป (Resolution)",
                        content = storyBeats.act3Climax + " " + storyBeats.act3Resolution,
                        color = NeonMagenta
                    )
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                InfoTag(label = "เป้าหมายตัวเอก", value = storyBeats.protagonistGoal, modifier = Modifier.weight(1f))
                InfoTag(label = "เดิมพันสูงสุด", value = storyBeats.stakes, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ActCard(actNumber: String, actTitle: String, content: String, color: Color) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
        shape = RoundedCornerShape(8.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.5f)),
        modifier = Modifier.width(220.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(text = actNumber, fontSize = 9.sp, fontWeight = FontWeight.Bold, color = color)
            Text(text = actTitle, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
            Text(text = content, fontSize = 10.sp, color = TextSecondary, maxLines = 4)
        }
    }
}

@Composable
fun StyleBibleAndCharactersViewHorizontal(styleBible: StyleBible, characters: List<CharacterProfile>) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "F3: Style Bible & Consistency Pack", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NeonCyan)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "สไตล์: ${styleBible.visualAesthetic}", fontSize = 11.sp, color = TextPrimary)
                // Color Palette Swatches
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    styleBible.colorPalette.forEach { hex ->
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .background(Color(android.graphics.Color.parseColor(hex)))
                                .border(1.dp, Color.White.copy(alpha = 0.4f), CircleShape)
                        )
                    }
                }
            }

            Text(text = "ตัวละครที่ล็อกความต่อเนื่อง (เลื่อน ซ้าย ➔ ขวา):", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = NeonMagenta)

            // Horizontal Character Cards Carousel
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(characters) { char ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
                        modifier = Modifier.width(240.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = char.name, fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NeonCyan)
                                Text(text = char.role, fontSize = 9.sp, color = NeonAmber)
                            }
                            Text(text = "รูปลักษณ์: ${char.visualPrompt}", fontSize = 10.sp, color = TextSecondary, maxLines = 2)
                            Text(text = "เสียง: ${char.voiceType}", fontSize = 9.sp, color = TextTertiary)
                            Surface(color = CyberBackground, shape = RoundedCornerShape(4.dp)) {
                                Text(
                                    text = "Tokens: ${char.consistencyTokens}",
                                    fontSize = 8.sp,
                                    fontFamily = FontFamily.Monospace,
                                    color = NeonMint,
                                    modifier = Modifier.padding(4.dp)
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
fun ScreenplayViewHorizontal(screenplay: List<SceneScript>) {
    var selectedSceneIndex by remember { mutableStateOf(0) }
    val activeScene = screenplay.getOrNull(selectedSceneIndex) ?: screenplay.firstOrNull()

    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "F4: บทภาพยนตร์ฉากต่อฉาก (Screenplay)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NeonCyan)

            // Horizontal Scene Selector Tabs (ฉาก 1 ➔ ฉาก 2 ➔ ฉาก 3)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                screenplay.forEachIndexed { idx, scene ->
                    val isSceneSelected = idx == selectedSceneIndex
                    Surface(
                        color = if (isSceneSelected) NeonCyan.copy(alpha = 0.2f) else CyberSurfaceVariant,
                        shape = RoundedCornerShape(6.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isSceneSelected) NeonCyan else CyberBorder),
                        modifier = Modifier.clickable { selectedSceneIndex = idx }
                    ) {
                        Text(
                            text = "ฉากที่ ${idx + 1}",
                            fontSize = 11.sp,
                            fontWeight = if (isSceneSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSceneSelected) NeonCyan else TextSecondary,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                        )
                    }
                }
            }

            if (activeScene != null) {
                Surface(
                    color = CyberSurfaceVariant,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Text(
                            text = activeScene.heading,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp,
                            color = NeonCyan,
                            fontFamily = FontFamily.Monospace
                        )
                        Text(text = activeScene.description, fontSize = 11.sp, color = TextSecondary)

                        HorizontalDivider(color = CyberBorder.copy(alpha = 0.5f))

                        activeScene.dialogueLines.forEach { line ->
                            Column(modifier = Modifier.padding(start = 8.dp)) {
                                Text(
                                    text = "${line.speaker} (${line.emotion}):",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NeonAmber
                                )
                                Text(text = "TH: \"${line.lineTh}\"", fontSize = 11.sp, color = Color.White)
                                Text(text = "EN: \"${line.lineEn}\"", fontSize = 10.sp, color = TextSecondary)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StoryboardViewHorizontal(
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
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "F5: สตอรี่บอร์ดและช็อตลิสต์ (Shot Sequence: ซ้าย ➔ ขวา)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NeonCyan)
                Text(text = "180° Axis Rule PASSED", fontSize = 10.sp, color = NeonMint, fontFamily = FontFamily.Monospace)
            }

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(shots) { index, shot ->
                    val isSelected = index == selectedShotIndex
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected) NeonCyan.copy(alpha = 0.15f) else CyberSurfaceVariant
                        ),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) NeonCyan else CyberBorder
                        ),
                        modifier = Modifier
                            .width(200.dp)
                            .clickable { onSelectShot(index) }
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                Text(text = "ช็อต #${shot.shotNumber}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = NeonCyan)
                                Text(text = "${shot.durationSec}s", fontSize = 10.sp, color = NeonAmber, fontFamily = FontFamily.Monospace)
                            }
                            Text(text = "${shot.shotSize} • ${shot.cameraAngle}", fontSize = 10.sp, color = TextPrimary)
                            Text(text = "การเคลื่อนกล้อง: ${shot.cameraMotion}", fontSize = 9.sp, color = TextSecondary)
                            Text(text = "บท: \"${shot.dialogue}\"", fontSize = 9.sp, color = TextTertiary, maxLines = 1)
                            Button(
                                onClick = { onOpenShotEdit(shot) },
                                colors = ButtonDefaults.buttonColors(containerColor = CyberBorder),
                                shape = RoundedCornerShape(4.dp),
                                contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Text(text = "แก้ช็อต", fontSize = 9.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun KeyframesViewHorizontal(
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
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "F6: คีย์เฟรมภาพต้นแบบ (Keyframe Inspector)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NeonCyan)

            if (activeShot != null) {
                Surface(
                    color = CyberSurfaceVariant,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text(text = "ช็อต #${activeShot.shotNumber}: ${activeShot.shotSize}", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = NeonCyan)
                            Text(text = "Consistency: ${activeShot.consistencyScore}%", fontSize = 10.sp, fontWeight = FontWeight.Bold, color = NeonMint)
                        }
                        Text(text = "Visual Prompt (EN):", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = NeonAmber)
                        Text(text = activeShot.visualPromptEn, fontSize = 10.sp, color = TextPrimary)

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            Button(
                                onClick = { onOpenShotEdit(activeShot) },
                                colors = ButtonDefaults.buttonColors(containerColor = CyberBorder),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "Inpaint / แก้เฉพาะจุด", fontSize = 10.sp)
                            }
                            Button(
                                onClick = { onOpenShotEdit(activeShot) },
                                colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                                shape = RoundedCornerShape(6.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(text = "สร้างตัวเลือกใหม่", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun VideoTakesViewHorizontal(
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
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "F7: จัดการคลิปวิดีโอ (Video Takes: ซ้าย ➔ ขวา)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NeonCyan)

            if (activeShot != null) {
                Text(text = "ช็อต #${activeShot.shotNumber} (${activeShot.durationSec}s): เลือกเทคที่ดีที่สุด", fontSize = 10.sp, color = TextSecondary)

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(activeShot.takes) { takeIdx, take ->
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
                                .width(180.dp)
                                .clickable { onSelectTake(selectedShotIndex, takeIdx) }
                        ) {
                            Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                Text(text = take.label, fontWeight = FontWeight.Bold, fontSize = 11.sp, color = if (isTakeSelected) NeonCyan else TextPrimary)
                                Text(text = "Seed: #${take.seed}", fontSize = 9.sp, fontFamily = FontFamily.Monospace, color = TextSecondary)
                                Surface(
                                    color = if (isTakeSelected) NeonMint.copy(alpha = 0.2f) else CyberBorder,
                                    shape = RoundedCornerShape(4.dp)
                                ) {
                                    Text(
                                        text = if (isTakeSelected) "✓ ACTIVE" else "เลือกเทคนี้",
                                        fontSize = 9.sp,
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
}

@Composable
fun VoiceAndSubtitlesViewHorizontal(screenplay: List<SceneScript>) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "F8: เสียงพากย์และซับไตเติล (Voice & Timed Subtitles)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NeonCyan)
            Text(text = "TTS Phonetic Matching: วรรณยุกต์ไทย 100% ตรงเวลา", fontSize = 10.sp, color = NeonMint)

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(screenplay.flatMap { it.dialogueLines }) { line ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
                        modifier = Modifier.width(240.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.Mic, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(14.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = "${line.speaker} • ${line.emotion}", fontWeight = FontWeight.Bold, fontSize = 10.sp, color = NeonAmber)
                            }
                            Text(text = line.lineTh, fontSize = 11.sp, color = TextPrimary)
                            Text(text = line.lineEn, fontSize = 9.sp, color = TextSecondary)
                            Surface(color = CyberBackground, shape = RoundedCornerShape(4.dp)) {
                                Text(text = "TTS MATCH OK", fontSize = 8.sp, color = NeonMint, modifier = Modifier.padding(3.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MusicScoreViewHorizontal(musicBrief: MusicBrief) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "F9: ดนตรีประกอบภาพยนตร์ (Film Score Cue Sheet)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NeonMagenta)

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                InfoTag(label = "BPM", value = "${musicBrief.bpm}", modifier = Modifier.weight(1f))
                InfoTag(label = "Musical Key", value = musicBrief.musicalKey, modifier = Modifier.weight(1f))
                InfoTag(label = "เป้าหมาย LUFS", value = "${musicBrief.loudnessLufs}", modifier = Modifier.weight(1f))
            }

            Surface(
                color = CyberSurfaceVariant,
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "เนื้อเพลงและท่อนดนตรี (Lyrics & Cues):", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = NeonMagenta)
                    Text(
                        text = musicBrief.lyricsThEn,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = TextPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun SoundDesignViewHorizontal() {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(text = "F10: ออกแบบเสียงและมิกซ์ (Sound Design & Ducking)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NeonCyan)
            Text(text = "ระบบปรับลดเสียงอัตโนมัติ (Audio Ducking Hierarchy):", fontSize = 10.sp, color = NeonAmber)

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                DuckingChannelCard(title = "1. บทพูด", level = "0 dB", note = "ลำดับสูงสุด", color = NeonMint, modifier = Modifier.weight(1f))
                DuckingChannelCard(title = "2. SFX", level = "-3 dB", note = "ลดเมื่อมีพูด", color = NeonCyan, modifier = Modifier.weight(1f))
                DuckingChannelCard(title = "3. ดนตรี", level = "-5 dB", note = "ลดอัตโนมัติ", color = NeonMagenta, modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun DuckingChannelCard(title: String, level: String, note: String, color: Color, modifier: Modifier = Modifier) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
        shape = RoundedCornerShape(6.dp),
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = color)
            Text(text = level, fontSize = 12.sp, fontWeight = FontWeight.Black, color = TextPrimary)
            Text(text = note, fontSize = 8.sp, color = TextTertiary)
        }
    }
}

@Composable
fun QCAndExportViewHorizontal(qcChecklist: List<QCCheckItem>, onOpenExport: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "F12: ตรวจสอบคุณภาพและส่งออก (QC & Delivery)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NeonMint)
                Surface(color = NeonMint.copy(alpha = 0.2f), shape = RoundedCornerShape(4.dp)) {
                    Text(text = "QC 100% PASSED", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = NeonMint, modifier = Modifier.padding(4.dp))
                }
            }

            // QC items row
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(qcChecklist) { item ->
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                        shape = RoundedCornerShape(6.dp),
                        modifier = Modifier.width(200.dp)
                    ) {
                        Column(modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = NeonMint, modifier = Modifier.size(13.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(text = item.title, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
                            }
                            Text(text = item.description, fontSize = 9.sp, color = TextSecondary, maxLines = 2)
                        }
                    }
                }
            }

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
fun MusicPipelineMasterViewHorizontal(musicStep: MusicStep, musicBrief: MusicBrief) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(10.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = "CineSonic Music Pipeline [${musicStep.stepCode}: ${musicStep.titleTh}]",
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = NeonMagenta
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                InfoTag(label = "เป้าหมาย", value = musicBrief.intent, modifier = Modifier.weight(1f))
                InfoTag(label = "แนวเพลง", value = musicBrief.genre, modifier = Modifier.weight(1f))
                InfoTag(label = "BPM", value = "${musicBrief.bpm}", modifier = Modifier.weight(1f))
            }

            Surface(
                color = CyberSurfaceVariant,
                shape = RoundedCornerShape(6.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = "เนื้อเพลงและจังหวะพยางค์:", fontSize = 9.sp, fontWeight = FontWeight.Bold, color = NeonMagenta)
                    Text(text = musicBrief.lyricsThEn, fontSize = 10.sp, fontFamily = FontFamily.Monospace, color = TextPrimary)
                }
            }
        }
    }
}

// =========================================================================
// 3. COPILOT EMBEDDED TAB VIEW (ผู้ช่วย AI สัญญา 8 ข้อในลำดับแนวนอน)
// =========================================================================
@Composable
fun CoPilotEmbeddedView(
    contract: StepAssistantResponse?,
    onSelectProposal: (ProposalOption) -> Unit,
    onQuickRefine: (String) -> Unit,
    onApproveStep: () -> Unit,
    modifier: Modifier = Modifier
) {
    if (contract == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(text = "กำลังเตรียมข้อมูลผู้ช่วย AI ประจำขั้นตอน...", fontSize = 12.sp, color = TextSecondary)
        }
        return
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        // Contract Header & Explain
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "1. ${contract.assistantName} [${contract.stepCode}]",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = NeonCyan
                        )
                        Surface(color = CyberSurfaceVariant, shape = RoundedCornerShape(4.dp)) {
                            Text(
                                text = "สัญญา 8 ข้อครบถ้วน",
                                fontSize = 9.sp,
                                color = NeonMint,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                    Text(text = contract.explain.summaryTh, fontSize = 11.sp, color = TextPrimary)
                    Text(text = "ตัวอย่าง: ${contract.explain.exampleTh}", fontSize = 10.sp, color = TextSecondary)
                }
            }
        }

        // 3 Distinct Proposals (Horizontal Comparison Row A ➔ B ➔ C)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "3. ข้อเสนอ 3 แนวทาง (Propose: เปรียบเทียบ ซ้าย ➔ ขวา)",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = NeonMagenta
                    )

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(contract.proposals) { proposal ->
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = if (proposal.isRecommended) NeonCyan.copy(alpha = 0.12f) else CyberSurfaceVariant
                                ),
                                shape = RoundedCornerShape(8.dp),
                                border = androidx.compose.foundation.BorderStroke(
                                    1.dp,
                                    if (proposal.isRecommended) NeonCyan else CyberBorder
                                ),
                                modifier = Modifier
                                    .width(220.dp)
                                    .clickable { onSelectProposal(proposal) }
                            ) {
                                Column(modifier = Modifier.padding(10.dp), verticalArrangement = Arrangement.spacedBy(4.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = proposal.titleTh,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 11.sp,
                                            color = if (proposal.isRecommended) NeonCyan else TextPrimary
                                        )
                                        if (proposal.isRecommended) {
                                            Surface(color = NeonCyan, shape = RoundedCornerShape(4.dp)) {
                                                Text(text = "แนะนำ", fontSize = 8.sp, fontWeight = FontWeight.Bold, color = Color.Black, modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp))
                                            }
                                        }
                                    }
                                    Text(text = proposal.descriptionTh, fontSize = 10.sp, color = TextSecondary, maxLines = 3)
                                    Text(text = "+ ข้อดี: ${proposal.prosTh}", fontSize = 9.sp, color = NeonMint)
                                    Text(text = "- ข้อจำกัด: ${proposal.consTh}", fontSize = 9.sp, color = TextTertiary)
                                    Button(
                                        onClick = { onSelectProposal(proposal) },
                                        colors = ButtonDefaults.buttonColors(containerColor = if (proposal.isRecommended) NeonCyan else CyberBorder, contentColor = if (proposal.isRecommended) Color.Black else Color.White),
                                        shape = RoundedCornerShape(4.dp),
                                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(text = "เลือกแนวทางนี้", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Critique (4) and Quick Refinements (5)
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "5. วิจารณ์และคะแนน (Critique)", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = NeonAmber)
                        // Star Rating
                        Row {
                            for (i in 1..5) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = null,
                                    tint = if (i <= contract.critique.score) NeonAmber else TextTertiary,
                                    modifier = Modifier.size(13.dp)
                                )
                            }
                        }
                    }

                    Text(text = "จุดแข็ง: ${contract.critique.strengthsTh}", fontSize = 10.sp, color = NeonMint)
                    Text(text = "วิธีแก้ทันที: ${contract.critique.suggestedFixTh}", fontSize = 10.sp, color = NeonCyan)

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "6. ปรับแต่งด่วน (Quick Refine Chips):", fontWeight = FontWeight.Bold, fontSize = 11.sp, color = NeonCyan)

                    // Horizontal Refine Chips
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        contract.quickRefinements.forEach { chip ->
                            SuggestionChip(
                                onClick = { onQuickRefine(chip) },
                                label = { Text(text = chip, fontSize = 10.sp) }
                            )
                        }
                    }
                }
            }
        }

        // Estimate & Handoff Gate
        item {
            Card(
                colors = CardDefaults.cardColors(containerColor = CyberSurface),
                shape = RoundedCornerShape(10.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
            ) {
                Column(modifier = Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "ประมาณการค่าใช้จ่าย: $${String.format("%.2f", contract.estimate.estimatedCostUsd)} (ฟรี)", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = NeonMint)
                            Text(text = "เวลาประมวลผล: ~${contract.estimate.estimatedTimeSec}s", fontSize = 10.sp, color = TextSecondary)
                        }
                        Button(
                            onClick = onApproveStep,
                            colors = ButtonDefaults.buttonColors(containerColor = NeonMint, contentColor = Color.Black),
                            shape = RoundedCornerShape(6.dp)
                        ) {
                            Text(text = "อนุมัติ Gate & ไปต่อ", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// UTILITY CHIP AND TAGS
// -------------------------------------------------------------
@Composable
fun InfoChip(label: String, value: String) {
    Surface(
        color = CyberSurfaceVariant,
        shape = RoundedCornerShape(6.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "$label:", fontSize = 9.sp, color = TextTertiary)
            Text(text = value, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
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
            Text(text = value, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextPrimary)
        }
    }
}
