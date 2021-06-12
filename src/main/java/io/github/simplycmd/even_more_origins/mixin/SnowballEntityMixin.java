package io.github.simplycmd.even_more_origins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.simplycmd.even_more_origins.power.LightningPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.hit.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowballEntity.class)
public class SnowballEntityMixin {
    @Inject(at = @At("TAIL"), method = "onCollision(Lnet/minecraft/util/hit/HitResult;)V")
    protected void onCollision(HitResult hitResult, CallbackInfo ci) {
        if (((SnowballEntity) (Object) this).isGlowing() && PowerHolderComponent.hasPower(((SnowballEntity) (Object) this).getOwner(), LightningPower.class)) {
            LightningEntity entity = new LightningEntity(EntityType.LIGHTNING_BOLT, ((SnowballEntity) (Object) this).world);
            entity.updatePosition(hitResult.getPos().getX(), hitResult.getPos().getY(), hitResult.getPos().getZ());
            ((SnowballEntity) (Object) this).world.spawnEntity(entity);
        }
    }
}