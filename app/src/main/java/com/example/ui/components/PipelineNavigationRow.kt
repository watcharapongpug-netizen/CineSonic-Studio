package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.automirrored.filled.NavigateBefore
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.FilmStep
import com.example.data.model.MusicStep
import com.example.data.model.PipelineType
import com.example.ui.theme.*

@Composable
fun PipelineNavigationRow(
    activePipeline: PipelineType,
    currentFilmStep: FilmStep,
    currentMusicStep: MusicStep,
    onSelectFilmStep: (FilmStep) -> Unit,
    onSelectMusicStep: (MusicStep) -> Unit,
    onPreviousStep: () -> Unit,
    onNextStep: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = CyberBackground,
        modifier = modifier.fillMaxWidth()
    ) {
        val scrollState = rememberScrollState()

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Previous Step Button (Left)
            IconButton(
                onClick = onPreviousStep,
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CyberSurface)
                    .border(1.dp, CyberBorder, RoundedCornerShape(8.dp))
                    .testTag("nav_prev_step_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.NavigateBefore,
                    contentDescription = "ขั้นตอนก่อนหน้า",
                    tint = NeonCyan,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Left-to-Right Step Pipeline Track
            Row(
                modifier = Modifier
                    .weight(1f)
                    .horizontalScroll(scrollState),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (activePipeline == PipelineType.FILM) {
                    val steps = FilmStep.values()
                    steps.forEachIndexed { index, step ->
                        val isCurrent = step == currentFilmStep
                        val isPast = step.ordinal < currentFilmStep.ordinal

                        val stepBg = when {
                            isCurrent -> NeonCyan.copy(alpha = 0.2f)
                            isPast -> CyberSurfaceVariant
                            else -> CyberSurface
                        }
                        val borderColor = when {
                            isCurrent -> NeonCyan
                            isPast -> NeonMint
                            else -> CyberBorder
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(stepBg)
                                .border(if (isCurrent) 1.5.dp else 1.dp, borderColor, RoundedCornerShape(8.dp))
                                .clickable { onSelectFilmStep(step) }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                .testTag("step_pill_${step.stepCode}"),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            // Step Circle Badge
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isCurrent -> NeonCyan
                                            isPast -> NeonMint
                                            else -> CyberSurfaceVariant
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isPast) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "เสร็จแล้ว",
                                        tint = Color.Black,
                                        modifier = Modifier.size(13.dp)
                                    )
                                } else {
                                    Text(
                                        text = "${step.ordinal + 1}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (isCurrent) Color.Black else TextSecondary
                                    )
                                }
                            }

                            Column {
                                Text(
                                    text = step.stepCode,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCurrent) NeonCyan else TextTertiary
                                )
                                Text(
                                    text = step.titleTh,
                                    fontSize = 11.sp,
                                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isCurrent) TextPrimary else if (isPast) TextPrimary else TextSecondary
                                )
                            }
                        }

                        // Connecting directional arrow between steps (Left to Right)
                        if (index < steps.size - 1) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = if (isPast) NeonMint.copy(alpha = 0.7f) else CyberBorder,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                } else {
                    val steps = MusicStep.values()
                    steps.forEachIndexed { index, step ->
                        val isCurrent = step == currentMusicStep
                        val isPast = step.ordinal < currentMusicStep.ordinal

                        val stepBg = when {
                            isCurrent -> NeonMagenta.copy(alpha = 0.2f)
                            isPast -> CyberSurfaceVariant
                            else -> CyberSurface
                        }
                        val borderColor = when {
                            isCurrent -> NeonMagenta
                            isPast -> NeonMint
                            else -> CyberBorder
                        }

                        Row(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(stepBg)
                                .border(if (isCurrent) 1.5.dp else 1.dp, borderColor, RoundedCornerShape(8.dp))
                                .clickable { onSelectMusicStep(step) }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                                .testTag("music_step_pill_${step.stepCode}"),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(22.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isCurrent -> NeonMagenta
                                            isPast -> NeonMint
                                            else -> CyberSurfaceVariant
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isPast) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "เสร็จแล้ว",
                                        tint = Color.Black,
                                        modifier = Modifier.size(13.dp)
                                    )
                                } else {
                                    Text(
                                        text = "${step.ordinal + 1}",
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Black,
                                        color = if (isCurrent) Color.Black else TextSecondary
                                    )
                                }
                            }

                            Column {
                                Text(
                                    text = step.stepCode,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCurrent) NeonMagenta else TextTertiary
                                )
                                Text(
                                    text = step.titleTh,
                                    fontSize = 11.sp,
                                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Medium,
                                    color = if (isCurrent) TextPrimary else if (isPast) TextPrimary else TextSecondary
                                )
                            }
                        }

                        if (index < steps.size - 1) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = if (isPast) NeonMint.copy(alpha = 0.7f) else CyberBorder,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.width(6.dp))

            // Next Step Button (Right)
            IconButton(
                onClick = onNextStep,
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(CyberSurface)
                    .border(1.dp, NeonMint, RoundedCornerShape(8.dp))
                    .testTag("nav_next_step_button")
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.NavigateNext,
                    contentDescription = "ขั้นตอนถัดไป",
                    tint = NeonMint,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
