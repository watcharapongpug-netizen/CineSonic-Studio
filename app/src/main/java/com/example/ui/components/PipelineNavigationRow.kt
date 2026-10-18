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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
                .horizontalScroll(scrollState)
                .padding(horizontal = 12.dp, vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (activePipeline == PipelineType.FILM) {
                FilmStep.values().forEach { step ->
                    val isCurrent = step == currentFilmStep
                    val isPast = step.ordinal < currentFilmStep.ordinal

                    val stepBg = when {
                        isCurrent -> NeonCyan.copy(alpha = 0.15f)
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
                            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
                            .clickable { onSelectFilmStep(step) }
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                            .testTag("step_pill_${step.stepCode}"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        // Badge
                        Box(
                            modifier = Modifier
                                .size(20.dp)
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
                                    contentDescription = "Completed",
                                    tint = Color.Black,
                                    modifier = Modifier.size(12.dp)
                                )
                            } else {
                                Text(
                                    text = step.stepCode,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isCurrent) Color.Black else TextSecondary
                                )
                            }
                        }

                        Text(
                            text = step.titleTh,
                            fontSize = 11.sp,
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                            color = if (isCurrent) NeonCyan else if (isPast) TextPrimary else TextSecondary
                        )
                    }
                }
            } else {
                MusicStep.values().forEach { step ->
                    val isCurrent = step == currentMusicStep
                    val isPast = step.ordinal < currentMusicStep.ordinal

                    val stepBg = when {
                        isCurrent -> NeonMagenta.copy(alpha = 0.15f)
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
                            .border(1.dp, borderColor, RoundedCornerShape(8.dp))
                            .clickable { onSelectMusicStep(step) }
                            .padding(horizontal = 8.dp, vertical = 6.dp)
                            .testTag("music_step_pill_${step.stepCode}"),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
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
                                    contentDescription = "Completed",
                                    tint = Color.Black,
                                    modifier = Modifier.size(12.dp)
                                )
                            } else {
                                Text(
                                    text = step.stepCode,
                                    fontSize = 9.sp,
                                    fontWeight = FontWeight.Black,
                                    color = if (isCurrent) Color.White else TextSecondary
                                )
                            }
                        }

                        Text(
                            text = step.titleTh,
                            fontSize = 11.sp,
                            fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                            color = if (isCurrent) NeonMagenta else if (isPast) TextPrimary else TextSecondary
                        )
                    }
                }
            }
        }
    }
}
