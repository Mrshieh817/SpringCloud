package com.xcf.mybatis.Task;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xcf.mybatis.Core.Resultmodel;
import com.xcf.mybatis.Core.TeambasicModel;
import com.xcf.mybatis.Core.enums.Regionenum;
import com.xcf.mybatis.Tool.HttpsUtils;
import com.xcf.mybatis.Tool.redis.RedisUtil;

/**
 * @author 作者:大飞
 * @version 创建时间：2019年10月28日 下午2:30:17 类说明
 */
@Component
public class TeamAndPlayers {
	@Autowired
	private RedisUtil redisutil;

	static Map<String, Object> gosugamersTeamMap = null;// 存储GOSUGAMERS团队信息
	private static final String urlhltv = "https://www.hltv.org";
	private static final String urlgosugamers = "https://www.gosugamers.net";

	/*
	 * public static void main(String[] args) { try { getHltvTeanInfo(); } catch
	 * (IOException e) { e.printStackTrace(); }
	 * 
	 * }
	 */

	/**
	 * 查询HLTV站点的所有的团队基本信息
	 * 
	 * @throws IOException
	 */
	@Scheduled(fixedRate = 1000 * 6)
	public void getHltvBasicTeanInfo() throws IOException {			
		redisutil.getconnection();
		List<TeambasicModel> insert=new ArrayList<TeambasicModel>();
		int jj = 0;
		String errname="";
		Gson jsonObject = new Gson();		
		//List<TeambasicModel> list=jsonObject.fromJson(redisutil.getString("addTeam").toString(), new TypeToken<List<TeambasicModel>>(){}.getType());
		Resultmodel msg = null;
		try {			
			// 存储GOSUGAMERS站点的所有的团队信息
			getgosugamersTeanInfo();
			// 查询HLTV站点的所有的团队信息
			String hltvurl = MessageFormat.format("{0}/stats/teams?minMapCount=0", urlhltv);
			msg = HttpsUtils.Get(hltvurl);
			if (msg.getCode() == 200) {
				Document doc = Jsoup.parse(msg.getMessage());
				Elements table = doc.getElementsByClass("stats-table player-ratings-table");
				for (Element element : table) {
					Document containerDoc = Jsoup.parse(element.toString());
					Elements urlm = containerDoc.select("tbody").select("tr");
					for (Element element2 : urlm) {
						String teamName = element2.select("a").text();// HLTV站点的团队名称
						errname=teamName;
						String tltvteamId=getNumber(element2.select("a").attr("href").toString()).replace("/", "");// HLTV站点的团队id
						String nationimgUrl=element2.select("img").attr("src").toString();//HLTV国旗
						String nation=element2.select("img").attr("title").toString();//国家	
						String rankingCore="";//团队核心排名
						String region="";//区域
						if (gosugamersTeamMap != null) {
							jj++;
							if (gosugamersTeamMap.get(teamName)!=null) {
								//System.out.println("数量:" + jj + "==teamnName:" + teamName + "==teamnrating:"+ gosugamersTeamMap.get(keyString));// 正式发布的时候删除
								msg = HttpsUtils.Get("https://www.gosugamers.net/counterstrike/rankings/list?opponentName="+URLEncoder.encode(teamName,"utf-8")+"" );// 获取GOSUGAMERS 团队的世界排名及东部排名，占比信息
								if (msg.getCode() == 200) {
									TeambasicModel teambasicmodel=null;
									Document doc1 = Jsoup.parse(msg.getMessage());
									if (doc1.getElementsByClass("rank-info").select("div").size()>4) {
										 region=doc1.getElementsByClass("rank-info").select("div").get(4).select("div").get(1).textNodes().get(0).toString().replace("&amp;", "&").replace("&", "").replace(" ", "").toLowerCase();//区域
									}									
									String rating=gosugamersTeamMap.get(teamName).toString();//团队的能力值
									String teamimgUrl=getDataFromkuohao(doc1.getElementsByClass("team-profile").select("a").get(0).attr("style").toString()).replace("'", "");//团队logo
									String totalMoney=checkNumber(doc1.getElementsByClass("rank-earnings").select("span").text().replace("$", "").replace(",", "")); //累计金额
									Elements recent=doc1.getElementsByClass("ranking-table").select("tbody").select("tr");
									List<Map<String, Object>> recentlist=null;
									recentlist=new ArrayList<Map<String,Object>>();
									Map<String, Object> recentmap=null;
									for (Element element3 : recent) {
										recentmap=new HashMap<>();											
										String tecentname=element3.select("th").text();//近来赛事的名称
										String tecentwin=element3.select("td").get(0).text();//赢场次
										String tecentdram=element3.select("td").get(1).text();//平场次
										String tecentloss=element3.select("td").get(2).text();//输场次	
										recentmap.put("Statistics", tecentname);
										recentmap.put("September", tecentwin);
										recentmap.put("October", tecentdram);
										recentmap.put("All Time", tecentloss);	
										recentlist.add(recentmap);
									}
									// 获取团队核心排名
									msg = HttpsUtils.Get("https://www.hltv.org/team/"+tltvteamId+"/"+teamName.replace(" ", "-")+"");// 获取HLTV站点的团队核心排名
									if (msg.getCode()==200) {
										Document doc2 = Jsoup.parse(msg.getMessage());
										Elements rankingCoreele=doc2.getElementsByClass("chart-container core-chart-container ");
										for (Element element3 : rankingCoreele) {
											Document doc3 = Jsoup.parse(element3.toString());
										    Elements rankingCoreeles=doc3.getElementsByClass("graph");
										    if (rankingCoreeles!=null) {
										       rankingCore=rankingCoreeles.attr("data-fusionchart-config");
											}
										}
									}
									// 写数据到数据库
									teambasicmodel=new TeambasicModel();
									teambasicmodel.setTltvteamId(Integer.parseInt(tltvteamId));
									teambasicmodel.setTeamName(teamName);
									teambasicmodel.setRating(Integer.parseInt(rating));
									teambasicmodel.setNation(nation);
									teambasicmodel.setNationimgUrl(nationimgUrl);
									teambasicmodel.setTeamimgUrl(teamimgUrl);
									teambasicmodel.setRegion(Regionenum.getCode(region));
									teambasicmodel.setTotalMoney(new BigDecimal(totalMoney));
									teambasicmodel.setRecentmatchesStatistics(jsonObject.toJson(recentlist));
									teambasicmodel.setRankingCore(rankingCore);
									insert.add(teambasicmodel);
									System.out.println("第:"+jj+"条=数据增加信息:"+teamName+"\n");
									
								}								
								
							}else{
								TeambasicModel teambasicmodel=null;
								//设置团队基本排名信息为空的处理
								String rating="0";
								String teamimgUrl="";
								String totalMoney="0";
								String rankingCore1="";
								List<Map<String, Object>> recentlist=null;
								recentlist=new ArrayList<Map<String,Object>>();
								Map<String, Object> recentmap=null;
								int ii=0;
								for (int i=0; i<2;i++) {
									ii++;
									recentmap=new HashMap<>();											
									String tecentwin="";//赢场次
									String tecentdram="";//平场次
									String tecentloss="";//输场次	
									if (ii==1) {
										recentmap.put("Statistics", "Win Rate");
									}else{
										recentmap.put("Statistics", "Matches Played");
									}										
									recentmap.put("September", tecentwin);
									recentmap.put("October", tecentdram);
									recentmap.put("All Time", tecentloss);	
									recentlist.add(recentmap);
								}
								// 获取团队核心排名
								msg = HttpsUtils.Get("https://www.hltv.org/team/"+tltvteamId+"/"+teamName.replace(" ", "-")+"");// 获取HLTV站点的团队核心排名
								if (msg.getCode()==200) {
									Document doc2 = Jsoup.parse(msg.getMessage());
									Elements rankingCoreele=doc2.getElementsByClass("chart-container core-chart-container ");
									for (Element element3 : rankingCoreele) {
										Document doc3 = Jsoup.parse(element3.toString());
									    Elements rankingCoreeles=doc3.getElementsByClass("graph");
									    if (rankingCoreeles!=null) {
									      rankingCore1=rankingCoreeles.attr("data-fusionchart-config");
										}
									}
								}
								// 写数据到数据库
								teambasicmodel=new TeambasicModel();
								teambasicmodel.setTltvteamId(Integer.parseInt(tltvteamId));
								teambasicmodel.setTeamName(teamName);
								teambasicmodel.setRating(Integer.parseInt(rating));
								teambasicmodel.setNation(nation);
								teambasicmodel.setNationimgUrl(nationimgUrl);
								teambasicmodel.setTeamimgUrl(teamimgUrl);
								teambasicmodel.setRegion(Regionenum.getCode(region));
								teambasicmodel.setTotalMoney(new BigDecimal(totalMoney));
								teambasicmodel.setRecentmatchesStatistics(jsonObject.toJson(recentlist));
								teambasicmodel.setRankingCore(rankingCore1);
								insert.add(teambasicmodel);
								System.out.println("第:"+jj+"条=数据增加信息:"+teamName+"\n");
							}
						}
					}
				}
				redisutil.setString("addTeam",jsonObject.toJson(insert));
			}

		} catch (Exception e) {
			System.out.println(""+jj+"HLTV异常：" + e.getMessage()+"errname:"+errname);
		}
	}

