package com.apple.weather.app;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.apple.weather.app.adapters.primary.WeatherControllerTest;
import com.apple.weather.app.adapters.secondary.WeatherLocationRepositoryTest;
import com.apple.weather.app.services.WeatherServiceTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ WeatherApplicationTests.class, WeatherControllerTest.class, WeatherServiceTest.class,
		WeatherLocationRepositoryTest.class })
public class WeatherAppTestsSuite {

}
