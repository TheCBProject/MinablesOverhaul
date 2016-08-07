package teamasm.moh.client.render.item;

import codechicken.lib.render.IItemRenderer;
import codechicken.lib.render.TransformUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import org.apache.commons.lang3.tuple.Pair;
import teamasm.moh.client.render.tile.RenderTileCrusherAutomatic;
import teamasm.moh.handler.EventHandler;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by covers1624 on 8/7/2016.
 */
public class RenderItemCrusherAutomatic implements IItemRenderer, IPerspectiveAwareModel {

    @Override
    public void renderItem(ItemStack item) {
        RenderTileCrusherAutomatic.render(0, 0, 0, 2, (EventHandler.clientTicks + EventHandler.renderTickFrame) / 3);
    }

    @Override
    public Pair<? extends IBakedModel, Matrix4f> handlePerspective(TransformType cameraTransformType) {
        return MapWrapper.handlePerspective(this, TransformUtils.DEFAULT_BLOCK.getTransforms(), cameraTransformType);
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
        return new LinkedList<BakedQuad>();
    }

    @Override
    public boolean isAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean isGui3d() {
        return false;
    }

    @Override
    public boolean isBuiltInRenderer() {
        return true;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        return null;
    }

    @Override
    public ItemCameraTransforms getItemCameraTransforms() {
        return ItemCameraTransforms.DEFAULT;
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.NONE;
    }
}
