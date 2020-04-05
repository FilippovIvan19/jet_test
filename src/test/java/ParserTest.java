
import com.parser.Checker;
import com.parser.MySyntaxException;
import com.parser.MyTypeException;
import org.junit.Test;
import static org.junit.Assert.*;
import com.parser.Parser;


public class ParserTest {

	@Test
	public void exampleTest() {
		assertEquals("first example test",
				"filter{(element>10)&(element<20)}%>%map{element}",
				Parser.parse("filter{(element>10)}%>%filter{(element<20)}"));

		String res2 = Parser.parse("map{(element+10)}%>%filter{(element>10)}%>%map{(element*element)}");
		assertTrue("second example test",
				res2.equals("filter{((element+10)>10)}%>%map{((element+10)*(element+10))}") ||
						res2.equals("filter{(element>0)}%>%map{((element*element)+((element*20)+100))}"));

		String res3 = Parser.parse("filter{(element>0)}%>%filter{(element<0)}%>%map{(element*element)}");
		assertTrue("third example test",
				res3.equals("filter{(1=0)}%>%map{element}") ||
						res3.equals("filter{(element>0)&(element<0)}%>%map{(element*element)}"));

	}


	@Test
	public void exceptionsTest() {
		try {
			Checker.str = "map{(element+10)}%>%fiter{(element>10)}%>%map{(element*element)}";
			Checker.callChain();
			fail("MyTypeException should be thrown");
		} catch (MySyntaxException ex) {
			fail("MyTypeException should be thrown");
		} catch (MyTypeException ex) {
			assertEquals("should be filter of map", ex.getMessage());
		}


		try {
			Checker.str = "map{(element+10}%>%filter{(element>10)}%>%map{(element*element)}";
			Checker.callChain();
			fail("MySyntaxException should be thrown");
		} catch (MySyntaxException ex) {
			assertEquals("should be )", ex.getMessage());
		} catch (MyTypeException ex) {
			fail("MySyntaxException should be thrown");
		}


		try {
			Checker.str = "map{(element+10)}filter{(element>10)}%>%map{(element*element)}";
			Checker.callChain();
			fail("MySyntaxException should be thrown");
		} catch (MySyntaxException ex) {
			assertEquals("should be %>%", ex.getMessage());
		} catch (MyTypeException ex) {
			fail("MySyntaxException should be thrown");
		}
	}

}
