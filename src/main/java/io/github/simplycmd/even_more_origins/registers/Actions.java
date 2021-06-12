package io.github.simplycmd.even_more_origins.registers;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.simplycmd.even_more_origins.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Actions {
    public static void init() {
        register(new ActionFactory<>(new Identifier(Main.MOD_ID, "become_bat"), new SerializableData(),
                (data, entity) -> {
                    if (entity.getEntityWorld().getDimension().hasRaids()) {
                        //((PlayerEntity)entity).
                        BatEntity batEntity = new BatEntity(EntityType.BAT, entity.world);
                        batEntity.startRiding(entity);
                        batEntity.setCustomName(new LiteralText("§4§lVampire"));
                        entity.world.spawnEntity(batEntity);
                        ((PlayerEntity)entity).addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 600, 0, false, false, false));
                        ((PlayerEntity)entity).addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 600, -4, false, false, false));
                    }
                }));
    }

    private static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ApoliRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
