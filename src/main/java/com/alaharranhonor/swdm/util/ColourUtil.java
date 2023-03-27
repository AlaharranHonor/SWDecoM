package com.alaharranhonor.swdm.util;

import net.minecraft.world.item.DyeColor;

import java.util.*;

public class ColourUtil {

	public static Map<String, List<DyeColor>> NATURAL_TO_DYE = new HashMap() {{
		put("tuscan", new ArrayList<>(Arrays.asList(DyeColor.ORANGE, DyeColor.RED)));
		put("peach", new ArrayList<>(Arrays.asList(DyeColor.PINK, DyeColor.ORANGE)));
		put("thistle", new ArrayList<>(Arrays.asList(DyeColor.PURPLE, DyeColor.LIGHT_BLUE)));
		put("dark_brown", new ArrayList<>(Arrays.asList(DyeColor.BROWN, DyeColor.BLACK)));
		put("mahogany", new ArrayList<>(Arrays.asList(DyeColor.BROWN, DyeColor.RED)));
		put("brown", new ArrayList<>(Arrays.asList(DyeColor.BROWN)));
		put("muted_brown", new ArrayList<>(Arrays.asList(DyeColor.BROWN, DyeColor.GRAY)));
		put("vivid_red", new ArrayList<>(Arrays.asList(DyeColor.RED)));
		put("orange", new ArrayList<>(Arrays.asList(DyeColor.ORANGE)));
		put("golden", new ArrayList<>(Arrays.asList(DyeColor.YELLOW)));
		put("pale", new ArrayList<>(Arrays.asList(DyeColor.WHITE, DyeColor.YELLOW)));
		put("yellow", new ArrayList<>(Arrays.asList(DyeColor.YELLOW, DyeColor.WHITE)));
		put("white", new ArrayList<>(Arrays.asList(DyeColor.WHITE)));
		put("pearl", new ArrayList<>(Arrays.asList(DyeColor.WHITE, DyeColor.LIGHT_BLUE)));
		put("dusted_gray", new ArrayList<>(Arrays.asList(DyeColor.LIGHT_GRAY, DyeColor.WHITE)));
		put("light_gray", new ArrayList<>(Arrays.asList(DyeColor.LIGHT_GRAY)));
		put("slate", new ArrayList<>(Arrays.asList(DyeColor.LIGHT_GRAY, DyeColor.LIGHT_BLUE)));
		put("blue_gray", new ArrayList<>(Arrays.asList(DyeColor.BLUE, DyeColor.GRAY)));
		put("gray", new ArrayList<>(Arrays.asList(DyeColor.GRAY)));
		put("royal_gray", new ArrayList<>(Arrays.asList(DyeColor.GRAY, DyeColor.PURPLE)));
		put("black", new ArrayList<>(Arrays.asList(DyeColor.BLACK)));
	}};

	public static Map<String, List<DyeColor>> CUSTOM_COLORS_TO_DYE = new HashMap() {{
		put("sage", new ArrayList<>(Arrays.asList(DyeColor.LIME, DyeColor.YELLOW)));
		put("golden", new ArrayList<>(Arrays.asList(DyeColor.YELLOW, DyeColor.WHITE)));
	}};

}
