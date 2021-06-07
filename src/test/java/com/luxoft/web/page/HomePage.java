package com.luxoft.web.page;


import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import java.util.Iterator;
import java.util.List;



public class HomePage {
    private final WebDriver driver;
    private HomePage homePage;
    private int qtyAvailable = 0;
    private int qtyNotAvailable = 0;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "/html/body/app-root/div/div/rz-search/rz-catalog/div/div[2]/aside/rz-filter-stack/div[3]")
    WebElement ManufacturerList; // poductListP;

    public void ManufacturerList() {
        int j1 = 0;
        int QtyMin = 100000;
        int QtyMax = 0;
        int NumberMin = 0;
        int NumberMax = 0;

        driver.findElement(By.name("search")).sendKeys("кресло" + Keys.ENTER);
        List<WebElement> links = ManufacturerList.findElements(By.tagName("li"));
        Iterator<WebElement> iter = links.iterator();

        while (iter.hasNext()) {

            WebElement item = iter.next();
            String label = item.getText();
            String ItemName = label.substring(0, label.indexOf("("));
            String sqty = item.findElement(By.tagName("a")).findElement(By.tagName("Label")).findElement(By.tagName("span")).getText();
            String qty = sqty.substring(sqty.indexOf("(") + 1, sqty.indexOf(")"));
            System.out.println( ItemName +" -- " + qty);

            int i = Integer.parseInt(qty);
            if (Integer.parseInt(qty) < QtyMin) {
                QtyMin = i;
                NumberMin = j1;
            }
            if (Integer.parseInt(qty) > QtyMax) {
                QtyMax = i;
                NumberMax = j1;
            }

            j1 += 1;
        }


        System.out.println(" Производитель с минимальным кол. товара    " + links.get(NumberMin).getText());
        System.out.println(" Производитель с максимальныным кол. товара " + links.get(NumberMax).getText());

    }

    public void CalcStatus (){

        WebElement WebCat = driver.findElement(By.xpath("/html/body/app-root/div/div/rz-search/rz-catalog/div/div[2]/section/rz-grid/ul"));
        List<WebElement> links2 = WebCat.findElements(By.tagName("li"));
        Iterator<WebElement> iter2 = links2.iterator();


        while (iter2.hasNext()) {

            WebElement item = iter2.next();
            String  label = item.getText();

            int l1 = label.lastIndexOf("Есть в наличии");
            if (l1 != -1) { qtyAvailable += 1; }

            l1 = label.lastIndexOf("Нет в наличии");
            if (l1 != -1) { qtyNotAvailable += 1; }
        }

    }

 public void compareLinkTexts_Count(String testManufacturer)  throws InterruptedException {
     int k1 = 0;
     int QtyPage = 1;
     String qty = "";
     String NameMfct = "";
     String Str1 = "";
     System.out.println("Работаем с поставщиком: " + testManufacturer);

     driver.findElement(By.name("search")).sendKeys("кресло" + Keys.ENTER);
     List<WebElement> links = ManufacturerList.findElements(By.tagName("li"));
     Iterator<WebElement> iter = links.iterator();



     while (iter.hasNext()) {
         WebElement item = iter.next();
         String label = item.getText();
         NameMfct = label.substring(0, label.indexOf("(")).trim();

         if (NameMfct.equalsIgnoreCase(testManufacturer)) {
             System.out.println("Поставщик :   " + links.get(k1).getText());

             String sqty = item.findElement(By.tagName("a")).findElement(By.tagName("Label")).findElement(By.tagName("span")).getText();
             qty = sqty.substring(sqty.indexOf("(") + 1, sqty.indexOf(")"));

             links.get(k1).click();
             break;
         }
          k1 += 1;
      }

      WebElement WebListPages = driver.findElement(By.xpath("/html/body/app-root/div/div/rz-search/rz-catalog/div/div[2]/section/rz-paginator"));
      List<WebElement> linksLPage = WebListPages.findElements(By.tagName("li"));

     if (linksLPage.size() == 0 ) {              // Одна страница с товаром
         CalcStatus ();
     } else {

         WebElement WebListPagesArrow = driver.findElement(By.xpath("/html/body/app-root/div/div/rz-search/rz-catalog/div/div[2]/section/rz-paginator/div/a[2]"));

         while (WebListPagesArrow.isEnabled()) {

             if  (Integer.parseInt(qty) > 3000) {     // если количество позиций большое, что не  успевает загружать страницу
                 Thread.sleep(3500);}

             CalcStatus ();
             try {
                 if (WebListPagesArrow.isEnabled()) {
                     WebListPagesArrow.click();
                 }
             } catch (ElementClickInterceptedException e) {
                 break;
             }

             QtyPage += 1;
         }

     }
         System.out.println("Количество страниц = " + QtyPage);
         System.out.println(NameMfct  +  " (Есть в наличии = " + qtyAvailable + "; Нет в наличии = " + qtyNotAvailable + ")");
         System.out.println(Str1);
 }


}
