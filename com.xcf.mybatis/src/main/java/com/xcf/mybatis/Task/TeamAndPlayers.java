package com.xcf.mybatis.Task;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.xcf.mybatis.Core.GamePlayers;
import com.xcf.mybatis.Core.TeamMatChes;
import com.xcf.mybatis.Core.Resultmodel;
import com.xcf.mybatis.Core.TeamMapsChild;
import com.xcf.mybatis.Core.TeamMapsParent;
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
	private static final String urlhltvplayer="https://www.hltv.org/stats/players";

	/*
	 * public static void main(String[] args) { try { getHltvTeanInfo(); } catch
	 * (IOException e) { e.printStackTrace(); }
	 * 
	 * }
	 */
	
	/////////////////////////////////////////////////////////////团队信息//////////////////////////////////////////

	/**
	 * 查询HLTV站点的所有的团队基本信息
	 * 
	 * @throws IOException
	 */
	//@Scheduled(fixedRate = 1000 * 6)
	//@Scheduled(cron="0 48 19 ? * *")
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
						//////////////////////开始存储///////////////////
					}
				}
				redisutil.setString("addTeam",jsonObject.toJson(insert));
			}

		} catch (Exception e) {
			System.out.println(""+jj+"==HLTV站点获取团队基本信息异常：" + e.getMessage()+"===团队名称:"+errname);
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
				String gosugamersurl = MessageFormat.format("{0}/counterstrike/rankings?maxResults=50&page=" + i + "",urlgosugamers);
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
							//System.out.println("数量:" + ii + "==teamnName:" + getText(teamnName) + "====teamnrating:"+ teamnrating);// 正式发布的时候删除
						}
					}
				}

			}

		} catch (Exception e) {
			System.out.println("获取GOSUGAMERS站点团队信息异常：" + e.getMessage());
		}
	}
	
	

	/**
	 * 查询HLTV站点的所有的团队的比赛信息
	 * 
	 * @throws IOException
	 */
	//@Scheduled(fixedRate = 1000 * 6)
	//@Scheduled(cron="0 48 19 ? * *")
	public void getHltvTeanMatchesInfo() throws IOException {			
		redisutil.getconnection();
		List<TeamMatChes> insert=new ArrayList<TeamMatChes>();
		String errname="";
		Gson jsonObject = new Gson();		
		Resultmodel msg = null;
		String tltvteamId="0";
		String teamName="";
		String date="";
		String Event="";
		String Opponent="";
		String Map="";
		String Result="";
		String WL="";
		try {			
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
						 teamName = element2.select("a").text();// HLTV站点的团队名称
						 errname=teamName;
						 tltvteamId=getNumber(element2.select("a").attr("href").toString()).replace("/", "");// HLTV站点的团队id
						msg = HttpsUtils.Get(MessageFormat.format("{0}/stats/teams/matches/{1}/{2}", urlhltv,tltvteamId,URLEncoder.encode(teamName,"utf-8")));//获取团队的比赛信息
						if (msg.getCode()==200) {
							Document doc1=Jsoup.parse(msg.getMessage());
							Elements urlm1=doc1.getElementsByClass("stats-table no-sort");
							TeamMatChes model=null;
							for (Element element3 : urlm1) {
								Document doc2 = Jsoup.parse(element3.toString());
								Elements urlm2 = doc2.select("tbody").select("tr");
								for (Element element4 : urlm2) {
									Map<String, Object> eventmap=new HashMap<>();
									Map<String, Object> opponentmap=new HashMap<>();
									// 判断是否存在比赛数据
									if (doc2.select("tbody").select("tr").size()>1) {									
										date=element4.select("td").get(0).select("a").text();//时间
										eventmap.put("img", element4.select("td").get(1).select("img").attr("src"));//event 图片
										eventmap.put("name", element4.select("td").get(1).select("a").text());	// event 名称
										opponentmap.put("img", element4.select("td").get(3).select("img").attr("src"));// opponent 图片
										opponentmap.put("name", element4.select("td").get(3).select("a").text());	//opponent 名称
										Event=jsonObject.toJson(eventmap);
										Opponent=jsonObject.toJson(opponentmap);
										Map=element4.select("td").get(4).select("span").text();// 地图
										Result=element4.select("td").get(5).select("span").text();// 比分
										WL=element4.select("td").get(6).text();// 输赢
									}
									model=new TeamMatChes();
									model.setTltvteamId(Integer.parseInt(tltvteamId));
									model.setTeamName(teamName);
									model.setDate(date);
									model.setEvent(Event);
									model.setOpponent(Opponent);
									model.setMap(Map);
									model.setResult(Result);
									model.setWL(WL);
									insert.add(model);										
								}							
							}
						}
						////////////////开始存储//////////////////
					}
				}
				redisutil.setString("addTeamMatChes",jsonObject.toJson(insert));				
			}

		} catch (Exception e) {
			System.out.println("HLTV站点,获取比赛赛事异常：" + e.getMessage()+"===团队名称:"+errname);
		}
	}
	
	
	/**
	 * 查询HLTV站点的所有的团队地图基本概况
	 * 
	 * @throws IOException
	 */
	//@Scheduled(fixedRate = 1000 * 6)
	//@Scheduled(cron="0 48 19 ? * *")
	public void getHltvTeanParentsMapsInfo() throws IOException {			
		redisutil.getconnection();
		List<TeamMapsParent> insert=new ArrayList<TeamMapsParent>();
		String errname="";
		Gson jsonObject = new Gson();		
		Resultmodel msg = null;
		String tltvteamId="0";
		String teamName="";
		String mapBreakdown="";
		String mapHighlight="";
		String mapOverview="";
		try {			
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
						 teamName = element2.select("a").text();// HLTV站点的团队名称
						 errname=teamName;
						 tltvteamId=getNumber(element2.select("a").attr("href").toString()).replace("/", "");// HLTV站点的团队id
						msg = HttpsUtils.Get(MessageFormat.format("{0}/stats/teams/maps/{1}/{2}", urlhltv,tltvteamId,URLEncoder.encode(teamName,"utf-8")));//获取团队的比赛信息
						if (msg.getCode()==200) {
							/////////////////////////////存储 Map breakdown 和 Map highlight///////////////
							TeamMapsParent model=null;
							List<Map<String, Object>> mapBreakdownmaplist=new ArrayList<>();
							Map<String, Object> mapBreakdownmap=new HashMap<>();
							List<Map<String, Object>>mapHighlightmaplist=new ArrayList<>();
							Map<String, Object> mapHighlightmap=new HashMap<>();		
							Document doc1=Jsoup.parse(msg.getMessage());
							Elements urlm1=doc1.getElementsByClass("narrow-columns");//存储 Map breakdown 和 Map highlight							
							for (Element element3 : urlm1) {
								 mapBreakdownmap=new HashMap<>();
								 mapHighlightmap=new HashMap<>();								
								String breakdownname= element3.getElementsByClass("standard-box big-padding").select("span").text();//存储 Map breakdown
								String breakdownvalue= element3.getElementsByClass("graph ").attr("data-fusionchart-config").toString();//存储 Map breakdown
								mapBreakdownmap.put("name", breakdownname);	
								mapBreakdownmap.put("value", breakdownvalue);	
								mapBreakdownmaplist.add(mapBreakdownmap);
								mapBreakdown=jsonObject.toJson(mapBreakdownmaplist);
								Elements highlightele=element3.getElementsByClass("standard-box big-padding map-pool").select("a");//存储  Map highlight
								for (Element element4 : highlightele) {
									mapHighlightmap=new HashMap<>();
									String heighlightname= element4.select("img").attr("title").toString();
									String heighlightvalue= element4.select("a").text();
									mapHighlightmap.put("name", heighlightname);
									mapHighlightmap.put("value", heighlightvalue);
									mapHighlightmaplist.add(mapHighlightmap);
								}
								mapHighlight=jsonObject.toJson(mapHighlightmaplist);
							}
							////////////////////////存储 Map overview////////////////////////////
							Elements urlm2=doc1.getElementsByClass("two-grid");//存储 Map overview
							Document doc2=Jsoup.parse(urlm2.toString());//获取two-grid的下面col的div
							Elements urlm3=doc2.getElementsByClass("col");													
							Map<String, Object> mapOverviewmap=new HashMap<>();
							List<Map<String, Object>> maplist=new ArrayList<>();	
							Map<String,Object> listmapOverviewmap=new HashMap<String,Object>();
							List<Map<String, Object>> Totalmaplist=new ArrayList<>();
							for (Element element3 : urlm3) {
								 maplist=new ArrayList<>();
								String viewname=element3.getElementsByClass("map-pool-map-holder").select("img").attr("title");							
								Elements overviewele=element3.getElementsByClass("stats-rows standard-box");
								Document doc3=Jsoup.parse(overviewele.toString());//获取two-grid的下面col的div
								Elements urlm4=doc3.getElementsByClass("stats-row");
								for (Element element4 : urlm4) {
									mapOverviewmap=new HashMap<>();
									String overviewname=element4.select("span").get(0).text().replaceAll("(\\s)","");
									String overviewvalue=element4.select("span").get(1).text().replaceAll("(\\s)","");
									mapOverviewmap.put("name", overviewname);
									mapOverviewmap.put("value", overviewvalue);
									maplist.add(mapOverviewmap);
								}
								Elements overviewele1=element3.getElementsByClass("two-grid win-defeat-container");
								Document doc4=Jsoup.parse(overviewele1.toString());//获取biggest的下面col的div
								Elements urlm5=doc4.getElementsByClass("col");
								for (Element element5 : urlm5) {
									//String img=element5.select("img").attr("src").toString();
									if (element5.select("div").size()>2) {
										mapOverviewmap=new HashMap<>();
										String biggest=element5.select("div").get(2).child(0).text();
										String vs=element5.select("div").get(2).child(1).text();
										mapOverviewmap.put("bigname", biggest);
										mapOverviewmap.put("bigvalue", vs);
										maplist.add(mapOverviewmap);
									}	
								}
								if (viewname.length()>0) {									
									listmapOverviewmap=new HashMap<String,Object>();
									listmapOverviewmap.put(viewname,maplist);
									Totalmaplist.add(listmapOverviewmap);
								}								
							}
							mapOverview=jsonObject.toJson(Totalmaplist);	
							///////////////实体/////////////
							model=new TeamMapsParent();
							model.setTltvteamId(Integer.parseInt(tltvteamId));
							model.setTeamName(teamName);
							model.setMapBreakdown(mapBreakdown);
							model.setMapHighlight(mapHighlight);
							model.setMapOverview(mapOverview);
							insert.add(model);	
						}
						
				        ////////开始存储////////////////
					}
					
				}
				redisutil.setString("addTeamParentMaps",jsonObject.toJson(insert));				
			}

		} catch (Exception e) {
			System.out.println("HLTV站点,获取比赛赛事异常：" + e.getMessage()+"===团队名称:"+errname);
		}
	}
	
	
	
	/**
	 *查询HLTV站点的所有的团队地图扩展概况信息
	 * 
	 * @throws IOException
	 */
	//@Scheduled(fixedRate = 1000 * 6)
	//@Scheduled(cron="0 48 19 ? * *")
	public void getHltvTeanMapsExpansionInfo() throws IOException {			
		redisutil.getconnection();
		List<TeamMapsChild> insert=new ArrayList<TeamMapsChild>();
		String errname="";
		Gson jsonObject = new Gson();		
		Resultmodel msg = null;
		String tltvteamId="0";
		String teamName="";
		String mapstypeId="0";
		String mapstypeName="";
		String rawStats="";
		String sideBreakdown="";
		String gameChangers="";
		String matches="";
		int jj=0;
		try {			
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
						 teamName = element2.select("a").text();// HLTV站点的团队名称
						 errname=teamName;
						 tltvteamId=getNumber(element2.select("a").attr("href").toString()).replace("/", "");// HLTV站点的团队id
						msg = HttpsUtils.Get(MessageFormat.format("{0}/stats/teams/maps/{1}/{2}", urlhltv,tltvteamId,URLEncoder.encode(teamName,"utf-8")));//获取团队的比赛信息
						if (msg.getCode()==200) {
							/////////////////////////////根据团队地图的信息，获取额外扩展信息///////////////
							Document doc1=Jsoup.parse(msg.getMessage());
							Element urlm1=doc1.getElementsByClass("tabs standard-box").get(1);
							Document doc2=Jsoup.parse(urlm1.toString());
							Elements urlm2=doc2.getElementsByClass("stats-top-menu-item");//获取扩展的maps详情
							for (Element element3 : urlm2) {								
								String url=element3.select("a").attr("href").toString();
								mapstypeId=getNumber(url).replaceAll("(\\/)", "");//扩展地图的id
								mapstypeName=element3.select("a").text();// 扩展地图的名称								
								msg=HttpsUtils.Get(urlhltv+url);//获取扩展地图的连接拿取数据
								if (msg.getCode()==200) {
									List<Map<String, Object>> rawstatsmaplist=new ArrayList<>();
									Map<String, Object> rawstatsmap=new HashMap<>();
									List<Map<String, Object>> sitebreakdownmaplist=new ArrayList<>();
									Map<String, Object> sitebreakdownmap=new HashMap<>();
									List<Map<String, Object>> gamechangersdownmaplist=new ArrayList<>();
									Map<String, Object> gamechangersdownmap=new HashMap<>();
									TeamMapsChild model=null;
									Document doc3=Jsoup.parse(msg.getMessage());
									Elements urlm3=doc3.getElementsByClass("columns");//存储 raw stats 和 site breakdown
									Document doc4=Jsoup.parse(urlm3.toString());//获取columns的下面col
									Elements urlm4=doc4.getElementsByClass("col");
									for (Element element4 : urlm4) {										
										Elements rawstatsele=element4.getElementsByClass("stats-rows standard-box");
										Document doc5=Jsoup.parse(rawstatsele.toString());//获取stats-rows standard-box的下面stats-row
										Elements urlm5=doc5.getElementsByClass("stats-row");
										for (Element element5 : urlm5) {	
											rawstatsmap=new HashMap<>();
											String raestatsname=element5.select("span").get(0).text().replaceAll("(\\s)","");
											String raestatsvalue=element5.select("span").get(1).text().replaceAll("(\\s)","");
											rawstatsmap.put("name", raestatsname);//存储rawstats
											rawstatsmap.put("value", raestatsvalue);//存储rawstats
											rawstatsmaplist.add(rawstatsmap);
										}
										rawStats=jsonObject.toJson(rawstatsmaplist);
										/////////////////////////存储 Side breakdown////////////////////////
										String sitebreakdownname= element4.getElementsByClass("standard-box big-padding").select("span").text();//存储 Side breakdown
										String sitebreakdownvalue= element4.getElementsByClass("graph ").attr("data-fusionchart-config").toString();//存储 Side breakdown
										if (sitebreakdownname.length()>0) {
											sitebreakdownmap=new HashMap<>();
											sitebreakdownmap.put("name", sitebreakdownname);
											sitebreakdownmap.put("value", sitebreakdownvalue);
											sitebreakdownmaplist.add(sitebreakdownmap);
											sideBreakdown=jsonObject.toJson(sitebreakdownmaplist);	
										}																			
									}
									/////////////////////////Game changers////////////////////									
									Elements gamechangerseles=doc3.getElementsByClass("col standard-box big-padding");
									for (Element element4 : gamechangerseles) {			
										gamechangersdownmap=new HashMap<>();
										String gamechangersname=element4.select("div").get(0).child(1).text();
										String gamechangersvalue=element4.select("div").get(0).child(0).text();
										gamechangersdownmap.put("name", gamechangersname);
										gamechangersdownmap.put("value", gamechangersvalue);
										gamechangersdownmaplist.add(gamechangersdownmap);
									}
									gameChangers=jsonObject.toJson(gamechangersdownmaplist);
									////////////////////////Matches/////////////////////////
									List<Map<String, Object>> matchesmaplist=new ArrayList<>();
									Elements matcheslels=doc3.getElementsByClass("stats-table");
									for (Element element4 : matcheslels) {
										Elements urlm5 = element4.select("tbody").select("tr");										
										for (Element element5 : urlm5) {
											Map<String, Object> matchesmap=new HashMap<>();
											Map<String, Object> eventmap=new HashMap<>();
											Map<String, Object> opponentmap=new HashMap<>();
											// 判断是否存在比赛数据
											if (element4.select("tbody").select("tr").size()>=1) {									
												String date=element5.select("td").get(0).select("a").text();//时间												
												opponentmap.put("img", element5.select("td").get(1).select("img").attr("src"));// opponent 图片
												opponentmap.put("name", element5.select("td").get(1).select("a").text());	//opponent 名称
												eventmap.put("img", element5.select("td").get(2).select("img").attr("src"));//event 图片
												eventmap.put("name", element5.select("td").get(2).select("a").text());	// event 名称
												String Result=element5.select("td").get(3).text().replaceAll("(\\s)", "");// 比分											
												matchesmap.put("date", date);
												matchesmap.put("opponent",opponentmap);
												matchesmap.put("event",eventmap);
												matchesmap.put("result",Result);
												matchesmaplist.add(matchesmap);
											}
										}
									}
									matches=jsonObject.toJson(matchesmaplist);
									///////////////////////////开始存储//////////////////////////////
									model=new TeamMapsChild();
									model.setTltvteamId(Integer.parseInt(tltvteamId));
									model.setTeamName(teamName);
									model.setMapstypeId(Integer.parseInt(mapstypeId));
									model.setMapstypeName(mapstypeName);
									model.setRawStats(rawStats);
									model.setSideBreakdown(sideBreakdown);
									model.setGameChangers(gameChangers);
									model.setMatches(matches);
									insert.add(model);										
								}
								
							}
						}
	                     //////////////////////开始存储///////////////////////
						jj++;
						if (jj==2) {
							//redisutil.setString("addTeamMapsExpansion",jsonObject.toJson(insert));
							String vv="";
						}	
					}
					
				}
							
			}

		} catch (Exception e) {
			System.out.println("HLTV站点,获取团队扩展信息异常：" + e.getMessage()+"===团队名称:"+errname);
		}
	}
	
	
	
	/////////////////////////////////////////////////玩家信息///////////////////////////////////////////////////////////////
	

	/**
	 *抓取HLTV站点的玩家列表信息
	 * 
	 * @throws IOException
	 */
	//@Scheduled(fixedRate = 1000 * 6)
	//@Scheduled(cron="0 48 19 ? * *")
	public void getHltvGamePlayersInfo() throws IOException {	
		redisutil.getconnection();
		List<GamePlayers> insertlist=new ArrayList<>();
		Gson jsonObject = new Gson();
		String playerId="0";
		String playerName="";
		String country="";
		String countryImg="";
		String teamName="";
		String maps="";
		String killdeathDiff="";
		String killdeathScale="";
		String rating="";
		Resultmodel msg = null;
		int jj=0;
		try {
			msg=HttpsUtils.Get(MessageFormat.format("{0}", urlhltvplayer));
			if (msg.getCode()==200) {
				Document doc=Jsoup.parse(msg.getMessage());
				Elements elements=doc.getElementsByClass("stats-table player-ratings-table").select("tbody").select("tr");
				if (elements.size()>=1) {
					Map<String, Object> teamMaps=new HashMap<>();
					List<Map<String, Object>> listteamMaps=new ArrayList<>();
					GamePlayers model=null;
					for (Element element : elements) {		
						 model=new GamePlayers();
						 listteamMaps=new ArrayList<>();
						 //国家图片
						 countryImg=element.getElementsByClass("playerCol ").select("img").attr("src").toString();
						 //国家名称
						 country=element.getElementsByClass("playerCol ").select("img").attr("title").toString();
						 //玩家ID
						 playerId=getNumber(element.getElementsByClass("playerCol ").select("a").attr("href").toString()).replaceAll("(\\/)", "");
						 // 玩家名称
						 playerName=element.getElementsByClass("playerCol ").select("a").text();
						 Elements team=element.getElementsByClass("teamCol").select("a");
						 for (Element element2 : team) {
							 teamMaps=new HashMap<>();
							 teamMaps.put("teamid", getNumber(element2.select("img").attr("src").toString()).replaceAll("(\\/)", ""));
							 teamMaps.put("teamname", element2.select("img").attr("title").toString());
							 teamMaps.put("teamimg", element2.select("img").attr("src").toString());
							 listteamMaps.add(teamMaps);
						 }
						 // 属于的团队
						 teamName=jsonObject.toJson(listteamMaps);
						 // maps
						 maps=element.getElementsByClass("statsDetail").get(0).text();
						 // 杀敌和死亡差异
						 killdeathDiff=element.getElementsByClass("kdDiffCol won").text();
						 //杀敌和死亡比例
						 killdeathScale=element.getElementsByClass("statsDetail").get(1).text();
						 // 评分
						 rating=element.getElementsByClass("ratingCol").text();
						 ////////////////////////////////开始存储///////////////////////////////
						 jj++;
						 model.setPlayerId(Integer.parseInt(playerId));
						 model.setPlayerName(playerName);
						 model.setCountry(country);
						 model.setCountryImg(countryImg);
						 model.setTeamName(teamName);
						 model.setMaps(maps);
						 model.setKilldeathDiff(killdeathDiff);
						 model.setKilldeathScale(killdeathScale);
						 model.setRating(rating);
						 insertlist.add(model);
						 if (jj==10) {
							redisutil.setString("addTeamPlayers", jsonObject.toJson(insertlist));
						}
					}
					
				}
			}
			
		} catch (Exception e) {
			System.out.println("抓取玩家列表信息异常:"+e.getMessage());
		}
	}
	
	////////////////////////////////////////////////////////辅助/////////////////////////////////////////////////////////////

	/**
	 * 获取一串字符里面的文字"(\\D)(\\w+.\\w+)(\\D)"
	 * @param data
	 * @return
	 */
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
	/**
	 * 获取/ /里面的数字"(\\/\\d+\\/)"
	 * @param data
	 * @return
	 */
	public static String getNumber(String data) {
		String madata = "";
		String pattern = "(\\/\\d+)"; // 精确匹配文字
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
	 * 获取括号内的信息"(?<=\\()[^\\)]+"
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
	
	/**
	 * 验证是否为数字
	 * @param data
	 * @return
	 */
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
	
	/**
	   * 将长时间格式字符串转换为时间 yyyy-MM-dd
	   * 
	   * @param strDate
	   * @return
	   */
	public static Date strToDateLong(String strDate) {
	   SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	   ParsePosition pos = new ParsePosition(0);
	   Date strtodate = formatter.parse(strDate, pos);
	   return strtodate;
	}
	
	

}
