package gamma02.gammasmultiblocklib.lib;

import gamma02.gammasmultiblocklib.MultiblockDetection.MultiblockDetectionTickerType;
import net.fabricmc.fabric.api.lookup.v1.block.BlockApiLookup;
import net.minecraft.block.Block;
import net.minecraft.block.StructureBlock;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.Structure;
import net.minecraft.util.Identifier;
import gamma02.gammasmultiblocklib.GammasMultiblockLib;
import net.minecraft.world.World;
import gamma02.gammasmultiblocklib.MultiblockDetection.DetectionThread;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

/**
 * <h1>Multiblock Controller</h1>
 *
 * This is the main driver for the library, impliment this on the controller of your multiblock and register the
 * {@code searchForMultiblock()} into the detection thread with {@link GammasMultiblockLib#addToRecognizationThread(World, MultiblockController)}
 * as well as register it for the BlockLookupApi with {@link MultiblockController#TYPED}.
 */

public interface MultiblockController {


    /**
     * @return the Multiblock that you generate when the {@link DetectionThread} finds your multiblock;
     */
    Multiblock buildMultiblock();

    /**
     * The method that you use to find your multiblock.  TODO: example mod
     * @return if you found your multiblock or not
     */
    boolean searchForMultiblock();


    /**
     * This method is called when your multiblock is found
     */
    default void onMultiblockFound(){}

    /**
     * @return the multiblock that the current MultiblockController is a part of
     */
    @Nullable
    Multiblock getMultiblockOwner();

    /**
     *
     * @return This is the method that defines the type of your multiblock.
     */
    @Nonnull
    MultiblockType getMultiblockType();

    /**
     * This is used for multiblock detection, either with ticks, as used here, or with milliseconds.
     * @return the {@link MultiblockDetectionTickerType} that your multiblock uses.
     */
    @Nonnull
    default MultiblockDetectionTickerType getDetectionTickerType(){
        return MultiblockDetectionTickerType.getWithTicks(10);
    }

    /**
     * sets the owner of the multiblock, used in the detection thread once a valid multiblock is found and built.
     * @param multiblock The multiblock that you want this to control.
     */
    void setOwner(Multiblock multiblock);

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

    /**
     * This is a method for {@link MultiblockType#STRUCTURE_TEIRED} ONLY, else this SHOULD return null!
     * @return the structure of your multiblock class, for loading/saving see {@link StructureBlockBlockEntity} and {@link Structure#readNbt(NbtCompound)}
     * @see StructureBlock
     * @see Structure
     * @see StructureBlockBlockEntity
     */
    @Nullable
    default ArrayList<Structure> getMultiblockStructureTeirs(){
        return null;
    }




    /**
     * The BlockApiLookup instance that you register your controller into.
     * @see BlockApiLookup
     */
    BlockApiLookup<MultiblockController, MultiblockType> TYPED = BlockApiLookup.get(new Identifier("multiblocklib", "typed_controller"), MultiblockController.class, MultiblockType.class);
}
