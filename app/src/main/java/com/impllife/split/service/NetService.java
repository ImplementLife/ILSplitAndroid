package com.impllife.split.service;

import com.impllife.split.data.dto.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

public class NetService {
    private final String baseUrl = "http://134.249.102.244:8080";
    private final RestTemplate restTemplate;

    public NetService() {
        this.restTemplate = new RestTemplate();
        this.restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
    }

    public boolean serverAlive() {
        return false;
    }

    public Optional<User> sendData() {
        ResponseEntity<User> userResponseEntity = null;
        try {
            User user = new User();
            user.setName("Alex");

            userResponseEntity = restTemplate.postForEntity(baseUrl + "/register", user, User.class);
        } catch (Throwable e) {
            e.printStackTrace();
        }

        if (userResponseEntity == null || !userResponseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return Optional.empty();
        }
        return Optional.of(userResponseEntity.getBody());
    }
    public void tst0() {
        /*try {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://localhost:8080/tst").openConnection();
            connection.setRequestProperty("User-Agent", "split-mobile-v0.1");

            if (connection.getResponseCode() == 200) {
                InputStream responseBody = connection.getInputStream();
                InputStreamReader responseBodyReader = new InputStreamReader(responseBody, "UTF-8");

                JsonReader jsonReader = new JsonReader(responseBodyReader);
                jsonReader.toString();
            } else {
                // Error handling code goes here
            }
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
