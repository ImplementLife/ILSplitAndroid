package com.impllife.split.service;

import com.impllife.split.data.dto.User;
import com.impllife.split.data.jpa.entity.Rec;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.data.jpa.provide.Repo;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

public class ComService {
    private static ComService instance;
    public static ComService getInstance() {
        if (instance == null) {
            instance = new ComService();
        }
        return instance;
    }
    private ComService() {}

    private final Repo repo = new Repo();

    public Optional<User> tst() {
        ResponseEntity<User> userResponseEntity = null;
        try {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

            User user = new User();
            user.setName("Alex");

            userResponseEntity = restTemplate.postForEntity("http://134.249.102.244:8080/register", user, User.class);
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
    public void add(Rec rec) {
        repo.add(rec);
    }
    public List<Rec> read() {
        return repo.readAll();
    }
    public void delete() {
        repo.deleteAll();
    }
    public boolean deleteById(int id) {
        return repo.deleteById(id);
    }


    public void insert(Transaction... transactions) {
        repo.insert(transactions);
    }

    public List<Transaction> getAllTransactions() {
        return repo.getAllTransactions();
    }
}
