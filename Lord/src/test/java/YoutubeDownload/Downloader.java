package YoutubeDownload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import com.github.kiulian.downloader.YoutubeDownloader;
import com.github.kiulian.downloader.downloader.YoutubeProgressCallback;
import com.github.kiulian.downloader.downloader.request.RequestChannelUploads;
import com.github.kiulian.downloader.downloader.request.RequestPlaylistInfo;
import com.github.kiulian.downloader.downloader.request.RequestVideoFileDownload;
import com.github.kiulian.downloader.downloader.request.RequestVideoInfo;
import com.github.kiulian.downloader.downloader.response.Response;
import com.github.kiulian.downloader.model.playlist.PlaylistDetails;
import com.github.kiulian.downloader.model.playlist.PlaylistInfo;
import com.github.kiulian.downloader.model.playlist.PlaylistVideoDetails;
import com.github.kiulian.downloader.model.videos.VideoDetails;
import com.github.kiulian.downloader.model.videos.VideoInfo;
import com.github.kiulian.downloader.model.videos.formats.AudioFormat;
import com.github.kiulian.downloader.model.videos.formats.Format;
import com.github.kiulian.downloader.model.videos.formats.VideoFormat;

public class Downloader {

	public String[] videoTitles;
	
	public Downloader() {
		
	}
	
	public String getVideoTitle(String videoId) {
		YoutubeDownloader downloader = new YoutubeDownloader();
		RequestVideoInfo request = new RequestVideoInfo(videoId);
		Response<VideoInfo> response = downloader.getVideoInfo(request);
		VideoInfo video = response.data();
		VideoDetails details = video.details();
		return details.title();
	}
	
	public String[] getTitles() {
		return videoTitles;
	}
	
	public String[] getChannelVideos(String channelId) {
		YoutubeDownloader downloader = new YoutubeDownloader();
		
		
		
		RequestChannelUploads requestChannel = new RequestChannelUploads(channelId);
		Response<PlaylistInfo> responseChannel = downloader.getChannelUploads(requestChannel);
		PlaylistInfo playlistInfoChannel = responseChannel.data();
		
		
		
		List<PlaylistVideoDetails> listChannel = playlistInfoChannel.videos();
		
		String[] videoIds = new String[listChannel.size()];
		videoTitles = new String[listChannel.size()];
		int i=0;
		for(PlaylistVideoDetails d : listChannel) {
			System.out.println(d.videoId());
			videoIds[i] = d.videoId();
			videoTitles[i] = d.title();
			i++;
		}
		return videoIds;
	}
	
	public File downloadAudio(String videoId, String output) {
		YoutubeDownloader downloader = new YoutubeDownloader();
		RequestVideoInfo request = new RequestVideoInfo(videoId);
		Response<VideoInfo> response = downloader.getVideoInfo(request);
		VideoInfo video = response.data();
		VideoDetails details = video.details();
		Format format = video.bestAudioFormat();
		
		File outputDir = new File(output);
		
		RequestVideoFileDownload requestDownload = new RequestVideoFileDownload(format)
			    // optional params    
			    .saveTo(outputDir) // by default "videos" directory
			    .renameTo(details.title())
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
		
		Response<File> responseDownload = downloader.downloadVideoFile(requestDownload);
		VideoInfo data = response.data();
		
		return new File(outputDir.toPath() + "\\" + details.title());
	} 
	
	public File downloadVideo(String videoId, String output) {
		YoutubeDownloader downloader = new YoutubeDownloader();
		RequestVideoInfo request = new RequestVideoInfo(videoId);
		Response<VideoInfo> response = downloader.getVideoInfo(request);
		VideoInfo video = response.data();
		VideoDetails details = video.details();
		Format format = video.bestVideoFormat();
		
		File outputDir = new File(output);
		
		RequestVideoFileDownload requestDownload = new RequestVideoFileDownload(format)
			    // optional params    
			    .saveTo(outputDir) // by default "videos" directory
			    .renameTo(details.title())
			    .overwriteIfExists(true); // if false and file with such name already exits sufix will be added video(1).mp4
		
		Response<File> responseDownload = downloader.downloadVideoFile(requestDownload);
		VideoInfo data = response.data();
		
		return new File(outputDir.toPath() + "\\" + details.title());
	}
	
	public File mergeAudioToVideo(
	        File ffmpegExecutable,  // ffmpeg/bin/ffmpeg.exe
	        File audioFile,
	        File videoFile,
	        File outputDir,
	        String outFileName) throws IOException, InterruptedException {

	    for (File f : Arrays.asList(ffmpegExecutable, audioFile, videoFile, outputDir)) {
	        if (! f.exists()) {
	            throw new FileNotFoundException(f.getAbsolutePath());
	        }
	    }

	    File mergedFile = Paths.get(outputDir.getAbsolutePath(), outFileName).toFile();
	    if (mergedFile.exists()) {
	        mergedFile.delete();
	    }

	    ProcessBuilder pb = new ProcessBuilder(
	            ffmpegExecutable.getAbsolutePath(),
	            "-i",
	            audioFile.getAbsolutePath(),
	            "-i",
	            videoFile.getAbsolutePath() ,
	            "-acodec",
	            "copy",
	            "-vcodec",
	            "copy",
	            mergedFile.getAbsolutePath()
	    );
	    pb.redirectErrorStream(true);
	    Process process = pb.start();
	    //process.waitFor();

	    if (!mergedFile.exists()) {
	        return null;
	    }
	    return mergedFile;
	}
}
