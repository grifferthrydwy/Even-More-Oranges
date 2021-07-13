package io.github.simplycmd.even_more_origins.registers;

import java.util.ArrayList;
import java.util.Random;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.simplycmd.even_more_origins.Main;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.FoxEntity;
import net.minecraft.entity.passive.ParrotEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class Actions {
    private static final Random RANDOM = new Random();

    public static void init() {
        register(new ActionFactory<>(new Identifier(Main.MOD_ID, "become_bat"), new SerializableData(),
                (data, entity) -> {
                    BatEntity batEntity = new BatEntity(EntityType.BAT, entity.world);
                    batEntity.startRiding(entity);
                    batEntity.setCustomName(new LiteralText("§4§lVampire"));
                    entity.world.spawnEntity(batEntity);
                    ((PlayerEntity)entity).addStatusEffect(new StatusEffectInstance(StatusEffects.INVISIBILITY, 600, 0, false, false, false));
                    ((PlayerEntity)entity).addStatusEffect(new StatusEffectInstance(StatusEffects.HEALTH_BOOST, 600, -4, false, false, false));
                })
        );
        register(new ActionFactory<>(new Identifier(Main.MOD_ID, "become_geist"), new SerializableData(),
                (data, entity) -> {
                    ((PlayerEntity)entity).addStatusEffect(new StatusEffectInstance(StatusEffects.LUCK, 600, 0, false, false, false));
                })
        );
        register(new ActionFactory<>(new Identifier(Main.MOD_ID, "flip"), new SerializableData(),
                (data, entity) -> {
                    int radius = 10;
                    for (Entity entity1 : entity.world.getOtherEntities(entity, new Box(entity.getX() - radius, entity.getY() - radius, entity.getZ() - radius, entity.getX() + radius, entity.getY() + radius, entity.getZ() + radius))) {
                        entity1.setBodyYaw(-entity1.getYaw());
                    }
                })
        );
        register(new ActionFactory<>(new Identifier(Main.MOD_ID, "parrot"), new SerializableData(),
                (data, entity) -> {
                    ParrotEntity parrot = new ParrotEntity(EntityType.PARROT, entity.world);
                    parrot.updatePosition(entity.getX(), entity.getY(), entity.getZ());
                    parrot.setVariant(parrot.getRandom().nextInt(5));
                    parrot.setCustomName(new LiteralText("Pesky Bird"));
                    entity.world.spawnEntity(parrot);
                })
        );
        register(new ActionFactory<>(new Identifier(Main.MOD_ID, "flower"), new SerializableData(),
                (data, entity) -> {
                    ArrayList<Item> items = new ArrayList<>();
                    items.add(Items.DANDELION);
                    items.add(Items.POPPY);
                    items.add(Items.BLUE_ORCHID);
                    items.add(Items.ALLIUM);
                    items.add(Items.AZURE_BLUET);
                    items.add(Items.ORANGE_TULIP);
                    items.add(Items.PINK_TULIP);
                    items.add(Items.RED_TULIP);
                    items.add(Items.WHITE_TULIP);
                    items.add(Items.OXEYE_DAISY);
                    items.add(Items.CORNFLOWER);
                    items.add(Items.LILY_OF_THE_VALLEY);
                    ItemEntity flower = new ItemEntity(entity.getEntityWorld(), entity.getX(), entity.getY(), entity.getZ(), items.get(RANDOM.nextInt(items.size())).getDefaultStack());
                    entity.world.spawnEntity(flower);
                })
        );
        register(new ActionFactory<>(new Identifier(Main.MOD_ID, "chorus"), new SerializableData(),
                (data, entity) -> {
                    if (!entity.world.isClient && entity instanceof LivingEntity) {
                        World world = entity.world;
                        LivingEntity user = (LivingEntity) entity;
                        double d = user.getX();
                        double e = user.getY();
                        double f = user.getZ();

                        for(int i = 0; i < 16; ++i) {
                            double g = user.getX() + (user.getRandom().nextDouble() - 0.5D) * 16.0D;
                            double h = MathHelper.clamp(user.getY() + (double)(user.getRandom().nextInt(16) - 8), (double)world.getBottomY(), (double)(world.getBottomY() + ((ServerWorld)world).getLogicalHeight() - 1));
                            double j = user.getZ() + (user.getRandom().nextDouble() - 0.5D) * 16.0D;
                            
                            if (user.hasVehicle()) {
                                user.stopRiding();
                            }

                            if (user.teleport(g, h, j, true)) {
                                SoundEvent soundEvent = user instanceof FoxEntity ? SoundEvents.ENTITY_FOX_TELEPORT : SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT;
                                world.playSound((PlayerEntity)null, d, e, f, soundEvent, SoundCategory.PLAYERS, 1.0F, 1.0F);
                                user.playSound(soundEvent, 1.0F, 1.0F);
                                break;
                            }
                        }
                    }
                })
        );
    }

    private static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ApoliRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
