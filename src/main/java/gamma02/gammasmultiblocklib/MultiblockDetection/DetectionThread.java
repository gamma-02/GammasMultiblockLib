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
    private ArrayDeque<Pair<Pair<World, MultiblockController>, Long>> queue;

    public DetectionThread(ArrayDeque<Pair<Pair<World, MultiblockController>, Long>> toFind){
        this.queue = toFind;
    }

    @Override
    public void run() {
        while(!queue.isEmpty()){//run while the queue is not empty
            if(queue.getFirst().getA().getB() == null){//determine if the MultiblockController has a valid multiblock
                if(System.currentTimeMillis() > queue.getFirst().getB()) {

                    if (queue.getFirst().getA().getB().searchForMultiblock()) {
                        queue.getFirst().getA().getB().setOwner(queue.getFirst().getA().getB().buildMultiblock(queue.getFirst().getA()));
                        Multiblock.CACHE.put(queue.getFirst().getA().getB(), queue.getFirst().getA().getB().getOwner());
                    } else {
                        Pair<Pair<World, MultiblockController>, Long> temp = queue.removeFirst();
                        Pair<Pair<World, MultiblockController>, Long> temp2 = new Pair<>(temp.getA(), System.currentTimeMillis() + 1000);
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

    public void addToQueue(Pair<Pair<World, MultiblockController>, Long> pair){
        this.queue.add(pair);
    }
}
