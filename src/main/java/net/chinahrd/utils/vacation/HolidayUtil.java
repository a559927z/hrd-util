package net.chinahrd.utils.vacation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNodeList;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class HolidayUtil {

	private static String latestVocationName = "";

	public String getVocationName(DomNodeList<HtmlElement> htmlElements,
			String date) throws ParseException {
		String rst = "";
		boolean pastTimeFlag = false;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date paramDate = dateFormat.parse(date);
		if (new Date().getTime() >= paramDate.getTime()) {
			pastTimeFlag = true;
		}
		// first step //jugde if can get vocation name from html page
		for (int i = 0; i < htmlElements.size(); i++) {
			HtmlElement element = htmlElements.get(i);
			if (element.getAttribute("class").indexOf("vacation") != -1) {
				boolean hitFlag = false;
				String voationName = "";
				for (; i < htmlElements.size(); i++) {
					HtmlElement elementTmp = htmlElements.get(i);
					String liDate = elementTmp.getAttribute("date");
					List<HtmlElement> lunar = elementTmp
							.getElementsByAttribute("span", "class", "lunar");
					String lanarText = lunar.get(0).asText();
					if (lanarText.equals("元旦")) {
						voationName = "元旦";
					} else if (lanarText.equals("除夕") || lanarText.equals("春节")) {
						voationName = "春节";
					} else if (lanarText.equals("清明")) {
						voationName = "清明";
					} else if (lanarText.equals("国际劳动节")) {
						voationName = "国际劳动节";
					} else if (lanarText.equals("端午节")) {
						voationName = "端午节";
					} else if (lanarText.equals("中秋节")) {
						voationName = "中秋节";
					} else if (lanarText.equals("国庆节")) {
						voationName = "国庆节";
					}
					if (liDate.equals(date)) {
						hitFlag = true;
					}
					if (elementTmp.getAttribute("class").indexOf("vacation") == -1) {
						break;
					}
				}
				if (hitFlag == true && !voationName.equals("")) {
					rst = voationName;
					break;
				}
			} else {
				continue;
			}
		}
		// if first step fail(rarely), get from the latest Vocation name
		if (rst.equals("")) {
			System.out
					.println("warning: fail to get vocation name from html page.");
			// you can judge by some simple rule
			// from the latest Vocation name
			rst = HolidayUtil.latestVocationName;
		} else if (pastTimeFlag == true) {
			// 更新《当前时间，且最近一次的可见的假期名
			HolidayUtil.latestVocationName = rst;
		}
		return rst;
	}
	/**
	 * 获取某web标签内的文字
	 * @param url 请求的页面地址
	 * @param elementId 需要获取到的标签名
	 * @return
	 */
	public static String getElementTextContent(String url, String elementId){
		WebClient webClient = null;
		String textContent = "";
		try{
			webClient = new WebClient(BrowserVersion.FIREFOX_3);
			HtmlPage page = webClient.getPage(url);
			for(int k = 0; k < 60; k++){
				if(!page.getElementById("M-dates").asText().equals(""))
					break;
				Thread.sleep(10000);
			}
			DomElement domelement = page.getElementById(elementId);
			textContent = domelement.getFirstChild().getTextContent();
		}catch(Exception ex){
			ex.printStackTrace();
		}
		finally{
			webClient.closeAllWindows();
		}
		return textContent;
	}
	public List<ChinaDateDto> getCurrentDateInfo() {
		WebClient webClient = null;
		List<ChinaDateDto> dateList = null;
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
			dateList = new ArrayList<ChinaDateDto>();
			webClient = new WebClient();
			HtmlPage page = webClient.getPage("http://hao.360.cn/rili/");
			// 最大等待60秒
			for (int k = 0; k < 60; k++) {
				if (!page.getElementById("M-dates").asText().equals(""))
					break;
				Thread.sleep(1000);
			}
			// 睡了8秒，等待页面加载完成...，有时候，页面可能获取不到，不稳定（）
//			Thread.sleep(8000);

			DomNodeList<HtmlElement> htmlElements = page
					.getElementById("M-dates").getElementsByTagName("li");
			// System.out.println(htmlElements.size());
			for (HtmlElement element : htmlElements) {
				ChinaDateDto ChinaDateDto = new ChinaDateDto();
				List<HtmlElement> lunar = element.getElementsByAttribute("span",
						"class", "lunar");
				List<HtmlElement> solar = element.getElementsByAttribute("div",
						"class", "solar");
				ChinaDateDto.setLunar(lunar.get(0).asText());
				ChinaDateDto.setSolar(solar.get(0).asText());
				ChinaDateDto.setSolarDate(
						dateFormat.parse(element.getAttribute("date")));
				if (element.getAttribute("class").indexOf("vacation") != -1) {
					ChinaDateDto.setVacation(true);
					ChinaDateDto.setVacationName(this.getVocationName(
							htmlElements, element.getAttribute("date")));
				}
				if (element.getAttribute("class").indexOf("weekend") != -1
						&& element.getAttribute("class")
								.indexOf("last") == -1) {
					ChinaDateDto.setSaturday(true);
				}
				if (element.getAttribute("class")
						.indexOf("last weekend") != -1) {
					ChinaDateDto.setSunday(true);
				}
				if (element.getAttribute("class").indexOf("work") != -1) {
					ChinaDateDto.setWorkFlag(true);
				} else if (ChinaDateDto.isSaturday() == false
						&& ChinaDateDto.isSunday() == false
						&& ChinaDateDto.isVacation() == false) {
					ChinaDateDto.setWorkFlag(true);
				} else {
					ChinaDateDto.setWorkFlag(false);
				}
				dateList.add(ChinaDateDto);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("get date from http://hao.360.cn/rili/ error~");
		} finally {
			webClient.closeAllWindows();
		}
		return dateList;
	}
	public ChinaDateDto getTodayInfo() {
		List<ChinaDateDto> dateList = this.getCurrentDateInfo();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		for (ChinaDateDto date : dateList) {
			if (dateFormat.format(date.getSolarDate())
					.equals(dateFormat.format(new Date()))) {
				return date;
			}
		}
		return new ChinaDateDto();
	}
	public static void main(String[] args)
			throws FailingHttpStatusCodeException, MalformedURLException,
			IOException, InterruptedException {
		List<ChinaDateDto> dateList = new HolidayUtil().getCurrentDateInfo();
		ChinaDateDto today = new HolidayUtil().getTodayInfo();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		System.out.println("本月详情：");
		for (ChinaDateDto date : dateList) {
			System.out.println(dateFormat.format(date.getSolarDate()));
			System.out.print(date.getVacationName());
			System.out.println(dateFormat.format(date.getSolarDate()) + " " + date.getVacationName());
		}
		
		
		System.out.println(
				"------------------------------------------------------------------------");
		System.out.println("今日详情：");
		System.out.println("日期：" + today.getSolarDate());
		System.out.println("农历：" + today.getLunar());
		System.out.println("公历：" + today.getSolar());
		System.out.println("假期名：" + today.getVacationName());
		System.out.println("是否周六：" + today.isSaturday());
		System.out.println("是否周日：" + today.isSunday());
		System.out.println("是否休假：" + today.isVacation());
		System.out.println("是否工作日：" + today.isWorkFlag());
		System.out.println("已发生的最近一次假期:" + HolidayUtil.latestVocationName);
	}
}
