package utils;

import java.io.FileReader;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class JSONUtils {

	public static JSONObject getDetailsFromJson(String jsonPath) {
		Object obj = null;
		JSONParser jsonParser = new JSONParser();
		try {
			FileReader fr = new FileReader(jsonPath);
			obj = jsonParser.parse(fr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (JSONObject) obj;
	}

}
