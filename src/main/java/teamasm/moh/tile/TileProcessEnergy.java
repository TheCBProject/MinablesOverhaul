package teamasm.moh.tile;

import codechicken.lib.tile.IGuiTile;
import cofh.api.energy.EnergyStorage;
import cofh.api.energy.IEnergyReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import teamasm.moh.MinablesOverhaul;
import teamasm.moh.reference.GuiIds;

/**
 * Created by brandon3055 on 5/08/2016.
 */
public abstract class TileProcessEnergy extends TileProcessorBase implements IEnergyReceiver, IGuiTile {

    public EnergyStorage energyStorage = new EnergyStorage(512000, 32000, 0);

    @Override
    public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
        return energyStorage.receiveEnergy(maxReceive, simulate);
    }

    @Override
    public int getEnergyStored(EnumFacing from) {
        return energyStorage.getEnergyStored();
    }

    @Override
    public int getMaxEnergyStored(EnumFacing from) {
        return energyStorage.getMaxEnergyStored();
    }

    @Override
    public boolean canConnectEnergy(EnumFacing from) {
        return true;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        energyStorage.writeToNBT(compound);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        energyStorage.readFromNBT(compound);
        super.readFromNBT(compound);
    }

    protected void openGui(GuiIds id, World world, BlockPos pos, EntityPlayer player) {
        player.openGui(MinablesOverhaul.instance, id.ordinal(), world, pos.getX(), pos.getY(), pos.getZ());
    }

    public long getPowerScaled (int scale) {
        return (this.getEnergyStored(EnumFacing.DOWN) * scale) / this.getMaxEnergyStored(EnumFacing.DOWN);
    }

    @Override
    public void openGui(World world, BlockPos pos, EntityPlayer player) {
        openGui(getGuiID(), world, pos, player);
    }

    public abstract GuiIds getGuiID();
}
