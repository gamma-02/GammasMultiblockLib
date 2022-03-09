package gamma02.gammasmultiblocklib.mixin;

import gamma02.gammasmultiblocklib.GammasMultiblockLib;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftServer.class)
public class ServerMixin {
    @Inject(method = "shutdown", at = @At("HEAD"))
    public void shutdownThread(CallbackInfo ci){
        GammasMultiblockLib.DETECTION_THREAD.shouldContinue(false);
    }
}
