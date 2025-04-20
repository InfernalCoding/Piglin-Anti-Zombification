package dev.infernal_coding.antizombify.mixin;

import dev.infernal_coding.antizombify.Registry;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.monster.HoglinEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(MobEntity.class)
public class MobEntityMixin {

    @Unique
    private static Random random = new Random();
    private MobEntity mob = (MobEntity) (Object) this;

    @Inject(method = "getEntityInteractionResult", at = @At("HEAD"), cancellable = true)
    protected void $getInteractionResult(PlayerEntity playerIn, Hand hand, CallbackInfoReturnable<ActionResultType> ci) {
        World world = playerIn.world;
        if ((mob instanceof AbstractPiglinEntity || mob instanceof HoglinEntity) && !world.isRemote) {
            if (playerIn.getHeldItemMainhand().getItem() == Items.NETHER_WART) {
                mob.addPotionEffect(new EffectInstance(Registry.ANTI_ZOMBIFY.get(), 9600));

                world.setEntityState(mob, (byte) 16);
                playerIn.getHeldItemMainhand().setCount(playerIn.getHeldItemMainhand().getCount() - 1);
                ci.cancel();
                ci.setReturnValue(ActionResultType.SUCCESS);
            }
        }
    }
}
