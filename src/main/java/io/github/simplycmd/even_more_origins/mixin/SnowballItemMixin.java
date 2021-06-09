package io.github.simplycmd.even_more_origins.mixin;

import io.github.apace100.origins.component.OriginComponent;
import io.github.simplycmd.even_more_origins.power.LightningPower;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SnowballItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(SnowballItem.class)
public class SnowballItemMixin {
    @Inject(at = @At("HEAD"), method = "use(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Hand;)Lnet/minecraft/util/TypedActionResult;", cancellable = true)
    public void use(World world, PlayerEntity user, Hand hand, CallbackInfoReturnable<TypedActionResult<ItemStack>> cir) {
        if (OriginComponent.hasPower(user, LightningPower.class) && user.getStackInHand(hand).getName().asString().matches("Lightning Ball")) {
            ItemStack itemStack = user.getStackInHand(hand);
            world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_SNOWBALL_THROW, SoundCategory.NEUTRAL, 0.5F, 0.4F / (new Random().nextFloat() * 0.4F + 0.8F));
            if (!world.isClient) {
                SnowballEntity snowballEntity = new SnowballEntity(world, user);
                snowballEntity.setItem(itemStack);
                snowballEntity.setProperties(user, user.pitch, user.yaw, 0.0F, 1.5F, 1.0F);
                snowballEntity.setGlowing(true);
                world.spawnEntity(snowballEntity);
            }

            user.incrementStat(Stats.USED.getOrCreateStat(((SnowballItem) (Object) this)));
            if (!user.abilities.creativeMode) {
                itemStack.decrement(1);
            }

            cir.setReturnValue(TypedActionResult.success(itemStack, world.isClient()));
            cir.cancel();
        }
    }
}