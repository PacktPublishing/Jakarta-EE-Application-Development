package com.ensode.jakartaee8book.jsonpobject;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonWriter;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import java.io.StringReader;
import java.io.StringWriter;

@Path("jsonpmodel")
public class JsonPModelResource {

  @Path("build")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String jsonpModelBuildJson() {
    JsonObject jsonObject = Json.createObjectBuilder().
            add("firstName", "Scott").
            add("lastName", "Gosling").
            add("email", "sgosling@example.com").
            build();

    StringWriter stringWriter = new StringWriter();

    try (JsonWriter jsonWriter = Json.createWriter(stringWriter)) {
      jsonWriter.writeObject(jsonObject);
    }

    return stringWriter.toString();

  }

  @Path("parse")
  @POST
  @Produces(MediaType.TEXT_PLAIN)
  @Consumes(MediaType.APPLICATION_JSON)
  public String jsonpModelParseJson(String jsonStr) {
    Customer customer = new Customer();

    JsonObject jsonObject;
    try (JsonReader jsonReader = Json.createReader(new StringReader(jsonStr))) {
      jsonObject = jsonReader.readObject();
    }

    customer.setFirstName(jsonObject.getString("firstName"));
    customer.setLastName(jsonObject.getString("lastName"));
    customer.setEmail(jsonObject.getString("email"));

    return customer.toString();
  }

}
