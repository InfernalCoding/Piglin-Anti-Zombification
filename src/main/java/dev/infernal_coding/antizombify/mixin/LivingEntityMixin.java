package dev.infernal_coding.antizombify.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinBruteEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    private LivingEntity entity = (LivingEntity) (Object) this;

    @Inject(method = "triggerItemPickupTrigger", at = @At("TAIL"))
    private void $triggerItemPickupTrigger(ItemEntity item, CallbackInfo ci) {
        ItemStack stack = item.getItem();
        if (entity instanceof PiglinEntity) {
            PiglinEntity piglin = (PiglinEntity) entity;
            if (stack.getItem() == Items.NETHER_WART) {
                piglin.onItemPickup(item, stack.getCount());
                piglin.inventory.addItem(stack);
                item.remove();
            }
        }
    }

}
