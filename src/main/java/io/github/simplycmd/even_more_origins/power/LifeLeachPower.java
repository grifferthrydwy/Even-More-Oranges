package io.github.simplycmd.even_more_origins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;

public class LifeLeachPower extends Power {
    final float percentage;

    public LifeLeachPower(PowerType<?> type, LivingEntity entity, float percentage) {
        super(type, entity);
        this.percentage = percentage;
    }

    public void onHit(LivingEntity target, DamageSource damageSource, float damageAmount) {
        target.getAttacker().heal(damageAmount * percentage);
    }
}