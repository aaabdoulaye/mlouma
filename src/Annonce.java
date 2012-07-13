

/**
 *
 * @author Ndjido A BAR
 */
public class Annonce implements JSONable
{
    private Integer _Id;
    private String  _produit;
    private Integer _quantite;
    private String  _unite;
    private Integer _prix;
    private String  _lieu;
    private boolean _transport = false;

    public Integer getId() {
        return _Id;
    }

    public void setId(Integer _Id) {
        this._Id = _Id;
    }

    public String getLieu() {
        return _lieu;
    }

    public void setLieu(String _lieu) {
        this._lieu = _lieu;
    }

    public Integer getPrix() {
        return _prix;
    }

    public void setPrix(Integer _prix) {
        this._prix = _prix;
    }

    public String getProduit() {
        return _produit;
    }

    public void setProduit(String _produit) {
        this._produit = _produit;
    }

    public Integer getQuantite() {
        return _quantite;
    }

    public void setQuantite(Integer _quantite) {
        this._quantite = _quantite;
    }

    public boolean isTransport() {
        return _transport;
    }

    public void setTransport(boolean _transport) {
        this._transport = _transport;
    }

    public String getUnite() {
        return _unite;
    }

    public void setUnite(String _unite) {
        this._unite = _unite;
    }

	public String toJSON() {
		// TODO Auto-generated method stub
		return null;
	}

	public void fromJSON(String jsonString) {
		// TODO Auto-generated method stub
		
	}


}
