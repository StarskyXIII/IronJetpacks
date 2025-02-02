package com.blakebr0.ironjetpacks.lib;

import com.blakebr0.cucumber.util.Tooltip;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public final class ModTooltips {
	public static final Tooltip ON = new Tooltip("tooltip.ironjetpacks.on", ChatFormatting.GREEN);
	public static final Tooltip OFF = new Tooltip("tooltip.ironjetpacks.off", ChatFormatting.RED);
	public static final Tooltip INFINITE = new Tooltip("tooltip.ironjetpacks.infinite");
	public static final Tooltip ENGINE = new Tooltip("tooltip.ironjetpacks.engine");
	public static final Tooltip HOVER = new Tooltip("tooltip.ironjetpacks.hover");
	public static final Tooltip TIER = new Tooltip("tooltip.ironjetpacks.tier");
	public static final Tooltip THROTTLE = new Tooltip("tooltip.ironjetpacks.throttle");
	public static final Tooltip FUEL_USAGE = new Tooltip("tooltip.ironjetpacks.fuel_usage");
	public static final Tooltip VERTICAL_SPEED = new Tooltip("tooltip.ironjetpacks.vertical_speed");
	public static final Tooltip VERTICAL_ACCELERATION = new Tooltip("tooltip.ironjetpacks.vertical_acceleration");
	public static final Tooltip HORIZONTAL_SPEED = new Tooltip("tooltip.ironjetpacks.horizontal_speed");
	public static final Tooltip HOVER_SPEED = new Tooltip("tooltip.ironjetpacks.hover_speed");
	public static final Tooltip HOVER_ASCEND_SPEED = new Tooltip("tooltip.ironjetpacks.hover_ascend_speed");
	public static final Tooltip HOVER_DESCEND_SPEED = new Tooltip("tooltip.ironjetpacks.hover_descend_speed");
	public static final Tooltip SPRINT_MODIFIER = new Tooltip("tooltip.ironjetpacks.sprint_modifier");
	public static final Tooltip SPRINT_VERTICAL_MODIFIER = new Tooltip("tooltip.ironjetpacks.sprint_vertical_modifier");
	public static final Tooltip SPRINT_FUEL_MODIFIER = new Tooltip("tooltip.ironjetpacks.sprint_fuel_modifier");
	public static final Tooltip TOGGLE_ENGINE = new Tooltip("tooltip.ironjetpacks.toggle_engine");
	public static final Tooltip TOGGLE_HOVER = new Tooltip("tooltip.ironjetpacks.toggle_hover");
	public static final Tooltip CHANGE_THROTTLE = new Tooltip("tooltip.ironjetpacks.change_throttle");
	public static final Tooltip STATE_TOOLTIP_LAYOUT = new Tooltip("tooltip.ironjetpacks.state_tooltip_layout");
	public static final Tooltip JETPACK_ID = new Tooltip("tooltip.ironjetpacks.jetpack_id");

	public static final Component INFINITE_STATIC = INFINITE.build();
	public static final Component ON_STATIC = ON.build();
	public static final Component OFF_STATIC = OFF.build();

	public static Component getStatusComponent(boolean on) {
		return on ? ON_STATIC : OFF_STATIC;
	}
}
