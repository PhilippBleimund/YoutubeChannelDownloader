package YoutubeDownload;

import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import javax.swing.SwingWorker;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.Format;

public class YoutubeConnector {
	
	public YoutubeDownloader downloader;
	public YoutubeConnector() {
		downloader = new YoutubeDownloader();
	}
	
	public Object[] getVideoInfo(String URL) {
		Object[] info = new Object[4];
		RequestVideoInfo request = new RequestVideoInfo(URL);
		Response<VideoInfo> response = downloader.getVideoInfo(request);
		VideoInfo video = response.data();
		VideoDetails details = video.details();
		Format formatAudio = video.bestAudioFormat();
		Format formatVideo = video.bestVideoFormat();
		info[0] = details.title();
		info[1] = details.videoId();
		info[2] = formatVideo;
		info[3] = formatAudio;
		
		return info;
	}
	
	public void downloadVideoAndAudio(String workingDir, Format VideoURL, Format AudioURL, String name) {
		
    	RequestVideoFileDownload requestDownloadVideo = new RequestVideoFileDownload(VideoURL)
			    // optional params    
			    .saveTo(new File(workingDir)) // by default "videos" directory
			    .renameTo(name)
			    .overwriteIfExists(true) // if false and file with such name already exits sufix will be added video(1).mp4
			.callback(new YoutubeProgressCallback<File>() {
	        @Override
	        public void onDownloading(int progress) {
	            System.out.printf("Downloaded %d%%\n", progress);
	        }
	        @Override
	        public void onFinished(File videoInfo) {
	            System.out.println("Finished file: " + videoInfo);
	        }
	        @Override
	        public void onError(Throwable throwable) {
	            System.out.println("Error: " + throwable.getLocalizedMessage());
	        }
	    })
	    .async();
		RequestVideoFileDownload requestDownloadAudio = new RequestVideoFileDownload(AudioURL)
			    // optional params    
			    .saveTo(new File(workingDir)) // by default "videos" directory
			    .renameTo(name)
			    .overwriteIfExists(true) // if false and file with such name already exits sufix will be added video(1).mp4
			.callback(new YoutubeProgressCallback<File>() {
	        @Override
	        public void onDownloading(int progress) {
	            System.out.printf("Downloaded %d%%\n", progress);
	        }
	        @Override
	        public void onFinished(File videoInfo) {
	            System.out.println("Finished file: " + videoInfo);
	        }
	        @Override
	        public void onError(Throwable throwable) {
	            System.out.println("Error: " + throwable.getLocalizedMessage());
	        }
	    })
	    .async();
		downloader.downloadVideoFile(requestDownloadVideo);
		downloader.downloadVideoFile(requestDownloadAudio);
		Response<File> responseVideo = downloader.downloadVideoFile(requestDownloadVideo);
		File dataVideo = responseVideo.data(); // will block current thread
		Response<File> responseAudio = downloader.downloadVideoFile(requestDownloadAudio);
		File dataAudio = responseAudio.data(); // will block current thread
	}
}
