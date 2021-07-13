package io.github.simplycmd.even_more_origins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.simplycmd.even_more_origins.power.DoubleJumpPower;
import io.github.simplycmd.even_more_origins.power.ProjectileImmunityPower;
import io.github.simplycmd.even_more_origins.power.FlapPower;
import io.github.simplycmd.even_more_origins.power.LifeLeachPower;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow protected abstract void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition);

    private boolean jumped = false;
    private boolean falling = false;
    private int tick = 0;

    @Inject(method="tickMovement", at = @At("HEAD"))
    private void updateInput(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (PowerHolderComponent.hasPower(entity, DoubleJumpPower.class)) {
            // Set falling
            if (entity.getVelocity().y < 0) {
                if (!entity.verticalCollision) {
                    if (!jumped) falling = true;
                } else {
                    falling = false;
                    jumped = false;
                }
            }

            // Detect double jump
            if (entity.getVelocity().y > 0 && falling) {
                jumped = true;
            }

            // Allow double jump
            else if (entity.getVelocity().y < 0 && falling && !jumped) {
                entity.setOnGround(true);
            }
        }
        if (entity.getPassengerList().size() > 0 && entity.getPassengerList().get(0).getDisplayName().getString().equals("§4§lVampire")) {
            entity.setOnGround(true);
            entity.addVelocity(entity.getRotationVector().getX() / 4, 0, entity.getRotationVector().getZ() / 4);
            tick++;
            if (tick >= 600) {
                entity.removeAllPassengers();
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 40, 255, false, false, false));
                entity.addStatusEffect(new StatusEffectInstance(StatusEffects.RESISTANCE, 100, 255, false, false, false));
                tick = 0;
            }
        }
        if (PowerHolderComponent.hasPower(entity, FlapPower.class) && entity.getStatusEffect(StatusEffects.LUCK) != null)
            entity.setOnGround(true);
    }

    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (PowerHolderComponent.hasPower((LivingEntity)(Object)this, ProjectileImmunityPower.class) && source.isProjectile()) cir.setReturnValue(false);
    }

    @Inject(method = "damage", at = @At("RETURN"))
    private void invokeHitActions(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if(cir.getReturnValue()) {
            PowerHolderComponent.getPowers(source.getAttacker(), LifeLeachPower.class).forEach(p -> p.onHit((LivingEntity)(Object)this, source, amount));
        }
    }
}
