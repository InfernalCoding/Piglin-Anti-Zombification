package dev.infernal_coding.antizombify;

import net.minecraft.entity.ai.brain.sensor.HoglinMobsSensor;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class Registry {

    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }

    public static final DeferredRegister<Effect> EFFECTS = DeferredRegister.create(ForgeRegistries.POTIONS, AntiZombify.MOD_ID);


    public static final RegistryObject<Effect> ANTI_ZOMBIFY = EFFECTS.register("anti_zombify", () ->
            new ZombificationResistance(EffectType.NEUTRAL, 14762269));


}
