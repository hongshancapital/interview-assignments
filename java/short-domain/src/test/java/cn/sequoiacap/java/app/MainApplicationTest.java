package cn.sequoiacap.java.app;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class MainApplicationTest extends TestCase {
	@Test
	public void testMain() {
		MainApplication application = new MainApplication();
		String[] args = {""};
		MainApplication.main(args);
		Assert.assertNotNull(application);
	}

}
