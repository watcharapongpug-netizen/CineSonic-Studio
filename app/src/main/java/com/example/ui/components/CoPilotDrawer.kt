package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.data.model.ProposalOption
import com.example.data.model.StepAssistantResponse
import com.example.ui.theme.*

@Composable
fun CoPilotDrawer(
    contract: StepAssistantResponse?,
    isGenerating: Boolean,
    onApproveStep: () -> Unit,
    onSelectProposal: (ProposalOption) -> Unit,
    onQuickRefine: (String) -> Unit,
    onCloseDrawer: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = CyberSurface,
        modifier = modifier
            .fillMaxHeight()
            .border(1.dp, CyberBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp)
        ) {
            // Co-pilot Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(NeonCyan.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SmartToy,
                            contentDescription = null,
                            tint = NeonCyan,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                    Column {
                        Text(
                            text = contract?.assistantName ?: "AI Co-pilot",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = NeonCyan
                        )
                        Text(
                            text = "Step Contract [${contract?.stepCode ?: "ACTIVE"}]",
                            fontSize = 10.sp,
                            color = TextSecondary,
                            fontFamily = FontFamily.Monospace
                        )
                    }
                }

                IconButton(
                    onClick = onCloseDrawer,
                    modifier = Modifier.size(28.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "ปิดแผง Co-pilot",
                        tint = TextSecondary
                    )
                }
            }

            HorizontalDivider(
                color = CyberBorder,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            // Content Scroll
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(scrollState),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                if (contract == null) {
                    Text(
                        text = "กำลังโหลดข้อมูลผู้ช่วย AI ประจำขั้นตอน...",
                        fontSize = 12.sp,
                        color = TextSecondary
                    )
                } else {
                    // 1. Explain
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = null,
                                    tint = NeonCyan,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "1. คำอธิบายขั้นตอนนี้ (Explain)",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = NeonCyan
                                )
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = contract.explain.summaryTh,
                                fontSize = 12.sp,
                                color = TextPrimary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "ตัวอย่าง: ${contract.explain.exampleTh}",
                                fontSize = 11.sp,
                                color = TextSecondary,
                                fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                            )
                        }
                    }

                    // 2. Estimate (Time & Cost)
                    Surface(
                        color = CyberBackground,
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, NeonAmber.copy(alpha = 0.5f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "4. ประมาณการเวลา & ค่าใช้จ่าย (Estimate)",
                                    fontSize = 10.sp,
                                    color = NeonAmber,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "${contract.estimate.providerName} | ${contract.estimate.noteTh}",
                                    fontSize = 10.sp,
                                    color = TextSecondary
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Text(
                                    text = "$${String.format("%.2f", contract.estimate.estimatedCostUsd)}",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Black,
                                    color = NeonMint,
                                    fontFamily = FontFamily.Monospace
                                )
                                Text(
                                    text = "~${contract.estimate.estimatedTimeSec} วินาที",
                                    fontSize = 10.sp,
                                    color = TextSecondary,
                                    fontFamily = FontFamily.Monospace
                                )
                            }
                        }
                    }

                    // 3. Propose (3 distinct options)
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Lightbulb,
                                contentDescription = null,
                                tint = NeonMagenta,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "3. ข้อเสนอ 3 แนวทาง (Propose)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonMagenta
                            )
                        }

                        contract.proposals.forEach { proposal ->
                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = if (proposal.isRecommended)
                                        NeonCyan.copy(alpha = 0.08f)
                                    else
                                        CyberSurfaceVariant
                                ),
                                shape = RoundedCornerShape(8.dp),
                                border = androidx.compose.foundation.BorderStroke(
                                    width = if (proposal.isRecommended) 1.5.dp else 1.dp,
                                    color = if (proposal.isRecommended) NeonCyan else CyberBorder
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSelectProposal(proposal) }
                                    .testTag("proposal_${proposal.id}")
                            ) {
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = proposal.titleTh,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = if (proposal.isRecommended) NeonCyan else TextPrimary,
                                            modifier = Modifier.weight(1f)
                                        )
                                        if (proposal.isRecommended) {
                                            Surface(
                                                color = NeonCyan,
                                                shape = RoundedCornerShape(4.dp)
                                            ) {
                                                Text(
                                                    text = "แนะนำ",
                                                    fontSize = 9.sp,
                                                    fontWeight = FontWeight.Bold,
                                                    color = Color.Black,
                                                    modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                                )
                                            }
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = proposal.descriptionTh,
                                        fontSize = 11.sp,
                                        color = TextSecondary
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Text(
                                            text = "ข้อดี: ${proposal.prosTh}",
                                            fontSize = 10.sp,
                                            color = NeonMint,
                                            modifier = Modifier.weight(1f)
                                        )
                                        Text(
                                            text = "ข้อจำกัด: ${proposal.consTh}",
                                            fontSize = 10.sp,
                                            color = TextTertiary,
                                            modifier = Modifier.weight(1f)
                                        )
                                    }
                                }
                            }
                        }
                    }

                    // 4. Critique & Score (1 to 5)
                    Card(
                        colors = CardDefaults.cardColors(containerColor = CyberSurfaceVariant),
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(1.dp, CyberBorder)
                    ) {
                        Column(modifier = Modifier.padding(10.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.RateReview,
                                        contentDescription = null,
                                        tint = NeonAmber,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = "5. วิจารณ์และคะแนน (Critique)",
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = NeonAmber
                                    )
                                }
                                // Stars
                                Row {
                                    for (i in 1..5) {
                                        Icon(
                                            imageVector = Icons.Default.Star,
                                            contentDescription = null,
                                            tint = if (i <= contract.critique.score) NeonAmber else TextTertiary,
                                            modifier = Modifier.size(12.dp)
                                        )
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "จุดแข็ง: ${contract.critique.strengthsTh}",
                                fontSize = 11.sp,
                                color = NeonMint
                            )
                            Text(
                                text = "จุดควรระวัง: ${contract.critique.weaknessesTh}",
                                fontSize = 11.sp,
                                color = TextSecondary
                            )
                            Text(
                                text = "วิธีแก้ทันที: ${contract.critique.suggestedFixTh}",
                                fontSize = 11.sp,
                                color = NeonCyan
                            )
                        }
                    }

                    // 5. Quick Refinements (Refine chips)
                    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Tune,
                                contentDescription = null,
                                tint = NeonCyan,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "6. ปรับแต่งด่วน (Refine Chips)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = NeonCyan
                            )
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            contract.quickRefinements.take(2).forEach { chip ->
                                SuggestionChip(
                                    onClick = { onQuickRefine(chip) },
                                    label = { Text(text = chip, fontSize = 10.sp) },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                        if (contract.quickRefinements.size > 2) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                contract.quickRefinements.drop(2).take(2).forEach { chip ->
                                    SuggestionChip(
                                        onClick = { onQuickRefine(chip) },
                                        label = { Text(text = chip, fontSize = 10.sp) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                            }
                        }
                    }

                    // 6. Conflict Check
                    Surface(
                        color = if (contract.conflictCheck.hasConflict) DarkError.copy(alpha = 0.15f) else CyberBackground,
                        shape = RoundedCornerShape(8.dp),
                        border = androidx.compose.foundation.BorderStroke(
                            1.dp,
                            if (contract.conflictCheck.hasConflict) DarkError else NeonMint.copy(alpha = 0.5f)
                        )
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                imageVector = if (contract.conflictCheck.hasConflict) Icons.Default.Warning else Icons.Default.CheckCircle,
                                contentDescription = null,
                                tint = if (contract.conflictCheck.hasConflict) DarkError else NeonMint,
                                modifier = Modifier.size(16.dp)
                            )
                            Column {
                                Text(
                                    text = "7. ตรวจความขัดแย้งข้ามขั้น (Check)",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = if (contract.conflictCheck.hasConflict) DarkError else NeonMint
                                )
                                Text(
                                    text = contract.conflictCheck.detailsTh,
                                    fontSize = 10.sp,
                                    color = TextSecondary
                                )
                            }
                        }
                    }

                    // 7. Handoff Summary
                    Text(
                        text = "8. สิ่งที่ส่งต่อ (Handoff): ${contract.handoffSummary}",
                        fontSize = 10.sp,
                        color = TextSecondary,
                        fontStyle = androidx.compose.ui.text.font.FontStyle.Italic
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Approval Gate Button (Crucial requirement from prompt)
            Button(
                onClick = onApproveStep,
                enabled = !isGenerating,
                colors = ButtonDefaults.buttonColors(
                    containerColor = NeonMint,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("approve_gate_button")
            ) {
                if (isGenerating) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = Color.Black,
                        strokeWidth = 2.dp
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "อนุมัติขั้นนี้และไปต่อ (Approve Gate) →",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}
