package tiparire.model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class HandleJSONReceivedData {

	private String JsonString;
	private JSONArray jsonObject;

	public HandleJSONReceivedData(String JsonString) {
		this.JsonString = JsonString;
	}

	public void decodeLogonInfo() {

		UserInfo userInfo = UserInfo.getInstance();
		try {

			JSONObject jsonObject = new JSONObject(JsonString);

			userInfo.setLogonStatus(jsonObject.get("logonStatus").toString());
			userInfo.setDepart(jsonObject.get("departament").toString());
			userInfo.setFiliala(jsonObject.get("filiala").toString());
			userInfo.setTipAcces(jsonObject.get("tipAcces").toString());
			userInfo.setId(jsonObject.get("codUser").toString());
			userInfo.setNume(jsonObject.get("numeUser").toString());
			userInfo.setUnitLog(getFiliala(userInfo.getFiliala()));
			userInfo.setInitDepart(jsonObject.get("departament").toString());

		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

	public ArrayList<Articol> decodeDocumentData() {
		Articol unArticol = null;
		ArrayList<Articol> objectsList = new ArrayList<Articol>();

		try {

			Object json = new JSONTokener(JsonString).nextValue();

			if (json instanceof JSONArray) {
				jsonObject = new JSONArray(JsonString);

				for (int i = 0; i < jsonObject.length(); i++) {
					JSONObject articolObject = jsonObject.getJSONObject(i);

					unArticol = new Articol();
					unArticol.setDocumentId(articolObject.getString("id"));
					unArticol.setClient(articolObject.getString("client"));
					unArticol.setEmitere(articolObject.getString("emitere"));
					unArticol.setCod(articolObject.getString("codArticol"));
					unArticol.setNume(articolObject.getString("numeArticol"));
					unArticol.setCantitate(articolObject.getString("cantitate"));
					unArticol.setUm(articolObject.getString("um"));
					unArticol
							.setPozitie(String.valueOf(Integer.valueOf(articolObject.getString("pozitieArticol")) / 10));

					objectsList.add(unArticol);

				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}

		return objectsList;

	}

	public String getFiliala(String numeFiliala) {
		String fl = "NN10";

		if (numeFiliala.equals("BACAU"))
			fl = "BC10";

		if (numeFiliala.equals("GALATI"))
			fl = "GL10";

		if (numeFiliala.equals("PITESTI"))
			fl = "AG10";

		if (numeFiliala.equals("TIMISOARA"))
			fl = "TM10";

		if (numeFiliala.equals("ORADEA"))
			fl = "BH10";

		if (numeFiliala.equals("FOCSANI"))
			fl = "VN10";

		if (numeFiliala.equals("GLINA"))
			fl = "BU10";

		if (numeFiliala.equals("ANDRONACHE"))
			fl = "BU13";

		if (numeFiliala.equals("OTOPENI"))
			fl = "BU12";

		if (numeFiliala.equals("CLUJ"))
			fl = "CJ10";

		if (numeFiliala.equals("BAIA"))
			fl = "MM10";

		if (numeFiliala.equals("MILITARI"))
			fl = "BU11";

		if (numeFiliala.equals("CONSTANTA"))
			fl = "CT10";

		if (numeFiliala.equals("BRASOV"))
			fl = "BV10";

		if (numeFiliala.equals("PLOIESTI"))
			fl = "PH10";

		if (numeFiliala.equals("PIATRA"))
			fl = "NT10";

		if (numeFiliala.equals("MURES"))
			fl = "MS10";

		if (numeFiliala.equals("IASI"))
			fl = "IS10";

		if (numeFiliala.equals("CRAIOVA"))
			fl = "DJ10";

		return fl;

	}

}