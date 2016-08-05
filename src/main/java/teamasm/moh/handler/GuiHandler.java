package teamasm.moh.handler;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import teamasm.moh.client.gui.GuiReducerCrusher;
import teamasm.moh.container.ContainerReducerCrusher;
import teamasm.moh.reference.GuiIds;
import teamasm.moh.tile.machines.teir1.TileReducerCrusher;

/**
 * Created by Gigabit101 on 05/08/2016.
 */
public class GuiHandler implements IGuiHandler{

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        switch (GuiIds.parse(ID)) {
            case REDUCER_CRUSHER:
                return new ContainerReducerCrusher(player.inventory, (TileReducerCrusher) tileEntity);
            case UNKNOWN:
            default:
                return null;
        }
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        switch (GuiIds.parse(ID)) {
            case REDUCER_CRUSHER:
                return new GuiReducerCrusher(player.inventory, (TileReducerCrusher) tileEntity);
            case UNKNOWN:
            default:
                return null;
        }
    }
}