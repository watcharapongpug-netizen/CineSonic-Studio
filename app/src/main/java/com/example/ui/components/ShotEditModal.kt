package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.model.ShotItem
import com.example.ui.theme.*

@Composable
fun ShotEditModal(
    shot: ShotItem,
    onSaveShot: (ShotItem) -> Unit,
    onDismiss: () -> Unit
) {
    var shotSize by remember { mutableStateOf(shot.shotSize) }
    var cameraAngle by remember { mutableStateOf(shot.cameraAngle) }
    var cameraMotion by remember { mutableStateOf(shot.cameraMotion) }
    var promptEn by remember { mutableStateOf(shot.visualPromptEn) }
    var dialogue by remember { mutableStateOf(shot.dialogue) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            colors = CardDefaults.cardColors(containerColor = CyberSurface),
            shape = RoundedCornerShape(16.dp),
            border = androidx.compose.foundation.BorderStroke(1.dp, NeonCyan),
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .testTag("shot_edit_dialog")
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "ปรับแต่งช็อตที่ #${shot.shotNumber}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = NeonCyan
                )

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = shotSize,
                        onValueChange = { shotSize = it },
                        label = { Text("ขนาดภาพ (Shot Size)", fontSize = 10.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                    OutlinedTextField(
                        value = cameraAngle,
                        onValueChange = { cameraAngle = it },
                        label = { Text("มุมกล้อง (Angle)", fontSize = 10.sp) },
                        modifier = Modifier.weight(1f),
                        singleLine = true
                    )
                }

                OutlinedTextField(
                    value = cameraMotion,
                    onValueChange = { cameraMotion = it },
                    label = { Text("การเคลื่อนกล้อง (Motion)", fontSize = 10.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                OutlinedTextField(
                    value = promptEn,
                    onValueChange = { promptEn = it },
                    label = { Text("Visual Prompt (English)", fontSize = 10.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )

                OutlinedTextField(
                    value = dialogue,
                    onValueChange = { dialogue = it },
                    label = { Text("บทพูด / ซับไตเติล", fontSize = 10.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        onClick = {
                            val updated = shot.copy(
                                shotSize = shotSize,
                                cameraAngle = cameraAngle,
                                cameraMotion = cameraMotion,
                                visualPromptEn = promptEn,
                                dialogue = dialogue,
                                consistencyScore = (shot.consistencyScore + 1).coerceAtMost(100)
                            )
                            onSaveShot(updated)
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = NeonCyan, contentColor = Color.Black),
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Icon(imageVector = Icons.Default.AutoAwesome, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(text = "บันทึกช็อต", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                    }

                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(text = "ยกเลิก", fontSize = 11.sp)
                    }
                }
            }
        }
    }
}
