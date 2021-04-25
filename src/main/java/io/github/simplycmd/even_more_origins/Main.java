package io.github.simplycmd.even_more_origins;

import io.github.simplycmd.even_more_origins.registers.Conditions;
import io.github.simplycmd.even_more_origins.registers.Powers;
import io.github.simplycmd.even_more_origins.registers.ScaleTypes;
import net.fabricmc.api.ModInitializer;

public class Main implements ModInitializer {
	public static final String MOD_ID = "even_more_origins";

	@Override
	public void onInitialize() {
		ScaleTypes.init();
		Powers.init();
		Conditions.init();
	}
}
