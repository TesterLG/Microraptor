package com.huice.test;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.huice.base.LoginPage;
import com.huice.base.DriverFactory;
import com.huice.base.Order;

/**
 * 
 * @author thinkpad
 *for (String string : info) 默认右边是个集合,map不行,info改成info.keySet()循环map的一种方式,把以第一个key做了循环,有了可以就可以拿到value
 *把页面抽象成类,对象,元素是类的成员变量,页面的操作是类的成员方法. 不用管元素定位的事,统一管理,写在类开始的地方.下面就直接用定义的变量
 *seleinum强制定义的格式,实际很少使用.不好.How.name How.id 2种,单一
 *把driver藏起来了,不支持driver.的操作.
 *他是注解类,new之前已经定义好了.
 *跨页面操作,new page很多,无法封装
 *好处: 元素统一管理,修改方便.
 *     初步数据和脚本分离.
 *
 *折中的方案: LPage.java
 *找平衡点.
 *知道就行了.
 *
 *首页的内容很重要,(如产品经理数据分析过的产品信息),所以首页链接不能为空,失效...
 */
public class Case01 {
public static void main(String[] args) {
//	WebDriver driver=DriverFactory.getDriver("chrome");
	WebDriver driver=DriverFactory.getDriver("firefox");
	LoginPage.login(driver, "gaoq", "123456");
	Map<String,String> info=Order.searchOrderById(driver, "2017032497536");
	
	for (String key : info.keySet()) {
		String s=info.get(key);
		System.out.println("key:"+key+" | value:"+s);
	}
	
}
}
