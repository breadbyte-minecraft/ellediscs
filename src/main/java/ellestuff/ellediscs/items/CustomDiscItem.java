package ellestuff.ellediscs.items;

import ellestuff.ellediscs.ElleDiscsClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.component.ComponentMap;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CustomDiscItem extends Item {
    int DEFAULT_RECORD_COLOR;
    int DEFAULT_LABEL_COLOR;

    public CustomDiscItem(Item.Settings settings, int record_colour, int label_colour) {
        super(15, SoundEvents.INTENTIONALLY_EMPTY, settings, 1);
        DEFAULT_RECORD_COLOR = record_colour;
        DEFAULT_LABEL_COLOR = label_colour;
    }

    public int getRecordColor(ItemStack stack) {
        Integer component = stack.get(ElleDiscsClient.RECORD_COLOUR);
        return component != null ? component : DEFAULT_RECORD_COLOR;
    }

    public int getLabelColor(ItemStack stack) {
        Integer component = stack.get(ElleDiscsClient.LABEL_COLOUR);
        return component != null ? component : DEFAULT_LABEL_COLOR;
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("item.ellediscs.discs.tip").formatted(Formatting.GRAY));

        if (type.isAdvanced()) {
            String record_hex = Integer.toHexString(this.getRecordColor(stack)).toUpperCase();
            String label_hex = Integer.toHexString(this.getLabelColor(stack)).toUpperCase();

            MutableText colour_tooltip = Text.translatable("item.ellediscs.discs.colour_tooltip", record_hex, label_hex);
            tooltip.add(colour_tooltip.formatted(Formatting.GRAY));
        }
    }


    // TODO: Convert the from NBT to components
    // Check if the item has a custom sound by checking if the "CustomSound" component exists.
    // If it does, use that translation key, else, use ours.

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable("item.ellediscs.music_disc");
        return super.getName(stack);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        ComponentMap components = stack.getComponents();
        if (nbtCompound != null && nbtCompound.contains("CustomSound")) { return "item.ellediscs.music_disc"; }
        return super.getTranslationKey(stack);
    }
}
