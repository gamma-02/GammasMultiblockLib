package gamma02.gammasmultiblocklib;

import gamma02.gammasmultiblocklib.MultiblockDetection.DetectionThread;
import gamma02.gammasmultiblocklib.lib.Multiblock;
import gamma02.gammasmultiblocklib.lib.MultiblockController;
import net.fabricmc.api.ModInitializer;
import net.minecraft.world.World;
import oshi.util.tuples.Pair;

import java.util.ArrayDeque;
import java.util.function.Function;

public class GammasMultiblockLib implements ModInitializer {
    /**
     * This is the searching queue, it is passed into a thread when it's not empty to recognize multiblocks.
     */
    public static ArrayDeque<Pair<Pair<World, MultiblockController>, Long>> multiblockRecognizationQueue = new ArrayDeque<Pair<Pair<World, MultiblockController>, Long>>();
    public static DetectionThread DETECTION_THREAD = new DetectionThread(multiblockRecognizationQueue);

    @Override
    public void onInitialize() {
        DETECTION_THREAD.start();
    }

    /**
     * Method used to add a multiblock to the detection thread and queue. don't do this every tick, please!
     * @param function xa function that takes a World and gives a Multiblock, this should be your multiblock's {@link MultiblockController#searchForMultiblock(Pair)} method. CALL IN THE SERVER!!! gamma <3
     * @param controller The multiblock controller that you want to detect the multiblock of.
     * @param world The world of your multiblock.
     */
    public static void addToRecognizationThread( World world,  MultiblockController controller){
//        multiblockRecognizationQueue.add(new Pair<Pair<Function<Pair<World, MultiblockController>, Multiblock>, Pair<World, MultiblockController>>, Pair<MultiblockController, Long>>(new Pair<Function<Pair<World, MultiblockController>, Multiblock>, Pair<World, MultiblockController>>(function, new Pair<World, MultiblockController>(world, controller)), new Pair<MultiblockController, Long>(controller, System.currentTimeMillis())));
        DETECTION_THREAD.addToQueue(new Pair<Pair<World, MultiblockController>, Long>(new Pair<World, MultiblockController>(world, controller), System.currentTimeMillis()));
        DETECTION_THREAD.notifyAll();
    }


}
