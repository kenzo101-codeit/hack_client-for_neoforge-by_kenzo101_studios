package com.wurstclient_v7.mixin;

import net.minecraft.resources.ResourceLocation;

/**
 * This interface lets us access our new methods without referencing the Mixin directly.
 * Think of it as a "contract" that says these methods exist on GameRenderer.
 */
public interface GameRendererAccessor {
	void wurst$loadEffectPublic(ResourceLocation resourceLocation);
	boolean wurst$isEffectActive();
	void wurst$disableEffect();
}