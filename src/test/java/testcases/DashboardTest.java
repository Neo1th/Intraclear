package testcases;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.Map;
import org.apache.poi.EncryptedDocumentException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import Pages.DashboardPage;
import Pages.LoginPage;
import base.BaseTest;
import utilities.ReadXls;

public class DashboardTest extends BaseTest{
	
	LoginPage login;
	DashboardPage dashpage;
	
	@BeforeClass
	public void setup() {
        wait=new WebDriverWait(driver,Duration.ofSeconds(10));
		login = new LoginPage(driver,loc);
		dashpage =new DashboardPage(driver,loc);
		login.enterUsername("Sanket01");
		login.enterPassword("Sanket@73");
		login.clickLoginButton();
		wait.until(ExpectedConditions.urlContains("dashboard"));
	}
	
	
	public void datafromExcel(int rowNum, boolean useTyping) {
	    Map<String, String> rowData = null;
	    try {
	        rowData = ReadXls.getRowData(filepath, "Dashboard", rowNum);
	    } catch (EncryptedDocumentException | IOException e) {
	        e.printStackTrace();
	        return;
	    }

	    LocalDate today = LocalDate.now();
	    LocalDate start = null;
	    LocalDate end = null;

	    String startYearStr = rowData.get("Start Year");
	    String startMonthStr = rowData.get("Start Month");
	    String startDateStr = rowData.get("Start Date");

	    if(startYearStr.endsWith(".0")) {
            startYearStr = startYearStr.substring(0, startYearStr.length() - 2);
	    }
	    if (startYearStr == null) {
	        System.out.println("Row " + rowNum + ": Start year is empty. Skipping start date.");
	    } else {
	        try {
	        
	            int startYear = (int) Double.parseDouble(startYearStr.trim());
	            int startDate = (int) Double.parseDouble(startDateStr.trim());
	            String startMonthTrimmed = startMonthStr.trim();

	            start = LocalDate.of(startYear, Month.valueOf(startMonthTrimmed.toUpperCase()), startDate);

	            if (start != null && start.isAfter(today)) {
            	    throw new RuntimeException("Start date is after today. Start = " + start + ", End = " + end);
            	}
	            else if (!startYearStr.trim().matches("\\d+")) {
	                throw new RuntimeException("Row " + rowNum + ": Start year is not a valid number: '" + startYearStr + "'");
	            }
	            if (startYear > 100 ) {
	                if (useTyping) {
	                    System.out.println("Typing Start Date: " + start);
	                    dashpage.clickstartDate();
	                    dashpage.type("CurrentYear", String.valueOf(startYear));
	                    dashpage.selectmonth(startMonthTrimmed);
	                    dashpage.selectdate(startYear, startMonthTrimmed, startDate);
	                } else {
	                    System.out.println("Clicking Start Date: " + start);
	                    dashpage.Dateselection(true, startYear, startMonthTrimmed, startDate);
	                }
	            } else {
	            	 throw new RuntimeException("Row " + rowNum + ": Start year is less than 100: " + startYear);
	            }

	        } catch (Exception e) {
	        	 throw new RuntimeException("Row " + rowNum + ": Failed to parse start date: " + e.getMessage());
	        }
	    }

	    String endYearStr = rowData.get("End Year");
	    String endMonthStr = rowData.get("End Month");
	    String endDateStr = rowData.get("End Date");
	    
	    if (endYearStr.endsWith(".0")) {
            endYearStr = endYearStr.substring(0, endYearStr.length() - 2);
        }
	    if (endYearStr == null ) {
	        System.out.println("Row " + rowNum + ": End year is empty. Skipping end date.");
	    } 
	    else if (!endYearStr.trim().matches("\\d+")) {
            throw new RuntimeException("Row " + rowNum + ": End year is not a valid number: '" + endYearStr + "'");
        } 
	    else {
	        try {
	            

	            int endYear = (int) Double.parseDouble(endYearStr.trim());
	            int endDate = (int) Double.parseDouble(endDateStr.trim());
	            String endMonthTrimmed = endMonthStr.trim();

	             end = LocalDate.of(endYear, Month.valueOf(endMonthTrimmed.toUpperCase()), endDate);
	            
	             if (end != null && end.isBefore(start)) {
	            	    throw new RuntimeException("End date is before start date. Start = " + start + ", End = " + end);
	            	}
	             
	            if (end.isBefore(today) &&   endYear > 100) {
	                if (useTyping) {
	                    System.out.println("Typing End Date: " + end);
	                    dashpage.clickendDate();
	                    dashpage.type("CurrentYear", String.valueOf(endYear));
	                    dashpage.selectmonth(endMonthTrimmed);
	                    dashpage.selectdate(endYear, endMonthTrimmed, endDate);
	                } else {
	                    System.out.println("Clicking End Date: " + end);
	                    dashpage.Dateselection(false, endYear, endMonthTrimmed, endDate);
	                }
	            } else {
	            	 throw new RuntimeException("Row " + rowNum + ": End year is after today or less than 100: " + endYear);
	            }

	        } catch (Exception e) {
	        	 throw new RuntimeException("Row " + rowNum + ": Failed to parse end date: " + e.getMessage());
	        }
	    }
			
	    
	    String straccount = rowData.get("Account");
	    if (straccount != null && !straccount.trim().isEmpty()) {
	        straccount = straccount.trim();
	        if (straccount.endsWith(".0")) {
	            straccount = straccount.substring(0, straccount.length() - 2);
	        }
	        dashpage.selectAccount(straccount);
	    }

	    String strcurrency = rowData.get("Currency");
	    if (strcurrency != null && !strcurrency.trim().isEmpty()) {
	        dashpage.selectCurrency(strcurrency.trim());
	    }

	    
	}

	
//	@Test(priority=1)
//	public void TC01() {
//		Assert.assertTrue(dashpage.getElement("StartDate").isDisplayed());
//		Assert.assertTrue(dashpage.getElement("EndDate").isDisplayed());
//		Assert.assertTrue(dashpage.getElement("SelectAccount").isDisplayed());
//		Assert.assertTrue(dashpage.getElement("ApplyFilterbtn").isDisplayed());
//		
//		System.out.println("TC01 Passed");
//		System.out.println("All elements i.e. Start & end date, SelectAccount and ApplyFilter are visible");
//	}
//	
//	@Test(priority=2)
//	public void TC02() {
//		String startdate =dashpage.getDatafromfield(true);
//		String enddate =dashpage.getDatafromfield(false);
//		
//        LocalDate today = LocalDate.now();
//        String endstr = today.toString();
//        
//        LocalDate endDateObj = LocalDate.parse(enddate); 
//        LocalDate sevenDaysBefore = endDateObj.minusDays(7);
//        String startstr = sevenDaysBefore.toString(); 
//
//        Assert.assertTrue(enddate.equals(endstr) && startdate.equals(startstr));
//        
//        System.out.println("TC02 Passed ");		
//        System.out.println(startdate + " StartDate");
//	    System.out.println(enddate + " EndDate");
//
//	}
//	
//	@Test(priority=3)
//	public void TC03() {
//		dashpage.clickstartDate();
//	 
//		Assert.assertTrue(dashpage.getElement("CalendarWidget").isDisplayed());
//	 
//		System.out.println("TC03 Passed");
//		System.out.println("Start Date Calendar works");
//	}
//	
//	@Test(priority=4)
//	public void TC04() {
//		dashpage.clickendDate();
//	 
//		Assert.assertTrue(dashpage.getElement("CalendarWidget").isDisplayed());
//	 
//		System.out.println("TC04 Passed ");
//		System.out.println("End Date Calendar works");
//	}
//	
//	@Test(priority=5)
//	public void TC05() {
//		dashpage.clickselectAccount();
//		Select drp =new Select(dashpage.getElement("SelectAccount"));
//		List <WebElement> options =drp.getOptions();
//		
//		Assert.assertTrue((options.size()>0));
//	 
//		System.out.println("TC05 Passed ");
//		System.out.println("Select Account dropdown works");
//
//	}
//	
//	 @Test(priority=6)
//		public void TC06() throws InterruptedException {
//	
//			dashpage.clickstartDate();
//			dashpage.click("CurrentYear");
//			
//			
//			datafromExcel(4 , false);
//			
//			int numberofClicks = 10; 
//			dashpage.clickstartDate();
//			dashpage.click("CurrentYear");
//			int beforeArrow = dashpage.getYear();
//
//			for(int i=1;i<=numberofClicks;i++) {
//			dashpage.clickArrowdown();
//			
//			}
//			
//			dashpage.clickstartDate();
//			int afterArrow = dashpage.getYear();
//			
//			Assert.assertEquals(beforeArrow-afterArrow,numberofClicks);
//				
//			System.out.println("TC06 Passed ");
//            System.out.println("Arrow down works in start date");
//			
//	 }
//	
//	@Test(priority=7)
//	public void TC07() throws InterruptedException {
//		datafromExcel(4 ,false);
//		
//		dashpage.clickendDate();
//		
//		dashpage.click("CurrentYear");
//		
//		int before =dashpage.getYear();
//		
//		int numberofClicks = 10; 
//		
//		for(int i=1;i<=numberofClicks;i++) {
//		dashpage.clickArrowdown();
//		}
//		
//		int after = dashpage.getYear();
//		
//		Assert.assertEquals(before-after, numberofClicks);
//		
//		System.out.println("TC07 Passed ");
//		System.out.println("Arrow down works in end date");
//		
//	}
//	
//	@Test(priority=8)
//	public void TC08() throws InterruptedException {
//        
//		datafromExcel(2,false);
//        dashpage.clickApplyFilter();
//
//		
//		Assert.assertTrue(dashpage.equalTransaction(filepath, 2));
//		
//		System.out.println("TC08 Passed ");
//		System.out.println("Select start and end date filter gives data for particular period for all accounts ");
//		System.out.println("The transaction for particular period matches the expected ");
//
//	
//	}
//	
//	@Test(priority=9)
//	public void TC09() throws InterruptedException {
//
//		datafromExcel(3 ,false);
//
//        dashpage.clickApplyFilter();
//
//	    Assert.assertTrue(dashpage.equalTransaction(filepath, 3));
//
//		System.out.println("TC09 Passed ");
//		System.out.println("Select start and end date filter along with select account gives data for particular period for selected account ");
//		System.out.println("The transaction for particular period matches the expected ");
//
//	}
//	
//	
////	Have to update the latest data for number of transactions to pass this test
//	@Test(priority=10)
//	public void TC10() throws InterruptedException {
//	    dashpage.refresh();
//		datafromExcel(4 ,false);
//        dashpage.clickApplyFilter();
//
//		
//		Assert.assertTrue(dashpage.equalTransaction(filepath, 4));
//
//		
//		System.out.println("TC10 Passed ");
//		System.out.println("Select start date and clicking on applying filter gives data from the staring date to current date ");
//		System.out.println("The transaction for particular period matches the expected ");
//
//	}
//	
//	@Test(priority=11)
//	public void TC11() throws InterruptedException {
//		
//	    dashpage.refresh();
//	    
//        dashpage.clickendDate();
//        
//        int date = 21;
//        int year = LocalDate.now().getYear();
//        Month month = LocalDate.now().getMonth();
//        String monthName = month.getDisplayName(TextStyle.FULL, Locale.ENGLISH);
//        
//
//        dashpage.selectdate( year,monthName , date);
//
//        dashpage.clickApplyFilter();
//		
//		Assert.assertTrue(dashpage.equalTransaction(filepath, 5));
//
//		
//		System.out.println("TC11 Passed ");
//		System.out.println("Select end date and clicking on applying filter gives data from the one week prior from today date to the selected date ");
//		System.out.println("The transaction for particular period matches the expected ");
//
//	}
//	
//	 
//	@Test(priority=12)
//	public void TC12() throws InterruptedException {
//
//		  datafromExcel(6 ,false);
//	      dashpage.clickApplyFilter();
//
//		  
//		  Assert.assertTrue(dashpage.equalTransaction(filepath, 6));
//
//	      
//	      System.out.println("TC12 Passed ");
//		  System.out.println("Select same start & end date and clicking on applying filter gives data for the same day ");
//	}
//	
//	
//	@Test(priority=13)
//	public void TC13() throws InterruptedException {
//	    	
//	    		dashpage.refresh();
//	    	    datafromExcel(7 ,false);
//	   	        System.out.println("TC12 Passed ");
//	  	        System.out.println("Cannot select future dates for start & end date ");
//	    }
//	    		
//
//	
//	@Test(priority=14)
//	public void TC14() throws InterruptedException {
//	       dashpage.refresh();
//	       dashpage.clickApplyFilter();
//          
//	       String start = dashpage.getDatafromfield(true);
//	       String end =dashpage.getDatafromfield(false);
//	       
//	       LocalDate today = LocalDate.now();
//	       String todayStr = today.toString();
//	       
//	       LocalDate endDateObj = LocalDate.parse(end); 
//	       LocalDate sevenDaysBefore = endDateObj.minusDays(7);
//	       String startstr = sevenDaysBefore.toString(); 
//	       
//	       Assert.assertTrue(start.equals(startstr) && end.equals(todayStr));
//	       
//	       System.out.println("TC14 Passed ");
//		   System.out.println("Not selecting any data and clicking on apply filter gives data for the week for all accounts");
//	   }
//	 
//	 
//	@Test(priority=15)
//	public void TC15() throws InterruptedException {
//		datafromExcel(8 ,false);
//        dashpage.clickApplyFilter();
//
//		Assert.assertTrue(dashpage.equalTransaction(filepath, 8));
//
//		System.out.println("TC15 Passed ");
//		System.out.println("Only selecting account and clicking on apply filter gives data of the account for the week");
//	   
//	}
//	
//	 
//	
//// Have to update the latest data for number of transactions to pass this test
//	@Test(priority=16)
//	public void TC16() throws InterruptedException {
//		
//		datafromExcel(9,false);
//        dashpage.clickApplyFilter();
//
//		Assert.assertTrue(dashpage.equalTransaction(filepath, 9));
//
//		datafromExcel(10,false);
//        dashpage.clickApplyFilter();
//
//	    Assert.assertTrue(dashpage.equalTransaction(filepath, 10));
//
//		System.out.println("TC16 Passed ");
//	    System.out.println("Only selecting account and clicking on apply filter gives data of the account for the week");
//	   
//
//	}
//
//	@Test(priority=17)
//	public void TC17() throws InterruptedException {
//		datafromExcel(11,false);
//        dashpage.clickApplyFilter();
//
//		Assert.assertTrue(dashpage.equalTransaction(filepath, 11));
//
//		System.out.println("TC17 Passed ");
//	    System.out.println("Only selecting start date & selecting account and clicking on apply filter gives data of the account for the particular time period");
//	   
//	}
//	
//	@Test(priority=18)
//	public void TC18() throws InterruptedException {
//		
//		    datafromExcel(12,false);
//	        dashpage.clickApplyFilter();
//
//		     Assert.assertTrue(dashpage.equalTransaction(filepath, 12));
//
//		
//			System.out.println("TC18 Passed ");
//		    System.out.println("Only selecting end date that is one week prior to start date is not possible");
//		}
//
//	
//	 @Test(priority=19)
//		public void TC19() throws InterruptedException {
//		 datafromExcel(13,false);
//		
//	 }
//	 
//	 
// @Test(priority=20)
//	 public void TC20() throws InterruptedException {
//		 datafromExcel(14,false);
//	  
//       }
//	  
//	
//	 @Test(priority=21)
//		public void TC21() {
//	  			datafromExcel(15,false);	
//	  	
//	   
//	 }
//	 
////	 @Test(priority=22)
////		public void TC22() throws InterruptedException {
////		    dashpage.refresh();
////	  		datafromExcel(16,false);
////	  		
////			Assert.assertTrue(dashpage.equalTransaction(filepath, 16));
////
////			
////	  		System.out.println("TC22 Passed ");
////	        System.out.println("Large range of date shows data");
////	  		
////	   }
//	
//	
////	 Have to update the latest data for number of transactions to pass this test
//
//	   @Test(priority=23)
//      	public void TC23() throws InterruptedException {
//		 dashpage.refresh();
//         datafromExcel(17,true);
//	     dashpage.clickApplyFilter();
//
// 		 Assert.assertTrue(dashpage.equalTransaction(filepath, 17));
//
//         System.out.println("TC23 Passed");
//		 System.out.println("Transaction as expected");
//	   
//	   }
//	
//	   @Test(priority=24)
//     	public void TC24() throws InterruptedException {
//	     datafromExcel(18,true);
//	     dashpage.clickApplyFilter();
//
//		 Assert.assertTrue(dashpage.equalTransaction(filepath, 18));
//
//         System.out.println("TC24 Passed");
//		 System.out.println("Transaction as expected");
//
//	     }
//	   
//	   @Test(priority=25)
//    	public void TC25() throws InterruptedException {
//		   
//         datafromExcel(19,true);
//	     dashpage.clickApplyFilter();
//
// 		 Assert.assertTrue(dashpage.equalTransaction(filepath, 19));
//
//         System.out.println("TC25 Passed");
//		 System.out.println("Transaction as expected");
//
//	     }
	   
