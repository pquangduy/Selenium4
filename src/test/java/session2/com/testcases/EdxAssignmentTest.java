package session2.com.testcases;

import static selenium4.com.helpers.WebElementsHelpers.*;

import java.util.List;
import java.util.Map;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.testng.Assert;
import org.testng.annotations.Test;

import selenium4.com.common.BaseTest;
import selenium4.com.helpers.WordHelpers;
import session2.com.pages.CourseDetailsPage;
import session2.com.pages.EdxCourseContentPage;
import session2.com.pages.EdxHomePage;
import session2.com.pages.EdxIndexPage;
import session2.com.pages.EdxLoginPage;
import session2.com.pages.EdxSearchPage;
import session2.com.pages.EdxSearchResultPage;

public class EdxAssignmentTest extends BaseTest {

	EdxIndexPage indexPage;
	EdxLoginPage loginPage;
	EdxHomePage homePage;
	EdxSearchPage searchPage;
	EdxSearchResultPage searchResultPage;
	CourseDetailsPage courseDetailsPage;
	EdxCourseContentPage courseContentPage;
	WordHelpers wordHelpers;
	
	@Test(priority = 0)
	public void testHomePageAppearCorrect() throws Exception {
		//String url = "https://www.edx.org/learn/coding/university-of-british-columbia-how-to-code-simple-data?index=product&queryID=5fec8a34fb7bc1b8b3ca7a25dd0f7567&position=1&results_level=first-level-results&term=How+to+Code%3A+Simple+Data&objectID=course-895afa12-6162-420d-a522-9945ddf29011&campaign=How+to+Code%3A+Simple+Data&source=edX&product_category=course&placement_url=https%3A%2F%2Fwww.edx.org%2Fsearch";
		String url = "https://authn.edx.org/login";
		String username = "pqduytma@gmail.com";
		String password = "Edx@123456";
		//String course = "Software Construction: Object-Oriented Design";
		String course = "How to Code: Simple Data";

		//getURL("https://www.edx.org/");
		//indexPage = new EdxIndexPage();
		//Assert.assertEquals(indexPage.getEdxIndexPageTitle(), "Build new skills. Advance your career. | edX");
		//loginPage = indexPage.clickSignIn();
		//Assert.assertEquals(loginPage.getEdxLoginPageTitle(), "Authn | edX");
		getURL(url);
		//loginPage = new EdxIndexPage().clickSignIn();
		homePage = new EdxLoginPage().loginToApplication(username, password);
		//Assert.assertEquals(homePage.getEdxHomePageTitle(), "Login | edX");
		searchPage = homePage.clickExploreCourses();
		//Assert.assertEquals(searchPage.getEdxSearchPageTitle(), "Learner Home");
		searchPage.setSearchBox(course);
		searchResultPage = searchPage.clickSearch();
		Assert.assertEquals(searchResultPage.getEdxSearchResultPageTitle(), "edX Courses | View all online courses on edX.org");
		//courseDetailsPage = searchResultPage.selectCourse("Object-Oriented Design");
		courseDetailsPage = searchResultPage.selectCourse(course);
		Assert.assertEquals(courseDetailsPage.getCourseDetailsPageTitle(), "edX Courses | View all online courses on edX.org");
		Map<String, Object> courseDetails = courseDetailsPage.getCourseDetails(course);
		//sleep(2);
        courseDetailsPage.clickEnroll();
        courseContentPage = courseDetailsPage.clickContinue();
		Assert.assertEquals(courseContentPage.getEdxCourseContentPageTitle(), "Course | edX");
        courseContentPage.clickExpandAll();
        Map<String, Object> courseContent = courseContentPage.getCourseContent1();
        
        wordHelpers = new WordHelpers();
        wordHelpers.setParagraph(ParagraphAlignment.LEFT, 0);
        wordHelpers.setContent((String) courseDetails.get("Title"), true, 22, "New Roman", true);
        wordHelpers.setParagraph(ParagraphAlignment.LEFT, 0);
        wordHelpers.setContent((String) courseDetails.get("Description"), true, 14, "New Roman", true);
        wordHelpers.setParagraph(ParagraphAlignment.LEFT, 0);        
        wordHelpers.setContent("What you'll learn", true, 14, "New Roman", true);
    	List<?> l4 = (List<?>) courseDetails.get("Summary");
    	for(int i=0; i<l4.size(); i++) {
    		String sa = (String) l4.get(i);
            wordHelpers.setParagraph(ParagraphAlignment.LEFT, 600);
            wordHelpers.setContent(sa, false, 14, "New Roman", false);
    	}
        wordHelpers.setParagraph(ParagraphAlignment.LEFT, 0);
        wordHelpers.setContent("Instructor", true, 14, "New Roman", false);
        String ins = "";
        wordHelpers.setParagraph(ParagraphAlignment.LEFT, 0);
    	List<?> l6 = (List<?>) courseDetails.get("Instructors");
    	for(int i=0; i<l6.size(); i++) {
    		String sa = (String) l6.get(i);
    		if (i == l6.size()-1) {
    			ins = ins + sa;
    		}
    		else {
    			ins = ins + sa + " -- ";
    		}
    	}
    	wordHelpers.setContent(ins, false, 14, "New Roman", true);
        wordHelpers.setParagraph(ParagraphAlignment.LEFT, 0);
        wordHelpers.setContent("Course contents:", true, 14, "New Roman", true);
        String[][] l8 = (String[][]) courseContent.get("CourseContent"); 
        logConsole("---Test l8.length: " + l8.length);
		for(int i=0; i<l8.length; i++) {
	        wordHelpers.setParagraph(ParagraphAlignment.LEFT, 600);
	        wordHelpers.setContent((String) l8[i][0], true, 14, "New Roman", false);
            logConsole("---Test l8[i].length: " + l8[i].length);
			for(int j=1; j<l8[i].length; j++) {
        		String sa = l8[i][j];
        		String content = sa.split("\n")[0];
        		String hyperlink  = sa.split("\n")[1];
        		logConsole("---Test l8[i].content: " + content);
        		logConsole("---Test l8[i].hyperlink: " + hyperlink);
    	        wordHelpers.setParagraph(ParagraphAlignment.LEFT, 900);
    	        wordHelpers.setContent("" + j + ". " + content, hyperlink, false, 14, "New Roman", false);
			}
		}
		String fileName = System.getProperty("user.dir") + "\\OutputFiles\\Course.docx";
		wordHelpers.writeToWordFile(fileName);
	}
}
