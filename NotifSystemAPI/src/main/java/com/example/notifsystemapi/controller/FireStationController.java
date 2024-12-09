package com.example.notifsystemapi.controller;

import com.example.notifsystemapi.service.FireStationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class FireStationController {

    private final FireStationService fireStationService;

    public FireStationController(FireStationService fireStationService) {
        this.fireStationService = fireStationService;
    }

    @GetMapping("/communityEmail")
    public List<String> getEmailsByCity(@RequestParam("city") String city) {
        List<String> response = fireStationService.getEmailsByCity(city);
        return response;
    }

    @GetMapping("/personInfo")
    public List<Map<String, Object>> getPersonInfo(
            @RequestParam("firstName") String firstName,
            @RequestParam("lastName") String lastName) {
        List<Map<String, Object>> response = fireStationService.getPersonInfo(firstName, lastName);
        return response;
    }

    @GetMapping("/phoneAlert")
    public List<String> getPhoneNumbersByStation(@RequestParam("firestation") int fireStationNumber) {
        List<String> response = fireStationService.getPhoneNumbersByStation(fireStationNumber);
        return response;
    }

    @GetMapping("/firestation")
    public Map<String, Object> getPersonsByStationNumber(@RequestParam("stationNumber") int stationNumber) {
        Map<String, Object> response = fireStationService.getPersonsByStation(stationNumber);
        return response;
    }
}
