package com.ensode.jakartaeebook.jsonpatch;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonPatch;
import jakarta.json.JsonReader;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.StringReader;

@Path("jsonpatch")
public class JsonPatchDemoService {

  private String jsonString;

  @GET
  public Response jsonPatchDemo() {
    initializeJsonString();
    JsonReader jsonReader = Json.createReader(new StringReader(jsonString));
    JsonArray jsonArray = jsonReader.readArray();
    JsonPatch jsonPatch = Json.createPatchBuilder()
            .replace("/1/dateOfBirth", "1977-01-01")
            .build();
    JsonArray modifiedJsonArray = jsonPatch.apply(jsonArray);

    return Response.ok(modifiedJsonArray.toString(), MediaType.APPLICATION_JSON).build();
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
