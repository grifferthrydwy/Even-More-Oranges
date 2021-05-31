package io.github.simplycmd.even_more_origins.registers;

import io.github.apace100.origins.power.factory.action.ActionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import net.minecraft.entity.Entity;
import net.minecraft.tag.EntityTypeTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.registry.Registry;
import io.github.apace100.origins.Origins;
import io.github.apace100.origins.util.*;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

public class Actions {
    private static final Map<ActionFactory<?>, Identifier> ACTION_FACTORIES = new LinkedHashMap<>();

    private static ActionFactory<Entity> create(ActionFactory<Entity> actionFactory) {
        ACTION_FACTORIES.put(actionFactory, actionFactory.getSerializerId());
        return actionFactory;
    }

    public static void init() {
        ACTION_FACTORIES.keySet().forEach(actionFactory -> {
            register((ActionFactory<Entity>) actionFactory);
        });
    }

    private static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ModRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
