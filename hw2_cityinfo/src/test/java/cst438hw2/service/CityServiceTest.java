package cst438hw2.service;
 
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;



import static org.mockito.ArgumentMatchers.anyString;

import cst438hw2.domain.*;
 
@SpringBootTest
public class CityServiceTest {

	@MockBean
	private WeatherService weatherService;
	
	@Autowired
	private CityService cityService;
	
	@MockBean
	private CityRepository cityRepository;
	
	@MockBean
	private CountryRepository countryRepository;

	
	@Test
	public void contextLoads() {
	}


	@Test
	public void testCityFound() throws Exception {
		City testCity1 = 
				new City(0, "OtterCity", "USA", "District 9", 9);    	
  
        // mocked cityRepository will return testCity1 when findByName("OtterCity") is called
		given(cityRepository.findByName("OtterCity")).willReturn(List.of(testCity1));
        given(countryRepository.findByCode("USA")).willReturn(new Country("USA", "United States"));
        given(weatherService.getTempAndTime("OtterCity")).willReturn(new TempAndTime(300.0, 101, 0));
        
        // call the cityService
        CityInfo actual   = cityService.getCityInfo("OtterCity");
        CityInfo expected = new CityInfo(0, "OtterCity", "USA", "United States", "District 9", 9, 80.0, "4:00:00 PM");
        
        assertThat( actual ).isEqualTo(expected);
	}
	
	@Test 
	public void  testCityNotFound() {
//		City testCity1 = 
//				new City(0, "OtterCity", "USA", "District 9", 9);   	
  
        // mocked cityRepository will return testCity1 when findByName("OtterCity") is called
		given(cityRepository.findByName("OtterCity")).willReturn(null);
        given(countryRepository.findByCode("USA")).willReturn(new Country("USA", "United States"));
        given(weatherService.getTempAndTime("OtterCity")).willReturn(new TempAndTime(300.0, 101, 0));
        
        // call the cityService
        CityInfo actual   = cityService.getCityInfo("OtterCity");
        CityInfo expected = null;
        
        assertThat( actual ).isEqualTo(expected);
	}
	
	@Test 
	public void  testCityMultiple() {
		City testCity1 = 
				new City(0, "OtterCity", "USA", "District 9", 9);
		City testCity2 = 
				new City(1, "OtterCity", "USA", "District 8", 9);
    	
  
        // mocked cityRepository will return testCity1 when findByName("OtterCity") is called
		given(cityRepository.findByName("OtterCity")).willReturn(List.of(testCity1, testCity2));
        given(countryRepository.findByCode("USA")).willReturn(new Country("USA", "United States"));
        given(weatherService.getTempAndTime("OtterCity")).willReturn(new TempAndTime(300.0, 101, 0));
        
        // call the cityService
        CityInfo actual   = cityService.getCityInfo("OtterCity");
        CityInfo expected = new CityInfo(0, "OtterCity", "USA", "United States", "District 9", 9, 80.0, "4:00:00 PM");

        assertThat( actual ).isEqualTo(expected);
	}

}
