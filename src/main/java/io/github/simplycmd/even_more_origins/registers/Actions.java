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
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.registry.Registry;

import java.util.Iterator;
import java.util.List;

public class Actions {
    public static void init() {
        register(new ActionFactory<>(new Identifier(Main.MOD_ID, "become_bat"), new SerializableData(),
                (data, entity) -> {
                    BatEntity batEntity = new BatEntity(EntityType.BAT, entity.world);
                    batEntity.startRiding(entity);
                    batEntity.setCustomName(new LiteralText("§4§lVampire"));
                    entity.world.spawnEntity(batEntity);
                    ((PlayerEntity)entity).addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 600, 0, false, false, false));
                    ((PlayerEntity)entity).addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 600, -4, false, false, false));
                })
        );
        register(new ActionFactory<>(new Identifier(Main.MOD_ID, "flip"), new SerializableData(),
                (data, entity) -> {
                    int radius = 10;
                    for (Entity entity1 : entity.world.getOtherEntities(entity, new Box(entity.getX() - radius, entity.getY() - radius, entity.getZ() - radius, entity.getX() + radius, entity.getY() + radius, entity.getZ() + radius))) {
                        entity1.setBodyYaw(-entity1.getYaw());
                    }
                })
        );
        register(new ActionFactory<>(new Identifier(Main.MOD_ID, "parrot"), new SerializableData(),
                (data, entity) -> {
                    ParrotEntity parrot = new ParrotEntity(EntityType.PARROT, entity.world);
                    parrot.updatePosition(entity.getX(), entity.getY(), entity.getZ());
                    parrot.setVariant(parrot.getRandom().nextInt(5));
                    parrot.setCustomName(new LiteralText("Pesky Bird"));
                    entity.world.spawnEntity(parrot);
                })
        );
    }

    private static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ApoliRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
