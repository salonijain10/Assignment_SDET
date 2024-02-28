import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import atu.testrecorder.ATUTestRecorder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



public class TestFirst {
	public static void main(String[] args) throws Exception {
		 DateFormat dateFormat = new SimpleDateFormat("yy-MM-dd HH-mm-ss");
		  Date date = new Date();
		  //Created object of ATUTestRecorder
		  //Provide path to store videos and file name format.
		  ATUTestRecorder recorder = new ATUTestRecorder("D:\\ScriptVideos\\","TestVideo-"+dateFormat.format(date),false);
		  //To start video recording.
		  recorder.start(); 
		
        // Set the path to the ChromeDriver executable
        System.setProperty("webdriver.chrome.driver", "D:\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        // Create a new instance of the Chrome driver
        WebDriver driver = new ChromeDriver();

        // Launch the URL
        driver.get("https://testpages.herokuapp.com/styled/tag/dynamic-table.html");
        
        // Wait for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }        
        
        // Find the Table Data button and click it
        WebElement tableDataButton = driver.findElement(By.xpath ("//*[contains(text(),'Table Data')]"));
        tableDataButton.click();
        
        // Insert data into the input text box using JavaScript
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String jsonData = "[{\"name\" : \"Bob\", \"age\" : 20, \"gender\": \"male\"}, {\"name\": \"George\", \"age\" : 42, \"gender\": \"male\"}, {\"name\": \"Sara\", \"age\" : 42, \"gender\": \"female\"}, {\"name\": \"Conor\", \"age\" : 40, \"gender\": \"male\"}, {\"name\": \"Jennifer\", \"age\" : 42, \"gender\": \"female\"}]";
        js.executeScript("document.getElementById('jsondata').value='" + jsonData + "'");
        
        // Wait for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Click on the Refresh Table button
        WebElement refreshButton = driver.findElement(By.xpath ("//*[contains(text(),'Refresh Table')]"));
        refreshButton.click();
                
        // Wait for 2 seconds for the table to be visible
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        
        // Get the table rows
        List<WebElement> rows = driver.findElements(By.xpath("//table[@id='dynamictable']//tbody//tr"));
        
     
        // Assert the data in the table with the inserted data
        String[][] expectedData = {
                {"Bob", "20", "male"},
                {"George", "42", "male"},
                {"Sara", "42", "female"},
                {"Conor", "40", "male"},
                {"Jennifer", "42", "female"}
        };

        boolean dataMatches = true;
        int rowNumber = 1;
        for (WebElement row : rows) {
            List<WebElement> columns = row.findElements(By.tagName("td"));
            for (int i = 0; i < columns.size(); i++) {
                if (!columns.get(i).getText().equals(expectedData[rowNumber - 1][i])) {
                    dataMatches = false;
                    break;
                }
            }
            if (!dataMatches) {
                break;
            }
            rowNumber++;
        }

        // Assert the data
        if (dataMatches) {
            System.out.println("Data matches!");
        } else {
            System.out.println("Data does not match!");
        }       

        // Wait for 2 seconds
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }        
        
        // Close the browser
        driver.close();
        
        
      //To stop video recording.
        recorder.stop();
        
    }


}
