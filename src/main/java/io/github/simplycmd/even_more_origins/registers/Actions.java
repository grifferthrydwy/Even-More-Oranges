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
    
    private static final ActionFactory<Entity> ENTITIES_IN_RADIUS = create(new ActionFactory<>(Origins.identifier("entities_in_radius"), new SerializableData()
            .add("action", SerializableDataType.ENTITY_ACTION, null)
            .add("tag", SerializableDataType.ENTITY_TAG, null)
            .add("radius", SerializableDataType.INT, null),
            (data, entity) -> {
                System.out.println("test");
                Iterator<Entity> entities = entity.world.getOtherEntities(entity, new Box(
                        entity.getPos().x - (Integer)data.get("radius"),
                        entity.getPos().y - (Integer)data.get("radius"),
                        entity.getPos().z - (Integer)data.get("radius"),
                        entity.getPos().x + (Integer)data.get("radius"),
                        entity.getPos().y + (Integer)data.get("radius"),
                        entity.getPos().z + (Integer)data.get("radius")
                )).iterator();
                while (entities.hasNext()) {
                    Entity target_entity = entities.next();
                    if (target_entity.getType().isIn(EntityTypeTags.SKELETONS)) ((ActionFactory<Entity>.Instance)data.get("action")).accept(target_entity);
                }
            }));

    private static ActionFactory<Entity> create(ActionFactory<Entity> actionFactory) {
        ACTION_FACTORIES.put(actionFactory, actionFactory.getSerializerId());
        return actionFactory;
    }

    public static void init() {
        ACTION_FACTORIES.keySet().forEach(actionFactory -> {
            register((ActionFactory<Entity>) actionFactory);
            System.out.println("redigisjoasd");
        });
    }

    private static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ModRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
