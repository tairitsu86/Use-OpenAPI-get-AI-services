package com.chatGPT.openAPI;

import java.util.HashMap;

import org.json.JSONObject;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class OpenAPI {
	final static String KEY="your key", //the openAPI key
						URL="https://api.openai.com/v1/completions";
	JSONObject requestJson = new JSONObject();
	JSONObject result;
	HashMap<String,String> headers = new HashMap<String, String>(); 
	/*
	public static void main(String[] args) {
		OpenAPI oa = new OpenAPI();
		String result = oa.ask("what time is it?");
		System.out.println(result);
	}
	*/
	public String ask(String question) {
		if(KEY.equals("your key")) {
			System.out.println("你好!因為OpenAPI密碼屬於重要個資，所以我沒有附在程式碼中!\n若想執行程式請使用自己的金鑰!\n謝謝!");
			return "No OpenAPI key!";
		}
		//set the post headers
		headers.put("Content-Type", "application/json");
		headers.put("Authorization", "Bearer "+KEY);
		//set the post body
		requestJson.put("model", "text-davinci-003");
		requestJson.put("prompt", question);
		requestJson.put("temperature", 0.4);
		requestJson.put("max_tokens", 100);
		//send request and get response
		HttpResponse<String> httpResponse;
		try {
			httpResponse = Unirest.post(URL).headers(headers).body(requestJson).asString();
		} catch (UnirestException e) {
			e.printStackTrace();
			return e.toString();
		}
		result = new JSONObject(httpResponse.getBody());
		return result.getJSONArray("choices").getJSONObject(0).getString("text").substring(2);
	}
}
