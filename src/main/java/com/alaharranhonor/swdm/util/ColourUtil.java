package com.alaharranhonor.swdm.util;

import net.minecraft.item.DyeColor;

import java.util.*;

public class ColourUtil {

	public static Map<String, List<DyeColor>> NATURAL_TO_DYE = new HashMap() {{
		put("dark_brown", new ArrayList<>(Arrays.asList(DyeColor.BROWN, DyeColor.BLACK)));
		put("brown", new ArrayList<>(Arrays.asList(DyeColor.BROWN)));
		put("muted_brown", new ArrayList<>(Arrays.asList(DyeColor.BROWN, DyeColor.GRAY)));
		put("vivid_red", new ArrayList<>(Arrays.asList(DyeColor.RED)));
		put("tuscan", new ArrayList<>(Arrays.asList(DyeColor.ORANGE)));
		put("golden", new ArrayList<>(Arrays.asList(DyeColor.YELLOW)));
		put("pale", new ArrayList<>(Arrays.asList(DyeColor.WHITE, DyeColor.YELLOW)));
		put("white", new ArrayList<>(Arrays.asList(DyeColor.WHITE)));
		put("dusted_gray", new ArrayList<>(Arrays.asList(DyeColor.LIGHT_GRAY, DyeColor.WHITE)));
		put("light_gray", new ArrayList<>(Arrays.asList(DyeColor.LIGHT_GRAY)));
		put("blue_gray", new ArrayList<>(Arrays.asList(DyeColor.BLUE, DyeColor.GRAY)));
		put("gray", new ArrayList<>(Arrays.asList(DyeColor.GRAY)));
		put("black", new ArrayList<>(Arrays.asList(DyeColor.BLACK)));
	}};

	public static Map<String, List<DyeColor>> CUSTOM_COLORS_TO_DYE = new HashMap() {{
		put("sage", new ArrayList<>(Arrays.asList(DyeColor.LIME, DyeColor.YELLOW)));
		put("golden", new ArrayList<>(Arrays.asList(DyeColor.YELLOW, DyeColor.WHITE)));
	}};

}
