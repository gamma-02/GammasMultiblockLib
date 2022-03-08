package gamma02.gammasmultiblocklib.lib;

import javax.annotation.Nullable;

/**
 * This is an interface for block entities that make up the sides and internals of
 * a multiblock, so any block that is in a multiblock.
 */

public interface MultiblockConstituant {

    /**
     * @return the controller of the multiblock that owns this block entity
     */
    @Nullable
    MultiblockController getOwner();

    /**
     *
     * @return the multiblock that owns this BlockEntity
     */

    @Nullable
    default Multiblock getOwnerMultiblock(){
        return this.getOwner().getOwner();
    }

    /**
     * If this is a member of a multiblock this should return true.
     * @return if the blockEntity is owned by a controller or not
     */
    boolean isOwned();
}
