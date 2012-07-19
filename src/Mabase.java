import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.Display;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
import javax.microedition.rms.RecordStoreNotOpenException;


public class Mabase {
	
	Display display;
	private RecordStore rs = null ;
	static final String NOMBASE = "Mloumadb";
	
	
	public Mabase(String nombase){
		//deleteRecStore();
		openRectStore(nombase);
		
		//WriteTestData("bonjour");
		//ReadStream();
		
		//CloseRectStore();
	}
	

	public void CloseRectStore() {
		try
	    {
	      rs.closeRecordStore();
	    }
	    catch (Exception e)
	    {
	      db("a la fermeture "+e.toString());
	    }
		
	}


	public String[] ReadStream() {
		String[] s=null;
		
		try{
		 s = new String[rs.getNumRecords()];
		 System.out.println(rs.getNumRecords());
		byte[] rectData = new byte[500];
		
		 ByteArrayInputStream strmBytes = new ByteArrayInputStream(rectData);
		 
		 DataInputStream strmDataType = new DataInputStream(strmBytes);
		 
		  for (int i=0; i < rs.getNumRecords(); i++)
	      {
			  System.out.println(i);
	        rs.getRecord(i+1, rectData, 0);
	        int t = strmDataType.readInt()+1;
	        String st =strmDataType.readUTF();
	       System.out.println(st);
	        s[i] = st;
	        strmBytes.reset();
	        System.out.println("la piste "+s[i]);
	        
	      }
		  
		  strmBytes.close();
		  strmDataType.close();
		  
		}catch(Exception e)
		{
			db("a l'insertion "+e.toString());
		}
		
		return s;

	}


	public void openRectStore(String nom) {
		  try
		    {
		      // The second parameter indicates that the record store
		      // should be created if it does not exist
		      rs = RecordStore.openRecordStore(nom, true );
		    }
		    catch (Exception e)
		    {
		 
		      db("a l'ouverture"+e.toString());
		    }
	}


	public void db(String string) {
		 System.err.println("Msg: " + string);
		
	}


	public void WriteTestData(String s,int i,String a,String b,String c,String d) {
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
		}catch (Exception e)
		{
			db("a l'ecriture"+e.toString());
		}
		
	}
	
	
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

	 
	 

		public String ReadStream2(int j) {
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
							   +"\n quantite: "+strmDataType.readUTF()+"\n unite: "+strmDataType.readUTF();
					  System.out.println(s);
					  strmBytes.reset();
				  }
		      }
			  
			  strmBytes.close();
			  strmDataType.close();
			  
			}catch(Exception e)
			{
				db(e.toString());
			} 
			
			return s;

		}
		
		public void deleteRecord(int i ){
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
			
				
				
			} catch (Exception e) {
				db(e.toString());
			}
		}
			
		
		
		
		
		
		
		public int RecordNumber(){
			try {
				return rs.getNumRecords();
			} catch (RecordStoreNotOpenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return 0;
		}
		
}
