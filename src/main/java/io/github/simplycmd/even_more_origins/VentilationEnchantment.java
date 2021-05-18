package io.github.simplycmd.even_more_origins;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;

public class VentilationEnchantment extends Enchantment {
    public VentilationEnchantment(Rarity weight, EnchantmentTarget type) {
        super(weight, type, new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET});
    }

    public int getMinPower(int level) {
        return 8 + level * 5;
    }

    public int getMaxPower(int level) {
        return this.getMinPower(level) + 8;
    }

    public boolean isTreasure() {
        return true;
    }

    public int getMaxLevel() {
        return 1;
    }
}
