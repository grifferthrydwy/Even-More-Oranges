package io.github.simplycmd.even_more_origins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class LightningPower extends Power {
    public LightningPower(PowerType<?> type, LivingEntity player) {
        super(type, player);
    }
}
