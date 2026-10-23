package com.ziroau.cobblequestbridge.api.client.settings.textbox

import earth.terrarium.heracles.client.components.widgets.textbox.editor.MultiLineTextBox
import earth.terrarium.heracles.client.components.widgets.textbox.editor.TextHighlighter
import net.minecraft.client.font.TextRenderer
import net.minecraft.client.gui.DrawContext

/**
 * Extension of [MultiLineTextBox] with a border
 */
class BorderedMultiLineTextBox(
    old: MultiLineTextBox?, font: TextRenderer,
    width: Int, height: Int,
    highlighter: TextHighlighter,
    var fillColour: Int = 0xFF202020.toInt(),
    var borderColour: Int = 0xFF555555.toInt(),
    var borderColourFocused: Int = 0xFFFFFFFF.toInt()
) : MultiLineTextBox(old, font, width, height, highlighter) {
    override fun drawBox(context: DrawContext) {
        context.fill(
            x, y,
            x + width, y + height,
            fillColour
        )

        val borderColourResult = if (isFocused)
            borderColourFocused
        else
            borderColour

        context.drawBorder(x, y, width, height, borderColourResult)
    }
}
