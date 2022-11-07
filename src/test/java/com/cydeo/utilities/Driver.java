package com.cydeo.utilities;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.concurrent.TimeUnit;

public class Driver {
    private Driver(){}// constructor ->private

   //create re-usable utility method which will return same driver instance when we call it
  private  static InheritableThreadLocal<WebDriver> driverPool=new InheritableThreadLocal<>();

    public static WebDriver getDriver(){

    if(driverPool .get()==null){

        String browserType=ConfigurationReader.getProperty("browser");

       switch (browserType){
           case "chrome":
               WebDriverManager.chromedriver().setup();
               driverPool.set (new ChromeDriver());
               driverPool .get().manage().window().maximize();
               driverPool.get() .manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
               break;
           case "fireFox":
               WebDriverManager.firefoxdriver().setup();
               driverPool .set(new FirefoxDriver());
               driverPool.get() .manage().window().maximize();
               driverPool .get().manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
               break;

       }


    }

//same driver instance will be return every time we call Driver.getDriver() meth

return driverPool.get() ;

    }

public static void  closeDriver(){

        if(driverPool.get()!=null)
        driverPool.get().quit();//this line kill the session .value will be not null but nothing when we clall again in next @test come to if(driver=null )-->false because = nothing because that we need the next step
    driverPool.remove();// assign to null again to return to if ()in above

}


}
