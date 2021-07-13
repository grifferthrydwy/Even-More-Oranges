package io.github.simplycmd.even_more_origins.registers;

import java.util.function.BiFunction;
import java.util.function.Function;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.apoli.util.HudRender;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.simplycmd.even_more_origins.Main;
import io.github.simplycmd.even_more_origins.power.DoubleHealthPower;
import io.github.simplycmd.even_more_origins.power.DoubleJumpPower;
import io.github.simplycmd.even_more_origins.power.FlapPower;
import io.github.simplycmd.even_more_origins.power.GolemTargetPower;
import io.github.simplycmd.even_more_origins.power.HammerPower;
import io.github.simplycmd.even_more_origins.power.InvisibilityPower;
import io.github.simplycmd.even_more_origins.power.LifeLeachPower;
import io.github.simplycmd.even_more_origins.power.LightningPower;
import io.github.simplycmd.even_more_origins.power.ModifySizePower;
import io.github.simplycmd.even_more_origins.power.ProjectileImmunityPower;
import io.github.simplycmd.even_more_origins.power.SilentPower;
import io.github.simplycmd.even_more_origins.power.SugarInhalePower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Powers {
    private static void register(PowerFactory<?> serializer) {
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
        registerBlank("hammer", data -> HammerPower::new);
        registerBlank("flap", data -> FlapPower::new);

        register(new PowerFactory<>(new Identifier(Main.MOD_ID, "double_health"),
                new SerializableData(), data -> (BiFunction<PowerType<Power>, LivingEntity, Power>) DoubleHealthPower::new));

        register(new PowerFactory<>(new Identifier(Main.MOD_ID, "modify_size"),
                new SerializableData().add("scale", SerializableDataTypes.FLOAT),
                data ->
                        (type, player) -> new ModifySizePower(type, player, data.getFloat("scale")))
                .allowCondition());

        register(new PowerFactory<>(new Identifier(Main.MOD_ID, "life_leach"),
                new SerializableData()
                    .add("strength", SerializableDataTypes.FLOAT),
                data ->
                    (type, player) -> new LifeLeachPower(type, player, data.getFloat("strength")))
                .allowCondition());
    }

    public static void registerBlank(String name, Function<SerializableData.Instance, BiFunction<PowerType<Power>, LivingEntity, Power>> factory) {
        register(new PowerFactory<>(new Identifier(Main.MOD_ID, name), new SerializableData(), factory).allowCondition());
    }
}
