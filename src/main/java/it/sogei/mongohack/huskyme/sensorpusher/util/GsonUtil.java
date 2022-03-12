package it.sogei.mongohack.huskyme.sensorpusher.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import it.sogei.mongohack.huskyme.sensorpusher.model.enums.ERazza;
import it.sogei.mongohack.huskyme.sensorpusher.model.enums.ETipo;

import java.io.IOException;
import java.time.LocalDateTime;

public final class GsonUtil {
    private static GsonUtil gsonUtil;

    static {
        gsonUtil = new GsonUtil();
    }

    private Gson gson;

    private GsonUtil() {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(ETipo.class, new TypeAdapter<ETipo>() {
            @Override
            public void write(JsonWriter jsonWriter, ETipo eTipo) throws IOException {
                jsonWriter.beginObject();
                jsonWriter.name("codTipoAnimale");
                jsonWriter.value(eTipo.getCodTipoAnimale());
                jsonWriter.name("tipo");
                jsonWriter.value(eTipo.getTipo());
                jsonWriter.endObject();
            }

            @Override
            public ETipo read(JsonReader jsonReader) throws IOException {
                jsonReader.beginObject();
                String name = jsonReader.nextName();
                while (!name.equals("codTipoAnimale")) {
                    jsonReader.nextString();
                    name = jsonReader.nextName();
                }
                int codTipoAnimale = jsonReader.nextInt();
                while (jsonReader.hasNext() && !jsonReader.peek().equals(JsonToken.END_OBJECT)) {
                    jsonReader.skipValue();
                }

                jsonReader.endObject();

                return ETipo.getTipo(codTipoAnimale);
            }
        });

        builder.registerTypeAdapter(ERazza.class, new TypeAdapter<ERazza>() {
            @Override
            public void write(JsonWriter jsonWriter, ERazza eRazza) throws IOException {
                jsonWriter.beginObject();
                jsonWriter.name("codRazza");
                jsonWriter.value(eRazza.getCodRazza());
                jsonWriter.name("razza");
                jsonWriter.value(eRazza.getRazza());
                jsonWriter.endObject();
            }

            @Override
            public ERazza read(JsonReader jsonReader) throws IOException {
                jsonReader.beginObject();
                String name = jsonReader.nextName();
                while (!name.equals("codRazza")) {
                    jsonReader.nextString();
                    name = jsonReader.nextName();
                }
                int codRazza = jsonReader.nextInt();
                while (jsonReader.hasNext() && !jsonReader.peek().equals(JsonToken.END_OBJECT)) {
                    jsonReader.skipValue();
                }

                jsonReader.endObject();

                return ERazza.getRazza(codRazza);
            }
        });

        this.gson = builder.create();
    }

    public Gson getGson() {
        return this.gson;
    }

    public static GsonUtil getUtil() {
        return gsonUtil;
    }
}
