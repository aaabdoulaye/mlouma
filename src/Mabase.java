/**
 * @author A. Abdoul Aziz
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreNotOpenException;


public class Mabase
{
	
	Display display;
	private RecordStore rs = null ;
	
	
	public Mabase(String nombase)
	{
		openRectStore(nombase);
	}
	
	// fermeture de l'enregistrement
	public void CloseRectStore() 
	{
		try
	    {
			rs.closeRecordStore();
	    }
	    catch (Exception e)
	    {
	    	db(e.toString());
	    }
		
	}
	
	

	// recuperation de tous les enregistrements 
	public String[] ReadStream() {
		String[] s=null;
		
		try
		{
			s = new String[rs.getNumRecords()];
			System.out.println(rs.getNumRecords());
			byte[] rectData = new byte[500];
		
			ByteArrayInputStream strmBytes = new ByteArrayInputStream(rectData);
		 
			DataInputStream strmDataType = new DataInputStream(strmBytes);
		 
			for (int i=0; i < rs.getNumRecords(); i++)
			{
				System.out.println(i);
				rs.getRecord(i+1, rectData, 0);
				String st =strmDataType.readUTF();
				s[i] = st;
				strmBytes.reset();
				System.out.println("la piste "+s[i]);
	        
			}
		  
			strmBytes.close();
			strmDataType.close();
		  
			} 
			catch(Exception e)
			{
				db(e.toString());
			}
		
			return s;

		}
	
	

	// ouverture de l'enregistrement dont le nom a été passé en parametre
	public void openRectStore(String nom)
	{
		  try
		    {
		      rs = RecordStore.openRecordStore(nom, true );
		    }
		    catch (Exception e)
		    {
		      db("a l'ouverture"+e.toString());
		    }
	}
	


	public void db(String string)
	{
		 System.err.println("Msg: " + string);
		
	}

	// enregistrement des données 
	public void WriteTestData(String s,int i,String a,String b,String c,String d,String t)
	{
		try 
		{
			ByteArrayOutputStream strmBytes = new ByteArrayOutputStream();
			DataOutputStream strmDataType = new DataOutputStream(strmBytes);
			
			byte[] record;
			
			strmDataType.writeInt(i);
			strmDataType.writeUTF(s);
			strmDataType.writeUTF(a);
			strmDataType.writeUTF(b);
			strmDataType.writeUTF(c);
			strmDataType.writeUTF(d);
			strmDataType.writeUTF(t);
			strmDataType.flush();
			
			record = strmBytes.toByteArray();
			rs.addRecord(record, 0, record.length);
			
			Alert alert = new Alert("Alert");
        	alert.setString("la donnée a été enregistré");
        	alert.setTimeout(2000);
        	display.setCurrent(alert);
			strmBytes.reset();
			
			strmBytes.close();
			strmDataType.close();
		} 
		catch (Exception e)
		{
			db(e.toString());
		}
		
	}
	
	// suppression tout les enregeristrement d'une base donné 
	 public void deleteRecStore(String s)
	  {
		 if (RecordStore.listRecordStores() != null)
		 {
			 try
			 {
				 RecordStore.deleteRecordStore(s);
	        
			 }
			 catch (Exception e)
			 {
				 db(e.toString());
			 }
		 }      
	  }

	 
	 
	 // lecture d'un enregistrement
		public String ReadStream2(int j)
		{
			String s=null;
			try{
			byte[] rectData = new byte[500];
			ByteArrayInputStream strmBytes = new ByteArrayInputStream(rectData);	 
			DataInputStream strmDataType = new DataInputStream(strmBytes);
			 
			  for (int i = 1; i <= rs.getNumRecords(); i++)
		      {
				  if(i==j)
				  {
					  rs.getRecord(i, rectData, 0);
					 
					  int t =strmDataType.readInt()+1;
					  s ="numero prduit: "+t+"\n nom: "+strmDataType.readUTF()
							   +"\n date: "+strmDataType.readUTF()+"\n localite: "+strmDataType.readUTF()
							   +"\n quantite: "+strmDataType.readUTF()+"\n unite: "+strmDataType.readUTF()
							   +"\n transport: "+strmDataType.readUTF();
					  strmBytes.reset();
				  }
		      }
			  
			  strmBytes.close();
			  strmDataType.close();
			  
			}
			catch(Exception e)
			{
				db(e.toString());
			} 
			
			return s;

		}
		
		
		// supression  d'un enregistrement donné (code à revoir)
		public void deleteRecord(int i )
		{
			try {
				ByteArrayOutputStream strmBytes = new ByteArrayOutputStream();
				DataOutputStream strmDataType = new DataOutputStream(strmBytes);
				
				byte[] record;
				
				strmDataType.writeInt(i);
				strmDataType.writeUTF("<supprimé>");
				strmDataType.writeUTF("");
				strmDataType.writeUTF("");
				strmDataType.writeUTF("");
				strmDataType.writeUTF("");
				strmDataType.flush();
					
				record = strmBytes.toByteArray();
					
				rs.setRecord(i+1,record, 0, record.length);
					
				Alert alert = new Alert("Alert");
		        alert.setString("la donnée a été supprimé");
		        alert.setTimeout(2000);
		        display.setCurrent(alert);
					
				strmBytes.reset();
				strmBytes.close();
				strmDataType.close();
							
			}
			catch (Exception e) 
			{
				db(e.toString());
			}
		}
			
		// mise a jour d'un enregistrement
		public void updateRecord(int i,String p,String d,String l, String q,String u,String t)
		{
			try 
			{
				ByteArrayOutputStream strmBytes = new ByteArrayOutputStream();
				DataOutputStream strmDataType = new DataOutputStream(strmBytes);
				
				byte[] record;
				
				strmDataType.writeInt(i);
				strmDataType.writeUTF(p);
				strmDataType.writeUTF(d);
				strmDataType.writeUTF(l);
				strmDataType.writeUTF(q);
				strmDataType.writeUTF(u);
				strmDataType.writeUTF(t);
				strmDataType.flush();
					
				record = strmBytes.toByteArray();
					
				rs.setRecord(i,record, 0, record.length);
					
					
				strmBytes.reset();
				strmBytes.close();
				strmDataType.close();
				
			}
			
			// message permettant d'afficher les erreurs 
			catch (Exception e) 
			{
				db(e.toString());
			}
			
		}
		
		// methode permettant de recuperer le nombre d'enregistrement d'une base
		public int RecordNumber()
		{
			try 
			{
				return rs.getNumRecords();
			} 
			catch (RecordStoreNotOpenException e) 
			{
				e.printStackTrace();
			}
			return 0;
		}
		
}
