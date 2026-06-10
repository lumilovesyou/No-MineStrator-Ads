package com.lumi.nmsa;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ComponentArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static net.minecraft.commands.Commands.literal;

public class NoMineStratorAds implements ModInitializer {
	public static final String MOD_ID = "no-minestrator-ads";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Lumi says \"Hello from 'NoMineStratorAds'!\"");
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(literal("tellraw")
                .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
                .then(Commands.argument("targets", EntityArgument.players())
                .then(Commands.argument("message", ComponentArgument.textComponent(registryAccess))
                .executes((context) -> {
                    int i = 0;
                    String input = ComponentArgument.getResolvedComponent(context, "message", context.getSource().getPlayer()).toString();
                    if (!input.contains("MineStrator") && !input.contains("FlexCore™") &&  !input.contains("MyBoxFree") && !input.contains("SmartBackup™") && !input.contains("MineStrator.com")) {

                        for(ServerPlayer serverPlayerEntity : EntityArgument.getPlayers(context, "targets")) {
                            serverPlayerEntity.sendSystemMessage(ComponentArgument.getResolvedComponent(context, "message", serverPlayerEntity), false);
                            ++i;
                        }
                    }
                    return i;
                })))
            );
        });
	}
}