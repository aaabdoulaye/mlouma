/**
 * 
 * @author A. abdoul aziz 
 *
 */


public class Produit implements JSONable
{
	private String _ldp;
	private String _nomProduit;
	private String _quantite;
	private String _type;
	private String _unite;
	private String _date; 
	private int _transport;
	
	public Produit()
	{
		
	}
	
	public String getLdp()
	{
		return _ldp;
	}
	public String getNom()
	{
		return _nomProduit;
	}
	public String getQuantite()
	{
		return _quantite;
	}
	public String getType()
	{
		return _type;
	}
	public String getUnite()
	{
		return _unite; 
	}
	public String getDate()
	{
		return _date;
	}
	
	public int getTransport()
	{
		return _transport;
	}
	
	public void SetLdp(String l){
		this._ldp = l;
	}
	public void SetNom(String n){
		this._nomProduit = n;
	}
	public void SetQuantite( String q){
		this._quantite = q ;
	}
	public void SetType(String t){
		this._type = t;
	}
	public void SetUnite(String u){
		this._unite = u;
	}
	public void SetDate(String d){
		this._date = d;
	}
	
	public void setTransport(int t)
	{
		this._transport = t;
	}

	public String toJSON() 
	{
		JSONObject inner = new JSONObject();
		try {
			inner.put("LDP", getLdp());
			inner.put("NDP", getNom());
			inner.put("QTe", getQuantite());
			inner.put("TY", getType());
			inner.put("UT", getUnite());
			inner.put("DPD", getDate());
			inner.put("TR", getTransport());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return inner.toString(); 
		
	}

	public void fromJSON(String jsonString) 
	{
		
		
	}

}
