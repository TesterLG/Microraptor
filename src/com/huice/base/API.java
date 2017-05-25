package com.huice.base;
/*
 * 错误提示： It is indirectly referenced from required .class file
原因：你正要使用的类调用了另一个类，而这个类又调用了其他类，这种关系可能会有好多层。而在这个调用的过程中，某个类所在的包的缺失就会造成以上那个错误。
实际是Referenced Librariesz中缺少guaba-xxx.jar,其中有WebDriverWait第三层调用的com.google.common.*;
# 经验:学会看提示,根据提示找原因.
 */
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class API{
	/*
	 * 省略driver
	 * 1.定义私有driver
	 * 2.建立Api构造方法,构造方法是初始化数据的.传参driver,在里面定义一个替代它
	 * 3.this.driver=driver 这个类的driver=传入的driver因为他们同名,要区分他们,
	 * 4.Api中的静态方法变成成员方法,driver参数可以省了,因为new实例化这个类Api的时候已经new driver了.
	 * 5.要用的话Api,先new出来,也不麻烦,整个脚本都用的到Api,在diver起头的时候,new它
	 */
	
	private WebDriver driver=null;

	public API(WebDriver driver){
		this.driver=driver;
	}
	
	//等待时间time
	public void wait(int time) {
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* 分析和演变过程
	//1.void 2.先不写入参了 3.WebElement对象,还要内容,就写这2入参,4.实际就省了2句话,还是有点繁琐.
	//WebElement keyword = driver.findElement(By.id("keyword"));
	//	Api.sendkeys(keyword, "诺基亚");   合成一句话.driver藏里面. 5.(By.id("keyword")怎么包到里面去?每次不一样.
    //6.返回值是By对象,这是个类,静态方法是id(),入参是个By对象, 
	//By by=By.id("keyword");
	//WebElement keyword= driver.findElement(by);  7.这是标准写法, 连写容易晕,不知怎么办.因为就用一次.
    //public static void sendKeys(WebDriver driver,String text){
 *   driver也想省了,发现不好省,Api中一直用它,省了,怎么找?又是个全局的
 */
	public void sendKeys(By by,String text){
		WebElement element=driver.findElement(by);
		element.click();
		element.clear();
		element.sendKeys(text);
	}
	
	/*
	 * 把select简化,理想的语句这样:api.select(By.id("category"),"value","3")
	 * 1.建立方法,参数也是三个
	 * 2.Select类有三个selectByxxx 方法.
	 * 	select.selectByIndex(int index)		select.selectByValue (String value);
		select.selectByVisibleText(String text);
	   3.参数type不同针对三个不同方法.
	   4.麻烦事来了,第3个参数类型设置成什么? int? String? 用重载? low! 用万类之祖Object
	   5.修改参数 toString Integer.parseInt(value.toString())绕了个弯
	   6.switch
	 */
	public void select(By by,String type,Object value){
		WebElement element =driver.findElement(by);
		Select select =new Select(element); 
		switch (type.toLowerCase().trim()){
			case "index":
				select.selectByIndex(Integer.parseInt(value.toString()));
				break;
			case "value":
				select.selectByValue(value.toString());
				break;
			case "text":
				select.selectByVisibleText(value.toString());
				
			default:
				System.out.println("类型不存在!");
				break;
	}
	}
	
	/*  随机验证下拉选项
	 * 分类的下来表,验证列表可用,固定给值,或随机值,雨露均沾 ;)随机选
	 * 
	 * */
	public void selectTheLast(By by){
		WebElement element =driver.findElement(by);
		Select select=new Select(element);
		select.selectByIndex(select.getOptions().size()-1);
	}
	/*
	 * 随机选,也写By参数, 
	 * 怎么解决随机? 
	 * 2个类解决随机
	 *  1)Random 类 , 方法是成员方法,要先new出Random,看有什么方法 nextInt()整形,
	 *  有2个方法重载,无参数的,打出来看一下(main()中测试).再试有参数的,给几个数试试,10..,1..试出不包括右边界: [0,n)
	 *  2)Math 包
	 *  
	 *  有点不完美,第一个选择是全部分类,怎么在随机选择时不选择它? nextInt()不能控制左边界.
	 *  6  - 11
	 *  
	 *  6     6+0
	 *  7     6+1
	 *  8     6+2
	 *  9
	 *  10
	 *  11    6+5
	 *  +的数从0开始的,6+[0,5] > 6+[0,11-6] > 11是end > Start+nextInt(End-Start) 
	 *  从start开始,1,就把第一个踢出去了,永远不会选择.
	 */
	public void selectTheRandom(int start,By by){
		WebElement element =driver.findElement(by);
		Select select=new Select(element);
		Random random=new Random();
//		int index=random.nextInt(select.getOptions().size());
		int index=start+random.nextInt(select.getOptions().size()-start);
		select.selectByIndex(index);
	}
	
	/*
	 * 根据给定的页签名称,选择页签的方法
	 * 就一个入参,为什么不用定位了,为什么不用传By参数了?封装的是大家都能用的select框,在页面上有多个,定位信息不一样,要传定位信息.
	 * 业务封装,这个导航栏在页面上存在的,切到别的页面上也有,在任何一个页面要切页面,要用的这样个方法.
	 * div有id
	 */
	public void clickByText(String text){
		List<WebElement> list=driver.findElements(By.xpath("//div[@id='mainNav']/a"));//待点
		for (WebElement e : list) {
			if(e.getText().equals(text)){
				e.click();
				break;
			}
		}	
	}
	
	public WebElement waitElemet(final By by,int timeout){
		try {
			new WebDriverWait(driver, timeout).until(new ExpectedCondition<WebElement>() {
				@Override
				public WebElement apply(WebDriver driver) {
					return driver.findElement(by);
				}
			});
		} catch (Exception e) {
		System.out.println("等待"+timeout+"秒后元素"+by+"未出现!");
		} 
		
	return null;
	}
	
	/*
	 * 对象可见和存在是2回事.
	 * 判定元素是否存在也要等待,seleinum里没有判定元素存在的方法,1里有,2里没有,只留了个isDisplay.
	 * 什么是存在? 就是findElement没有发生异常,waiElement改造一下,就是返回boolean
	 * 没发生异常return true, 发生异常return false;
	 */
	
}
