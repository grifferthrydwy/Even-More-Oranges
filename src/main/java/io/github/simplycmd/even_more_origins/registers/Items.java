package io.github.simplycmd.even_more_origins.registers;

import io.github.simplycmd.even_more_origins.HammerItem;
import io.github.simplycmd.even_more_origins.Main;
import io.github.simplycmd.even_more_origins.VentilationEnchantment;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Items {

    public static void init() {
        Registry.register(Registry.ITEM, new Identifier(Main.MOD_ID, "hammer"), new HammerItem(new FabricItemSettings().maxDamage(0).group(ItemGroup.COMBAT)));
    }
}
