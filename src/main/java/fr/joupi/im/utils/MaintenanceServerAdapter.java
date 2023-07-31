package fr.joupi.im.utils;

import com.google.gson.*;
import fr.joupi.im.common.maintenance.MaintenanceServer;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;

public class MaintenanceServerAdapter implements JsonDeserializer<MaintenanceServer> {

    @Override
    public MaintenanceServer deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        final List<String> whitelists = new LinkedList<>();
        final JsonArray jsonArray = jsonObject.get("whitelists").getAsJsonArray();

        jsonArray.forEach(element -> whitelists.add(element.getAsString()));

        return new MaintenanceServer(
                jsonObject.get("serverName").getAsString(),
                whitelists,
                jsonObject.get("isWhitelisted").getAsBoolean()
        );
    }

}

