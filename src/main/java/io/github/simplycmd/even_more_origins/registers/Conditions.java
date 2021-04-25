package io.github.simplycmd.even_more_origins.registers;

import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;

public class Conditions {
    private static final Map<ConditionFactory<LivingEntity>, Identifier> ENTITY_CONDITIONS = new LinkedHashMap<>();

    private static ConditionFactory<LivingEntity> createEntityCondition(ConditionFactory<LivingEntity> factory) {
        ENTITY_CONDITIONS.put(factory, factory.getSerializerId());
        return factory;
    }

    public static void init() {
        ENTITY_CONDITIONS.keySet().forEach(condition -> Registry.register(ModRegistries.ENTITY_CONDITION, ENTITY_CONDITIONS.get(condition), condition));
    }
}
