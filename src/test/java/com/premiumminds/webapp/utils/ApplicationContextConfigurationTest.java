/**
 * Copyright (C) 2014 Premium Minds.
 *
 * This file is part of pm-webapp-utils.
 *
 * pm-webapp-utils is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 *
 * pm-webapp-utils is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with pm-webapp-utils. If not, see <http://www.gnu.org/licenses/>.
 */
package com.premiumminds.webapp.utils;

import java.io.InputStream;
import java.net.URL;

import org.eclipse.jetty.testing.ServletTester;

import junit.framework.TestCase;

public class ApplicationContextConfigurationTest extends TestCase {
	private ServletTester tester;
	
	@Override
	protected void setUp() throws Exception {
		tester = new ServletTester();
		
		URL url = this.getClass().getResource("/");
		tester.setResourceBase(url.getPath());
	}
	
	@Override
	protected void tearDown() throws Exception {
	}
	
	public void testSuccessLoad(){
		tester.addEventListener(new ApplicationContextConfigurationListener());
		tester.getContext().setInitParameter("app-config", "classpath:/com/premiumminds/webapp/utils/applicationConfigurationTest.properties");
		try {
			tester.start();
			assertTrue(ApplicationContextConfiguration.get().containsKey("application"));
			assertTrue("test".equals(ApplicationContextConfiguration.get().getProperty("application")));
			
	    	WebAppFileLoader loader = new WebAppFileLoader("classpath:/com/premiumminds/webapp/utils/applicationConfigurationTest2.properties");
	    	InputStream stream = loader.load();
	    	ApplicationContextConfiguration.configure(stream);
			stream.close();

			assertTrue("test2".equals(ApplicationContextConfiguration.get().getProperty("application")));
			assertTrue("123".equals(ApplicationContextConfiguration.get().getProperty("application2")));

			tester.stop();
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
	}
}
