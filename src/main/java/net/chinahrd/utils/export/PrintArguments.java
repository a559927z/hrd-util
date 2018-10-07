package net.chinahrd.utils.export;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.Validate;

public class PrintArguments {

	public enum PdfSize {
		A4, A3
	}

	public enum Format {
		PNG, JPEG, PDF
	}

	public enum PageDirection {
		HORIZONTAL, VERTICAL
	}

	private String url;

	private String filename;

	private Format fileFormat = Format.PDF;

	private PdfSize pdfSize;

	private PageDirection direction;

	private Double zoom;

	private Integer timeout = 500;

	private String phantomjsPath;

	private HttpServletRequest request;

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public String getPhantomjsPath() {
		return phantomjsPath;
	}

	public void setPhantomjsPath(String phantomjsPath) {
		this.phantomjsPath = phantomjsPath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public PdfSize getPdfSize() {
		return pdfSize;
	}

	public void setPdfSize(PdfSize pdfSize) {
		this.pdfSize = pdfSize;
	}

	public PageDirection getDirection() {
		return direction;
	}

	public void setDirection(PageDirection direction) {
		this.direction = direction;
	}

	public Double getZoom() {
		return zoom;
	}

	public void setZoom(Double zoom) {
		this.zoom = zoom;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Format getFileFormat() {
		return fileFormat == null ? Format.PDF : fileFormat;
	}

	public void setFileFormat(Format fileFormat) {
		this.fileFormat = fileFormat;
	}

	public String getFullFilePath() {
		StringBuilder s = new StringBuilder();
		s.append(filename);
		if (null == fileFormat) {
			s.append(".pdf");
		} else {
			s.append(".").append(fileFormat.name().toLowerCase());
		}
		return s.toString();
	}

	@Override
	public String toString() {
		return "PrintArguments [url=" + url + ", filename=" + filename + ", pdfSize=" + pdfSize + ", direction="
				+ direction + ", zoom=" + zoom + ", timeout=" + timeout + "]";
	}

	public String toArgumentsString() {
		Validate.notEmpty(url, "url can't be null.");
		Validate.notEmpty(filename, "filename can't be null.");

		StringBuilder args = new StringBuilder();
		args.append(" -url ").append(url);
		args.append(" -filename ").append(getFullFilePath());
		if (null != pdfSize) {
			args.append(" -pdfSize ").append(pdfSize.name());
		}
		if (null != direction) {
			args.append(" -direction ").append(direction.name().toLowerCase());
		}
		if (null != zoom) {
			args.append(" -zoom ").append(zoom);
		}
		if (null != timeout) {
			args.append(" -timeout ").append(timeout);
		}
		return args.toString();
	}

}