	/**
	 * 查询GOSUGAMERS站点的所有的团队信息
	 * 
	 * @throws IOException
	 */
	public void getgosugamersTeanInfo() throws IOException {
		gosugamersTeamMap = new HashMap<String, Object>();
		try {
			// 查询GOSUGAMERS站点的所有的团队信息
			int ii = 0;
			for (int i = 1; i <= 12; i++) {
				String gosugamersurl = MessageFormat.format("{0}/counterstrike/rankings?maxResults=50&page=" + i + "",
						urlgosugamers);
				Resultmodel msg = HttpsUtils.Get(gosugamersurl);
				if (msg.getCode() == 200) {
					Document doc = Jsoup.parse(msg.getMessage());
					Elements table = doc.getElementsByClass("ranking-list");
					for (Element element : table) {
						Document containerDoc = Jsoup.parse(element.toString());
						Elements urlm = containerDoc.select("li");
						for (Element element2 : urlm) {
							ii++;
							String teamnName = element2.select("a").text();
							String teamnrating = element2.getElementsByClass("elo").text().toString();
							gosugamersTeamMap.put(getText(teamnName), teamnrating);
							// 寻找GOSUGAMERS站点的团队信息
							System.out.println("数量:" + ii + "==teamnName:" + getText(teamnName) + "====teamnrating:"+ teamnrating);// 正式发布的时候删除
						}
					}
				}

			}
			System.err.println("GOSUGAMERS条数:" + gosugamersTeamMap.size());// 正式发布的时候删除

		} catch (Exception e) {
			System.out.println("GOSUGAMERS异常：" + e.getMessage());
		}
	}

