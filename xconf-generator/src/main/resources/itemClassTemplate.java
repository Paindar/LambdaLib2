package $config.itemsPackageName;

import net.minecraft.item.Item;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.client.model.ModelLoader;

import cn.lambdalib2.registry.StateEventCallback;
import cn.lambdalib2.registry.RegistryCallback;

#foreach ($import in $config.additionalImports.all)
import $import;
#end
#foreach ($import in $config.additionalImports.item)
import $import;
#end

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
        ${id}.setRegistryName("$config.domain:$id");
        ${id}.setUnlocalizedName("${config.locPrefix}$id");
    #if($item.creativeTab)
        ${id}.setCreativeTab($item.creativeTab);
    #end
        #if ($item.maxStackSize)
        ${id}.setMaxStackSize($item.maxStackSize);
        #end
        #if ($item.maxDamage)
        ${id}.setMaxDamage($item.maxDamage);
        #end
        #if($item.init)
        #foreach($stmt in $item.init)
        ${id}.$stmt;
        #end
        #end
        event.getRegistry().register($id);
    #end

        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            registerItemRenderers();
        }
    }

    @SideOnly(Side.CLIENT)
    private static void registerItemRenderers() {
    #foreach ($item in $items)
    #foreach ($pair in $item.modelBindings.entrySet())
        ModelLoader.setCustomModelResourceLocation($item.id, $pair.key, new ModelResourceLocation("$pair.value", "inventory"));
    #end
    #end
    }

}