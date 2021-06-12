package io.github.simplycmd.even_more_origins.registers;


import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.simplycmd.even_more_origins.Main;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Predicate;

public class Conditions {
    private static final Map<ConditionFactory<LivingEntity>, Identifier> ENTITY_CONDITIONS = new LinkedHashMap<>();

    public static final ConditionFactory<LivingEntity> ENTITY_IN_RADIUS = createEntityCondition(
            new ConditionFactory<>(
                    new Identifier(Main.MOD_ID, "entity_in_radius"), new SerializableData(),
                    (instance, playerEntity) -> {
                        playerEntity.world.getOtherEntities(playerEntity, new Box(
                                playerEntity.getPos().x - 5,
                                playerEntity.getPos().y - 5,
                                playerEntity.getPos().z - 5,
                                playerEntity.getPos().x + 5,
                                playerEntity.getPos().y + 5,
                                playerEntity.getPos().z + 5
                        ));
                        return playerEntity.world.getDimension().isPiglinSafe();
                    }));


    private static ConditionFactory<LivingEntity> createEntityCondition(ConditionFactory<LivingEntity> factory) {
        ENTITY_CONDITIONS.put(factory, factory.getSerializerId());
        return factory;
    }

    public static void init() {
        ENTITY_CONDITIONS.keySet().forEach(condition -> Registry.register(ApoliRegistries.ENTITY_CONDITION, ENTITY_CONDITIONS.get(condition), condition));
    }
}
