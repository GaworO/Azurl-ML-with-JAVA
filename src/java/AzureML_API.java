
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @autor Abhishek Karan
 */
public class AzureML_API {

    // public static JSONObject inpParms;
    public static String apikey;
    public static String apiurl;
    public static String jsonBody;
    static String industrial_risk = "";
    static String management_risk = "";
    static String financial_flexibility = "";
    static String credibility = "";
    static String operating_risk = "";
    static String competiteveness = "";

    public AzureML_API(String industrial_risk, String management_risk, String financial_flexibility, String credibility, String operating_risk, String Competiteveness) {
        this.industrial_risk = industrial_risk;
        this.management_risk = management_risk;
        this.financial_flexibility = financial_flexibility;
        this.credibility = credibility;
        this.operating_risk = operating_risk;
        this.competiteveness = Competiteveness;
    }

    /**
     * Read the JSON schema from the file rrsJson.json
     *
     * @param filename It expects a fully qualified file name that contains
     * input JSON file
     */
    public static void readJson() {
        try {
            jsonBody = "{\n"
                    + "        \"Inputs\": {\n"
                    + "                \"input1\":\n"
                    + "                [\n"
                    + "                    {\n"
                    + "                            'Industrial Risk': \"" + industrial_risk + "\",   \n"
                    + "                            'Management Risk': \"" + management_risk + "\",   \n"
                    + "                            'Financial Flexibility': \"" + financial_flexibility + "\",   \n"
                    + "                            'Credibility': \"" + credibility + "\",   \n"
                    + "                            'Competiteveness': \"" + competiteveness + "\",   \n"
                    + "                            'Operating Risk': \"" + operating_risk + "\",   \n"
                    + "                            'Class': \"0\",   \n"
                    + "                    }\n"
                    + "                ],\n"
                    + "        },\n"
                    + "    \"GlobalParameters\":  {\n"
                    + "    }\n"
                    + "}";

            //System.out.println(jsonBody);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    /**
     * Read the API key and API URL of Azure ML request response REST API
     *
     * @param filename fully qualified file name that contains API key and API
     * URL
     */
    public static void readApiInfo() {

        try {

            apiurl = "https://ussouthcentral.services.azureml.net/workspaces/4a00eed9da5240afba0fc4041cf8a076/services/8656bc023c014a19a1bd44695aee48e7/execute?api-version=2.0&format=swagger";//sc.nextLine();
            apikey = "5P5ReV5ALQU0eK4UsxxxUyH028FkBYoPcXX2SGsnDqxRvzpLmPOBtoqI6hslsHLiCRvCNGJ88K3irADccGfcDw==";//sc.nextLine();

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    /**
     * Call REST API for retrieving prediction from Azure ML
     *
     * @return response from the REST API
     */
    public static String rrsHttpPost() {

        HttpPost post;
        DefaultHttpClient client;
        StringEntity entity;
        HttpEntity ent;

        try {
            // create HttpPost and HttpClient object
            post = new HttpPost(apiurl);
            client = new DefaultHttpClient();

            entity = new StringEntity(jsonBody, "UTF-8");

            entity.setContentEncoding("UTF-8");
            entity.setContentType("text/json");

            // add HTTP headers
            post.setHeader("Accept", "text/json");
            post.setHeader("Accept-Charset", "UTF-8");

            // set Authorization header based on the API key
            post.setHeader("Authorization", ("Bearer " + apikey));
            //System.out.println(entity);
            post.setEntity(entity);

            // Call REST API and retrieve response content
            HttpResponse authResponse = client.execute(post);

            return EntityUtils.toString(authResponse.getEntity());

        } catch (Exception e) {

            return e.toString();
        }

    }

    /**
     * @param args the command line arguments specifying JSON and API info file
     * names
     */
    public String getData() throws JSONException {
        JSONObject jo = null;
        try {

            // call method to read API URL and key from API file
            readApiInfo();

            // call method to read JSON input from the JSON file
            readJson();

            jo = new JSONObject(rrsHttpPost());
            jo = jo.getJSONObject("Results");
            JSONArray outp = jo.getJSONArray("Outputsss");
            jo = outp.getJSONObject(0);
            //System.out.println(jo.getString("Scored Labels"));

        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return jo.getString("Scored Labels");
    }
}//class
