package com.ensode.jakartaeebook.jsonpointer;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonPointer;
import jakarta.json.JsonReader;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.io.StringReader;

@Path("jsonpointer")
public class JsonPointerDemoService {

  private String jsonString;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String jsonPointerDemo() {
    initializeJsonString();
    JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
    JsonArray jsonArray = jsonReader.readArray();
    JsonPointer jsonPointer = Json.createPointer("/1/lastName");

    return jsonPointer.getValue(jsonArray).toString();
  }

  private void initializeJsonString() {
    jsonString = """
                 [
                   {
                     "dateOfBirth": "1997-01-01",
                     "firstName": "David",
                     "lastName": "Delabassee",
                     "salutation": "Mr"
                   },
                   {
                     "dateOfBirth": "1997-03-03",
                     "firstName": "David",
                     "lastName": "Heffelfinger",
                     "middleName": "Raymond",
                     "salutation": "Mr"
                   }
                 ]""";
  }
}
