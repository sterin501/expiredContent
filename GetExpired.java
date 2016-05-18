import java.io.*;
import oracle.stellent.ridc.*;
import oracle.stellent.ridc.model.*;
import oracle.stellent.ridc.protocol.*;
import oracle.stellent.ridc.protocol.intradoc.*;
import oracle.stellent.ridc.common.log.*;
import oracle.stellent.ridc.model.serialize.*;
import java.util.Properties;

/*
 * @author Sterin- Oracle Inc
 * 
 * This is a class used to test the basic functionality
 * of submitting a search to Content Server using RIDC.  
 * The response is then used to retrieve metadata about
 * the content items.  
 */

public class GetExpired {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		IdcClientManager manager = new IdcClientManager ();

                  	Properties prop = new Properties();
	                InputStream input = null;
                    
               

                    
		try{
			

               input = new FileInputStream("config.properties");
 
		// load a properties file
		prop.load(input);


       // Create a new IdcClient Connection using idc protocol (i.e. socket connection to Content Server)
			IdcClient idcClient = manager.createClient (prop.getProperty("url"));

                       IdcContext userContext = new IdcContext (prop.getProperty("user"),prop.getProperty("password"));

			// Create an HdaBinderSerializer; this is not necessary, but it allows us to serialize the request and response data binders
			HdaBinderSerializer serializer = new HdaBinderSerializer ("UTF-8", idcClient.getDataFactory ());
			
			// Create a new binder for submitting a search
			DataBinder dataBinder = idcClient.createBinder();
       
                        dataBinder.putLocal("IdcService", "GET_EXPIRED");



                          dataBinder.putLocal("startDate",prop.getProperty("startDate"));

           

                                 dataBinder.putLocal("endDate",prop.getProperty("endDate"));

                                 dataBinder.putLocal("isExpiredQuery","1");

                                dataBinder.putLocal("isQueryResult","1");

                                   dataBinder.putLocal("expiredQuery","on");

                                     dataBinder.putLocal("option2","");
                                    dataBinder.putLocal("submit1","Search");
                                     dataBinder.putLocal("option3","4/20/16 6:18 PM");
                                           


           
            // Send the request to Content Server
            ServiceResponse response = idcClient.sendRequest(userContext,dataBinder);
            
            // Get the data binder for the response from Content Server
            DataBinder responseData = response.getResponseAsBinder();
            // Write the response data binder to stdout
      //      serializer.serializeBinder (System.out, responseData);
            // Retrieve the SearchResults ResultSet from the response
            DataResultSet resultSet = responseData.getResultSet("EXPIRED_LIST");
           
 File file = new File("contentID.txt");

if (!file.exists()) {
				file.createNewFile();
		}     
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);         
 
            // Iterate over the ResultSet, retrieve properties from the content items
            for (DataObject dataObject : resultSet.getRows ()) {
              // to check the content status GENWW
               
                System.out.println ("ContentID is : " + dataObject.get ("dDocName")+ " " +dataObject.get ("dID") );
                bw.write(dataObject.get ("dDocName") + " " + dataObject.get ("dID") + "\n");
               
            }
bw.close();
            

			
		} catch (IdcClientException ice){
			ice.printStackTrace();
		} catch (IOException ioe){
			ioe.printStackTrace();
		}
	}

}
