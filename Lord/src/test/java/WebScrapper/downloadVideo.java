package WebScrapper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class downloadVideo {
	
	public downloadVideo() {
		
		
		File file = new File("C:\\Users\\Philipp Bleimund\\Videos\\derOger\\test2.webm");
		URL url;
		try {
			url = new URL("https://ogermirror.tk/youtube/NWAit7Yqm0E.webm");
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
			//Add this right after you initialize httpUrlConnection but before beginning download
			if(file.exists())
			    httpUrlConnection.setRequestProperty("Range", "bytes="+file.length()+"-");
			//...any other httpUrlConnection setup, such as setting headers
			BufferedInputStream in = new BufferedInputStream(httpUrlConnection.getInputStream());
			FileOutputStream fos;
			//And then you'd initialize the file output stream like so:
			if(file.exists())
			    fos = new FileOutputStream(file, true); //resume download, append to existing file
			else
			    fos = new FileOutputStream(file);
			BufferedOutputStream bout = new BufferedOutputStream(fos, 1024);
			try
			{
			    byte[] data = new byte[1024];
			    int x = 0;
			    while ((x = in.read(data, 0, 1024)) >= 0) 
			    {
			        bout.write(data, 0, x);
			    }
			}
			catch(Exception e)
			{
			    e.printStackTrace();
			}
			finally
			{
			    if(bout!=null)
			    {
			        bout.flush();
			        bout.close();
			    }
			    if(fos!=null)
			    {
			        fos.flush();
			        fos.close();
			    }
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		/*try {
			URL url = new URL("https://ogermirror.tk/youtube/NWAit7Yqm0E.webm");
			File outputFile = new File("C:\\Users\\Philipp Bleimund\\Videos\\derOger\\test2.webm");
			HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
			httpConnection.setRequestMethod("HEAD");
			long fileLength = httpConnection.getContentLengthLong();
			
			System.out.println(fileLength);
			
			try (BufferedInputStream in = new BufferedInputStream(url.openStream());
			  	OutputStream os = new FileOutputStream("C:\\Users\\Philipp Bleimund\\Videos\\derOger\\test2.webm", true);) {
			    byte dataBuffer[] = new byte[1024];
			    int bytesRead;
			    while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
			        os.write(dataBuffer, 0, bytesRead);
			    }
			} catch (IOException e) {
			    // handle exception
			}
			
			/*long existingFileSize = outputFile.length();
			if (existingFileSize < fileLength) {
			    httpFileConnection.setRequestProperty(
			      "Range", 
			      "bytes=" + existingFileSize + "-" + fileLength
			    );
			}
			
			/*InputStream in = url.openStream();
			Files.copy(in, Paths.get("C:\\Users\\Philipp Bleimund\\Videos\\derOger\\test2.webm"), StandardCopyOption.REPLACE_EXISTING);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
	
}
