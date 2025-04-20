package dev.infernal_coding.antizombify.mixin;

import dev.infernal_coding.antizombify.Registry;
import net.minecraft.entity.monster.piglin.AbstractPiglinEntity;
import net.minecraft.entity.monster.piglin.PiglinEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.EffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractPiglinEntity.class)
public class AbstractPiglinEntityMixin {

    private AbstractPiglinEntity piglinBase = (AbstractPiglinEntity) (Object) this;

    @Inject(method = "updateAITasks", at = @At("HEAD"))
    protected void $updateAITasks(CallbackInfo ci) {
        if (piglinBase instanceof PiglinEntity) {
            PiglinEntity piglin = (PiglinEntity) piglinBase;
            if (piglin.field_242334_c > 50 && piglin.inventory.count(Items.NETHER_WART) > 0) {
                piglin.addPotionEffect(new EffectInstance(Registry.ANTI_ZOMBIFY.get(), 9600));
                for (int j = 0; j < piglin.inventory.getSizeInventory(); ++j) {
                    ItemStack itemstack = piglin.inventory.getStackInSlot(j);
                    if (itemstack.getItem().equals(Items.NETHER_WART)) {
                        itemstack.shrink(1);
                        break;
                    }
                }
            }
        }
    }
}
