package $config.blocksPackageName;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.client.renderer.block.model.ModelResourceLoader;

import net.minecraftforge.fml.common;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.client.model.ModelLoader;

import cn.lambdalib2.registry.StateEventCallback;

/**
 * Automatically generated by LambdaLib2.xconf in $date.
 */
public class $config.blocksClassName {

#foreach ($block in $blocks)
    public static final $block.baseClass $block.id = new ${block.baseClass}($block.ctorArgs);
#end

#foreach ($block in $blocksWithItemBlock)
    public static final ItemBlock item_$block.id = new ItemBlock($block.id);
#end

    @RegistryCallback
    private static void registerBlocks(RegistryEvent.Register<Block> event) {
    #foreach ($block in $blocks)
        #set($id = $block.id)
        ${id}.setRegistryName("${config.locPrefix}$id");
        ${id}.setUnlocalizedName("$config.domain:$id");
        event.getRegistry().register($id);
    #end
    }

    @RegistryCallback
    private static void registerItems(RegistryEvent.Register<Item> event) {
    #foreach ($block in $blocksWithItemBlock)
        #set($id = "item_$block.id")
        ${id}.setRegistryName("$config.domain:$block.id");
        ${id}.setUnlocalizedName("$config.domain:$block.id");
        event.getRegistry().register($id);
    #end

        if (FMLCommonHandler.INSTANCE.getSide() == Side.CLIENT) {
            registerItemRenderers();
        }
    }

    @SideOnly(Side.CLIENT)
    private static void registerItemRenderers() {
    #foreach ($block in $blocksWithItemBlock)
        ModelLoader.setCustomModelResourceLocation(item_${block.id}, 0, new ModelResourceLocation(${block.id}.getRegistryName(), "inventory"));
    #end
    }

}