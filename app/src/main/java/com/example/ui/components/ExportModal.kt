package com.example.ui.components

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.Project
import com.example.data.model.ShotItem
import com.example.ui.theme.*

@Composable
fun ExportModal(
    project: Project,
    shots: List<ShotItem>,
    onDismiss: () -> Unit,
    onCopied: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedExportTab by remember { mutableStateOf(0) } // 0: SRT Subtitles, 1: Project JSON, 2: Screenplay TXT

    val srtContent = remember(shots) {
        buildString {
            shots.forEachIndexed { idx, shot ->
                val startSec = idx * 6.5
                val endSec = startSec + shot.durationSec
                val startFormatted = formatSrtTime(startSec)
                val endFormatted = formatSrtTime(endSec)
                appendLine("${idx + 1}")
                appendLine("$startFormatted --> $endFormatted")
                appendLine(shot.dialogue.ifBlank { "[ดนตรีสังเคราะห์และเสียงสายฝนไซเบอร์]" })
                appendLine()
            }
        }
    }

    val jsonContent = remember(project, shots) {
        """
        {
          "studio": "CineSonic Studio",
          "version": "1.0",
          "c2pa_provenance": {
            "is_ai_generated": true,
            "pipeline": "CineSonic Multi-Model Engine",
            "timestamp": ${System.currentTimeMillis()}
          },
          "project": {
            "id": "${project.id}",
            "title": "${project.title}",
            "ideaPrompt": "${project.ideaPrompt}",
            "budgetSpentUsd": ${project.budgetSpentUsd}
          },
          "shots_count": ${shots.size},
          "shots": [
            ${shots.joinToString(",\n            ") { s ->
                """{"shotNumber": ${s.shotNumber}, "size": "${s.shotSize}", "angle": "${s.cameraAngle}", "duration": ${s.durationSec}, "prompt": "${s.visualPromptEn.replace("\"", "\\\"")}"}"""
            }}
          ]
        }
        """.trimIndent()
    }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = CyberSurface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, NeonMint),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .testTag("export_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "ส่งออกผลงาน (MASTER EXPORT)",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = NeonMint,
                            letterSpacing = 1.sp
                        )
                        Text(
                            text = "พร้อมป้ายกำกับ AI Provenance (C2PA) ตามมาตรฐาน",
                            fontSize = 11.sp,
                            color = TextSecondary
                        )
                    }
                }

                // Tabs: SRT vs JSON
                TabRow(
                    selectedTabIndex = selectedExportTab,
                    containerColor = CyberSurfaceVariant,
                    contentColor = NeonCyan
                ) {
                    Tab(
                        selected = selectedExportTab == 0,
                        onClick = { selectedExportTab = 0 },
                        text = { Text("ซับไตเติล (SRT)", fontSize = 11.sp) }
                    )
                    Tab(
                        selected = selectedExportTab == 1,
                        onClick = { selectedExportTab = 1 },
                        text = { Text("โปรเจกต์ (JSON)", fontSize = 11.sp) }
                    )
                }

                val currentContent = if (selectedExportTab == 0) srtContent else jsonContent

                // Preview content box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(CyberBackground)
                        .border(1.dp, CyberBorder, RoundedCornerShape(8.dp))
                        .padding(10.dp)
                ) {
                    val scrollState = rememberScrollState()
                    Text(
                        text = currentContent,
                        fontSize = 10.sp,
                        fontFamily = FontFamily.Monospace,
                        color = if (selectedExportTab == 0) NeonAmber else NeonMint,
                        modifier = Modifier.verticalScroll(scrollState)
                    )
                }

                // Actions
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText("CineSonic Export", currentContent)
                            clipboard.setPrimaryClip(clip)
                            onCopied(if (selectedExportTab == 0) "คัดลอกไฟล์ซับไตเติล SRT แล้ว" else "คัดลอกไฟล์โปรเจกต์ JSON แล้ว")
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonMint, contentColor = Color.Black),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("copy_export_button")
                    ) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "คัดลอกไฟล์", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "ปิด", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}

private fun formatSrtTime(totalSec: Double): String {
    val hrs = (totalSec / 3600).toInt()
    val mins = ((totalSec % 3600) / 60).toInt()
    val secs = (totalSec % 60).toInt()
    val millis = ((totalSec - totalSec.toInt()) * 1000).toInt()
    return String.format("%02d:%02d:%02d,%03d", hrs, mins, secs, millis)
}
