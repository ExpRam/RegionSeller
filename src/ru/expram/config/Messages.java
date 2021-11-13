package ru.expram.config;

import com.google.common.collect.Lists;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import ru.expram.color.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public enum Messages {

    NO_PERMS,
    NOT_A_PLAYER,
    USAGE_SELL,
    USAGE_REMOVE,
    USAGE_RELOAD,
    CONFIG_RELOADED,
    INVALID_NUMBER,
    REGION_NOT_FOUND,
    NOT_AN_OWNER,
    ALREADY_FOR_SALE,
    REGION_SOLD,
    REMOVED_FROM_SALE,
    REGION_NOT_SALE,
    ALREADY_ON_FIRST_PAGE,
    ON_LAST_PAGE,
    NOTENOUGHMONEY,
    ALREADY_SOLD,
    CANNOT_TELEPORT,
    REGION_WAS_DELETED,
    REGION_PURCHASED,
    INVENTORY_CLOSE,
    INVENTORY_RIGHT,
    INVENTORY_LEFT,
    INVENTORY_INVTITLE,
    INVENTORY_RGITEMTITLE,
    INVENTORY_RGITEMLORE,
    INVENTORY_TPTOREGION,
    INVENTORY_CLICKTOBUY;

    private List<String> msg;

    public static void load(FileConfiguration c) {
        for(Messages msg : Messages.values()) {
            String message = msg.name().contains("INVENTORY") ? msg.name().replace("_", ".") : msg.name();
            Object obj = c.get("messages." + message.toLowerCase());
            if(obj instanceof List) {
                List<String> str = (List<String>) obj;
                msg.msg = str.stream().
                        map(m -> Color.colorize(m)).
                        collect(Collectors.toList());
            }
            else {
                msg.msg = Lists.newArrayList(obj == null ? "" : Color.colorize(obj.toString()));
            }
        }
    }

    public MessageSender replace(String s1, String s2) {
        return new MessageSender().replace(s1, s2);
    }

    public void send(CommandSender sender) {
        new MessageSender().send(sender);
    }

    public String getFirstItem() {
        return msg.get(0);
    }

    public class MessageSender {
        private HashMap<String, String> placeholders = new HashMap<>();

        public void send(CommandSender player) {
            for(String message : Messages.this.msg) {
                sendMsg(player, message);
            }
        }

        public void sendMsg(CommandSender sender, String msg) {
            sender.sendMessage(ReplacePlaceholders(msg));
        }

        public MessageSender replace(String s1, String s2) {
            placeholders.put(s1, s2);
            return this;
        }

        public String ReplacePlaceholders(String msg) {
            if(!msg.contains("{")) return msg;
            for(Map.Entry<String, String> entry : placeholders.entrySet()) {
                msg = msg.replace(entry.getKey(), entry.getValue());
            }
            return msg;
        }

        public String getFirstItem() {
            return ReplacePlaceholders(Messages.this.msg.get(0));
        }

        public ArrayList<String> getAllItems() {
            ArrayList<String> tmp = new ArrayList<>();
            for(String msg : Messages.this.msg) {
                tmp.add(ReplacePlaceholders(msg));
            }
            return tmp;
        }

    }
}
