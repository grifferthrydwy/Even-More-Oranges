package io.github.simplycmd.even_more_origins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.simplycmd.even_more_origins.power.DoubleJumpPower;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    private boolean doubleJumped = false;
    private int tick = 0;

    @Inject(method="tickMovement", at = @At("HEAD"))
    private void updateInput(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (PowerHolderComponent.hasPower((LivingEntity)(Object)this, DoubleJumpPower.class)) {
            if (!doubleJumped && entity.getVelocity().y < 0) {
                entity.setOnGround(true);
                doubleJumped = true;
            }
            if (!entity.world.getBlockState(entity.getBlockPos().add(0, -1, 0)).isAir()) {
                doubleJumped = false;
            }
        }
        if (entity.getPassengerList().size() > 0 && entity.getPassengerList().get(0).getDisplayName().getString().equals("§4§lVampire")) {
            entity.setOnGround(true);
            tick++;
            if (tick >= 600) {
                entity.removeAllPassengers();
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40, 255, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 255, false, false, false));
                tick = 0;
            }
        }
    }
}
