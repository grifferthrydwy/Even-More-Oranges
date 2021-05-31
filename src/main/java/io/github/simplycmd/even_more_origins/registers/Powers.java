package io.github.simplycmd.even_more_origins.registers;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import io.github.simplycmd.even_more_origins.Main;
import io.github.simplycmd.even_more_origins.power.DoubleJumpPower;
import io.github.simplycmd.even_more_origins.power.ModifySizePower;
import io.github.simplycmd.even_more_origins.power.ProjectileImmunityPower;
import io.github.simplycmd.even_more_origins.power.SugarInhalePower;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class Powers {
    private static final Map<PowerFactory<?>, Identifier> POWER_FACTORIES = new LinkedHashMap<>();

    public static final PowerFactory<Power> MODIFY_SIZE = create(new PowerFactory<>(new Identifier(Main.MOD_ID, "modify_size"), new SerializableData().add("scale", SerializableDataType.FLOAT), data -> (type, player) -> new ModifySizePower(type, player, data.getFloat("scale"))).allowCondition());
    public static final PowerFactory<Power> PROJECTILE_IMMUNITY = create(new PowerFactory<>(new Identifier(Main.MOD_ID, "projectile_immunity"), new SerializableData(), data -> (BiFunction<PowerType<Power>, PlayerEntity, Power>) ProjectileImmunityPower::new)).allowCondition();
    public static final PowerFactory<Power> DOUBLE_JUMP = create(new PowerFactory<>(new Identifier(Main.MOD_ID, "double_jump"), new SerializableData(), data -> (BiFunction<PowerType<Power>, PlayerEntity, Power>) DoubleJumpPower::new)).allowCondition();
    public static final PowerFactory<Power> SUGAR_INHALE = create(new PowerFactory<>(new Identifier(Main.MOD_ID, "sugar_inhale"), new SerializableData(), data -> (BiFunction<PowerType<Power>, PlayerEntity, Power>) SugarInhalePower::new)).allowCondition();

    private static <T extends Power> PowerFactory<T> create(PowerFactory<T> factory) {
        POWER_FACTORIES.put(factory, factory.getSerializerId());
        return factory;
    }

    public static void init() {
        POWER_FACTORIES.keySet().forEach(powerType -> Registry.register(ModRegistries.POWER_FACTORY, POWER_FACTORIES.get(powerType), powerType));
    }
}
