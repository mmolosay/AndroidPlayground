package com.mmolosay.playground.ui.components

import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class MappingInteractionSource(
    producer: InteractionSource, // TODO: replace composition with inheritance
) : InteractionSource {

    private val mappedPresses =
        mutableMapOf<PressInteraction.Press, PressInteraction.Press>()

    override val interactions: Flow<Interaction> =
        producer.interactions.map { interaction ->
            when (interaction) {
                is PressInteraction.Press -> mapPress(interaction)
                is PressInteraction.Cancel -> mapCancel(interaction)
                is PressInteraction.Release -> mapRelease(interaction)
                else -> interaction
            }
        }

    protected abstract fun map(position: Offset): Offset

    private fun mapPress(interaction: PressInteraction.Press): PressInteraction.Press {
        TODO()
    }

    private fun mapCancel(interaction: PressInteraction.Cancel): PressInteraction.Cancel {
        TODO()
    }

    private fun mapRelease(interaction: PressInteraction.Release): PressInteraction.Release {
        TODO()
    }
}