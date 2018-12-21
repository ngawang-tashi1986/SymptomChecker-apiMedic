package com.symptomchecker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.symptomchecker.AccessToken;

import Decoder.BASE64Encoder;

/**
 * Servlet implementation class TokenGenerator
 */
@WebServlet("/TokenGenerator")
public class TokenGeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String authServiceUrl = ""; 
	private String username = "";
	private String password = "";
	private CloseableHttpClient httpclient;
	private AccessToken token;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TokenGeneratorServlet() {
        //super();
        // TODO Auto-generated constructor stub
    	authServiceUrl = "https://authservice.priaid.ch/login"; 
    	username = "r2X3Y_GMAIL_COM_AUT";
    	password = "Js24SrFo76Lzm5P8Z";
    }
    
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("This is inside the sevlet dopost");
		SecretKeySpec keySpec = new SecretKeySpec(password.getBytes(),"HmacMD5");
		
		String computedHashString = "";
		try {
			Mac mac = Mac.getInstance("HmacMD5");
			mac.init(keySpec);
			byte[] result = mac.doFinal(authServiceUrl.getBytes());
			
	        BASE64Encoder encoder = new BASE64Encoder();
	        computedHashString = encoder.encode(result); 
	        
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		
		HttpPost httpPost = new HttpPost(authServiceUrl);	
		httpPost.setHeader("Authorization", "Bearer " + username + ":" + computedHashString);
		
		DefaultHttpClient client = new DefaultHttpClient();
		HttpResponse httpresponse = client.execute(httpPost);
		int statusCode = httpresponse.getStatusLine().getStatusCode();
		
		if(statusCode == 200){
			BufferedReader rd = new BufferedReader(new InputStreamReader(httpresponse.getEntity().getContent()));
			StringBuffer result = new StringBuffer();
			String line = "";
			
			while((line = rd.readLine()) != null){
				result.append(line);
			}
			
			JsonParser parser = new JsonParser();
			JsonObject json = (JsonObject) parser.parse(result.toString());
			
			String accessToken = json.get("Token").getAsString();
			HttpSession session = request.getSession();
			session.setAttribute("token", accessToken);
		}
	}

}
