package dev.infernal_coding.antizombify.mixin;

import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinEntity.class)
public class PiglinEntityMixin {

    @Inject(method = "func_230293_i_", at = @At("RETURN"), cancellable = true)
    private void canPickUp(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() == Items.NETHER_WART) {
            cir.setReturnValue(true);
        }
    }

}
