package com.codejam.connection;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.MappingJsonFactory;

import com.codejam.trade.Trade;

public class Johnson
{
	public final static String JohnsonHeader = "{\n\"team:\" \"TEAMNAME\",\n\"destination:\" : \"mcgillcodejam2012@gmail.com\",\n\"transactions\" : [";
	public final static String JohnsonTail = "]\n}";

	public static String convertTradeToJohnson(Trade pJohnson)
	{
		String returner = "{\n";
		returner = returner + "\"time\" : \"" + pJohnson.time + "\",\n";
		returner = returner + "\"type\" : \"" + pJohnson.type.toString().toLowerCase() + "\",\n";
		returner = returner + "\"price\" : \"" + pJohnson.price + "\",\n";
		returner = returner + "\"manager\" : \"" + pJohnson.manager + "\",\n"; 
		returner = returner + "\"strategy\" : \"" + pJohnson.strategyType + "\",\n";
		returner = returner + "}";
		return returner;
	}

	public static void printHeader()
	{
		System.out.println(JohnsonHeader);
	}

	public static void printTail()
	{
		System.out.println(JohnsonTail);
	}

	public static void JohnsonPOST(String pData) throws IOException
	{
		String urlParameters = "param1: a&param2: b";
		String request = "https://stage-api.esignlive.com/aws/rest/services/codejam";
		URL url = new URL(request);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setInstanceFollowRedirects(false);
		connection.setRequestMethod("POST");
		connection.setRequestProperty("Authorization", "Basic Y29kZWphbTpBRkxpdGw0TEEyQWQx");
		connection.setRequestProperty("Content-Type", "application/json");
		connection.setUseCaches(false);

		DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
		wr.writeBytes(urlParameters);
		wr.flush();
		wr.writeBytes(pData);
		System.out.println(pData);
		wr.flush();

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String inputLine;

		while ((inputLine = in.readLine()) != null)
			System.out.println(inputLine);
		wr.close();
		connection.disconnect();
	}

	public final void parseJson(final String str)
	{

		JsonFactory f = new MappingJsonFactory();

		// loop until token equal to "}"
		try
		{
			JsonParser jParser = f.createJsonParser(str);
			while (jParser.nextToken() != JsonToken.END_OBJECT)
			{

				String fieldname = jParser.getCurrentName();
				if ("ceremonyId".equals(fieldname))
				{

					// current token is "name",
					// move to next, which is "name"'s value
					jParser.nextToken();
					System.out.println(jParser.getText()); // display mkyong

				}
			}
		} catch (JsonParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException
	{
		// System.out.println(JohnsonHeader);
		// System.out.println(convertTradeToJohnson(new Trade(123, "100",
		// TradeType.BUY, StrategyType.SMA, "Manager1")));
		// System.out.println(JohnsonTail);
		JohnsonPOST("");
		// Map<String, String> params = new HashMap<String, String>();
		// params.put("param1", "HELLO");
		// sendHttpRequest("http://tlwr.org/codejam/johnson.php", "POST",
		// params);
	}
}
