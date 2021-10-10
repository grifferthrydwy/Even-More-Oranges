package io.github.simplycmd.even_more_origins;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.simplycmd.even_more_origins.power.DoubleJumpPower;
import io.github.simplycmd.even_more_origins.registers.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.server.ServerTickCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class Main implements ModInitializer {
	public static final String MOD_ID = "even_more_origins";
	public static HashMap<UUID, Integer> ramTicks = new HashMap<>();

	@Override
	public void onInitialize() {
		Powers.init();
		Conditions.init();
		Actions.init();

		ServerTickCallback.EVENT.register((server) -> {
			for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
				ramTicks.putIfAbsent(player.getUuid(), 0);
				if (ramTicks.get(player.getUuid()) > 0) {
					ramTicks.replace(player.getUuid(), ramTicks.get(player.getUuid()) - 1);
					ramTicks.replace(player.getUuid(), tickRam(player, ramTicks.get(player.getUuid())));
				}
			}
		});
	}

	private static int tickRam(ServerPlayerEntity player, int ramTicks) {
		Box box = player.getBoundingBox();
		List<Entity> list = player.world.getOtherEntities(player, box);
		if (!list.isEmpty()) {
			for (Entity entity : list) {
				if (entity instanceof LivingEntity) {
					entity.damage(DamageSource.player(player), 6);
				}
			}
			player.setVelocity(player.getVelocity().multiply(-0.2));
			player.playSound(SoundEvents.ENTITY_GOAT_SCREAMING_RAM_IMPACT, SoundCategory.PLAYERS, 1, 1);
			ramTicks = 0;
		} else if (player.horizontalCollision) {
			ramTicks = 0;
			player.playSound(SoundEvents.ENTITY_GOAT_SCREAMING_RAM_IMPACT, SoundCategory.PLAYERS, 1, 1);
		}
		return ramTicks;
	}
}
