package bot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.json.JSONObject;

public class Weather {
	//токен подключения к сервису погоды
	private static final String API_KEY = "eb38e12545146324827085a5262915dc";
	//шаблон запроса
	private static final String REQUEST_URL = 
			"http://api.openweathermap.org/data/2.5/weather?" +
            "lang=ru&" +
            "units=metric&" +
            "q=%s&" +
            "appid=%s";
	
	public static String getWeather(String city) throws IOException
	{
		//формирование строки запроса: подставляем город и токен
		String requestUrl = String.format(REQUEST_URL, city,API_KEY);
		
		// получение запроса
		URL url = new URL(requestUrl);
		//получение ответа
		URLConnection connection =  url.openConnection();
		
		//извлекать данные с ответа
		InputStream is = connection.getInputStream();
		InputStreamReader isr = new InputStreamReader(is);
		BufferedReader br = new BufferedReader(isr);
		StringBuffer buf = new StringBuffer();
		
		//строки ответа в текстовом буфере
		br.lines().forEach(n->buf.append(n));
		

		String result = buf.toString();
		//десериализация из JSON-строки в объект
		JSONObject json = new JSONObject(result);
		Map<String,Object> jsonMap = json.toMap();
		Map<String,Object> mainMap = (Map<String, Object>) jsonMap.get("main");
		//Map<String,Object> weatherMap=(Map<String,Object>) jsonMap.get("weather");
		int temp;
		if(mainMap.get("temp") instanceof BigDecimal)
		{
			temp=((BigDecimal)mainMap.get("temp")).intValue();
		}else {
			temp = (Integer) mainMap.get("temp");
		}
		int pressure = (Integer) mainMap.get("pressure");
		int humidity = (Integer) mainMap.get("humidity");
		//String des = (String) weatherMap.get("description");

		city = (String) jsonMap.get("name");
		if(temp>0)
		{
			result = String.format("текущая температура в городе %s: +%d, давление = %d, влажность = %d ", city,temp,pressure, humidity);
		}else {
			result = String.format("текущая температура в городе %s: %d давление = %d, влажность = %d", city,temp,pressure, humidity);
		}
		
		return result;
	}
	
	//плюс к температуре добавить
	//извлечь из джейсона что то еще
	//если пользователь вводит неправильные данные
	//залить на репозиторий на гитхабе + ссылка на репозиторий скинуть

}
