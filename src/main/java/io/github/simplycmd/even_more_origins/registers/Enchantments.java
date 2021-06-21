package io.github.simplycmd.even_more_origins.registers;

import io.github.simplycmd.even_more_origins.VentilationEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Enchantments {
    public static final Enchantment VENTILATION = new VentilationEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.ARMOR);

    public static void init() {
        register("ventilation", VENTILATION);
    }

    private static Enchantment register(String path, Enchantment enchantment) {
        Registry.register(Registry.ENCHANTMENT, new Identifier("even_more_origins", path), enchantment);
        return enchantment;
    }
}