package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey val id: String,
    val title: String,
    val ideaPrompt: String,
    val isAutoMode: Boolean,
    val filmStepCode: String,
    val musicStepCode: String,
    val budgetLimitUsd: Double,
    val budgetSpentUsd: Double,
    val createdAt: Long
)

@Entity(tableName = "shots")
data class ShotEntity(
    @PrimaryKey val shotId: String,
    val projectId: String,
    val sceneIndex: Int,
    val shotNumber: Int,
    val shotSize: String,
    val cameraAngle: String,
    val cameraMotion: String,
    val durationSec: Float,
    val visualPromptEn: String,
    val dialogue: String,
    val consistencyScore: Int
)

@Entity(tableName = "usage_ledger")
data class UsageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val projectId: String,
    val timestamp: Long,
    val step: String,
    val provider: String,
    val costUsd: Double,
    val tokensOrDuration: String
)
