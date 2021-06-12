package io.github.simplycmd.even_more_origins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import virtuoel.pehkui.api.ScaleData;

public class ModifySizePower extends Power {
    public final float scale;

    public ModifySizePower(PowerType<?> type, LivingEntity player, float scale) {
        super(type, player);
        this.scale = scale;
    }

    @Override
    public void onAdded() {
        super.onAdded();
        ScaleData data = ScaleData.of(entity);
        data.setScale(ScaleData.of(entity).getInitialScale() * scale);
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
        ScaleData data = ScaleData.of(entity);
        data.setScale(ScaleData.of(entity).getInitialScale() / scale);
    }
}