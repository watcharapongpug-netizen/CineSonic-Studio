package com.example.data.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioTrack
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.math.sin

object CyberAudioSynthesizer {

    private var currentTrack: AudioTrack? = null
    private var isPlaying = false

    // Cyberpunk D-Minor Arpeggio Frequencies (D3, F3, A3, C4, D4)
    private val arpeggioFreqs = doubleArrayOf(146.83, 174.61, 220.00, 261.63, 293.66)

    suspend fun playCyberArpeggio(durationMs: Int = 3000) = withContext(Dispatchers.Default) {
        stop()
        try {
            val sampleRate = 44100
            val numSamples = (sampleRate * (durationMs / 1000.0)).toInt()
            val buffer = ShortArray(numSamples)
            val noteDurationSamples = (sampleRate * 0.25).toInt() // 250ms per note

            for (i in 0 until numSamples) {
                val noteIdx = (i / noteDurationSamples) % arpeggioFreqs.size
                val freq = arpeggioFreqs[noteIdx]
                // Sine wave + slight warm 2nd harmonic
                val sample1 = sin(2.0 * Math.PI * i * freq / sampleRate)
                val sample2 = 0.3 * sin(4.0 * Math.PI * i * freq / sampleRate)
                val envelope = 1.0 - ((i % noteDurationSamples).toDouble() / noteDurationSamples) * 0.4
                val finalSample = ((sample1 + sample2) * envelope * 0.5 * Short.MAX_VALUE).toInt()
                buffer[i] = finalSample.coerceIn(Short.MIN_VALUE.toInt(), Short.MAX_VALUE.toInt()).toShort()
            }

            val track = AudioTrack.Builder()
                .setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build()
                )
                .setAudioFormat(
                    AudioFormat.Builder()
                        .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                        .setSampleRate(sampleRate)
                        .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                        .build()
                )
                .setBufferSizeInBytes(buffer.size * 2)
                .setTransferMode(AudioTrack.MODE_STATIC)
                .build()

            track.write(buffer, 0, buffer.size)
            track.play()
            currentTrack = track
            isPlaying = true
        } catch (_: Exception) {
            // Graceful fallback if device audio track is unavailable
        }
    }

    fun stop() {
        try {
            currentTrack?.stop()
            currentTrack?.release()
            currentTrack = null
            isPlaying = false
        } catch (_: Exception) {}
    }
}
