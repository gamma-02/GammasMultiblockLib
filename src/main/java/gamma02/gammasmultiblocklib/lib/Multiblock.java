package gamma02.gammasmultiblocklib.lib;


import com.ibm.icu.math.BigDecimal;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.Block;
import net.minecraft.block.StructureBlock;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.Structure;
import net.minecraft.util.math.Box;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * <h1>Multiblock</h1><br>
 * This interface is just the containter for your custom implimentation of a multiblock structure,
 * see {@link } for an example implimenation. Active multiblocks should be registered to the cache, TODO: example mod
 * for any server-side stuff that requires an instance of the multiblock.
 */

public interface Multiblock {
    /**
     * Cache for MultiblockControllers, so you don't have to store one yourself just access this
     */
    HashMap<MultiblockController, Multiblock> CACHE = new HashMap<>();

    /**
     * Gets the bounding box of your multiblock, so this gets how large your multiblock should be.
     * @return The bounding box of your multiblock
     */
    Box getBoundingBox();

    /**
     * @return the type of your multiblock
     * @see MultiblockType
     */
    MultiblockType getMultiblockType();

    /**
     * a method that is called once the {@link MultiblockController} finds your multiblock.
     */

    void onMultiblockFound();

    /**
     * Gets the controller of your multiblock.
     * @return the controller of your multiblock, as this is what <b><i>CREATES</i></b> your multiblock, this should NEVER be null, thus the annotation<3<3
     */

    @Nonnull
    MultiblockController getController();

    /**
     * This is a method for {@link MultiblockType#STRUCTURE} ONLY, else this SHOULD return null!
     * @return the structure of your multiblock class, for loading/saving see {@link StructureBlockBlockEntity} and {@link Structure#readNbt(NbtCompound)}
     * @see StructureBlock
     * @see Structure
     * @see StructureBlockBlockEntity
     */
    @Nullable
    default Structure getMultiblockStructure(){
        return null;
    }

    /**
     * this is a method for {@link MultiblockType#CUBE_CUSTOM} ONLY, else this SHOULD return null!
     * @return a list of valid internal blocks for your multiblock class.
     */
    @Nullable
    default ArrayList<Block> getValidMultiblockBlocks(){
        return null;
    }


}
