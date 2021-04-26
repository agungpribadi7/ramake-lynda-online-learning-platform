package edu.stts;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class coba
 */

@WebServlet("/coba")
public class coba extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public coba() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		JSONObject obj = new JSONObject(request);
//		String paymentcode = obj.getString("payment_code");
//		String hargaString = obj.getString("gross_amount");
//		System.out.println("harga: "+obj.getString("gross_amount"));
//		System.out.println("test: "+obj.getString("payment_code"));
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		JSONObject obj = new JSONObject(request);
//		String paymentcode = obj.getString("payment_code");
//		String hargaString = obj.getString("gross_amount");
//		System.out.println("harga: "+obj.getString("gross_amount"));
//		System.out.println("test: "+obj.getString("payment_code"));
	}

}
