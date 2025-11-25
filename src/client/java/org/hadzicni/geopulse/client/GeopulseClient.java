package org.hadzicni.geopulse.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;

public class GeopulseClient implements ClientModInitializer {

    public static GeopulseConfig CONFIG = new GeopulseConfig();

    @Override
    public void onInitializeClient() {

        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            HeatmapRenderer.render(drawContext);
        });
    }
}
