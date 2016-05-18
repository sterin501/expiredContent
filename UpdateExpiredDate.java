import java.io.*;
import oracle.stellent.ridc.*;
import oracle.stellent.ridc.model.*;
import oracle.stellent.ridc.protocol.*;
import oracle.stellent.ridc.protocol.intradoc.*;
import oracle.stellent.ridc.common.log.*;
import oracle.stellent.ridc.model.serialize.*;
import java.util.Properties;

/**
 * Stein - Oracle
 * To do the mass update of metadata using UPDATE_DOCINFO
 * ContentIDs will read from dID.txt
 * config settings will be read from config.properties file
 */
public class UpdateExpiredDate {

  public static void main(String[] args) {

                     		IdcClientManager manager = new IdcClientManager ();

                  	Properties prop = new Properties();
	                InputStream input = null;

    File file = new File("contentID.txt");  // Reading content dIDs 
    FileInputStream fis = null;
    BufferedInputStream bis = null;
    DataInputStream dis = null;

    try {
      fis = new FileInputStream(file);

      // Here BufferedInputStream is added for fast reading.
      bis = new BufferedInputStream(fis);
      dis = new DataInputStream(bis);


                                  

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

                                              
      while (dis.available() != 0) {

                          // Sending the dIDs 
 	    String [] tokens = dis.readLine().split("\\s+");

                            System.out.println("Updating "+ tokens[0] + "\n\n");
                        dataBinder.putLocal("IdcService", "UPDATE_DOCINFO");
                        dataBinder.putLocal ("dID", tokens[1]);
                        dataBinder.putLocal ("dDocName",tokens[0]);
                        dataBinder.putLocal("dOutDate",prop.getProperty("dOutDate"));
                     
                                      // ("yyyy-MM-dd HH:mm:ss");
   
            
            // Write the data binder for the request to stdout
            serializer.serializeBinder (System.out, dataBinder);
           
            // Send the request to Content Server
            ServiceResponse response = idcClient.sendRequest(userContext,dataBinder);

            // Get the data binder for the response from Content Server
            DataBinder responseData = response.getResponseAsBinder();
            // Write the response data binder to stdout
        //    serializer.serializeBinder (System.out, responseData);
     
                    try {
            // thread to sleep for 4000 milliseconds
            Thread.sleep(4000);
         } catch (Exception e) {
              System.out.println(e);
           }
                                        }

      // dispose all the resources after using them.
      fis.close();
      bis.close();
      dis.close();

                                       



		} catch (IdcClientException ice){
			ice.printStackTrace();
                                    } catch (FileNotFoundException ioe) {
     ioe.printStackTrace();
    }
     
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}

