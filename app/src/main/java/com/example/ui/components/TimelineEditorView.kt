package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GraphicEq
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.TimelineClip
import com.example.data.model.TrackType
import com.example.ui.theme.*

@Composable
fun TimelineEditorView(
    clips: List<TimelineClip>,
    currentPositionMs: Long,
    totalDurationMs: Long = 60000L,
    onSeek: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Header: Multi-track info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "MULTI-TRACK TIMELINE",
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp,
                        color = NeonCyan
                    )
                    Surface(
                        color = CyberSurfaceVariant,
                        shape = RoundedCornerShape(4.dp)
                    ) {
                        Text(
                            text = "4 TRACKS | 60.0s",
                            fontSize = 9.sp,
                            fontFamily = FontFamily.Monospace,
                            color = TextSecondary,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                }

                Text(
                    text = "Ducking: Dialogue > SFX > BGM",
                    fontSize = 10.sp,
                    color = NeonMint,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            val scrollState = rememberScrollState()
            val timelineWidthDp = 720.dp

            // Timeline container with horizontal scrolling
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(scrollState)
            ) {
                Column(
                    modifier = Modifier.width(timelineWidthDp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Time Ruler
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(16.dp)
                            .background(CyberSurfaceVariant),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        for (sec in 0..60 step 5) {
                            Text(
                                text = "${sec}s",
                                fontSize = 9.sp,
                                fontFamily = FontFamily.Monospace,
                                color = TextTertiary,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }

                    // Track 1: Video
                    TimelineTrackRow(
                        trackName = "V1: VIDEO",
                        icon = Icons.Default.Movie,
                        trackColor = NeonCyan,
                        clips = clips.filter { it.trackType == TrackType.VIDEO },
                        totalDurationMs = totalDurationMs,
                        timelineWidthDp = timelineWidthDp,
                        onSeek = onSeek
                    )

                    // Track 2: Dialogue
                    TimelineTrackRow(
                        trackName = "A1: DIALOGUE",
                        icon = Icons.Default.Mic,
                        trackColor = NeonAmber,
                        clips = clips.filter { it.trackType == TrackType.DIALOGUE },
                        totalDurationMs = totalDurationMs,
                        timelineWidthDp = timelineWidthDp,
                        onSeek = onSeek
                    )

                    // Track 3: SFX & Ambience
                    TimelineTrackRow(
                        trackName = "A2: SFX / ENV",
                        icon = Icons.Default.GraphicEq,
                        trackColor = NeonMint,
                        clips = clips.filter { it.trackType == TrackType.SFX },
                        totalDurationMs = totalDurationMs,
                        timelineWidthDp = timelineWidthDp,
                        onSeek = onSeek
                    )

                    // Track 4: BGM / Score
                    TimelineTrackRow(
                        trackName = "A3: BGM SCORE",
                        icon = Icons.Default.MusicNote,
                        trackColor = NeonMagenta,
                        clips = clips.filter { it.trackType == TrackType.BGM },
                        totalDurationMs = totalDurationMs,
                        timelineWidthDp = timelineWidthDp,
                        onSeek = onSeek
                    )
                }

                // Scrubber Red Playhead line
                val playheadOffset = (currentPositionMs.toFloat() / totalDurationMs.toFloat()) * timelineWidthDp.value
                Box(
                    modifier = Modifier
                        .offset(x = playheadOffset.dp)
                        .width(2.dp)
                        .fillMaxHeight()
                        .background(Color.Red)
                )
            }
        }
    }
}

@Composable
private fun TimelineTrackRow(
    trackName: String,
    icon: ImageVector,
    trackColor: Color,
    clips: List<TimelineClip>,
    totalDurationMs: Long,
    timelineWidthDp: androidx.compose.ui.unit.Dp,
    onSeek: (Long) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp)
            .background(CyberBackground)
            .border(1.dp, CyberBorder.copy(alpha = 0.5f)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Track Header label
        Row(
            modifier = Modifier
                .width(90.dp)
                .fillMaxHeight()
                .background(CyberSurfaceVariant)
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = trackColor,
                modifier = Modifier.size(12.dp)
            )
            Text(
                text = trackName,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = TextPrimary
            )
        }

        // Track Clips Canvas
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clickable { /* Seek directly */ }
        ) {
            clips.forEach { clip ->
                val leftFraction = clip.startMs.toFloat() / totalDurationMs.toFloat()
                val widthFraction = clip.durationMs.toFloat() / totalDurationMs.toFloat()
                val availableWidth = timelineWidthDp.value - 90f

                Box(
                    modifier = Modifier
                        .offset(x = (leftFraction * availableWidth).dp)
                        .width((widthFraction * availableWidth).dp)
                        .fillMaxHeight()
                        .padding(vertical = 2.dp, horizontal = 1.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .background(trackColor.copy(alpha = 0.25f))
                        .border(1.dp, trackColor, RoundedCornerShape(4.dp))
                        .clickable { onSeek(clip.startMs) }
                        .padding(horizontal = 4.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = clip.title,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.White,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
