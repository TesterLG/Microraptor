package com.huice.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

/* 
 * chrome 48.0.2535.0
 * firefox 39.0 
 * 为了测试AutoTest,安装firefox 22.0.0
 * 上测试没有问题,改用其他更高版本的浏览器,脚本无法运行.
 */
public class DriverFactory {

	//static 因为要全局用到,各个方法需要
	private static WebDriver driver=null;
	
	private static WebDriver createDriver(String type){
		switch(type.toLowerCase().trim()){
		case"firefox":
			driver= createFirefox();
			break;
		case "chrome":
			System.setProperty("webdriver.chrome.driver", "source/chromedriver.exe");
			driver=createChrome();
			break;
		case "ie":
			System.setProperty("webdriver.ie.driver", "source/IEDriverServer.exe");
			driver = createIEDriver();
			break;	
		default:
			System.out.println("Error:Invalid Browser Type!");
			break;
		}
		return driver;
	}

	private static WebDriver createIEDriver() {
		return new InternetExplorerDriver();	
	}

	private static WebDriver createChrome() {
		return new ChromeDriver();
	}
	private static WebDriver createFirefox(){
		return new FirefoxDriver(); 
	}
	
	//单例模式: 1.构造方法私有 2.关掉门,开扇窗,写产生实例的方法  
	/*
	//方式1: 加同步锁
	//线程安全1.加synchronized
	private static synchronized WebDriver getDriver(String type){
		if(driver==null){
			driver=createDriver(type);
		}
		return driver;
	}
	*/
	//方式2:双重校验
	private static WebDriver getDriver(String type){
		if(driver==null){
			synchronized (DriverFactory.class) {
				if(driver==null){
					driver=createDriver(type);
				}
			}
		}
		return driver;
	}
	//构造方法私有
	private DriverFactory(){}
}
