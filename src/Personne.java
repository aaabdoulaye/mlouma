
public class Personne implements JSONable {
	 
	private String nom;
	private String prenom;
	private String localite;
	private String region;
	private int sexe; 
	private String adresse;
	private String mail;

	public Personne(){

	}

	public void setNom(String n ){
		this.nom=n;
	}

	public void setPrenom(String p ){
		this.prenom=p;
	}

	public void setLocalite(String l ){
		this.localite=l;
	}

	public void setRegion(String r ){
		this.region=r;
	}

	public void setSexe(int s ){
		this.sexe=s;
	}
	
	public void setMail(String m ){
		this.mail=m;
	}

	public void setAdresse(String a ){
		this.adresse=a;
	}

	public String getNom(){
		return this.nom; 
	}

	public String getPrenom(){
		return this.prenom; 
	}

	public String getLocalite(){
		return this.localite; 
	}

	public String getRegion(){
		return this.region; 
	}

	public String getAdresse(){
		return this.adresse; 
	}

	public String getMail(){
		return this.mail; 
	}

	public int getSexe(){
		return this.sexe; 
	}

	public void fromJSON(String jsonString) {
		try {
			JSONObject json = new JSONObject(jsonString);
			setNom(json.getString("nom"));
			setPrenom(json.getString("Prenom"));
			setLocalite(json.getString("localite"));
			setRegion(json.getString("region"));
			setMail(json.getString("mail"));
			setAdresse(json.getString("adresse"));
			setSexe(json.getInt("sexe"));
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}
	public String toJSON() {
		JSONObject inner = new JSONObject();
		try {
			inner.put("N", getNom());
			inner.put("PN", getPrenom());
			inner.put("S",getSexe());
			inner.put("RG",getRegion());
			inner.put("LC", getLocalite());
			inner.put("AD", getAdresse());
			inner.put("EM", getMail());
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
		return inner.toString();
	}
}




