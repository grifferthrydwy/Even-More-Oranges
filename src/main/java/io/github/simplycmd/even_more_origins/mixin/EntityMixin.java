package io.github.simplycmd.even_more_origins.mixin;

import io.github.apace100.origins.component.OriginComponent;
import io.github.simplycmd.even_more_origins.power.SilentPower;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(Entity.class)
public class EntityMixin {
    /**
     * @author SimplyCmd
     * @reason a
     */
    @Overwrite
    public void playStepSound(BlockPos pos, BlockState state) {
        if (!OriginComponent.hasPower((Entity) (Object)this, SilentPower.class) && !state.getMaterial().isLiquid()) {
            BlockState blockState = ((Entity) (Object) this).world.getBlockState(pos.up());
            BlockSoundGroup blockSoundGroup = blockState.isOf(Blocks.SNOW) ? blockState.getSoundGroup() : state.getSoundGroup();
            ((Entity) (Object) this).playSound(blockSoundGroup.getStepSound(), blockSoundGroup.getVolume() * 0.15F, blockSoundGroup.getPitch());
        }
    }
}
