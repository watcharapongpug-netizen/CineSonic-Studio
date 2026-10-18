package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.PipelineType
import com.example.ui.theme.*

@Composable
fun StudioHeader(
    activePipeline: PipelineType,
    isAutoMode: Boolean,
    budgetSpent: Double,
    isCopilotOpen: Boolean,
    onSwitchPipeline: (PipelineType) -> Unit,
    onToggleAutoMode: () -> Unit,
    onOpenSelfTest: () -> Unit,
    onOpenExport: () -> Unit,
    onToggleCopilot: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = CyberSurface,
        modifier = modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = CyberBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Logo & Title
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(CyberSurfaceVariant)
                            .border(1.dp, NeonCyan, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Movie,
                            contentDescription = "CineSonic Studio",
                            tint = NeonCyan,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "CINESONIC",
                                fontWeight = FontWeight.Black,
                                fontSize = 16.sp,
                                letterSpacing = 1.sp,
                                color = NeonCyan
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "STUDIO",
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                letterSpacing = 1.sp,
                                color = NeonMagenta
                            )
                        }
                        Text(
                            text = "AI Film & Music Pipeline",
                            fontSize = 10.sp,
                            color = TextSecondary
                        )
                    }
                }

                // Top Right Action Buttons
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    // Budget indicator
                    Surface(
                        color = CyberSurfaceVariant,
                        shape = RoundedCornerShape(16.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, NeonAmber)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "งบ: $${String.format("%.2f", budgetSpent)} (ฟรี)",
                                fontSize = 11.sp,
                                fontFamily = FontFamily.Monospace,
                                color = NeonAmber,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    // Self Test Button
                    IconButton(
                        onClick = onOpenSelfTest,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("self_test_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.BugReport,
                            contentDescription = "ตรวจสอบระบบ Self-test",
                            tint = TextSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Export Button
                    IconButton(
                        onClick = onOpenExport,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("export_header_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Share,
                            contentDescription = "ส่งออกผลงาน Master Export",
                            tint = NeonMint,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    // Co-pilot Drawer Toggle
                    Button(
                        onClick = onToggleCopilot,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isCopilotOpen) NeonCyan else CyberSurfaceVariant,
                            contentColor = if (isCopilotOpen) Color.Black else NeonCyan
                        ),
                        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .height(34.dp)
                            .testTag("copilot_toggle_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.SmartToy,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Co-pilot",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Sub-row: Pipeline switch & Guided/Auto toggle
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Pipeline Segmented Switch
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberSurfaceVariant)
                        .padding(2.dp),
                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (activePipeline == PipelineType.FILM) NeonCyan else Color.Transparent)
                            .clickable { onSwitchPipeline(PipelineType.FILM) }
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                            .testTag("tab_film_pipeline"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Movie,
                                contentDescription = null,
                                tint = if (activePipeline == PipelineType.FILM) Color.Black else TextSecondary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "ภาพยนตร์ (F1-F12)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (activePipeline == PipelineType.FILM) Color.Black else TextSecondary
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(if (activePipeline == PipelineType.MUSIC) NeonMagenta else Color.Transparent)
                            .clickable { onSwitchPipeline(PipelineType.MUSIC) }
                            .padding(horizontal = 12.dp, vertical = 4.dp)
                            .testTag("tab_music_pipeline"),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.MusicNote,
                                contentDescription = null,
                                tint = if (activePipeline == PipelineType.MUSIC) Color.White else TextSecondary,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "เพลง & ดนตรี (M1-M7)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (activePipeline == PipelineType.MUSIC) Color.White else TextSecondary
                            )
                        }
                    }
                }

                // Mode: Guided vs Auto
                FilterChip(
                    selected = isAutoMode,
                    onClick = onToggleAutoMode,
                    label = {
                        Text(
                            text = if (isAutoMode) "โหมด: Auto (เร็ว)" else "โหมด: Guided (ทีละขั้น)",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = if (isAutoMode) Icons.Default.AutoAwesome else Icons.Default.Psychology,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = if (isAutoMode) NeonAmber else NeonCyan
                        )
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        containerColor = CyberSurfaceVariant,
                        selectedContainerColor = NeonAmber.copy(alpha = 0.2f),
                        selectedLabelColor = NeonAmber
                    ),
                    modifier = Modifier.testTag("toggle_auto_mode")
                )
            }
        }
    }
}
