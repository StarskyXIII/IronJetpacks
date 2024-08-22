package com.blakebr0.ironjetpacks.client.handler;

import com.blakebr0.ironjetpacks.IronJetpacks;
import com.blakebr0.ironjetpacks.config.ModConfigs;
import com.blakebr0.ironjetpacks.item.JetpackItem;
import com.blakebr0.ironjetpacks.lib.ModTooltips;
import com.blakebr0.ironjetpacks.util.JetpackUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

public final class HudHandler {
    private static final ResourceLocation HUD_TEXTURE = IronJetpacks.resource("textures/gui/hud.png");

    @SubscribeEvent
    public void onRegisterGuiOverlays(RenderGuiEvent.Pre event) {
        var mc = Minecraft.getInstance();
        if (mc.player != null && isVisible(mc)) {
            var chest = JetpackUtils.getEquippedJetpack(mc.player);
            var item = chest.getItem();

            if (!chest.isEmpty() && item instanceof JetpackItem) {
                var pos = getHudPos();
                if (pos != null) {
                    int xPos = (int) (pos.x / 0.33) - 18;
                    int yPos = (int) (pos.y / 0.33) - 78;

                    var gfx = event.getGuiGraphics();

                    var matrix = gfx.pose();

                    matrix.pushPose();
                    matrix.scale(0.33F, 0.33F, 1.0F);
                    gfx.blit(HUD_TEXTURE, xPos, yPos, 0, 0, 28, 156, 256, 256);
                    int i2 = getEnergyBarScaled(chest);
                    gfx.blit(HUD_TEXTURE, xPos, 166 - i2 + yPos - 10, 28, 156 - i2, 28, i2, 256, 256);
                    matrix.popPose();

                    var fuel = getFuelComponent(chest);
                    var throttle = getThrottleComponent(chest);
                    var engine = getEngineComponent(chest);
                    var hover = getHoverComponent(chest);

                    if (pos.side == 1) {
                        gfx.drawString(mc.font, fuel, pos.x - 8 - mc.font.width(fuel), pos.y - 21, 16383998);
                        gfx.drawString(mc.font, throttle, pos.x - 8 - mc.font.width(throttle), pos.y - 6, 16383998);
                        gfx.drawString(mc.font, engine, pos.x - 8 - mc.font.width(engine), pos.y + 4, 16383998);
                        gfx.drawString(mc.font, hover, pos.x - 8 - mc.font.width(hover), pos.y + 14, 16383998);
                    } else {
                        gfx.drawString(mc.font, fuel, pos.x + 6, pos.y - 21, 16383998);
                        gfx.drawString(mc.font, throttle, pos.x + 6, pos.y - 6, 16383998);
                        gfx.drawString(mc.font, engine, pos.x + 6, pos.y + 4, 16383998);
                        gfx.drawString(mc.font, hover, pos.x + 6, pos.y + 14, 16383998);
                    }
                }
            }
        }
    }

    private static HudPos getHudPos() {
        var window = Minecraft.getInstance().getWindow();
        int xOffset = ModConfigs.HUD_OFFSET_X.get();
        int yOffset = ModConfigs.HUD_OFFSET_Y.get();

        return switch (ModConfigs.HUD_POSITION.get()) {
            case 0 -> new HudPos(10 + xOffset, 30 + yOffset, 0);
            case 1 -> new HudPos(10 + xOffset, window.getGuiScaledHeight() / 2 + yOffset, 0);
            case 2 -> new HudPos(10 + xOffset, window.getGuiScaledHeight() - 30 + yOffset, 0);
            case 3 -> new HudPos(window.getGuiScaledWidth() - 8 - xOffset, 30 + yOffset, 1);
            case 4 -> new HudPos(window.getGuiScaledWidth() - 8 - xOffset, window.getGuiScaledHeight() / 2 + yOffset, 1);
            case 5 -> new HudPos(window.getGuiScaledWidth() - 8 - xOffset, window.getGuiScaledHeight() - 30 + yOffset, 1);
            default -> null;
        };

    }

    private static int getEnergyBarScaled(ItemStack stack) {
        var jetpack = JetpackUtils.getJetpack(stack);
        if (jetpack.creative)
            return 156;

        var energy = JetpackUtils.getEnergyStorage(stack);
        int i = energy.getEnergyStored();
        int j = energy.getMaxEnergyStored();

        return (int) (j != 0 && i != 0 ? (long) i * 156 / j : 0);
    }

    private static Component getFuelComponent(ItemStack stack) {
        var jetpack = JetpackUtils.getJetpack(stack);
        if (jetpack.creative) {
            return Component.literal(ModTooltips.INFINITE_STATIC.getString() + " FE");
        }

        int energy = JetpackUtils.getEnergyStorage(stack).getEnergyStored();
        if (energy >= 1000000000) {
            int big = energy / 1000000000;
            int small = (energy - (big * 1000000000)) / 100000000;
            return Component.literal(big + ((small != 0) ? "." + small : "") + "G FE").withStyle(ChatFormatting.GRAY);
        } else if (energy >= 1000000) {
            int big = energy / 1000000;
            int small = (energy - (big * 1000000)) / 100000;
            return Component.literal(big + ((small != 0) ? "." + small : "") + "M FE").withStyle(ChatFormatting.GRAY);
        } else if (energy >= 1000) {
            return Component.literal(energy / 1000 + "k FE").withStyle(ChatFormatting.GRAY);
        } else {
            return Component.literal(energy + " FE").withStyle(ChatFormatting.GRAY);
        }
    }

    private static Component getThrottleComponent(ItemStack stack) {
        return Component.literal("T: " + (int) (JetpackUtils.getThrottle(stack) * 100) + "%").withStyle(ChatFormatting.GRAY);
    }

    private static Component getEngineComponent(ItemStack stack) {
        return Component.literal("E: ").append(ModTooltips.getStatusComponent(JetpackUtils.isEngineOn(stack))).withStyle(ChatFormatting.GRAY);
    }

    private static Component getHoverComponent(ItemStack stack) {
        return Component.literal("H: ").append(ModTooltips.getStatusComponent(JetpackUtils.isHovering(stack))).withStyle(ChatFormatting.GRAY);
    }

    private static boolean isVisible(Minecraft mc) {
        return ModConfigs.ENABLE_HUD.get()
                && (ModConfigs.SHOW_HUD_OVER_CHAT.get()
                || !ModConfigs.SHOW_HUD_OVER_CHAT.get()
                && !(mc.screen instanceof ChatScreen))
                && !mc.options.hideGui
                && !mc.getDebugOverlay().showDebugScreen();
    }

    private record HudPos(int x, int y, int side) { }
}
