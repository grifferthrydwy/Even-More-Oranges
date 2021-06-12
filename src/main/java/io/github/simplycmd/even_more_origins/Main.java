package io.github.simplycmd.even_more_origins;

import io.github.simplycmd.even_more_origins.registers.*;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
	public static final String MOD_ID = "even_more_origins";

	@Override
	public void onInitialize() {
		Powers.init();
		Conditions.init();
		Enchantments.init();
		Actions.init();
	}
}
