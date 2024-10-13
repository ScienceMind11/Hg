package net.mercury.hg.handler;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.ShaderProgram;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class FreezeframeHandler {

    private static int freezeFrames = 0;
    private static final FreezeScreen screen = new FreezeScreen();

    public static int getFreezeFrames() {
        return freezeFrames;
    }

    public static void addFreezeFrames(int frames) {
        freezeFrames += frames;
    }

    public static void tick(MinecraftClient client) {

        if(client.world == null) return;

        if(freezeFrames > 0) {
            freezeFrames--;

            if(client.isIntegratedServerRunning() && !client.getServer().isRemote()) {
                client.setScreen(screen);
            }

        } else {
            client.setScreen(null);
        }

    }

    static class FreezeScreen extends Screen {
        protected FreezeScreen() {
            super(Text.literal(""));
        }
    }

}
