package io.github.simplycmd.even_more_origins.registers;

import io.github.apace100.apoli.Apoli;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.ShaderPower;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataType;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.simplycmd.even_more_origins.Main;
import io.github.simplycmd.even_more_origins.power.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public class Powers {
    private static void register(PowerFactory serializer) {
        Registry.register(ApoliRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
    }

    public static void init() {
        registerBlank("projectile_immunity", data -> ProjectileImmunityPower::new);
        registerBlank("double_jump", data -> DoubleJumpPower::new);
        registerBlank("sugar_inhale", data -> SugarInhalePower::new);
        registerBlank("golem_target", data -> GolemTargetPower::new);
        registerBlank("lightning", data -> LightningPower::new);
        registerBlank("silent", data -> SilentPower::new);
        registerBlank("invisibility_glow", data -> InvisibilityPower::new);

        register(new PowerFactory<>(new Identifier(Main.MOD_ID, "modify_size"),
                new SerializableData().add("scale", SerializableDataTypes.FLOAT),
                data ->
                        (type, player) -> new ModifySizePower(type, player, data.getFloat("scale")))
                .allowCondition());
    }

    public static void registerBlank(String name, Function<SerializableData.Instance, BiFunction<PowerType<Power>, LivingEntity, Power>> factory) {
        register(new PowerFactory<>(new Identifier(Main.MOD_ID, "modify_size"), new SerializableData(), factory).allowCondition());
    }
}
