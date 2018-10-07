package net.chinahrd.utils.holiday;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.chinahrd.utils.CollectionKit;
import net.chinahrd.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 获取节假日-百度API
 *
 * @author jxzhang on 2017年2月13日
 * @Verdion 1.0 版本
 */
public class EasybotsGetDays implements GetDays {

    /**
     * 这个对easybots的类型，不是days的类型
     */
    @Getter
    @AllArgsConstructor
    enum EasybotsEnum {
        /**
         * 工作日对应结果为
         */
        IS_WORK_FLAG(0),

        /**
         * 休息日对应结果为
         */
        IS_HOLIDAY(1),

        /**
         * 节假日对应的结果为
         */
        IS_VACATION(2);

        private Integer code;

    }

    private String[] monthArr = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"};
    private String[] dayArr = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
            "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31"};

    /**
     * 从easybots获取Days对象
     *
     * @param ymd
     * @return 返一个日期是否节假期日
     */
    public DaysDto getDaysDto(Integer ymd) {
        // 检查一个日期是否为节假日
        String httpUrl = "http://www.easybots.cn/api/holiday.php?d=" + ymd;
        String jsonStr = httpUrlRequest(httpUrl);
        Map<String, Object> yearMonthDay = JSONObject.parseObject(jsonStr);
        Integer rs = Integer.parseInt((String) yearMonthDay.get(ymd + ""));

        DaysDto daysDto = new DaysDto();
        daysDto.setDays(DateUtil.formatDate(ymd));
        daysDto.setIsWorkFlag(0);
        daysDto.setIsHoliday(0);
        daysDto.setIsVacation(0);
        if (EasybotsEnum.IS_WORK_FLAG.getCode().equals(rs)) {
            daysDto.setIsWorkFlag(1);
        } else if (EasybotsEnum.IS_HOLIDAY.getCode().equals(rs)) {
            daysDto.setIsHoliday(1);
        } else if (EasybotsEnum.IS_VACATION.getCode().equals(rs)) {
            daysDto.setIsVacation(1);
        }

        return daysDto;
    }


    /**
     * 核心方法
     *
     * @param yearStr
     */
    private List<DaysDto> core(String yearStr) {
        String httpUrl = "http://www.easybots.cn/api/holiday.php";
        String param = "m=";
        for (String month : monthArr) {
            param += yearStr + month + ",";
        }
        httpUrl = httpUrl + "?" + param;
        String jsonStr = httpUrlRequest(httpUrl);
        List<DaysDto> rs = jsonToList(jsonStr);
        return sortByDays(sortByDays(rs));
    }

    /**
     * 排序
     *
     * @param dtos
     */
    private ImmutableList<DaysDto> sortByDays(List<DaysDto> dtos) {
        Ordering<DaysDto> ordering = Ordering.natural().nullsFirst().onResultOf(new Function<DaysDto, String>() {
            public String apply(DaysDto dto) {
                return dto.getDays();
            }
        });
        return ordering.immutableSortedCopy(dtos);
    }

    /**
     * 将json对像转List集合
     *
     * @param jsonStr
     * @return List<DaysDto>
     * @author jxzhang
     */
    private List<DaysDto> jsonToList(String jsonStr) {
        List<DaysDto> rs = CollectionKit.newList();
        Map<String, Object> yearMonth = JSONObject.parseObject(jsonStr);
        for (Entry<String, Object> yearMonthEntry : yearMonth.entrySet()) {
            Map<String, Object> whatDay = JSONObject.parseObject(yearMonthEntry.getValue().toString());
            String year = StringUtils.left(yearMonthEntry.getKey(), 4);
            String month = StringUtils.right(yearMonthEntry.getKey(), 2);

            int monthMaxDay = DateUtil.getMonthMaxDay(year + "-" + month + "-01");

            for (int i = 0; i < monthMaxDay; i++) {
                DaysDto dto = new DaysDto();
                String day = dayArr[i];
                dto.setDays(year + "-" + month + "-" + day);
                dto.setIsWorkFlag(1);
                dto.setIsHoliday(0);
                dto.setIsVacation(0);

                for (Entry<String, Object> entry : whatDay.entrySet()) {
                    if (day.equals(entry.getKey())) {
                        dto.setIsWorkFlag(0);
                        switch (Integer.parseInt(entry.getValue().toString())) {
                            case 1:
                                dto.setIsHoliday(1);
                                dto.setIsVacation(0);
                                break;
                            case 2:
                                dto.setIsHoliday(0);
                                dto.setIsVacation(1);
                                break;
                        }
                    }
                }
                rs.add(dto);
            }
        }
        return rs;
    }

    /**
     * 获取本年度节假日
     * <p>
     * 检查具体日期是否为节假日，工作日对应结果为 0, 休息日对应结果为 1, 节假日对应的结果为 2 检查一个日期是否为节假日
     * http://www.easybots.cn/api/holiday.php?d=20130101 检查多个日期是否为节假日
     * http://www.easybots.cn/api/holiday.php?d=20130101,20130103,20130105, 20130201
     * 获取2012年1月份节假日 http://www.easybots.cn/api/holiday.php?m=201201 获取2013年1/2月份节假日
     * http://www.easybots.cn/api/holiday.php?m=201301,201302
     *
     * @param httpUrl
     * @return json
     * @author jxzhang on 2017年2月13日
     */
    private String httpUrlRequest(String httpUrl) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<DaysDto> calcDays(String endDay, String startDay) {
        String year = StringUtils.left(startDay, 4);
        return core(year);
    }

    public static void main(String[] args) {
//        List<DaysDto> core = new EasybotsGetDays().core("2017");
//        System.out.println(JSON.toJSONString(core));
        DaysDto vaction = new EasybotsGetDays().getDaysDto(20160305);

    }
}
