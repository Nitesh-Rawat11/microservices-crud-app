package com.user.service.external.services;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.user.service.entities.Hotel;

@FeignClient(name = "HOTEL-SERVICE")
public interface FeignClientHotelService {

    @GetMapping("/hotels/{hotelId}")
    Hotel getHotel(@PathVariable("hotelId") String hotelId);
    
    @GetMapping("/hotels")
    Hotel getAllHotel(Hotel hotel);
    
   // @GetMapping("/hotels")
   // ResponseEntity<List<Hotel>> getAllHotels();

   
}
