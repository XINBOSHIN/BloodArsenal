package com.arc.bloodarsenal.common.gui;

import static com.arc.bloodarsenal.common.BloodArsenalConfig.*;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;

import com.arc.bloodarsenal.common.BloodArsenal;
import cpw.mods.fml.client.config.*;

public class ConfigGui extends GuiConfig {

    public ConfigGui(GuiScreen parentScreen) {
        super(
                parentScreen,
                getConfigElements(parentScreen),
                BloodArsenal.MODID,
                false,
                false,
                "BloodArsenal Configuration");
    }

    @SuppressWarnings("rawtypes")
    private static List<IConfigElement> getConfigElements(GuiScreen parent) {
        List<IConfigElement> list = new ArrayList<IConfigElement>();

        // Adds sections declared in BloodArsenalConfig. toLowerCase() is used because the configuration class
        // automatically does this, so must we.
        //
        list.add(new ConfigElement<ConfigCategory>(config.getCategory(potionId.toLowerCase())));
        list.add(new ConfigElement<ConfigCategory>(config.getCategory(ritualBlacklist.toLowerCase())));
        list.add(new ConfigElement<ConfigCategory>(config.getCategory(blockSettings.toLowerCase())));
        list.add(new ConfigElement<ConfigCategory>(config.getCategory(itemSettings.toLowerCase())));
        list.add(new ConfigElement<ConfigCategory>(config.getCategory(modSettings.toLowerCase())));
        list.add(new ConfigElement<ConfigCategory>(config.getCategory(lpSettings.toLowerCase())));
        list.add(new ConfigElement<ConfigCategory>(config.getCategory(misc.toLowerCase())));

        return list;
    }
}
