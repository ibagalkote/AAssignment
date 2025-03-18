package com.apple.weather.app.adapters.primary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.util.NestedServletException;

import com.apple.weather.app.data.WeatherData;
import com.apple.weather.app.dto.WeatherLocationDTO;
import com.apple.weather.app.exceptions.InvalidInputWeatherAppException;
import com.apple.weather.app.exceptions.WeatherServiceWeatherAppException;
import com.apple.weather.app.services.WeatherService;

@ExtendWith(MockitoExtension.class)
public class WeatherControllerTest {

	private MockMvc mockMvc;

	@Mock
	private WeatherService weatherService;

	@InjectMocks
	private WeatherController weatherController;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(weatherController).build();
	}

	@Test
	void testGetWeatherLocations_Success() throws Exception {
		// Given
		WeatherLocationDTO location1 = WeatherData.getWeatherLocationDTO1();
		WeatherLocationDTO location2 = WeatherData.getWeatherLocationDTO2();
		List<WeatherLocationDTO> locations = Arrays.asList(location1, location2);

		when(weatherService.getAllWeatherLocations()).thenReturn(Optional.of(locations));

		// When & Then
		mockMvc.perform(get("/api/weather/locations").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.length()").value(2))
				.andExpect(jsonPath("$[0].zipCode").value(WeatherData.ZIPCODE))
				.andExpect(jsonPath("$[0].city").value(WeatherData.CITY))
				.andExpect(jsonPath("$[1].zipCode").value(WeatherData.ZIPCODE_ANOTHER))
				.andExpect(jsonPath("$[1].city").value(WeatherData.CITY_ANOTHER));

		verify(weatherService, times(1)).getAllWeatherLocations();
	}

	@Test
	void testGetWeatherLocations_NotFound() throws Exception {
		// Given
		when(weatherService.getAllWeatherLocations()).thenReturn(Optional.empty());

		// When & Then
		mockMvc.perform(get("/api/weather/locations").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound()).andExpect(content().string("No weather locations found"));

		verify(weatherService, times(1)).getAllWeatherLocations();
	}

	@Test
	void testGetWeatherByZipCode_Success() throws Exception {
		// Given
		WeatherLocationDTO weatherLocation = WeatherData.getWeatherLocationDTO1();

		when(weatherService.getWeatherByZipCode(WeatherData.ZIPCODE)).thenReturn(Optional.of(weatherLocation));

		// When & Then
		mockMvc.perform(get("/api/weather/locations/" + WeatherData.ZIPCODE).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.zipCode").value(WeatherData.ZIPCODE))
				.andExpect(jsonPath("$.city").value(WeatherData.CITY));

		verify(weatherService, times(1)).getWeatherByZipCode(WeatherData.ZIPCODE);
	}

	@Test
	void testGetWeatherByZipCode_NotFound() throws Exception {
		// Given
		when(weatherService.getWeatherByZipCode("99999")).thenReturn(Optional.empty());

		// When & Then
		mockMvc.perform(get("/api/weather/locations/99999").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(content().string("Weather data not found for ZIP Code: 99999"));

		verify(weatherService, times(1)).getWeatherByZipCode("99999");
	}

	@Test
	void testGetWeatherByZipCode_InvalidInput() throws Exception {
		// When & Then
		Exception exception = assertThrows(NestedServletException.class, () -> {
			mockMvc.perform(get("/api/weather/locations/12") // Invalid ZIP Code
					.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
		});
		assertThat(exception.getCause()).isInstanceOf(InvalidInputWeatherAppException.class);
	}

	@Test
	void testGetWeatherByZipCode_ServiceFailure() throws Exception {
		// Given
		when(weatherService.getWeatherByZipCode("54321")).thenThrow(WeatherServiceWeatherAppException.class);

		// When & Then
		Exception exception = assertThrows(NestedServletException.class, () -> {
			mockMvc.perform(get("/api/weather/locations/54321"));
		});
		assertThat(exception.getCause()).isInstanceOf(WeatherServiceWeatherAppException.class);
		verify(weatherService, times(1)).getWeatherByZipCode("54321");
	}
}
