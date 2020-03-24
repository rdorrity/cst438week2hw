package cst438hw2.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cst438hw2.domain.*;

@Service
public class CityService {
	
	@Autowired
	private CityRepository cityRepository;
	
	@Autowired
	private CountryRepository countryRepository;
	
	@Autowired
	private WeatherService weatherService;
	
	public CityInfo getCityInfo(String cityName) {
		List<City> cites = cityRepository.findByName(cityName);	
		if (cites == null || cites.isEmpty()) {
			return null;
		}
		City c = cites.get(0);		
		Country country = countryRepository.findByCode(c.getCountryCode());
		TempAndTime t = weatherService.getTempAndTime(cityName);
		//SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
		DateFormat timeFormatter = DateFormat.getTimeInstance(DateFormat.DEFAULT);
		String time = timeFormatter.format(t.time);
		Double temp = t.temp;
		temp = (double) Math.round((temp - 273.15) * 9.0/5.0 + 32.0);
		CityInfo cityInfo = new CityInfo(c.getId(), c.getName(), c.getCountryCode(), country.getName(), c.getDistrict(), c.getPopulation(), temp, time);
		return cityInfo; 
	}
	
}
