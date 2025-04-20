package dev.infernal_coding.antizombify.mixin;

import com.google.common.collect.Lists;
import dev.infernal_coding.antizombify.Registry;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.memory.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.HoglinMobsSensor;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(HoglinMobsSensor.class)
public abstract class HoglinMobsSensorMixin {


    /**
     * @author Infernal_Coding
     * @reason To make hoglins not fear nether portals if they have anti-zombification active
     */
    @Inject(method = "update(Lnet/minecraft/world/server/ServerWorld;Lnet/minecraft/entity/monster/HoglinEntity;)V", at = @At("HEAD"), cancellable = true)
    protected void update(ServerWorld worldIn, HoglinEntity entityIn, CallbackInfo ci) {
        ci.cancel();
        Brain<?> brain = entityIn.getBrain();
        if (entityIn.getActivePotionEffect(Registry.ANTI_ZOMBIFY.get()) != null || entityIn.func_234368_eV_()) {
        } else brain.setMemory(MemoryModuleType.NEAREST_REPELLENT, this.findNearestRepellent(worldIn, entityIn));
        Optional<PiglinEntity> optional = Optional.empty();
        int i = 0;
        List<HoglinEntity> list = Lists.newArrayList();

        for(LivingEntity livingentity : brain.getMemory(MemoryModuleType.VISIBLE_MOBS).orElse(Lists.newArrayList())) {
            if (livingentity instanceof PiglinEntity && !livingentity.isChild()) {
                ++i;
                if (!optional.isPresent()) {
                    optional = Optional.of((PiglinEntity)livingentity);
                }
            }

            if (livingentity instanceof HoglinEntity && !livingentity.isChild()) {
                list.add((HoglinEntity)livingentity);
            }
        }

        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLIN, optional);
        brain.setMemory(MemoryModuleType.NEAREST_VISIBLE_ADULT_HOGLINS, list);
        brain.setMemory(MemoryModuleType.VISIBLE_ADULT_PIGLIN_COUNT, i);
        brain.setMemory(MemoryModuleType.VISIBLE_ADULT_HOGLIN_COUNT, list.size());
    }

    @Unique
    private Optional<BlockPos> findNearestRepellent(ServerWorld world, HoglinEntity hoglin) {
        return BlockPos.getClosestMatchingPosition(hoglin.getPosition(), 8, 4, (pos) -> world.getBlockState(pos).isIn(BlockTags.HOGLIN_REPELLENTS));
    }
}
