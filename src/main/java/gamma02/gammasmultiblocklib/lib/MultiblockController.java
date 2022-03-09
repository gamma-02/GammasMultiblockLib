package gamma02.gammasmultiblocklib.lib;

import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.lwjgl.system.CallbackI;
import oshi.util.tuples.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface MultiblockController {
    @Nonnull
    Multiblock buildMultiblock(Pair<World, MultiblockController> controllerWorldPair);

    boolean searchForMultiblock();

    @Nullable
    Multiblock getOwner();

    @Nonnull
    MultiblockType getMultiblockType();


    void setOwner(Multiblock multiblock);

    BlockApiLookup<MultiblockController, MultiblockType> TYPED = BlockApiLookup.get(new Identifier("multiblocklib", "typed_controller"), MultiblockController.class, MultiblockType.class);
}
