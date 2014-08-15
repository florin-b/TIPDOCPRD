package tiparire.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.ksoap2.HeaderProperty;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class WebService {

	public WebService() {

	}

	public void getDocsList() {

	}

	public String performLogon(String username, String password) throws IOException, XmlPullParserException {

		SoapObject request = new SoapObject(ConnectionStrings.getInstance().getNamespace(), "userLogon");

		request.addProperty("userId", username);
		request.addProperty("userPass", password);
		request.addProperty("ipAdr", "-1");

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(ConnectionStrings.getInstance().getUrl(), 20000);
		List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
		headerList.add(new HeaderProperty("Authorization", "Basic "
				+ org.kobjects.base64.Base64.encode("bflorin:bflorin".getBytes())));
		androidHttpTransport.call(ConnectionStrings.getInstance().getNamespace() + "userLogon", envelope, headerList);
		Object result = (Object) envelope.getResponse();
		String response = result.toString();

		return response;

	}

	public String getDocumente() throws IOException, XmlPullParserException {

		SoapObject request = new SoapObject(ConnectionStrings.getInstance().getNamespace(), "getDocumente");

		request.addProperty("filiala", UserInfo.getInstance().getUnitLog());
		request.addProperty("departament", Utils.getDepartCode(UserInfo.getInstance().getDepart()));

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(ConnectionStrings.getInstance().getUrl(), 40000);
		List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
		headerList.add(new HeaderProperty("Authorization", "Basic "
				+ org.kobjects.base64.Base64.encode("bflorin:bflorin".getBytes())));
		androidHttpTransport
				.call(ConnectionStrings.getInstance().getNamespace() + "getDocumente", envelope, headerList);
		Object result = (Object) envelope.getResponse();
		String response = result.toString();

		return response;

	}

	public String getDocumenteTiparite(String dataTip) throws IOException, XmlPullParserException {

		SoapObject request = new SoapObject(ConnectionStrings.getInstance().getNamespace(), "getDocumenteTiparite");

		request.addProperty("filiala", UserInfo.getInstance().getUnitLog());
		request.addProperty("departament", Utils.getDepartCode(UserInfo.getInstance().getDepart()));
		request.addProperty("dataTip", dataTip);

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		HttpTransportSE androidHttpTransport = new HttpTransportSE(ConnectionStrings.getInstance().getUrl(), 40000);
		List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
		headerList.add(new HeaderProperty("Authorization", "Basic "
				+ org.kobjects.base64.Base64.encode("bflorin:bflorin".getBytes())));
		androidHttpTransport
				.call(ConnectionStrings.getInstance().getNamespace() + "getDocumenteTiparite", envelope, headerList);
		Object result = (Object) envelope.getResponse();
		String response = result.toString();

		return response;

	}

	public String addDocumentTiparit(List<Document> documentTiparit) throws IOException, XmlPullParserException,
			JSONException {
		SoapObject request = new SoapObject(ConnectionStrings.getInstance().getNamespace(), "setPrintedDocuments");

		JSONArray docsList = new JSONArray(documentTiparit);

		request.addProperty("listaDocumente", docsList.toString());
		request.addProperty("gestionar", UserInfo.getInstance().getId());
		request.addProperty("departament", Utils.getDepartCode(UserInfo.getInstance().getDepart()));
		request.addProperty("filiala", UserInfo.getInstance().getUnitLog());

		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);

		envelope.dotNet = true;
		envelope.setOutputSoapObject(request);
		envelope.addMapping(ConnectionStrings.getInstance().getNamespace(), "Document", new Document().getClass());

		HttpTransportSE androidHttpTransport = new HttpTransportSE(ConnectionStrings.getInstance().getUrl(), 40000);
		List<HeaderProperty> headerList = new ArrayList<HeaderProperty>();
		headerList.add(new HeaderProperty("Authorization", "Basic "
				+ org.kobjects.base64.Base64.encode("bflorin:bflorin".getBytes())));
		androidHttpTransport.call(ConnectionStrings.getInstance().getNamespace() + "setPrintedDocuments", envelope,
				headerList);
		Object result = (Object) envelope.getResponse();
		String response = result.toString();

		return response;
	}

}