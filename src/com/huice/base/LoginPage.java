package com.huice.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

//没写在api中,把登陆页面抽象成了一个对象,就是pageobject模式.元素=对象,逻辑=方法.
//封装太细不易维护,封装太粗复用性差,在实际工作总要根据业务逻辑找到平衡点.
/**
 * 
 *是否登陆成功,1判断提示页面有"登陆成功" 2.首页上方是否有"xxx欢迎回来" 元素存在.seleinum没有判断元素存在的方法,
 *2.0后取代的方法是 isDisplayed() isEnabled() isSelect()
 *强行刷链接还是好的
 */
public class LoginPage {

	public static boolean login(WebDriver driver, String userName, String pwd){
		driver.get("http://www.huicewang.com/ecshop/user.php");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		driver.findElement(By.name("username")).sendKeys(userName);
		driver.findElement(By.name("password")).sendKeys(pwd);
		driver.findElement(By.name("submit")).click();
//		driver.findElement(By.name("submit")).isDisplayed();
		String result = driver.findElement(By.xpath("//div[@class='boxCenterList RelaArticle']/div/p")).getText();
		
		if(result.contains("登录成功")){
			return true;
		}else{
			return false;
		}
	
	}
}
