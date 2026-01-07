package com.wurstclient_v7.feature;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

@EventBusSubscriber(modid = "wurst_client_on_neoforge")
public class ShowHealthProcedure {

	@SubscribeEvent
	public static void onEntityTick(EntityTickEvent.Pre event) {
		Entity entity = event.getEntity();
		if (!(entity instanceof LivingEntity living)) return;

		float health = living.getHealth();
		float max = living.getMaxHealth();

		// Format with one decimal place
		String text = String.format("%s : %.1f / %.1f",
				BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType()).toString(),
				health, max);

		living.setCustomName(Component.literal(text));
		living.setCustomNameVisible(true);
	}
}
