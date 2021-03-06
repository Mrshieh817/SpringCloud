package com.xcf.mybatis.Controller;


import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.redisson.api.RBlockingQueue;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xcf.mybatis.UserType;
import com.xcf.mybatis.Core.SysUser;
import com.xcf.mybatis.Core.Delay.DelayJob;
import com.xcf.mybatis.Service.Delay.DelayJobService;
import com.xcf.mybatis.Tool.IPUtils;
import com.xcf.mybatis.Tool.StringDiyUtils;
import com.xcf.mybatis.aspect.WebLog;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
* @author 作者:大飞
* @version 创建时间：2019年12月25日 下午2:00:24
* /swagger-ui.html
*/
@Api(tags = "用户操作接口")
@Controller
@RequestMapping("/user")
@Slf4j
public class UserController {
	
	@Autowired
	private DelayJobService delayjobservice;
	
	@Autowired
	private RedissonClient client;
	
	@Value("${aite.canshu}")
	private String canshu;
	
	@ApiOperation(value = "获取otp", notes="通过手机号获取OTP验证码")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "telephone", value = "电话号码", paramType = "query", defaultValue = "10086", required = false, dataType = "String"),
		@ApiImplicitParam(name = "orderId", value = "订单号码", paramType = "query", defaultValue = "10086", required = false, dataType = "String") 
	})
    @RequestMapping(value = "getotp", method=RequestMethod.GET)
    @ResponseBody
    public Object test(@ApiParam(value="telephone",required=false,defaultValue="18716398693")String telephone){
		System.out.println("telephone:"+telephone);
		SysUser model=new SysUser();
		model.setId(1008611);
		//model.setName("中国移动");
		model.setName(canshu);
		model.setPass("1008612501235698696");
		model.setTel("18716398693");
		return model;
	}
	
	@ApiOperation("测试自动补0")
	@RequestMapping("/autoaddzero")
	@ResponseBody
	public Object autoaddzero(@RequestParam(name = "code", required = true) String code) {
		System.out.println(code);
		int count = 10;
		int oldcount = code.length();
		if (oldcount < count) {
			for (int i = 0; i < count - oldcount; i++) {
				code = code + "0";
			}
		}
		return code;

	}
	
	@ApiOperation("测试redssion的延迟服务")
	@RequestMapping("/redisson")
	@ResponseBody
	public void redisson() {
		try {
			DelayJob job=new DelayJob();
		    job.setNametest(new Date().toGMTString());
			delayjobservice.submitJob(job, 5, TimeUnit.SECONDS);
		}catch (Exception e) {
			System.out.println("异常："+e.getMessage());
		}finally {
			System.out.println("执行完毕!");
		}
	}
	
	@ApiOperation("测试吃什么")
	@RequestMapping("/testeat")
	@ResponseBody
	public Object testeat() {
		Random random = new Random();
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("1", "烤鱼");
		map.put("2", "火锅");
		map.put("3", "串串");
		Integer count= random.nextInt(3)+1;
		System.out.println("随机:"+map.get(count.toString()));
		return "吃"+map.get(count.toString());
	}
	
	
	@RequestMapping("/testel")
	public void testel() {
		 //测试SpringEL解析器
		//设置文字模板,其中#{}表示表达式的起止，#user是表达式字符串，表示引用一个变量。
       // String template = "#{#user}，早上好";
		//设置一个取值模板，root是需要获取根部的数据,必须加,[ff]代表map数组的key名字,到时候用这个来匹配数组ff，并且拿到他的值SysUser对象,所以#root[ff].name，这个.name实际上就是获取了数组值对象.name然后拿到它的值
        String template = "#root[ff].name";
        ExpressionParser paser = new SpelExpressionParser();//创建表达式解析器
         SysUser ff=new SysUser();
         ff.setName("caonim");
        //通过evaluationContext.setVariable可以在上下文中设定变量。
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("ff", ff);
        EvaluationContext context = new StandardEvaluationContext(map);
        //context.setVariable("ff","黎明");

        //解析表达式，如果表达式是一个模板表达式，需要为解析传入模板解析器上下文。
       // Expression expression = paser.parseExpression(template,new TemplateParserContext());
        Expression expression = paser.parseExpression(template);
        
        //bool类型获取及EL的T(type)表达式
        ExpressionParser boolpaser = new SpelExpressionParser();//创建表达式解析器
        boolean boolff=  boolpaser.parseExpression("T(Boolean).TRUE").getValue(Boolean.class);
         System.out.println("EL表达式Bool类型:"+boolff);
        //使用Expression.getValue()获取表达式的值，这里传入了Evalution上下文，第二个参数是类型参数，表示返回值的类型。
        System.out.println("EL表达式获取参数匹配信息:"+expression.getValue(context,String.class));
	}
	
	/**
	 * 测试时间计算
	 * @return
	 */
	@RequestMapping({"/testtime"})
	@ResponseBody
	public Object testtime() {
		Date date=new Date();
		Date deDate=new Date(date.getTime()-(30*60*1000));
		return deDate.toString();
		
	}
	
	
	/**
	 * 测试随机且自定义订单数
	 * @return
	 */
	@RequestMapping("/testautoId")
	@ResponseBody
	public Object testautoId() {
		StringBuilder builder=new StringBuilder();
		String NUMBERS = "0123456789";
		Random random=new Random();
		SysUser  core=new SysUser();
		core.setId(1008611);
		core.setName("中国移动");
		core.setPass(builder.append(NUMBERS.charAt(random.nextInt(8))).toString());
		core.setTel(random.nextInt(1000000000)+"");
		//1.把实体信息添加到JSon对象
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("person", core);
		System.out.println("添加SySUser对象到JSON:"+jsonObject);
		//2.解析JSON里面的信息,然后转换成SysUser对象
		SysUser modelSysUser=(SysUser)jsonObject.get("person");
		System.out.println("获取SysUser对象:"+modelSysUser.toString());
		//3.把List集合添加到Json集合
		JSONArray arry=new JSONArray(AddIncrement(core));
		System.err.println("添加JSON集合:"+arry);
		//4.获取JSON集合里面的对象
		SysUser arryobject= arry.getObject(0, SysUser.class);
		System.out.println("获取JSON集合里面的信息:"+arryobject);
		//String fyString="a,b,c,d,e,f,g";
		//StringDiyUtils.checkStrByNull(fyString.split(","));
		return StringDiyUtils.getOrderCode("XS", 6);
	}
	
	/**
	 * 集合公共添加方法
	 * @param <T>
	 * @param t
	 * @return
	 */
	private static <T> List<T> AddIncrement(T t){
		List<T> L=new ArrayList<T>();
		L.add(t);
		return L;
	}
	
	
	/**
	 * 获取IP
	 * @param request
	 * @return
	 */
	@WebLog(value = "AOP简单日志记录",description = "看看能不能记录上",params = {"id","name"})
	@RequestMapping("/getip")
	@ResponseBody
	public String getip(HttpServletRequest request) {
		HttpServletRequest request1 = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();//获取请求上下文
		//获取代理
		System.err.println(IPUtils.getDeviceType(request1.getHeader("User-Agent")));
		//获取ip信息
		String ipString=IPUtils.getIpAddr(request1);
		System.out.println(ipString);
		return ipString;
	}
	
	/**
	 * 测试枚举
	 * @param code
	 * @return
	 */
	@RequestMapping("/testenum/{code}")
	@ResponseBody
	public String testenum(@PathVariable(value ="code",required = true)String code) {
	 String result=UserType.getString(code);
	 System.out.println(result);
	 System.out.println(UserType.restatus.getStartTime());
	 String fgString=MessageFormat.format("message:{0}========>{1}", result,new Date());
	 log.info("记录结果:{}=======>{}========={}", result,new Date(),fgString);
	 return result;
	}
	

}
