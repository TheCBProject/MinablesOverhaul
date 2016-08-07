package teamasm.moh.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import teamasm.moh.client.gui.machine.*;
import teamasm.moh.container.machine.*;
import teamasm.moh.reference.GuiIds;
import teamasm.moh.tile.TileProcessEnergy;
import teamasm.moh.tile.machines.teir1.TileReducerCrusher;
import teamasm.moh.tile.machines.teir1.TileScreenCoarse;
import teamasm.moh.tile.machines.teir1.TileScreenFine;
import teamasm.moh.tile.machines.teir1.TileSeparatorGravity;
import teamasm.moh.tile.machines.tier2.TileReducerGrinder;

/**
 * Created by Gigabit101 on 05/08/2016.
 */
public class GuiHandler implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (!(tileEntity instanceof TileProcessEnergy)) {
            return null;
        }

        switch (GuiIds.parse(ID)) {
        case REDUCER_CRUSHER:
            return new ContainerReducerCrusher(player.inventory, (TileReducerCrusher) tileEntity);
        case REDUCER_GRINDER:
            return new ContainerReducerGrinder(player.inventory, (TileReducerGrinder) tileEntity);
        case SCREEN_COARSE:
            return new ContainerScreenCoarse(player.inventory, (TileScreenCoarse) tileEntity);
        case SCREEN_FINE:
            return new ContainerScreenFine(player.inventory, (TileScreenFine) tileEntity);
        case SEPARATOR_GRAVITY:
            return new ContainerSeperatorGravity(player.inventory, (TileSeparatorGravity) tileEntity);
        case UNKNOWN:
        default:
            return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (!(tileEntity instanceof TileProcessEnergy)) {
            return null;
        }

        switch (GuiIds.parse(ID)) {
        case REDUCER_CRUSHER:
            return new GuiReducerCrusher(player.inventory, (TileReducerCrusher) tileEntity);
        case REDUCER_GRINDER:
            return new GuiReducerGrinder(player.inventory, (TileReducerGrinder) tileEntity);
        case SCREEN_COARSE:
            return new GuiScreenCoarse(player.inventory, (TileScreenCoarse) tileEntity);
        case SCREEN_FINE:
            return new GuiScreenFine(player.inventory, (TileScreenFine) tileEntity);
        case SEPARATOR_GRAVITY:
            return new GuiSeperatorGravity(player.inventory, (TileSeparatorGravity) tileEntity);
        case UNKNOWN:
        default:
            return null;
        }
    }
}
