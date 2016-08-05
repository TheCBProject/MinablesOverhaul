package teamasm.moh.proxy;

import codechicken.lib.packet.PacketCustom;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import teamasm.moh.MinablesOverhaul;
import teamasm.moh.entity.capabilities.IResearch;
import teamasm.moh.entity.capabilities.ResearchProvider;
import teamasm.moh.entity.capabilities.ResearchStorage;
import teamasm.moh.handler.EventHandler;
import teamasm.moh.init.ModBlocks;
import teamasm.moh.init.ModItems;
import teamasm.moh.init.Recipes;
import teamasm.moh.manager.OreStripManager;
import teamasm.moh.network.PacketDispatcher;
import teamasm.moh.network.ServerPacketHandler;
import teamasm.moh.newnet.PacketResearchSync;

/**
 * Created by covers1624 on 8/4/2016.
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        EventHandler handler = new EventHandler();
        MinecraftForge.EVENT_BUS.register(handler);
        MinecraftForge.TERRAIN_GEN_BUS.register(handler);
        MinecraftForge.ORE_GEN_BUS.register(handler);

        ModBlocks.init();
        ModItems.init();
        Recipes.init();

        CapabilityManager.INSTANCE.register(IResearch.class, new ResearchStorage(), ResearchProvider.DefaultImpl.class);

        registerNetwork();
    }

    public void init(FMLInitializationEvent event) {
        PacketCustom.assignHandler(PacketDispatcher.NET_CHANNEL, new ServerPacketHandler());
    }

    public void postInit(FMLPostInitializationEvent event) {
        OreStripManager.init();
    }

    private void registerNetwork() {
        MinablesOverhaul.network = NetworkRegistry.INSTANCE.newSimpleChannel("MinablesNet");
        MinablesOverhaul.network.registerMessage(PacketResearchSync.Handler.class, PacketResearchSync.class, 0, Side.CLIENT);
    }
}
