package com.yourname.wurstmod.hacks;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.packet.c2s.play.ChatMessageC2SPacket;
import net.minecraft.text.Text;
import com.yourname.wurstmod.hack.Hack;
import com.yourname.wurstmod.utils.ChatUtils;

import java.util.Random;

public class ChatTriggerHack extends Hack {

    private final MinecraftClient mc = MinecraftClient.getInstance();
    private final Random random = new Random();

    public ChatTriggerHack() {
        super("ChatTrigger", "Responds with a random number or special messages based on specific commands.");
    }

    @Override
    public void onEnable() {
        super.onEnable();
        ChatUtils.message("Chat Trigger Hack enabled!");
    }

    @Override
    public void onChatMessage(Text message) {
        String msg = message.getString().toLowerCase();

        // Check for specific commands
        if (msg.contains("!random")) {
            respond("Your random number is: " + getRandomNumber());
        } else if (msg.contains("!gyatt")) {
            respond("You have a level " + getRandomNumber() + " gyatt!");
        } else if (msg.contains("!skibidi")) {
            respond("skibidi toilet will be mine!!!");
        } else if (msg.startsWith("!smelly ")) {
            // Extract the username from the command and send a whisper
            String username = extractUsername(msg);
            if (username != null && !username.isEmpty()) {
                whisper(username, "Hey " + username + ", you smell!");
            }
        }
    }

    private int getRandomNumber() {
        // Generate a random number between 1 and 100
        return random.nextInt(100) + 1;
    }

    private String extractUsername(String msg) {
        // Remove the !smelly command part and return the remaining string as the username
        String command = "!smelly ";
        return msg.substring(command.length()).trim();
    }

    private void whisper(String username, String message) {
        if (mc.player != null) {
            // Send a whisper/private message to the user
            mc.player.networkHandler.sendPacket(new ChatMessageC2SPacket("/msg " + username + " " + message));
        }
    }

    private void respond(String response) {
        if (mc.player != null) {
            mc.player.networkHandler.sendPacket(new ChatMessageC2SPacket(response));
        }
    }
}