	// 获取文字
	public static String getText(String data) {
		String madata = "";
		String pattern = "(\\D)(\\w+.\\w+)(\\D)"; // 精确匹配文字
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(data);
		if (m.find()) {
			m.reset();
			while (m.find()) {
				madata =madata+ m.group(0);
			}
		}
		return madata.trim();
	}
	// 获取数字
	public static String getNumber(String data) {
		String madata = "";
		String pattern = "(\\/\\d+\\/)"; // 精确匹配文字
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(data);
		if (m.find()) {
			m.reset();
			while (m.find()) {
				madata = m.group(0);
			}
		}
		return madata.trim();
	}
	/*
	 * 获取括号内的信息
	 */
	public static String getDataFromkuohao(String data) {
		String madata = "";
		String pattern = "(?<=\\()[^\\)]+"; // 精确匹配文字
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(data);
		if (m.find()) {
			m.reset();
			while (m.find()) {
				madata = m.group(0);
			}
		}
		return madata.trim();
	}
	
	// 验证是否为数字
	public static String checkNumber(String data) {
		String madata = "";
		String pattern = "(\\d+)"; // 精确匹配文字
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(data);
		if (m.find()) {
			m.reset();
			while (m.find()) {
				madata = m.group(0);
			}			
		}
		if (madata.equals("")) {
			madata="0";
		}
		return madata.trim();
	}
	
	

}
