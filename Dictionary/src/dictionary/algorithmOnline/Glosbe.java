package dictionary.algorithmOnline;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

// https://glosbe.com/gapi/translate?from=eng&dest=vi&format=json&phrase=next&pretty=true
public class Glosbe {

    private String dest = "vi";
    private String from = "eng";
    private final String base = "https://glosbe.com/gapi/translate?";
    private final String userAgent = "Mozilla/5.0 (iPhone; U; CPU iPhone OS 3_0 like Mac OS X; en-us) AppleWebKit/528.18 (KHTML, like Gecko) Version/4.0 Mobile/7A341 Safari/528.16";

    public Glosbe() {

    }

    public void setDestFrom(String from, String dest) {
        this.dest = dest;
        this.from = from;
    }

    public String urlBuilder(String query) {
        // builder
        StringBuilder url = new StringBuilder(base);
        url.append("from=").append(this.from);
        url.append("&dest=").append(this.dest);
        url.append("&format=json");
        url.append("&pretty=true");
        url.append("&phrase=");

        String queryEncoded = "";
        try {
            queryEncoded = URLEncoder.encode(query, "UTF-8");
        } catch (Exception e) {
            e.getMessage();
        }

        url.append(queryEncoded);
        return url.toString();
    }

    public String getResponse(String query) throws MalformedURLException, IOException, ParseException {
        // Action with JSOn
        URL url = new URL(this.urlBuilder(query));
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.addRequestProperty("User-Agent", this.userAgent);
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(1000);
        try {
            conn.connect();
        } catch (Exception e) {
            e.getMessage();
            return null;
        }

        StringBuilder data;
        try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            String inputLine;
            data = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                data.append(inputLine);
            }
        }

        JSONParser parser = new JSONParser();
        JSONObject newObject = (JSONObject) parser.parse(data.toString());
        JSONArray meanings = (JSONArray) newObject.get("tuc");
        String mean = "";
        int i = 1;
        for (Object meaning : meanings) {
            try {
                String s = ((JSONObject) ((JSONObject) meaning).get("phrase")).get("text").toString();
                mean = mean + "<span style=\"font-size: 12px\">" + (i++) + "</span>" + " : " + s + (i % 4 == 0 ? "<br>" : "     ");
            } catch (Exception e) {

            }
        }
        if (!mean.trim().equals(""))
            return "<pre>" + mean + "</pre>" + "<br><span style=\"color:blue; font-size:15px\">NGUỒN : Glosbe.com</span>";
        else
            return null;

    }
}
