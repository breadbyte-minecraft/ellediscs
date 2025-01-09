package ellestuff.ellediscs;

import com.mojang.serialization.Codec;
import ellestuff.ellediscs.items.CustomDiscItem;
import ellestuff.ellediscs.items.CustomDyeableItem;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.impl.client.rendering.ColorProviderRegistryImpl;
import net.minecraft.component.ComponentType;
import net.minecraft.item.Item;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

import static ellestuff.ellediscs.items.ElleItems.*;

public class ElleDiscsClient implements ClientModInitializer {
	public static final ComponentType<Integer> RECORD_COLOUR = registerComponent("record_colour", builder -> builder.codec(Codec.INT).packetCodec(PacketCodecs.INTEGER));
	public static final ComponentType<Integer> LABEL_COLOUR = registerComponent("label_colour", builder -> builder.codec(Codec.INT).packetCodec(PacketCodecs.INTEGER));

	@Override
	public void onInitializeClient() {
		registerColouredItem(DISC_RECORD);
		registerColouredItem(DISC_LABEL);

		registerCustomDisc(DYED_MUSIC_DISC);
		registerCustomDisc(DYED_BROKEN_DISC);
		registerCustomDisc(DYED_ECHO_DISC);
	}

	// TODO: ColorProviderRegistry doesn't exist anymore?

	// Makes registering coloured stuff easier.
	public void registerColouredItem(Item item) {
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> ((CustomDyeableItem)stack.getItem()).getColor(stack), item);
	}

	public void registerCustomDisc(Item item) {
		ColorProviderRegistry.ITEM.register((stack, tintIndex) -> {
			Item stackItem = stack.getItem();
			if (tintIndex == 0) {
				return ((CustomDiscItem)stackItem).getRecordColor(stack);
			} else if (tintIndex == 1) {
				return ((CustomDiscItem)stackItem).getLabelColor(stack);
			}
			return 0xFFFFFF; // Fallback color for other tint indices
		}, item);
	}

	private static <T>ComponentType<T> registerComponent(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
		return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(ElleDiscs.MODID, name), builderOperator.apply(ComponentType.builder()).build());
	}
}