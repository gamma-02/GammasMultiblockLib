package gamma02.gammasmultiblocklib;

import com.google.gson.JsonObject;
import gamma02.gammasmultiblocklib.MultiblockDetection.DetectionThread;
import gamma02.gammasmultiblocklib.lib.Multiblock;
import gamma02.gammasmultiblocklib.lib.MultiblockController;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.structure.Structure;
import net.minecraft.structure.StructureManager;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import oshi.util.tuples.Pair;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.function.Function;

public class GammasMultiblockLib implements ModInitializer {
    /**
     * This is the searching queue, it is passed into a thread when it's not empty to recognize multiblocks.
     */
    public static ArrayDeque<MultiblockController> multiblockRecognizationQueue = new ArrayDeque<>();
    public static DetectionThread DETECTION_THREAD = new DetectionThread(multiblockRecognizationQueue);
    public static MinecraftServer server;

    @Override
    public void onInitialize() {
        DETECTION_THREAD.start();
        ServerLifecycleEvents.SERVER_STARTED.register(GammasMultiblockLib::setServer);
    }


    public static void setServer(MinecraftServer server1){
        server = server1;
    }

    public static Structure readStructureFromIdentifier(Identifier id){
        Optional<Structure> structure = server.getStructureManager().getStructure(id);
        if(structure.isPresent()){
            return structure.get();
        }else{
            System.out.println("STRUCTURE FILE NOT FOUND! CHECK THE PATH AND ID! - Gamma's Multiblock Lib <3");
            return null;
        }
    }




    /**
     * Method used to add a multiblock to the detection thread and queue. don't do this every tick, please!
     * @param controller The multiblock controller that you want to detect the multiblock of.
     */
    public static void addToRecognizationThread(MultiblockController controller){
        DETECTION_THREAD.addToQueue(controller);
        DETECTION_THREAD.notify();
    }


}
