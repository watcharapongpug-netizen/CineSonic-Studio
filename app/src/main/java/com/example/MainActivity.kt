package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ui.components.*
import com.example.ui.screens.WorkspaceScreen
import com.example.ui.theme.CineSonicTheme
import com.example.ui.theme.CyberBackground
import com.example.ui.theme.NeonMint
import com.example.ui.viewmodel.CineSonicViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CineSonicViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CineSonicTheme {
                val uiState by viewModel.uiState.collectAsStateWithLifecycle()
                val snackbarHostState = remember { SnackbarHostState() }

                LaunchedEffect(uiState.userNotification) {
                    uiState.userNotification?.let { msg ->
                        snackbarHostState.showSnackbar(msg)
                        viewModel.dismissNotification()
                    }
                }

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = CyberBackground,
                    snackbarHost = {
                        SnackbarHost(hostState = snackbarHostState) { data ->
                            Snackbar(
                                snackbarData = data,
                                containerColor = Color(0xFF1B2333),
                                contentColor = NeonMint,
                                actionColor = NeonMint
                            )
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            // Top Studio Header
                            StudioHeader(
                                activePipeline = uiState.project.activePipeline,
                                isAutoMode = uiState.project.isAutoMode,
                                budgetSpent = uiState.project.budgetSpentUsd,
                                isCopilotOpen = uiState.isCopilotOpen,
                                onSwitchPipeline = { viewModel.switchPipeline(it) },
                                onToggleAutoMode = { viewModel.toggleAutoMode() },
                                onOpenSelfTest = { viewModel.openSelfTest() },
                                onOpenExport = { viewModel.openExportModal() },
                                onToggleCopilot = { viewModel.toggleCopilot() }
                            )

                            // Pipeline Stepper Navigation
                            PipelineNavigationRow(
                                activePipeline = uiState.project.activePipeline,
                                currentFilmStep = uiState.project.currentFilmStep,
                                currentMusicStep = uiState.project.currentMusicStep,
                                onSelectFilmStep = { viewModel.selectFilmStep(it) },
                                onSelectMusicStep = { viewModel.selectMusicStep(it) }
                            )

                            // Main Studio Stage Workspace
                            WorkspaceScreen(
                                project = uiState.project,
                                brief = uiState.brief,
                                storyBeats = uiState.storyBeats,
                                styleBible = uiState.styleBible,
                                characters = uiState.characters,
                                screenplay = uiState.screenplay,
                                shots = uiState.shots,
                                selectedShotIndex = uiState.selectedShotIndex,
                                musicBrief = uiState.musicBrief,
                                timelineClips = uiState.timelineClips,
                                qcChecklist = uiState.qcChecklist,
                                isPlaying = uiState.isPlayingTimeline,
                                timelinePositionMs = uiState.timelinePositionMs,
                                onIdeaSubmit = { viewModel.initializeDefaultProject(it) },
                                onSelectShot = { viewModel.selectShot(it) },
                                onSelectTake = { shotIdx, takeIdx -> viewModel.selectTake(shotIdx, takeIdx) },
                                onTogglePlay = { viewModel.toggleTimelinePlay() },
                                onSeek = { viewModel.seekTimeline(it) },
                                onApproveCurrentStep = { viewModel.approveCurrentStep() },
                                onOpenExport = { viewModel.openExportModal() },
                                onOpenShotEdit = { viewModel.openShotEdit(it) },
                                modifier = Modifier.weight(1f)
                            )
                        }

                        // Sliding Co-Pilot Step Assistant Drawer
                        AnimatedVisibility(
                            visible = uiState.isCopilotOpen,
                            enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
                            exit = slideOutHorizontally(targetOffsetX = { it }) + fadeOut(),
                            modifier = Modifier
                                .fillMaxHeight()
                                .widthIn(max = 420.dp)
                                .fillMaxWidth(0.92f)
                                .align(Alignment.CenterEnd)
                        ) {
                            CoPilotDrawer(
                                contract = uiState.currentStepContract,
                                isGenerating = uiState.isGenerating,
                                onApproveStep = {
                                    viewModel.approveCurrentStep()
                                },
                                onSelectProposal = { viewModel.selectProposal(it) },
                                onQuickRefine = { viewModel.applyQuickRefinement(it) },
                                onCloseDrawer = { viewModel.toggleCopilot() }
                            )
                        }

                        // Diagnostic Self-Test Modal
                        if (uiState.showSelfTest) {
                            SelfTestModal(
                                logs = uiState.selfTestLogs,
                                isPassed = uiState.selfTestPassed,
                                onRunAgain = { viewModel.runSelfTest() },
                                onDismiss = { viewModel.closeSelfTest() }
                            )
                        }

                        // Export Modal
                        if (uiState.showExportModal) {
                            ExportModal(
                                project = uiState.project,
                                shots = uiState.shots,
                                onDismiss = { viewModel.closeExportModal() },
                                onCopied = { msg -> viewModel.notifyUser(msg) }
                            )
                        }

                        // Shot Edit Modal
                        uiState.editingShot?.let { shot ->
                            ShotEditModal(
                                shot = shot,
                                onSaveShot = { updated -> viewModel.saveShot(updated) },
                                onDismiss = { viewModel.closeShotEdit() }
                            )
                        }
                    }
                }
            }
        }
    }
}
