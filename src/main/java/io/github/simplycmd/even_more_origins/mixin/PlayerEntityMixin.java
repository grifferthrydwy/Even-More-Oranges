package io.github.simplycmd.even_more_origins.mixin;

import io.github.apace100.origins.component.OriginComponent;
import io.github.simplycmd.even_more_origins.power.ProjectileImmunityPower;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {
    @Inject(at = @At("HEAD"), method = "damage", cancellable = true)
    public void damage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (OriginComponent.hasPower((PlayerEntity)(Object)this, ProjectileImmunityPower.class) && source.isProjectile()) cir.setReturnValue(false);
    }
}
