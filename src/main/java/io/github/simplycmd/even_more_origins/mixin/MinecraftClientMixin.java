package io.github.simplycmd.even_more_origins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.simplycmd.even_more_origins.power.InvisibilityPower;
import io.github.simplycmd.even_more_origins.power.SilentPower;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin { // Mixin credit to https://modrinth.com/mod/monsters-in-the-closet
    @Inject(method = "hasOutline", at = @At("RETURN"), cancellable = true)
    private void showOutline(Entity entity, CallbackInfoReturnable<Boolean> info) {
        if (((MinecraftClient) (Object) this).player.isInvisible() && entity instanceof PlayerEntity && PowerHolderComponent.hasPower(((MinecraftClient) (Object)this).player, InvisibilityPower.class)) {
            info.setReturnValue(true);
        }
    }
}
