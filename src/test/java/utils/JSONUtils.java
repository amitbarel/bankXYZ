package utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONUtils {
	
	JSONObject getDetailsFromJson(String jsonPath) throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		FileReader fr = new FileReader(jsonPath);
		Object obj = jsonParser.parse(fr);
		return (JSONObject) obj;
	}
	
	

}
