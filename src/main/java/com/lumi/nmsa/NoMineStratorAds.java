package com.lumi.nmsa;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.server.command.CommandManager.literal;

public class NoMineStratorAds implements ModInitializer {
	public static final String MOD_ID = "no-minestrator-ads";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Lumi says \"Hello from 'NoMineStratorAds'!\"");
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("tellraw")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("targets", EntityArgumentType.players())
                .then(CommandManager.argument("message", TextArgumentType.text(registryAccess))
                .executes((context) -> {
                    int i = 0;
                    String input = TextArgumentType.parseTextArgument(context, "message", context.getSource().getPlayer()).toString();
                    if (!input.contains("MineStrator") && !input.contains("FlexCore™") &&  !input.contains("MyBoxFree") && !input.contains("SmartBackup™") && !input.contains("MineStrator.com")) {

                    for(ServerPlayerEntity serverPlayerEntity : EntityArgumentType.getPlayers(context, "targets")) {
                        serverPlayerEntity.sendMessageToClient(TextArgumentType.parseTextArgument(context, "message", serverPlayerEntity), false);
                        ++i;
                    }
                    }
                    return i;
                })))
            );
        });
	}
}