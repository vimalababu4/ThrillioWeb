package thrillio.entities;

import thrillio.partner.Shareable;

public class WebLink extends Bookmark implements Shareable {

	private String url;
	private String host;
	private String htmlPage;
	

	private DownloadStatus downloadStatus = DownloadStatus.NOT_ATTEMPTED;
	public enum DownloadStatus{
		NOT_ATTEMPTED,
		SUCCESS,
		FAILED,
		NOT_ELIGIBLE; //NOT ELIGIBLE FOR DOWNLOAD
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	@Override
	public String toString() {
		return "WebLink [ url=" + url + ", host=" + host + "]";
	}

	@Override   //stub
	public boolean isKidsFriendlyEligible() {
		// TODO Auto-generated method stub
		if(url.contains("porn") ||getTitle().contains("porn") || host.contains("adult")) {
			return false;
		}
		return true;
	}
	@Override
	public String getItemData() {
		//xml -extensible mark up language
		//human readable, and machine level 
		StringBuilder builder = new StringBuilder();
		builder.append("<item>");
			builder.append("<type>WebLink</type>");
			builder.append("<title>").append(getTitle()).append("</title>");
		
			builder.append("<url>>").append(url).append("</url>");
			builder.append("<host>").append(host).append("</host>");
			builder.append("</item");
		return builder.toString();
	}

	public DownloadStatus getDownloadStatus() {
	
		// TODO Auto-generated method stub
		return downloadStatus;
	}

	public void setDownloadStatus(DownloadStatus downloadStatus) {
	
		// TODO Auto-generated method stub
		this.downloadStatus= downloadStatus;
	}

	public void setHtmlPage(String htmlPage) {
		this.htmlPage= htmlPage;
		
	}
	public String getHtmlPage() {
		return htmlPage;
	}

}
