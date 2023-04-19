package com.alaharranhonor.swdm.compat;

import com.alaharranhonor.swdm.SWDM;
import com.alaharranhonor.swdm.registry.BlockSetup;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class SWLMCompat {

    public static final String ID = "swlm";
    public static final File SWLM_CONFIG_FOLDER = FMLPaths.CONFIGDIR.get().resolve(ID).toFile();
    static {
        SWLM_CONFIG_FOLDER.mkdirs();
    }

    public static final List<String> SWLM_BLOCK_LIST = new ArrayList<>();

    public static boolean isLoaded() {
        return ModList.get().isLoaded(ID);
    }

    public static void init() {
        if (!isLoaded()) return;

        BlockSetup.MANUAL_BLOCKS.keySet().forEach(id -> {
            SWLM_BLOCK_LIST.add(id.toString());
        });

        generateSWLMBlockList();
    }

    public static void generateSWLMBlockList() {
        if (SWLM_BLOCK_LIST.isEmpty()) return;

        File swdmFile = new File(SWLM_CONFIG_FOLDER, SWDM.MOD_ID + ".txt");
        try {
            swdmFile.createNewFile();
        } catch (IOException e) {
            SWDM.LOGGER.error("Could not create SWDM Block List file for SWLM");
            e.printStackTrace();
            return;
        }

        try {
            Files.write(swdmFile.toPath(), SWLM_BLOCK_LIST);
        } catch (IOException e) {
            SWDM.LOGGER.error("Could not write SWDM Block List File for SWLM");
            e.printStackTrace();
        }
    }

    public static void addBlockName(ResourceLocation name) {
        if (isLoaded()) {
            SWLM_BLOCK_LIST.add(name.toString());
        }
    }
}
