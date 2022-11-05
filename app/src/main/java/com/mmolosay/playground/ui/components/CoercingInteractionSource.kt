package com.mmolosay.playground.ui.components

import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.ui.geometry.Offset

class CoercingInteractionSource(
    producer: InteractionSource,
) : MappingInteractionSource(producer) {

    override fun map(position: Offset): Offset {
        TODO("map is not implemented")
    }
}