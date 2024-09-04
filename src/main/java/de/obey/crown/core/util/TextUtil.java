/* CrownPlugins - CrownCore */
/* 17.08.2024 - 01:29 */

package de.obey.crown.core.util;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public final class TextUtil {

    private final String hi = "https://dsc.gg/crownplugins";
    private final String how = "https://dsc.gg/crownplugins";
    private final String are = "https://dsc.gg/crownplugins";
    private final String you = "https://dsc.gg/crownplugins";
    private final String doing = "https://dsc.gg/crownplugins";

    @Getter
    private final Map<String, String> placeholders = Maps.newConcurrentMap();
    /* Hex Pattern */
    private final Pattern HEX_PATTERN = Pattern.compile("#[A-Fa-f0-9]{6}");

    public boolean containsOnlyLettersAndNumbers(final String input) {
        return input.matches("\\w+");
    }

    public Location parseStringToLocation(final String data) {
        // #world#x#y#z#yaw#pitch
        final String[] parts = data.split("#");
        final World world = Bukkit.getWorld(parts[1]);

        if (world == null)
            return null;

        return new Location(world,
                Double.parseDouble(parts[2]),
                Double.parseDouble(parts[3]),
                Double.parseDouble(parts[4]),
                Float.parseFloat(parts[5]),
                Float.parseFloat(parts[6]));
    }

    public String parseLocationToString(final Location location) {
        // #world#x#y#z#yaw#pitch
        return "#" + location.getWorld().getName() +
                "#" + location.getX() + "#" +
                location.getY() + "#" +
                location.getZ() + "#" +
                location.getYaw() + "#" +
                location.getPitch();
    }

    public String formatTimeString(long millis) {
        int hours = 0, minutes = 0, seconds = 0;

        while (millis >= 1000) {
            seconds++;
            millis -= 1000;
        }

        while (seconds >= 60) {
            minutes++;
            seconds -= 60;
        }

        while (minutes >= 60) {
            hours++;
            minutes -= 60;
        }

        return (hours > 0 ? hours + "h " : "") + (minutes > 0 ? minutes + "m " : "") + (seconds > 0 ? seconds + "." + (millis / 100) + "s" : "0." + (millis / 100) + "s");
    }

    public String formatNumber(final long value) {
        return NumberFormat.getNumberInstance().format(value);
    }

    final DecimalFormat decimalFormat = new DecimalFormat("0.0#");

    public String formatNumber(final double value) {
        return decimalFormat.format(value);
    }

    public void sendActionBar(final Player player, final String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(translateColors(message)));
    }

    public String translateHexColors(String message) {
        if (message == null)
            return "";

        Matcher matcher = HEX_PATTERN.matcher(message);

        while (matcher.find()) {
            final String code = message.substring(matcher.start(), matcher.end());
            message = message.replace(code, "" + ChatColor.of(code));
            // message = message.replace(code, "" + TextColor.fromHexString(code));
            matcher = HEX_PATTERN.matcher(message);
        }

        return message;
    }

    public String translateLegacyColors(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String translateCorePlaceholder(String message) {
        if (message == null)
            return "";

        if (message.isEmpty())
            return message;

        if (placeholders.isEmpty())
            return message;

        for (final String key : placeholders.keySet()) {
            if (message.contains(key)) {
                message = message.replace(key, placeholders.get(key));
            }
        }

        return message;
    }

    public String translateGradient(String text) {// Regular expression to match the gradient format
        final String gradientPattern = "<#([0-9a-fA-F]{6}):#([0-9a-fA-F]{6}):([^>]+)>";
        final Pattern pattern = Pattern.compile(gradientPattern);
        final Matcher matcher = pattern.matcher(text);
        final StringBuilder translatedText = new StringBuilder();

        while (matcher.find()) {
            final Color startColor = Color.fromRGB(Integer.parseInt(matcher.group(1), 16));
            final Color endColor = Color.fromRGB(Integer.parseInt(matcher.group(2), 16));


            final String translateText = matcher.group(3);

            // Calculate the gradient step
            int step = (endColor.getRed() - startColor.getRed()) / translateText.length();

            // Translate each character in the text
            for (int i = 0; i < translateText.length(); i++) {

                // Calculate the current color

                int red = startColor.getRed() + (step * i);
                int green = startColor.getGreen() + (step * i);
                int blue = startColor.getBlue() + (step * i);


                // Create a ChatColor object for the current color
                final String hexColor = String.format("#%02x%02x%02x", red, green, blue);
                ChatColor color = ChatColor.of(hexColor);

                // Append the translated character to the StringBuilder
                translatedText.append(color).append(translateText.charAt(i));
            }
        }

        // Return the translated text
        return translatedText.toString();
    }

    public String translateColors(final String message) {
        return translateLegacyColors(translateHexColors(translateCorePlaceholder(message)));
    }

    public String registerCorePlaceholder(final String placeholder, String replacement) {
        replacement = translateHexColors(translateLegacyColors(replacement));
        placeholders.put(placeholder, replacement);
        return replacement;
    }
}
