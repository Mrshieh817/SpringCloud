package com.igxe.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.igxe.Core.student;
import com.igxe.Core.teacher;

@RestController
@RequestMapping({ "/sc" })
public class schoolContorller {
	
	/**
	 * 关于==和equals的比较
	 * @return
	 */
	
	@RequestMapping({ "/t" })
	public Object t() {
		student student=new student();
		teacher teacher=new teacher();
		student.setName("xcf");
		teacher.setName("xcf");
		String retString="equals类比较:"+student.equals(teacher)+"|equals类里面参数比较:"+student.getName().equals(teacher.getName());
		System.out.println(retString);
		System.out.println(student.getName()==teacher.getName());
		return retString;
	}

}
