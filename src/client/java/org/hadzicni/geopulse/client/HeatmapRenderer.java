package org.hadzicni.geopulse.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.block.BlockState;
import net.minecraft.block.Block;
import java.awt.*;

public class HeatmapRenderer {

    private static final MinecraftClient client = MinecraftClient.getInstance();

    public static void render(DrawContext ctx) {
        if (client.player == null || client.world == null) return;
        if (client.options.hudHidden) return;

        BlockPos pos = client.player.getBlockPos();
        World world = client.world;

        int danger = calculateDanger(world, pos);
        int ores = calculateOre(world, pos);
        Color color = chooseColor(danger, ores);

        drawBar(ctx, danger, ores, color);
    }

    // -----------------------------
    // DANGER CALCULATION
    // -----------------------------
    private static int calculateDanger(World world, BlockPos pos) {
        int danger = 0;

        // Hostile mobs in radius 8
        Box box = new Box(pos).expand(8);
        for (HostileEntity mob : world.getEntitiesByClass(HostileEntity.class, box, e -> true)) {
            double dist = mob.squaredDistanceTo(pos.toCenterPos());
            if (dist < 25) danger += 12;    // < 5 Blocks
            else if (dist < 64) danger += 8; // < 8 Blocks
        }

        // Light level danger
        int light = world.getLightLevel(pos);
        if (light <= 7) {
            danger += (7 - light) * 2;
        }

        // Lava near (check 4 blocks radius)
        for (BlockPos p : BlockPos.iterate(pos.add(-4, -4, -4), pos.add(4, 4, 4))) {
            if (world.getFluidState(p).isIn(FluidTags.LAVA)) {
                danger += 10;
                break;
            }
        }

        // Fall danger
        int fall = 0;
        BlockPos down = pos.down();
        while (world.isAir(down) && fall <= 32) {
            fall++;
            down = down.down();
        }

        if (fall > 8) danger += 30;
        else if (fall > 4) danger += 15;

        return danger;
    }

    // -----------------------------
    // ORE CALCULATION
    // -----------------------------
    private static int calculateOre(World world, BlockPos pos) {
        int score = 0;

        Iterable<BlockPos> scan = BlockPos.iterate(pos.add(-2, -3, -2), pos.add(2, 1, 2));

        for (BlockPos p : scan) {
            BlockState bs = world.getBlockState(p);
            Block b = bs.getBlock();

            String id = b.getRegistryEntry().getKey().orElseThrow().getValue().toString();

            if (id.contains("iron_ore") || id.contains("deepslate_iron_ore"))
                score += 2;

            if (id.contains("gold_ore") || id.contains("redstone_ore") || id.contains("lapis_ore"))
                score += 3;

            if (id.contains("diamond_ore") && !id.contains("deepslate"))
                score += 7;

            if (id.contains("deepslate_diamond_ore"))
                score += 10;

            if (id.contains("ancient_debris"))
                score += 12;
        }

        return score;
    }

    // -----------------------------
    // COLOR LOGIC
    // -----------------------------
    private static Color chooseColor(int danger, int ores) {
        if (ores > danger) return new Color(0, 0, 255);

        if (danger <= 20) return new Color(0, 255, 0);
        if (danger <= 40) return new Color(255, 255, 0);
        if (danger <= 70) return new Color(255, 136, 0);

        return new Color(255, 0, 0);
    }

    // -----------------------------
    // DRAW HUD BAR
    // -----------------------------
    private static void drawBar(DrawContext ctx, int danger, int ores, Color color) {
        GeopulseConfig cfg = GeopulseClient.CONFIG;

        int x = 8;
        int y = 20;
        int w = cfg.barWidth;
        int h = cfg.barHeight;

        // Background
        ctx.fill(x, y, x + w, y + h, 0x66000000);

        // Filled danger portion
        float perc = Math.min(1f, danger / 100f);
        int fill = (int) (h * perc);

        ctx.fill(
                x,
                y + (h - fill),
                x + w,
                y + h,
                color.getRGB()
        );

        // Text
        ctx.drawText(client.textRenderer, "Ore: " + ores, x + w + 6, y, 0xFFFFFF, false);
        ctx.drawText(client.textRenderer, "Danger: " + danger, x + w + 6, y + 12, 0xFFFFFF, false);
    }
}
