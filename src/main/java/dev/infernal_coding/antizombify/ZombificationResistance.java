package dev.infernal_coding.antizombify;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.world.DimensionType;

public class ZombificationResistance extends Effect {
    public ZombificationResistance(EffectType p_i50391_1_, int p_i50391_2_) {
        super(p_i50391_1_, p_i50391_2_);
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        if (entityLivingBaseIn instanceof AbstractPiglinEntity) {
            AbstractPiglinEntity piglin = (AbstractPiglinEntity) entityLivingBaseIn;
            piglin.field_242334_c--;
            if (piglin.field_242334_c < 0) {
                piglin.field_242334_c = 0;
            }
        } else if (entityLivingBaseIn instanceof HoglinEntity) {
            HoglinEntity hoglin = (HoglinEntity) entityLivingBaseIn;
            hoglin.field_234358_by_--;
            if (hoglin.field_234358_by_ < 0) {
                hoglin.field_234358_by_ = 0;
            }
        }
    }
}
