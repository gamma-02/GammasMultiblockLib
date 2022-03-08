package gamma02.gammasmultiblocklib.MultiblockDetection;

import gamma02.gammasmultiblocklib.lib.Multiblock;
import gamma02.gammasmultiblocklib.lib.MultiblockController;
import net.minecraft.world.World;
import oshi.util.tuples.Pair;

import java.util.ArrayDeque;
import java.util.function.Function;

/**
 * ...why are you here? this is cursed stuff that you don't need to see lmao
 */
public class DetectionThread extends Thread {
    private ArrayDeque<Pair<Pair<Function<Pair<World, MultiblockController>, Multiblock>, Pair<World, MultiblockController>>, Pair<MultiblockController, Long>>> queue;

    public DetectionThread(ArrayDeque<Pair<Pair<Function<Pair<World, MultiblockController>, Multiblock>, Pair<World, MultiblockController>>, Pair<MultiblockController, Long>>> toFind){
        this.queue = toFind;
    }

    @Override
    public void run() {
        while(!queue.isEmpty()){//run while the queue is not empty
            if(queue.getFirst().getB().getA().getOwner() == null){//determine if the MultiblockController has a valid multiblock
                if(System.currentTimeMillis() > queue.getFirst().getB().getB()) {
                    Multiblock possible = queue.getFirst().getA().getA().apply(queue.getFirst().getA().getB());
                    if (possible != null) {
                        queue.getFirst().getB().getA().setOwner(possible);
                        Multiblock.CACHE.put(queue.getFirst().getB().getA(), possible);
                    } else {
                        Pair<Pair<Function<Pair<World, MultiblockController>, Multiblock>, Pair<World, MultiblockController>>, Pair<MultiblockController, Long>> temp = queue.removeFirst();
                        Pair<Pair<Function<Pair<World, MultiblockController>, Multiblock>, Pair<World, MultiblockController>>, Pair<MultiblockController, Long>> temp2 = new Pair<Pair<Function<Pair<World, MultiblockController>, Multiblock>, Pair<World, MultiblockController>>, Pair<MultiblockController, Long>>(temp.getA(), new Pair<MultiblockController, Long>(temp.getB().getA(), System.currentTimeMillis() + 1000));
                        queue.addLast(temp2);
                    }
                }
            }else{
                queue.removeFirst();
            }
            if(queue.isEmpty()){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        super.run();
    }

    public void addToQueue(Pair<Pair<Function<Pair<World, MultiblockController>, Multiblock>, Pair<World, MultiblockController>>, Pair<MultiblockController, Long>> pair){
        this.queue.add(pair);
    }
}
