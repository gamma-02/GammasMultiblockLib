package gamma02.gammasmultiblocklib.MultiblockDetection;

import gamma02.gammasmultiblocklib.lib.Multiblock;
import gamma02.gammasmultiblocklib.lib.MultiblockController;
import net.minecraft.world.World;
import oshi.util.tuples.Pair;

import java.util.ArrayDeque;

/**
 * ...why are you here? this is cursed stuff that you don't need to see lmao
 */
public class DetectionThread extends Thread {
    private ArrayDeque<MultiblockController> queue;
    private long ticks = 0;
    boolean shouldContinue = true;

    public DetectionThread(ArrayDeque< MultiblockController> toFind){
        this.queue = toFind;
    }

    @Override
    public void run() {
        while(shouldContinue){//run while the queue is not empty
            if(queue.getFirst().getMultiblockOwner() == null){//determine if the MultiblockController has a valid multiblock
                if(queue.getFirst().getDetectionTickerType().isCountsWithTicks() ? this.ticks > queue.getFirst().getDetectionTickerType().getTime() : System.currentTimeMillis() > queue.getFirst().getDetectionTickerType().getTime()) {

                    if (queue.getFirst().searchForMultiblock()) {
                        Multiblock m = queue.getFirst().buildMultiblock();
                        if(m == null)
                            continue;
                        queue.getFirst().setOwner(m);
                        queue.getFirst().onMultiblockFound();
                        m.onMultiblockFound();
                        Multiblock.CACHE.put(queue.getFirst(), queue.getFirst().getMultiblockOwner());
                    } else {
                        MultiblockController temp = queue.removeFirst();
                        temp.getDetectionTickerType().setTime((temp.getDetectionTickerType().isCountsWithTicks() ? temp.getDetectionTickerType().getTime() + this.ticks : temp.getDetectionTickerType().getDurationBetweenChecks() + System.currentTimeMillis()));
                        queue.addLast(temp);
                    }
                }
            }else{
                queue.removeFirst();
            }
            if(queue.isEmpty()){
                try {
                    this.ticks = 0;
                    this.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void tick(){
        if(!this.queue.isEmpty())
            this.ticks++;
    }

    public void shouldContinue(boolean should){
        this.shouldContinue = should;
    }

    public void addToQueue(MultiblockController pair){
        this.queue.add(pair);
    }
}
