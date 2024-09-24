package com.user.service.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.user.service.entities.Hotel;
import com.user.service.entities.Rating;
import com.user.service.entities.User;
import com.user.service.exceptions.ResourceNotFoundException;
import com.user.service.external.services.FeignClientHotelService;
import com.user.service.repositories.UserRepository;
import com.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;

	 @Autowired
	 private FeignClientHotelService hotelService;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		
	//List<User> listOfAllUsers = userRepository.findAll();
//		
//		Rating[] ratingsOfAllUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + listOfAllUsers.get(0),Rating[].class);
//		
//		List<Rating> ratings = Arrays.stream(ratingsOfAllUser).toList();
//		
//		List<Rating> ratingList = ratings.stream().map(rating -> {
//			
//		//	ResponseEntity<List<Hotel>> allHotels = hotelService.getAllHotels();
//
//			 Hotel hotel = hotelService.getAllHotel(rating.getHotel());
//			
//			logger.info("hotel {} ", hotel);
//			
//			rating.setHotel(hotel);
//			return rating;
//			
//		}).collect(Collectors.toList());
//		
//	       ((User) listOfAllUsers).setRatings(ratingList);
	       
		//    return listOfAllUsers;
		    
		return userRepository.findAll();
	}

	// get single user
	@Override
	public User getUser(String userId) {

		User user = userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User with given id is not found on server !! : " + userId));

		// fetch rating of the above user from RATING SERVICE
		Rating[] ratingsOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/" + user.getUserId(),Rating[].class);
		List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
		List<Rating> ratingList = ratings.stream().map(rating -> {

	//ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
			Hotel hotel = hotelService.getHotel(rating.getHotelId());
			rating.setHotel(hotel);
			return rating;
		}).collect(Collectors.toList());
		user.setRatings(ratingList);

		return user;
	}

	@Override
	public User updateUser(User user) {
    Optional<User> userDb = this.userRepository.findById(user.getUserId());
		
		if(userDb.isPresent()) {
			User userUpdate = userDb.get();
			userUpdate.setUserId(user.getUserId());
			userUpdate.setName(user.getName());
			userUpdate.setEmail(user.getEmail());
			userUpdate.setAbout(user.getAbout());
			
			userRepository.save(userUpdate);
			return userUpdate;
		}else {
			throw new ResourceNotFoundException("Record not found with id : " + user.getUserId());
		}	
	}

	@Override
	public void deleteUserById(String userId) {
    Optional<User> userDb = this.userRepository.findById(userId);
		
		if(userDb.isPresent()) {
			this.userRepository.delete(userDb.get());
		}else {
			throw new ResourceNotFoundException("Record not found with id : " + userId);
		}
		
	}
}
