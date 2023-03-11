package net.Pandarix.betterarcheology;

import net.Pandarix.betterarcheology.block.ModBlocks;
import net.Pandarix.betterarcheology.item.ModItemGroup;
import net.Pandarix.betterarcheology.item.ModItems;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterArcheology implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger("betterarcheology");
	public static final String MOD_ID = "betterarcheology";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Better Archeology says Hello");	//info message

		ModItemGroup.registerTab();		//creates CreativeModeTab
		ModItems.registerModItems();	//registers Items and adds them to the CreativeModeTab
		ModBlocks.registerModBlocks();	//registers Blocks and BlockItems
	}
}
