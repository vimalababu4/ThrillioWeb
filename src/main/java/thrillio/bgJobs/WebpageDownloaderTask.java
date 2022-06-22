package thrillio.bgJobs;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import thrillio.dao.BookmarkDao;
import thrillio.entities.WebLink;
import thrillio.util.HttpConnect;
import thrillio.util.IOUtil;

public class WebpageDownloaderTask implements Runnable{
	private static BookmarkDao dao = new BookmarkDao();
	private static final long TIME_FRAME =3000000000L;//3 seconds
	private boolean downloadAll = false;
	ExecutorService downloadExecutor = Executors.newFixedThreadPool(5);
	
	private static class Downloader<T extends WebLink> implements Callable<T> {
		private T weblink;
		public Downloader(T weblink) {
			this.weblink= weblink;
		}
		@Override
		public T call() throws Exception {
			try {
				if (!weblink.getUrl().endsWith(".pdf")) {
					weblink.setDownloadStatus(WebLink.DownloadStatus.FAILED);
					String htmlPage = HttpConnect.download(weblink.getUrl());
					weblink.setHtmlPage(htmlPage);
				}else {
					weblink.setDownloadStatus(WebLink.DownloadStatus.NOT_ELIGIBLE);
				}
			
			}catch(MalformedURLException e) {
				e.printStackTrace();
			}catch(URISyntaxException e){
				e.printStackTrace();
			}
			return weblink;
		
	}
	}
	
	public WebpageDownloaderTask(boolean downloadAll) {
		
		this.downloadAll = downloadAll;
	}

	@Override
	public void run() {
	
		while(!Thread.currentThread().isInterrupted()) {
			//get weblinks
			List<WebLink> webLinks = getWebLinks();
			//downlad concurrently
			if(webLinks.size() >0) {
				download(webLinks);
			}else {
				System.out.println("No new Web Liks to download!!");
			}
			//wait
			
			try {
				TimeUnit.SECONDS.sleep(15);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		downloadExecutor.shutdown();
	}

	public void download(List<WebLink> webLinks) {
		List<Downloader<WebLink>> tasks = getTasks(webLinks);
		List<Future<WebLink>> futures = new ArrayList<>();
		
		try {
			futures = downloadExecutor.invokeAll(tasks, TIME_FRAME,TimeUnit.NANOSECONDS);//invokeAll holds the status(if done,true/false) and results of completed/uncompleted tasks
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for(Future<WebLink> future : futures) {
			try {
				if(!future.isCancelled()) {
					WebLink webLink = future.get();
					String webPage =webLink.getHtmlPage();
					if(webPage != null) {
						IOUtil.write(webPage,webLink.getId());
						webLink.setDownloadStatus(WebLink.DownloadStatus.SUCCESS);
						System.out.println("Download success " + webLink.getUrl());
					}else {
						System.out.println("Download Failed" + webLink.getUrl());
					}
				}else {
					System.out.println("Task cancelled .."+ Thread.currentThread());
				}
			}catch(InterruptedException e) {
				e.printStackTrace();
			}catch(ExecutionException e){
				e.printStackTrace();
			}
		
	}
	}

	private List<Downloader<WebLink>> getTasks(List<WebLink> webLinks) {
		List<Downloader<WebLink>> tasks = new ArrayList<>();
		for(WebLink webLink : webLinks) {
			tasks.add(new Downloader<WebLink>(webLink));
		}
		return tasks;
	}

	private List<WebLink> getWebLinks() {
		List<WebLink>  weblinks =null;
		if(downloadAll) {
			weblinks=dao.getAllWebLinks();
			downloadAll =false;
		}else {
			weblinks =dao.getWebLinks((WebLink.DownloadStatus.NOT_ATTEMPTED));
		}
		return weblinks;
	}
}
