package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.ShotItem
import com.example.ui.theme.*

@Composable
fun VisualCanvasPreview(
    activeShot: ShotItem?,
    isPlaying: Boolean,
    currentTimeMs: Long,
    totalDurationMs: Long = 60000L,
    onTogglePlay: () -> Unit,
    onSeek: (Long) -> Unit,
    modifier: Modifier = Modifier
) {
    val infiniteTransition = rememberInfiniteTransition(label = "cyber_canvas_anim")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 0.9f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_alpha"
    )

    val rainOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rain_offset"
    )

    Card(
        colors = CardDefaults.cardColors(containerColor = CyberSurface),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            // Viewport Canvas (16:9 Aspect Ratio)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF07090F))
                    .border(1.dp, NeonCyan.copy(alpha = 0.5f), RoundedCornerShape(8.dp))
                    .testTag("cinematic_canvas_viewport")
            ) {
                // Procedural Cinematic Canvas
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height

                    // 1. Subtle horizon background gradient line
                    val horizonY = canvasHeight * 0.65f
                    drawLine(
                        color = NeonCyan.copy(alpha = 0.15f),
                        start = Offset(0f, horizonY),
                        end = Offset(canvasWidth, horizonY),
                        strokeWidth = 1.5f
                    )

                    // 2. Perspective Cyber-grid lines
                    val centerBottom = Offset(canvasWidth * 0.5f, horizonY)
                    for (i in -4..4) {
                        val groundX = (canvasWidth * 0.5f) + (i * canvasWidth * 0.18f)
                        drawLine(
                            color = NeonCyan.copy(alpha = 0.1f),
                            start = centerBottom,
                            end = Offset(groundX, canvasHeight),
                            strokeWidth = 1.0f
                        )
                    }

                    // 3. Simulated rain streaks
                    for (r in 0..12) {
                        val rx = ((r * 73 + rainOffset * 3) % canvasWidth)
                        val ry = ((r * 47 + rainOffset * 4) % canvasHeight)
                        drawLine(
                            color = NeonCyan.copy(alpha = 0.35f),
                            start = Offset(rx, ry),
                            end = Offset(rx + 6f, ry + 22f),
                            strokeWidth = 1.2f
                        )
                    }

                    // 4. Center Holographic Silhouette / Focus Target
                    val centerCircle = Offset(canvasWidth * 0.5f, canvasHeight * 0.45f)
                    val radius = canvasHeight * 0.22f

                    drawCircle(
                        color = NeonMagenta.copy(alpha = pulseAlpha * 0.3f),
                        radius = radius,
                        center = centerCircle,
                        style = Stroke(width = 2.dp.toPx())
                    )

                    drawCircle(
                        color = NeonCyan.copy(alpha = 0.2f),
                        radius = radius * 0.6f,
                        center = centerCircle
                    )

                    // 5. Camera Viewfinder HUD brackets
                    val bracketLen = 20.dp.toPx()
                    val margin = 12.dp.toPx()

                    // Top-Left
                    drawLine(NeonCyan, Offset(margin, margin), Offset(margin + bracketLen, margin), 2f)
                    drawLine(NeonCyan, Offset(margin, margin), Offset(margin, margin + bracketLen), 2f)

                    // Top-Right
                    drawLine(NeonCyan, Offset(canvasWidth - margin, margin), Offset(canvasWidth - margin - bracketLen, margin), 2f)
                    drawLine(NeonCyan, Offset(canvasWidth - margin, margin), Offset(canvasWidth - margin, margin + bracketLen), 2f)

                    // Bottom-Left
                    drawLine(NeonCyan, Offset(margin, canvasHeight - margin), Offset(margin + bracketLen, canvasHeight - margin), 2f)
                    drawLine(NeonCyan, Offset(margin, canvasHeight - margin), Offset(margin, canvasHeight - margin - bracketLen), 2f)

                    // Bottom-Right
                    drawLine(NeonCyan, Offset(canvasWidth - margin, canvasHeight - margin), Offset(canvasWidth - margin - bracketLen, canvasHeight - margin), 2f)
                    drawLine(NeonCyan, Offset(canvasWidth - margin, canvasHeight - margin), Offset(canvasWidth - margin, canvasHeight - margin - bracketLen), 2f)

                    // Center Crosshair
                    drawLine(NeonMint.copy(alpha = 0.7f), Offset(centerCircle.x - 12f, centerCircle.y), Offset(centerCircle.x + 12f, centerCircle.y), 1.5f)
                    drawLine(NeonMint.copy(alpha = 0.7f), Offset(centerCircle.x, centerCircle.y - 12f), Offset(centerCircle.x, centerCircle.y + 12f), 1.5f)
                }

                // Top Viewfinder Telemetry Overlay
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Surface(
                            color = if (isPlaying) Color.Red else NeonAmber,
                            shape = RoundedCornerShape(4.dp)
                        ) {
                            Text(
                                text = if (isPlaying) "● REC 24FPS" else "STBY 1080P",
                                color = Color.White,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Black,
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                        Text(
                            text = activeShot?.shotSize ?: "WIDE ESTABLISHING",
                            color = NeonCyan,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily.Monospace
                        )
                    }

                    // Monospace Timecode
                    val minutes = (currentTimeMs / 60000) % 60
                    val seconds = (currentTimeMs / 1000) % 60
                    val millis = (currentTimeMs % 1000) / 10
                    Text(
                        text = String.format("%02d:%02d:%02d", minutes, seconds, millis),
                        color = NeonMint,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily.Monospace
                    )
                }

                // Bottom Subtitle & Audio Ducking Banner
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .background(Color.Black.copy(alpha = 0.75f))
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val subtitleText = activeShot?.dialogue ?: "ดนตรีสังเคราะห์และเสียงสายฝนโปรยปราย"
                    Text(
                        text = subtitleText,
                        color = Color.Yellow,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "Take #${(activeShot?.selectedTakeIndex ?: 0) + 1} | Motion: ${activeShot?.cameraMotion ?: "Pan Left"} | Consistency: ${activeShot?.consistencyScore ?: 96}%",
                        color = TextSecondary,
                        fontSize = 9.sp,
                        fontFamily = FontFamily.Monospace
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Playback Transport Bar
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Play / Pause & Rewind
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    IconButton(
                        onClick = { onSeek((currentTimeMs - 5000L).coerceAtLeast(0L)) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastRewind,
                            contentDescription = "ย้อนหลัง 5 วินาที",
                            tint = TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    FilledIconButton(
                        onClick = onTogglePlay,
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = NeonCyan,
                            contentColor = Color.Black
                        ),
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("play_pause_button")
                    ) {
                        Icon(
                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                            contentDescription = if (isPlaying) "Pause" else "Play",
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(
                        onClick = { onSeek((currentTimeMs + 5000L).coerceAtMost(totalDurationMs)) },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.FastForward,
                            contentDescription = "ข้าม 5 วินาที",
                            tint = TextSecondary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                // Time slider scrub
                Slider(
                    value = currentTimeMs.toFloat(),
                    onValueChange = { onSeek(it.toLong()) },
                    valueRange = 0f..totalDurationMs.toFloat(),
                    colors = SliderDefaults.colors(
                        thumbColor = NeonCyan,
                        activeTrackColor = NeonCyan,
                        inactiveTrackColor = CyberBorder
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp)
                        .testTag("timeline_scrub_slider")
                )

                // Time label
                Text(
                    text = "${currentTimeMs / 1000}s / ${totalDurationMs / 1000}s",
                    fontSize = 11.sp,
                    fontFamily = FontFamily.Monospace,
                    color = TextSecondary
                )
            }
        }
    }
}
