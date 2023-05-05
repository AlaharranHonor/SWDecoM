package com.alaharranhonor.swdm.mixin;

import com.alaharranhonor.swdm.registry.ItemSetup;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.HitResult;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ForgeHooks.class)
public class ForgeHooksMixin {

    //@Redirect(method = "onPickBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getPickedResult(Lnet/minecraft/world/phys/HitResult;)Lnet/minecraft/world/item/ItemStack;"))
    private static ItemStack onPickBlock(Entity instance, HitResult hitResult) {
        // TODO only do it if the nbt tag says its a mirror painting
        if (instance instanceof Painting) {
            return new ItemStack(ItemSetup.MIRROR_PAINTING.get());
        }

        return instance.getPickedResult(hitResult);
    }

    // TODO copy the above method for dropItem method (Painting.class Mixin)

}
