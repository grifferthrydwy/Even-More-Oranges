package io.github.simplycmd.even_more_origins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;

public class DoubleHealthPower extends Power {

    public LivingEntity entity;

    public DoubleHealthPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
        this.entity = entity;
    }

    @Override
    public void onAdded() {
        entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(40);
        entity.setHealth(40);
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        entity.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(20);
        entity.setHealth(20);
    }
}
