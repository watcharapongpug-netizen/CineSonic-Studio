package com.example.data.model

data class StepAssistantResponse(
    val stepCode: String,
    val assistantName: String,
    val explain: AssistantExplain,
    val interview: List<InterviewQuestion> = emptyList(),
    val proposals: List<ProposalOption> = emptyList(),
    val estimate: ResourceEstimate,
    val critique: StepCritique,
    val quickRefinements: List<String> = emptyList(),
    val conflictCheck: ConflictCheckResult,
    val handoffSummary: String
)

data class AssistantExplain(
    val summaryTh: String,
    val exampleTh: String
)

data class InterviewQuestion(
    val id: String,
    val questionTh: String,
    val optionsTh: List<String>,
    val selectedOptionIndex: Int = 0
)

data class ProposalOption(
    val id: String,
    val titleTh: String,
    val descriptionTh: String,
    val prosTh: String,
    val consTh: String,
    val isRecommended: Boolean = false
)

data class ResourceEstimate(
    val estimatedTimeSec: Int,
    val estimatedCostUsd: Double,
    val providerName: String,
    val noteTh: String
)

data class StepCritique(
    val score: Int, // 1 to 5
    val strengthsTh: String,
    val weaknessesTh: String,
    val suggestedFixTh: String
)

data class ConflictCheckResult(
    val hasConflict: Boolean,
    val detailsTh: String
)
