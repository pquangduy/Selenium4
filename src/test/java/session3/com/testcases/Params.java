package session3.com.testcases;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class Params {
	@Test
	@Parameters({ "val1", "val2" })
	public void Sum(int v1, int v2) {
		int sum = v1 + v2;
		System.out.println("The final sum of the given values is " + sum);
	}

	@Test
	@Parameters({ "val1", "val2" })
	public void Diff(int v1, int v2) {
		int diff = v1 - v2;
		System.out.println("The final difference of the given values is " + diff);
	}
}