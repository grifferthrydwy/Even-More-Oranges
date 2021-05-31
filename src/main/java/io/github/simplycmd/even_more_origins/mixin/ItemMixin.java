package io.github.simplycmd.even_more_origins.mixin;

import io.github.apace100.origins.component.OriginComponent;
import io.github.simplycmd.even_more_origins.power.SugarInhalePower;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Item.class)
public class ItemMixin {
    /**
     * @author SimplyCmd
     * @reason Eating sugar
     */
    @Overwrite
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack itemStack = user.getStackInHand(hand);
        if (itemStack.getItem() == Items.SUGAR && user.getHungerManager().isNotFull() && OriginComponent.hasPower(user, SugarInhalePower.class)) {
            itemStack.decrement(1);
            user.getHungerManager().add(1, 1);
            return TypedActionResult.consume(itemStack);
        }
        if (((Item) (Object) this).isFood()) {
            if (user.canConsume(((Item) (Object) this).getFoodComponent().isAlwaysEdible())) {
                user.setCurrentHand(hand);
                return TypedActionResult.consume(itemStack);
            } else {
                return TypedActionResult.fail(itemStack);
            }
        } else {
            return TypedActionResult.pass(user.getStackInHand(hand));
        }
    }
}
