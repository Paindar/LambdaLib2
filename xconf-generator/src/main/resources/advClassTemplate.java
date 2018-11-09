package $config.advsPackageName;

import cn.academy.Resources;
import cn.academy.ability.vanilla.VanillaCategories;
import cn.academy.ability.vanilla.electromaster.CatElectromaster;
import cn.academy.ability.vanilla.meltdowner.CatMeltdowner;
import cn.academy.ability.vanilla.teleporter.CatTeleporter;
import cn.academy.ability.vanilla.vecmanip.CatVecManip;
import cn.academy.ability.vanilla.vecmanip.skill.*;
import cn.academy.AcademyCraft;
import cn.academy.advancements.triggers.ACLevelChangeTrigger;
import cn.academy.advancements.triggers.ACTrigger;
import cn.lambdalib2.registry.StateEventCallback;
import net.minecraft.advancements.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

#foreach ($import in $config.additionalImports.all)
import $import;
#end
#foreach ($import in $config.additionalImports.adv)
import $import;
#end

/**
 * Automatically generated by LambdaLib2.xconf in $date.
 */
public class $config.advsClassName {

#foreach ($adv in $advs)
    #if(${adv.id}!="root")
    public static final $adv.baseClass $adv.id = new ${adv.baseClass}("$adv.id");
    #end

#end

    @StateEventCallback
    public static void init(FMLInitializationEvent event) {
        DispatcherAch.init();
    #foreach ($adv in $advs)

        #if(${adv.id}!="root")
        CriteriaTriggers.register(${adv.id})
        #end
    #end
    }

    /**
     * Trigger an achievement
     * @param player The player
     * @param achid The id of the achievement
     * @return true if succeeded
     * This method is server-only. --Paindar
     */
    public static boolean trigger(EntityPlayer player, ResourceLocation achid) {
            if (!(player instanceof EntityPlayerMP))
                return false;

            ICriterionTrigger ach = CriteriaTriggers.get(achid);
            if (ach == null || (!(ach instanceof ACTrigger))) {
                AcademyCraft.log.warn("AC Achievement '" + achid + "' does not exist");
                return false;
            }
            ((ACTrigger)ach).trigger((EntityPlayerMP) player);
            return true;
    }

    public static boolean trigger(EntityPlayer player, String achid) {
            return trigger(player, Resources.res(achid));
    }
}