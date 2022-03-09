package gamma02.gammasmultiblocklib.mixin;

import gamma02.gammasmultiblocklib.GammasMultiblockLib;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ProgressListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BooleanSupplier;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(BooleanSupplier shouldKeepTicking, CallbackInfo ci){
        GammasMultiblockLib.DETECTION_THREAD.tick();
    }

}
