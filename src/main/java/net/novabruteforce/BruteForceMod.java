package net.novabruteforce;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class BruteForceMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ScreenEvents.BEFORE_INIT.register((client, screen, scaledWidth, scaledHeight) -> {
            if (screen instanceof MultiplayerScreen) {
                screen.addDrawableChild(ButtonWidget.builder(
                    Text.literal("BruteForce"),
                    button -> client.setScreen(new BruteForceScreen())
                ).dimensions(scaledWidth - 120, 10, 110, 20).build());
            }
        });
    }
}
