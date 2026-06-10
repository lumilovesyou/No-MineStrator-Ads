package com.lumi.nmsa.mixin.client;

import net.minecraft.client.multiplayer.chat.ChatListener;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.lumi.nmsa.NoMineStratorAds.MOD_ID;

@Mixin(ChatListener.class)
public class ChatMessageMixin {
    @Inject(method = "handleSystemMessage", at = @At("HEAD"), cancellable = true)
    private void onGameMessage(Component message, boolean overlay, CallbackInfo ci) {
        final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
        LOGGER.info("Here's the message! " + message.toString());
        if (containsBlockedWord(message)) {
            ci.cancel();
        }
    }

    private boolean containsBlockedWord(Component text) {
        String message = text.getString();
        if (message.contains("MineStrator") || message.contains("FlexCore™") || message.contains("MyBoxFree") || message.contains("SmartBackup™") || message.contains("MineStrator.com")) {
            return true;
        }
        for (Component child : text.getSiblings()) {
            if (containsBlockedWord(child)) return true;
        }
        return false;
    }
}