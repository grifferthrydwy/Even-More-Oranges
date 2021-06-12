package io.github.simplycmd.even_more_origins;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.simplycmd.even_more_origins.power.HammerPower;
import io.github.simplycmd.even_more_origins.power.SilentPower;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.enchantment.LoyaltyEnchantment;
import net.minecraft.enchantment.RiptideEnchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Map;

public class HammerItem extends TridentItem {
    public HammerItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        user.setCurrentHand(hand);
        return TypedActionResult.consume(itemStack);
    }

    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        System.out.println(remainingUseTicks);
        stack.removeSubTag("Enchantments");
        stack.removeSubTag("StoredEnchantments");
        stack.addEnchantment(Enchantments.LOYALTY, 5);
        stack.addEnchantment(Enchantments.IMPALING, 5);
        if (remainingUseTicks < 71990) stack.addEnchantment(Enchantments.RIPTIDE, 2);
        if (remainingUseTicks < 71995) stack.addEnchantment(Enchantments.CHANNELING, 0);

        if (user instanceof PlayerEntity && !world.isClient) {
            PlayerEntity playerEntity = (PlayerEntity)user;
            int i = this.getMaxUseTime(stack) - remainingUseTicks;
            if (i >= 10) {
                float f = playerEntity.getYaw();
                float g = playerEntity.getPitch();
                float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                float k = -MathHelper.sin(g * 0.017453292F);
                float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                float m = MathHelper.sqrt(h * h + k * k + l * l);
                float n = 3.0F * ((1.0F + (float) 3) / 4.0F);
                h *= n / m;
                k *= n / m;
                l *= n / m;
                playerEntity.addVelocity((double) h, (double) k, (double) l);
                playerEntity.setRiptideTicks(20);
                if (playerEntity.isOnGround()) {
                    float o = 1.1999999F;
                    playerEntity.move(MovementType.SELF, new Vec3d(0.0D, 1.1999999284744263D, 0.0D));
                }

                SoundEvent soundEvent3;
                if (3 >= 3) {
                    soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
                } else if (3 == 2) {
                    soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
                } else {
                    soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
                }

                world.playSoundFromEntity((PlayerEntity) null, playerEntity, soundEvent3, SoundCategory.PLAYERS, 1.0F, 1.0F);
            } else if (i >= 5) {
                stack.addEnchantment(Enchantments.CHANNELING, 0);
            } else {
                TridentEntity tridentEntity = new TridentEntity(world, playerEntity, stack);
                tridentEntity.setProperties(playerEntity, playerEntity.getPitch(), playerEntity.getYaw(), 0.0F, 2.5F + (float)3 * 0.5F, 1.0F);
                if (playerEntity.getAbilities().creativeMode) {
                    tridentEntity.pickupType = PersistentProjectileEntity.PickupPermission.CREATIVE_ONLY;
                }

                world.spawnEntity(tridentEntity);
                world.playSoundFromEntity(null, tridentEntity, SoundEvents.ITEM_TRIDENT_THROW, SoundCategory.PLAYERS, 1.0F, 1.0F);
                if (!playerEntity.getAbilities().creativeMode) {
                    playerEntity.getInventory().removeOne(stack);
                }
            }
            playerEntity.incrementStat(Stats.USED.getOrCreateStat(this));
        }
    }
}
