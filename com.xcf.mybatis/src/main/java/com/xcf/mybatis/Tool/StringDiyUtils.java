package com.xcf.mybatis.Tool;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/** 
* @author xcf 
* @Date 创建时间：2021年5月25日 上午9:40:41 
*/
public class StringDiyUtils {
	  private static String FORMAT_STR = "00000";
	    private static String FID_TITLE = "A-";

	    /**
	     * 生成全站唯一交易流水号
	     * @return
	     */
	    public static String getFid(){
	        return FID_TITLE+DateUtils.datetimeFormatLong(new Date())+MD5Util.getRandomNumber(8);
	    }

	    /**
	     * 生成一个订单编号
	     * type:订单类型
	     * num: 几位随机数
	     * @return
	     */
	    public static String getOrderCode(String type, Integer num){
	        return type+DateUtils.parseDatetimeYYYYmmdd(new Date())+MD5Util.getRandomNumber(num);
	    }

	    /**
	     * 判断字符集是否存在为空的字符
	     * @param strings
	     * @return false: 不存在  true: 存在
	     */
	    public static Boolean checkStrByNull(String... strings) {
	        for (String str : strings) {
	            if(StringUtils.isEmpty(str)){
	                return true;
	            }
	        }
	        return false;
	    }

	    /**
	     * 将数字向上填充0
	     * @param num
	     * @return
	     */
	    public static String getFiveString(String num){
	        return FORMAT_STR.substring(0, FORMAT_STR.length() - num.length()) + num;
	    }

	    /**
	     * 删除list中的指定元素
	     * @param list
	     */
	    public static void remove(List<String> list, String del) {
	        Iterator<String> sListIterator = list.iterator();
	        while (sListIterator.hasNext()) {
	            String str = sListIterator.next();
	            if (str.contains(del)) {
	                sListIterator.remove();
	            }
	        }
	    }

	    /**
	     * 输入流转化为字符串
	     *
	     * @param inputStream 流
	     * @return String 字符串
	     * @throws Exception
	     */
	    public static String getStreamString(InputStream inputStream) throws Exception {
	        StringBuffer buffer = new StringBuffer();
	        InputStreamReader inputStreamReader = null;
	        BufferedReader bufferedReader = null;
	        try {
	            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
	            bufferedReader = new BufferedReader(inputStreamReader);
	            String line;
	            while ((line = bufferedReader.readLine()) != null) {
	                buffer.append(line);
	            }
	        } catch (Exception e) {
	            throw new Exception();
	        } finally {
	            if (bufferedReader != null) {
	                bufferedReader.close();
	            }
	            if (inputStreamReader != null) {
	                inputStreamReader.close();
	            }
	            if (inputStream != null) {
	                inputStream.close();
	            }
	        }
	        return buffer.toString();
	    }

	    /**
	     * XML格式字符串转换为Map
	     *
	     * @param strXML XML字符串
	     * @return XML数据转换后的Map
	     * @throws Exception
	     */
	    public static Map<String, String> xmlToMap(String strXML) throws Exception {
	        try {
	            Map<String, String> data = new HashMap<String, String>();
	            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

	            String FEATURE = "http://apache.org/xml/features/disallow-doctype-decl";
	            documentBuilderFactory.setFeature(FEATURE, true);

	            FEATURE = "http://xml.org/sax/features/external-general-entities";
	            documentBuilderFactory.setFeature(FEATURE, false);

	            FEATURE = "http://xml.org/sax/features/external-parameter-entities";
	            documentBuilderFactory.setFeature(FEATURE, false);

	            FEATURE = "http://apache.org/xml/features/nonvalidating/load-external-dtd";
	            documentBuilderFactory.setFeature(FEATURE, false);

	            documentBuilderFactory.setXIncludeAware(false);
	            documentBuilderFactory.setExpandEntityReferences(false);
	            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
	            InputStream stream = new ByteArrayInputStream(strXML.getBytes("UTF-8"));
	            org.w3c.dom.Document doc = documentBuilder.parse(stream);
	            doc.getDocumentElement().normalize();
	            NodeList nodeList = doc.getDocumentElement().getChildNodes();
	            for (int idx = 0; idx < nodeList.getLength(); ++idx) {
	                Node node = nodeList.item(idx);
	                if (node.getNodeType() == Node.ELEMENT_NODE) {
	                    org.w3c.dom.Element element = (org.w3c.dom.Element) node;
	                    data.put(element.getNodeName(), element.getTextContent());
	                }
	            }
	            try {
	                stream.close();
	            } catch (Exception ex) {
	                // do nothing
	            }
	            return data;
	        } catch (Exception ex) {
	            throw ex;
	        }
	    }

	    /**
	     * 取request的请求参数
	     * @param request
	     * @return
	     */
	    public static Map getParameterMap(HttpServletRequest request) {
	        // 参数Map
	        Map properties = request.getParameterMap();
	        // 返回值Map
	        Map returnMap = new HashMap();
	        Iterator entries = properties.entrySet().iterator();
	        Map.Entry entry;
	        String name = "";
	        String value = "";
	        while (entries.hasNext()) {
	            entry = (Map.Entry) entries.next();
	            name = (String) entry.getKey();
	            Object valueObj = entry.getValue();
	            if(null == valueObj){
	                value = "";
	            }else if(valueObj instanceof String[]){
	                String[] values = (String[])valueObj;
	                for(int i=0;i<values.length;i++){
	                    value = values[i] + ",";
	                }
	                value = value.substring(0, value.length()-1);
	            }else{
	                value = valueObj.toString();
	            }
	            returnMap.put(name, value);
	        }
	        return returnMap;
	    }

}
