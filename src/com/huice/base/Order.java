package com.huice.base;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * 
 * @author GQ
 * 根据订单号,查,返回信息.
 * 封装在业务层面,拿到别的公司用不了.
 * 
 * xpath是没有继承关系的,全局生效
 * 只能用封装好的,才有继承关系
 * LinkedHashMap 跟你塞的顺序一样,map有自己的排序规则.
 *
 *方式二:
 *动态拼串
 *tr从1开始,
 *+i 自动变成字符串,但是会丢精度.
 */
public class Order {

	public static Map<String,String> searchOrderById(WebDriver driver,String id){
		Map<String,String> result=new LinkedHashMap<String, String>();//有索引的map集合
		driver.get("http://www.huicewang.com/ecshop/user.php?act=order_list");
		String trXpath = "//div[@class='userCenterBox boxCenterList clearfix']/table//tr";
		List<WebElement> tr=driver.findElements(By.xpath(trXpath));
		
		//方式一:
		
		//方式二:
		for (int i = 2; i <= tr.size(); i++) {//tr的索引是从1开始
			String tdXpath=trXpath+"["+i+"]//td";
			List<WebElement> td=driver.findElements(By.xpath(tdXpath));
			if(td.get(0).getText().equals(id)){
				result.put("订单号",td.get(0).getText());//抓<a> 中的text值,不一定每次都管用.
				result.put("下单时间",td.get(1).getText());
				result.put("订单总金额", td.get(2).getText());
				result.put("订单状态", td.get(3).getText());
				result.put("操作", td.get(4).getText());
			}
		}
		
		return result;
		
	}
}