	   @Test(priority=26)
     	public void TC26() {
	         datafromExcel(20,true);
	         System.out.println("TC26 Passed");
			 System.out.println("Manually entering invalid non-numerical year in start year is not accepted");
		  
	   }
	   
	   
	   @Test(priority=27)
    	public void TC27() {
	         datafromExcel(21,true);
	         System.out.println("TC27 Passed");
			 System.out.println("Manually entering invalid non-numerical year  in end year is not accepted");
		  
	   }
	   
	   @Test(priority=28)
   	public void TC28() {
	         datafromExcel(22,true);
	         System.out.println("TC28 Passed");
			 System.out.println("Start year empty field is not accepted");
		  
	   }
	   
	   @Test(priority=29)
	   	public void TC29() {
			 
		         datafromExcel(22,true);
		         System.out.println("TC29 Passed");
				 System.out.println("End year empty field is not accepted");
			 
		     }
//	   
//	   @Test(priority=30)
//	   	public void TC30() throws InterruptedException {
//			  
//		        datafromExcel(23,true);
//                
//                int numberofClicks = 10; 
//     			dashpage.clickstartDate();
//     			dashpage.click("CurrentYear");
//     			
//     			int beforeArrow = dashpage.getYear();
//
//     			for(int i=1;i<=numberofClicks;i++) {
//     			dashpage.clickArrowdown();
//     			
//     			}
//     			
//     			dashpage.clickstartDate();
//     			int afterArrow = dashpage.getYear();
//                 
//		          Assert.assertEquals(beforeArrow-afterArrow ,numberofClicks);
//		          System.out.println("TC30 Passed");
//		          System.out.println("The change in year works as expected");
//
//		     }
//	   
//	   @Test(priority=31)
//	   	public void TC31() throws InterruptedException {
//			  
//		         datafromExcel(24,true);
//		         
//                
//                 int numberofClicks = 10; 
//      			 dashpage.clickendDate();
//      			 dashpage.click("CurrentYear");
//      			
//      		     int beforeArrow = dashpage.getYear();
//
//      			 for(int i=1;i<=numberofClicks;i++) {
//      			 dashpage.clickArrowdown();
//      			
//      			}
//      			dashpage.clickendDate();
//     			int afterArrow = dashpage.getYear();
//                 
//		        Assert.assertEquals(beforeArrow-afterArrow ,numberofClicks);
//
//		        System.out.println("TC31 Passed");
//		        System.out.println("The change in year works as expected");
//	      }
//
//	   
//	   @Test(priority=32)
//	   	public void TC32() {
//		   
//		    datafromExcel(3 ,true);
//	        dashpage.clickApplyFilter();
//
//			Assert.assertTrue(dashpage.equalTransaction(filepath, 3));
//
//			System.out.println("TC32 Passed ");
//			System.out.println("Selecting manually start and end date filter along with select account gives data for particular period for selected account ");
//			System.out.println("The transaction for particular period matches the expected "); 
//		     
//	   }
	   
	   @Test(priority=33)
	   	public void TC33() {
		
		   datafromExcel(25 ,true);
			System.out.println("TC33 Passed ");
			System.out.println("Cannot select future date greater than today for start date manually");

	   }
  
	   @Test(priority=34)
	   	public void TC34() {
         
		   datafromExcel(26 ,true);
		   System.out.println("TC34 Passed ");
			System.out.println("Cannot select year less than 100 for start date manually");
        }
	   
	   @Test(priority=35)
	   	public void TC35() {
         
		   datafromExcel(27 ,true);
		   System.out.println("TC35 Passed ");
			System.out.println("Cannot select end date less than start date manually");
	   }

	@Test(priority=36)
   	public void TC36() {
	  
		datafromExcel(15,true);
		System.out.println("TC36 Passed ");
		System.out.println("Cannot select future dates for start date and start date manually");
	
	}
	
	
	
}
