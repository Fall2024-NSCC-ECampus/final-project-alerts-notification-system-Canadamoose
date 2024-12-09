package com.example.notifsystemapi.service;

import com.example.notifsystemapi.model.Person;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FireStationService {

    // Mock data
    private static final Map<Integer, List<Person>> fireStationData = new HashMap<>();

    static {
        fireStationData.put(1, Arrays.asList(
                new Person("John", "Doe", "123 Main St", "123-456-7890", 25, "john.doe@example.com",
                        Arrays.asList("aspirin: 500mg"), Arrays.asList("peanuts"), "Springfield"),
                new Person("Jane", "Doe", "123 Main St", "234-567-8901", 16, "jane.doe@example.com",
                        Arrays.asList("ibuprofen: 200mg"), Arrays.asList("latex"), "Springfield")
        ));
        fireStationData.put(2, Arrays.asList(
                new Person("Alice", "Johnson", "101 Pine St", "456-789-0123", 30, "alice.johnson@example.com",
                        Arrays.asList("paracetamol: 500mg"), Arrays.asList(), "Shelbyville"),
                new Person("Bob", "Brown", "202 Cedar St", "567-890-1234", 40, "bob.brown@example.com",
                        Arrays.asList(), Arrays.asList("pollen"), "Springfield")
        ));
    }


    public List<String> getPhoneNumbersByStation(int fireStationNumber) {
        List<Person> persons = fireStationData.getOrDefault(fireStationNumber, Collections.emptyList());
        return persons.stream()
                .map(Person::getPhone)
                .distinct() // Ensure unique phone numbers
                .collect(Collectors.toList());
    }


    public Map<String, Object> getPersonsByStation(int stationNumber) {
        List<Person> persons = fireStationData.getOrDefault(stationNumber, Collections.emptyList());

        List<Map<String, String>> personDetails = persons.stream()
                .map(person -> Map.of(
                        "firstName", person.getFirstName(),
                        "lastName", person.getLastName(),
                        "address", person.getAddress(),
                        "phone", person.getPhone()))
                .collect(Collectors.toList());

        long adults = persons.stream().filter(person -> person.getAge() > 18).count();
        long children = persons.size() - adults;

        return Map.of(
                "persons", personDetails,
                "adults", adults,
                "children", children
        );
    }

    public List<Map<String, Object>> getPersonInfo(String firstName, String lastName) {
        List<Person> allPersons = fireStationData.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        // Find persons with the matching first and last name
        List<Person> matchingPersons = allPersons.stream()
                .filter(person -> person.getFirstName().equalsIgnoreCase(firstName) &&
                        person.getLastName().equalsIgnoreCase(lastName))
                .collect(Collectors.toList());

        // Map the matching persons to the desired output format
        return matchingPersons.stream()
                .map(person -> Map.of(
                        "name", person.getFirstName() + " " + person.getLastName(),
                        "address", person.getAddress(),
                        "age", person.getAge(),
                        "email", person.getEmail(),
                        "medications", person.getMedications(),
                        "allergies", person.getAllergies()))
                .collect(Collectors.toList());
    }

    public List<String> getEmailsByCity(String city) {
        List<Person> allPersons = fireStationData.values()
                .stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

        // Filter persons by city and collect their emails
        return allPersons.stream()
                .filter(person -> person.getCity().equalsIgnoreCase(city))
                .map(Person::getEmail)
                .distinct() // Ensure unique email addresses
                .collect(Collectors.toList());
    }

}
