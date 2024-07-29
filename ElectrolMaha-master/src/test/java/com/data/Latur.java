package com.data;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

 

public class Latur {

    public void downloadAllPdfs() throws InterruptedException {
    //    WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://ceoelection.maharashtra.gov.in/SearchInfo/ListPSs.aspx");
            driver.manage().window().maximize();
            Thread.sleep(5000);

            // Click on language selection
            driver.findElement(By.id("ctl00_content_LangList_1")).click();
            Thread.sleep(3000);

            // Get the district dropdown
            WebElement dist = driver.findElement(By.id("ctl00_content_DistrictList"));
            Select citySelect = new Select(dist);

            // Loop through each district
            for (int i = 0; i < citySelect.getOptions().size(); i++) {
                dist = driver.findElement(By.id("ctl00_content_DistrictList")); // Refresh element reference
                citySelect = new Select(dist);
                WebElement cityOption = citySelect.getOptions().get(i);
                String cityValue = cityOption.getAttribute("value");
                if (cityValue.isEmpty() || cityValue.equals("0")) {
                    continue; // Skip the default or empty value
                }

                citySelect.selectByValue(cityValue);
                Thread.sleep(3000);

                // Get the assembly dropdown
                WebElement assmb = driver.findElement(By.id("ctl00_content_AssemblyList"));
                Select assmblistSelect = new Select(assmb);

                // Loop through each assembly constituency
                for (int j = 0; j < assmblistSelect.getOptions().size(); j++) {
                    assmb = driver.findElement(By.id("ctl00_content_AssemblyList")); // Refresh element reference
                    assmblistSelect = new Select(assmb);
                    WebElement assemblyOption = assmblistSelect.getOptions().get(j);
                    String assemblyValue = assemblyOption.getAttribute("value");
                    if (assemblyValue.isEmpty() || assemblyValue.equals("0")) {
                        continue; // Skip the default or empty value
                    }

                    assmblistSelect.selectByValue(assemblyValue);
                    Thread.sleep(4000);

                    // Click the report link to generate the PDF
                    try {
                        WebElement element = driver
                                .findElement(By.id("ctl00_content_ReportViewer1_ctl05_ctl04_ctl00_ButtonLink"));
                        new Actions(driver).click(element).perform();
                        Thread.sleep(4000);

                        // Click on the PDF link to download
                        WebElement pdfLink = driver.findElement(By.linkText("PDF"));
                        Thread.sleep(4000);
                        pdfLink.click();
                        Thread.sleep(4000); // Wait for download to start
                    } catch (Exception e) {
                        System.out.println("Error handling PDF download for District: " + cityValue + " Assembly: "
                                + assemblyValue);
                        e.printStackTrace();
                    }
                }
            }
        } finally {
            driver.quit(); // Close the browser
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Latur ll = new Latur();
        ll.downloadAllPdfs();
    }
}
