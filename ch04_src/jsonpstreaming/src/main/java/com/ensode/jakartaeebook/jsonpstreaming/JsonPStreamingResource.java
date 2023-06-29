package com.ensode.jakartaeebook.jsonpstreaming;

import jakarta.json.Json;
import jakarta.json.stream.JsonGenerator;
import jakarta.json.stream.JsonParser;
import jakarta.json.stream.JsonParser.Event;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

@Path("jsonpstreaming")
public class JsonPStreamingResource {

  @Path("build")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String buildJson() {

    StringWriter stringWriter = new StringWriter();
    try (JsonGenerator jsonGenerator = Json.createGenerator(stringWriter)) {
      jsonGenerator.writeStartObject().
              write("firstName", "Larry").
              write("lastName", "Gates").
              write("email", "lgates@example.com").
              writeEnd();
    }

    return stringWriter.toString();

  }

  @Path("parse")
  @POST
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.APPLICATION_JSON)
  public String parseJson(String jsonStr) {

    Customer customer = new Customer();

    StringReader stringReader = new StringReader(jsonStr);

    JsonParser jsonParser = Json.createParser(stringReader);

    Map<String, String> keyValueMap = new HashMap<>();
    String key = null;
    String value = null;

    while (jsonParser.hasNext()) {
      JsonParser.Event event = jsonParser.next();

      if (event.equals(Event.KEY_NAME)) {
        key = jsonParser.getString();
      } else if (event.equals(Event.VALUE_STRING)) {
        value = jsonParser.getString();
      }

      keyValueMap.put(key, value);
    }

    customer.setFirstName(keyValueMap.get("firstName"));
    customer.setLastName(keyValueMap.get("lastName"));
    customer.setEmail(keyValueMap.get("email"));

    return customer.toString();
  }
}
