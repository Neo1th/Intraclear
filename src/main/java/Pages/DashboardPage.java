package Pages;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import utilities.ReadXls;

public class DashboardPage extends BasePage {
	
	public DashboardPage(WebDriver driver, Properties loc) {
		super(driver, loc);
	}
	
	public void refresh() {
		driver.navigate().refresh();
	}
	
	public void clickstartDate() {
		click("StartDate");
		
	}
	public void clickendDate() {
		click("EndDate");
	}
	public void clickselectAccount() {
		click("SelectAccount");
	}
	
	public void clickApplyFilter() {
		click("ApplyFilterbtn");
	}
	
	public void clickArrowup() {
		click("Triangleup");
	}
	
	public void clickArrowdown() {
		click("Triangledown");
	}
	
	public String getDatafromfield(boolean isStart) {
	    WebElement dateInput;
	    
	    if (isStart) {
	        dateInput = getElement("StartDate");
	    } else {
	        dateInput = getElement("EndDate");
	    }

	    String dateValue = dateInput.getAttribute("value");
	    return dateValue;
	}
	
	public boolean compareDate(String filepath, int rowNum) {
	    try {
	        String[] startParts = getDatafromfield(true).split("-");
	        String[] endParts = getDatafromfield(false).split("-");

	        int startYear = Integer.parseInt(startParts[0]);
	        int startMonth = Integer.parseInt(startParts[1]);
	        int startDay = Integer.parseInt(startParts[2]);

	        int endYear = Integer.parseInt(endParts[0]);
	        int endMonth = Integer.parseInt(endParts[1]);
	        int endDay = Integer.parseInt(endParts[2]);

	        Map<String, String> rowData = ReadXls.getRowData(filepath, "Dashboard", rowNum);

	        boolean startMatch = true;
	        boolean endMatch = true;

	        String startYearStr = rowData.get("Start Year");
	        String startMonthStr = rowData.get("Start Month");
	        String startDayStr = rowData.get("Start Date");

	        if (startYearStr != null && !startYearStr.trim().isEmpty()) {

	            int sheetStartYear = (int) Double.parseDouble(startYearStr.trim());
	            int sheetStartMonth = Month.valueOf(startMonthStr.trim().toUpperCase()).getValue();
	            int sheetStartDay = (int) Double.parseDouble(startDayStr.trim());


	            startMatch = (startYear == sheetStartYear && startMonth == sheetStartMonth && startDay == sheetStartDay);
	        } else {
	            System.out.println("Start date missing in Excel. Skipping start date comparison.");
	        }

	        String endYearStr = rowData.get("End Year");
	        String endMonthStr = rowData.get("End Month");
	        String endDayStr = rowData.get("End Date");

	        if (endYearStr != null && !endYearStr.trim().isEmpty()) {

	            int sheetEndYear = (int) Double.parseDouble(endYearStr.trim());
	            int sheetEndMonth = Month.valueOf(endMonthStr.trim().toUpperCase()).getValue();
	            int sheetEndDay = (int) Double.parseDouble(endDayStr.trim());


	            endMatch = (endYear == sheetEndYear && endMonth == sheetEndMonth && endDay == sheetEndDay);
	        } else {
	            System.out.println("End date missing in Excel. Skipping end date comparison.");
	        }

	        // Return true if both matches (or skipped), false only if a comparison fails
	        return startMatch && endMatch;

	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	    
	    return false;
	}
	
	public int getYear() {
	    WebElement yearInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class,'flatpickr-calendar') and contains(@class,'open')]//input[@aria-label='Year']")));
	    return Integer.parseInt(yearInput.getAttribute("value"));
	}
	
	public void selectdate(int year, String month,int date) {
		String ariaLabel = String.format("%s %d, %d", month, date, year);
	    String dateXpath = String.format("//div[contains(@class,'flatpickr-calendar') and contains(@class,'open')]//span[@aria-label='%s' and contains(@class,'flatpickr-day') and not(contains(@class,'disabled')) and not(contains(@style,'display: none')) and not(contains(@class,'hidden'))]",ariaLabel);
	    WebElement dateElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(dateXpath)));
        dateElement.click();
	}
	
	public void selectmonth(String month) {
		    click("CurrentMonth");
			Select mnth = new Select(getElement("CurrentMonth"));
			mnth.selectByVisibleText(month);
	}
	
	public void Dateselection(boolean isStart, int year, String month,int date ) {
        
		if (isStart) {
			clickstartDate();
	       }
		else {
	       clickendDate();
	    }  
		
        getElement("CurrentYear").click();
		
	    int currentYear = Integer.parseInt(getElement("CurrentYear").getAttribute("value"));
		
	    while (currentYear != year) {
	        if (currentYear < year) {
	            click("Triangleup");
	        } else if (currentYear > year) {
	            click("Triangledown");
	        }
          currentYear = getYear();
	    }
	    
	    selectmonth(month);
		
		selectdate( year,  month, date);
               
	}
    
	 public void selectAccount(String account) {
		 Select drp = new Select(getElement("SelectAccount"));
		 drp.selectByVisibleText(account);
	 }

	 public void selectCurrency(String currency) {
		 Select drp =new Select(getElement("SelectCurrency"));
		 drp.selectByVisibleText(currency);
	 }
	 
	 
	 public boolean equalTransaction(String filepath, int rowNum)  {
		 
		 ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(false);", getElement("ApprovedTransaction"));

		 try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		    try {
		        String amountStr = getElement("ApprovedTransaction").getText().trim();
		        int numberFromUI = Integer.parseInt(amountStr);
                
		        System.out.println("Amount real " + numberFromUI);
		        
		        Map<String, String> rowData = ReadXls.getRowData(filepath, "Dashboard", rowNum);
		        String sheetAmountStr = rowData.get("Approved Transaction");
		        
		        int sheetAmount = (int) Double.parseDouble(sheetAmountStr);

		        System.out.println("Amount in sheet " + sheetAmount);

                int numberFromExcel = sheetAmount;

		        return numberFromUI == numberFromExcel;
		    }
		     catch (Exception e) {
		        System.out.println("Unexpected error: " + e.getMessage());
		    }

		    return false;
		}
 

	 public int[] clickRandomArrow(boolean isArrowUp) {
		    int randomClicks = new Random().nextInt(50) + 1;
		    int currentYear = LocalDate.now().getYear();
		    int actualClicks = 0;

		    clickstartDate(); 

		    for (int i = 0; i < randomClicks; i++) {
		        if (isArrowUp) {
		            clickArrowup();
		            actualClicks++;
		            if (getYear() == currentYear) {
		                break; 
		            }
		        } else {
		            clickArrowdown();
		            actualClicks++;
		        }
		    }

		    return new int[]{getYear(), actualClicks, randomClicks}; 
		}


	 
		 
	}	 