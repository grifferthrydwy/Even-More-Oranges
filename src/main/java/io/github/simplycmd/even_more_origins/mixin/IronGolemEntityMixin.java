package io.github.simplycmd.even_more_origins.mixin;

import io.github.apace100.origins.component.OriginComponent;
import io.github.simplycmd.even_more_origins.power.GolemTargetPower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolemEntity.class)
public class IronGolemEntityMixin extends GolemEntity {
    protected IronGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("TAIL"), method = "initGoals")
    protected void initGoals(CallbackInfo ci) {
        this.targetSelector.add(3, new FollowTargetGoal(this, PlayerEntity.class, 5, false, false, (livingEntity) ->
                livingEntity instanceof PlayerEntity && OriginComponent.hasPower((PlayerEntity)livingEntity, GolemTargetPower.class)));
    }
}