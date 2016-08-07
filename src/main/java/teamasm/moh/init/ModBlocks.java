package teamasm.moh.init;

import codechicken.lib.block.ItemBlockMultiType;
import codechicken.lib.render.ModelRegistryHelper;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import teamasm.moh.block.BlockDebug;
import teamasm.moh.block.BlockMachine;
import teamasm.moh.block.BlockOre;
import teamasm.moh.client.model.tile.*;
import teamasm.moh.client.render.item.*;
import teamasm.moh.client.render.tile.*;
import teamasm.moh.reference.Reference;
import teamasm.moh.tile.TileDebug;
import teamasm.moh.tile.machines.hand.TileCentrifugeManual;
import teamasm.moh.tile.machines.hand.TileCrusherManual;
import teamasm.moh.tile.machines.hand.TileScreenManual;
import teamasm.moh.tile.machines.teir1.TileCentrifuge;
import teamasm.moh.tile.machines.teir1.TileCrusher;
import teamasm.moh.tile.machines.teir1.TileScreenCoarse;
import teamasm.moh.tile.machines.teir1.TileScreenFine;
import teamasm.moh.tile.machines.tier2.TileGrinder;

import static teamasm.moh.reference.VariantReference.machinesList;

/**
 * Created by brandon3055 on 4/08/2016.
 */
public class ModBlocks {

    public static BlockOre blockOre;
    public static BlockMachine blockMachine;

    public static Block testBlock;

    public static void init() {

        blockOre = new BlockOre();
        blockOre.setUnlocalizedName(Reference.MOD_PREFIX + "blockOre");
        GameRegistry.register(blockOre.setRegistryName("blockOre"));
        GameRegistry.register(new ItemBlock(blockOre).setRegistryName("blockOre"));

        blockMachine = new BlockMachine();
        GameRegistry.register(blockMachine.setRegistryName("machine"));
        GameRegistry.register(new ItemBlockMultiType(blockMachine).setRegistryName("machine"));

        blockMachine.registerSubItemAndTile(0, "crusher", TileCrusher.class);
        blockMachine.registerSubItemAndTile(1, "grinder", TileGrinder.class);
        blockMachine.registerSubItemAndTile(2, "screenCoarse", TileScreenCoarse.class);
        blockMachine.registerSubItemAndTile(3, "screenFine", TileScreenFine.class);
        //blockMachine.registerSubItemAndTile(4, "separatorMagnetic", TileSeparatorMagnetic.class);todo these are just disabled untill i get around to implementing them
        blockMachine.registerSubItemAndTile(5, "centrifuge", TileCentrifuge.class);
        //blockMachine.registerSubItemAndTile(6, "separatorFlotation", TileSeparatorFlotation.class);
        //blockMachine.registerSubItemAndTile(7, "separatorElectrostatic", TileSeparatorElectrostatic.class);
        //blockMachine.registerSubItemAndTile(8, "dryerRotary", TileDryerRotary.class);
        blockMachine.registerSubItemAndTile(9, "crusherManual", TileCrusherManual.class);
        blockMachine.registerSubItemAndTile(10, "screenManual", TileScreenManual.class);
        blockMachine.registerSubItemAndTile(11, "centrifugeManual", TileCentrifugeManual.class);

        testBlock = new BlockDebug();
        testBlock.setRegistryName("testBlock");
        GameRegistry.register(testBlock);
        GameRegistry.register(new ItemBlock(testBlock).setRegistryName("testBlock"));
        GameRegistry.registerTileEntity(TileDebug.class, "testBlock");

    }

    @SideOnly(Side.CLIENT)
    public static void registerModels() {
        ResourceLocation textureCentrifuge = new ResourceLocation(Reference.MOD_PREFIX + "textures/blocks/separator.png");
        ResourceLocation textureCrusher = new ResourceLocation(Reference.MOD_PREFIX + "textures/blocks/crusher.png");
        ResourceLocation textureScreen = new ResourceLocation(Reference.MOD_PREFIX + "textures/blocks/screen.png");

        ClientRegistry.bindTileEntitySpecialRenderer(TileDebug.class, new RenderTileDebug());

        ClientRegistry.bindTileEntitySpecialRenderer(TileCrusher.class, new RenderTileMachine(new ModelCrusher(), textureCrusher));
        ClientRegistry.bindTileEntitySpecialRenderer(TileGrinder.class, new RenderTileMachine(new ModelGrinder(), textureCrusher));
        ClientRegistry.bindTileEntitySpecialRenderer(TileCrusherManual.class, new RenderTileMachine(new ModelCrusherManual(), textureCrusher));

        ClientRegistry.bindTileEntitySpecialRenderer(TileScreenCoarse.class, new RenderTileMachine(new ModelScreenCoarse(), textureScreen));
        ClientRegistry.bindTileEntitySpecialRenderer(TileScreenManual.class, new RenderTileMachine(new ModelScreenManual(), textureScreen));
        ClientRegistry.bindTileEntitySpecialRenderer(TileScreenFine.class, new RenderTileMachine(new ModelScreenFine(), textureScreen));

        ClientRegistry.bindTileEntitySpecialRenderer(TileCentrifuge.class, new RenderTileMachine(new ModelCentrifuge(), textureCentrifuge));
        ClientRegistry.bindTileEntitySpecialRenderer(TileCentrifugeManual.class, new RenderTileMachine(new ModelCentrifugeManual(), textureCentrifuge));

        for (int i = 0; i < machinesList.size(); i++) {
            String variant = machinesList.get(i);
            ModelResourceLocation location = new ModelResourceLocation(blockMachine.getRegistryName(), "type=" + variant);
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockMachine), i, location);
        }

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockOre), 0, new ModelResourceLocation(blockOre.getRegistryName(), "normal"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(testBlock), 0, new ModelResourceLocation(testBlock.getRegistryName(), "inventory"));

        ModelRegistryHelper.register(new ModelResourceLocation(blockMachine.getRegistryName(), "type=crusher"), new RenderItemMachine(new RenderTileCrusher()));
        ModelRegistryHelper.register(new ModelResourceLocation(blockMachine.getRegistryName(), "type=grinder"), new RenderItemMachine(new RenderTileGrinder()));
        ModelRegistryHelper.register(new ModelResourceLocation(blockMachine.getRegistryName(), "type=screenCoarse"), new RenderItemMachine(new RenderTileScreenCoarse()));
        ModelRegistryHelper.register(new ModelResourceLocation(blockMachine.getRegistryName(), "type=screenFine"), new RenderItemMachine(new RenderTileScreenFine()));
        //ModelRegistryHelper.register(new ModelResourceLocation(blockMachine.getRegistryName(), "type=separatorMagnetic"), new RenderItemSeparatorMagnetic());
        ModelRegistryHelper.register(new ModelResourceLocation(blockMachine.getRegistryName(), "type=centrifuge"), new RenderItemMachine(new RenderTileCentrifuge()));
        ModelRegistryHelper.register(new ModelResourceLocation(testBlock.getRegistryName(), "inventory"), new RenderItemDebug());

        ModelRegistryHelper.register(new ModelResourceLocation(blockMachine.getRegistryName(), "type=crusherManual"), new RenderItemMachine(new RenderTileCrusherManual()));
        ModelRegistryHelper.register(new ModelResourceLocation(blockMachine.getRegistryName(), "type=screenManual"), new RenderItemMachine(new RenderTileScreenManual()));
        ModelRegistryHelper.register(new ModelResourceLocation(blockMachine.getRegistryName(), "type=centrifugeManual"), new RenderItemMachine(new RenderTileCentrifugeManual()));
    }

}
