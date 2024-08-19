package net.Pandarix.betterarcheology.block.entity;

import net.Pandarix.betterarcheology.block.custom.RadianceTotemBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;

import java.util.List;

public class RadianceTotemBlockEntity extends BlockEntity {
    public RadianceTotemBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.RADIANCE_TOTEM, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, RadianceTotemBlockEntity blockEntity)
    {
        // Apply the effects of the totem every 10 ticks (.5 seconds) to reduce server stress
        if (world.getRandom().nextBetween(1, 10) == 1)
        {
            //get all entities in a 10 block radius
            List<LivingEntity> livingEntities = world.getEntitiesByClass(LivingEntity.class, Box.of(pos.toCenterPos(), 20, 20, 20), EntityPredicates.VALID_LIVING_ENTITY);
            applyGlowingEffect(livingEntities, state);

            // 1/6 chance to hurt all monsters --> 1/6 chance every .5 seconds = damaging every 3 seconds
            if (world.getRandom().nextBetween(1, 6) == 1)
            {
                for (LivingEntity livingEntity : livingEntities)
                {
                    if (livingEntity instanceof HostileEntity monster)
                    {
                        monster.damage(monster.getDamageSources().magic(), 4);
                        world.playSound(null, monster.getBlockPos().getX(), monster.getBlockPos().getY(), monster.getBlockPos().getZ(), SoundEvents.BLOCK_AMETHYST_BLOCK_HIT, SoundCategory.HOSTILE, 0.5f, 0.5f);
                    }
                }
            }
        }
    }

    /**
     * Applies glow effect to all entities that are selected in the selector from the block
     *
     * @param livingEntities List of living entities around the Totem
     * @param state          BlockState of the Totem to get the selector from
     */
    private static void applyGlowingEffect(List<LivingEntity> livingEntities, BlockState state)
    {
        // get selector index which type of Entity should glow, default 0
        int selector = 0;
        if (state.getBlock() instanceof RadianceTotemBlock)
        {
            selector = state.get(RadianceTotemBlock.SELECTOR);
        }

        Class<?> filteredClass = getFilteredEntityClass(selector);
        livingEntities.stream().filter(filteredClass::isInstance)
                .forEach(livingEntity -> livingEntity.addStatusEffect(new StatusEffectInstance(StatusEffects.GLOWING, 200, 0, false, false)));
    }

    /**
     * Gets the corresponding Class of the Entity that is selected by the index set in the Radiance Totem
     *
     * @param selector Index of the Entityfilter
     * @return The Class of the Entities that should glow
     */
    private static Class<?> getFilteredEntityClass(int selector)
    {
        return switch (selector)
        {
            case 1 -> HostileEntity.class;
            case 2 -> AnimalEntity.class;
            case 3 -> PlayerEntity.class;
            default -> LivingEntity.class;
        };
    }
}
