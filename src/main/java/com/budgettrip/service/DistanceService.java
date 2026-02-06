package com.budgettrip.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DistanceService {

    // Free OpenStreetMap API URL
    private final String NOMINATIM_API = "https://nominatim.openstreetmap.org/search?format=json&q=";

    public double getDistance(String startCity, String endCity) {
        try {
            // 1. Get Coordinates for Start City
            Location loc1 = getCoordinates(startCity);
            // 2. Get Coordinates for End City
            Location loc2 = getCoordinates(endCity);

            if (loc1 == null || loc2 == null) {
                return 0.0; // City not found
            }

            // 3. Calculate Distance using Haversine Formula
            return calculateHaversine(loc1.lat, loc1.lon, loc2.lat, loc2.lon);

        } catch (Exception e) {
            e.printStackTrace();
            return 0.0;
        }
    }

    private Location getCoordinates(String city) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            // Append "Sri Lanka" to search to avoid foreign cities
            String url = NOMINATIM_API + city + ", Sri Lanka";

            // Call the API
            String response = restTemplate.getForObject(url, String.class);

            // Parse JSON response
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response);

            if (root.isArray() && !root.isEmpty()) {
                JsonNode firstResult = root.get(0);
                double lat = Double.parseDouble(firstResult.get("lat").asText());
                double lon = Double.parseDouble(firstResult.get("lon").asText());
                return new Location(lat, lon);
            }
        } catch (Exception e) {
            System.out.println("Error finding coordinates for: " + city);
        }
        return null;
    }

    private double calculateHaversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // Earth radius in km
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return Math.round(R * c * 100.0) / 100.0;
    }

    private static class Location {
        double lat, lon;
        public Location(double lat, double lon) { this.lat = lat; this.lon = lon; }
    }
}