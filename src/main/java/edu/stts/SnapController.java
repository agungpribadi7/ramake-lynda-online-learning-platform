package edu.stts;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.json.JSONObject;

import com.midtrans.Config;
import com.midtrans.ConfigFactory;
import com.midtrans.httpclient.error.MidtransError;
import com.midtrans.service.MidtransCoreApi;
import com.midtrans.service.MidtransSnapApi;

public class SnapController {
	private MidtransSnapApi snapApi = new ConfigFactory(new Config("SB-Mid-server-XYC8LOLjHP8kgG8QsiRc3bsG", "SB-Mid-client-0dvSs2kozHsFjvLJ", false)).getSnapApi();
	private MidtransCoreApi coreApi = new ConfigFactory(new Config("SB-Mid-server-XYC8LOLjHP8kgG8QsiRc3bsG", "SB-Mid-client-0dvSs2kozHsFjvLJ", false)).getCoreApi();
	
	public Map<String, Object> requestBody(String harga) {
	    UUID idRand = UUID.randomUUID();
	    Map<String, Object> params = new HashMap<>();
	    
	    Map<String, String> transactionDetails = new HashMap<>();
	    transactionDetails.put("order_id", idRand+"");
	    transactionDetails.put("gross_amount", harga.toString());
	    Map<String, String> creditCard = new HashMap<>();
	    creditCard.put("secure", "true");
	    params.put("transaction_details", transactionDetails);
	    params.put("test", "test");
	    params.put("credit_card", creditCard);
	    
	    return params;
	}
	
	public String getToken(String harga) throws MidtransError {
		return snapApi.createTransactionToken(requestBody(harga));
	}
	
	
}
