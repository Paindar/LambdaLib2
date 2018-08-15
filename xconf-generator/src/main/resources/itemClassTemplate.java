package $config.itemsPackageName;

import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.model.ModelResourceLoader;

import net.minecraftforge.fml.common;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.client.model.ModelLoader;

import cn.lambdalib2.registry.StateEventCallback;

/**
 * Automatically generated by LambdaLib2.xconf in $date.
 */
public class $config.itemsClassName {

#foreach ($item in $items)
    public static final $item.baseClass $item.id = new ${item.baseClass}($item.ctorArgs);
#end

    @RegistryCallback
    private static void registerItems(RegistryEvent.Register<Item> event) {
    #foreach ($item in $items)
        #set($id = $item.id)
        ${id}.setRegistryName("${config.locPrefix}$id");
        ${id}.setUnlocalizedName("$config.domain:$id");
        event.getRegistry().register($id);
    #end

        if (FMLCommonHandler.INSTANCE.getSide() == Side.CLIENT) {
            registerItemRenderers();
        }
    }

    @SideOnly(Side.CLIENT)
    private static void registerItemRenderers() {
    #foreach ($item in $items)
        ModelLoader.setCustomModelResourceLocation($item.id, 0, new ModelResourceLocation(${item.id}.getRegistryName(), "inventory"));
    #end
    }

}