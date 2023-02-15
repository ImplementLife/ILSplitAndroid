package com.impllife.split.service;

import com.impllife.split.data.dto.User;
import com.impllife.split.data.jpa.entity.People;
import com.impllife.split.data.jpa.entity.Transaction;
import com.impllife.split.data.jpa.provide.DaoFactory;
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

    private final DaoFactory repo = new DaoFactory();

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

    public void insert(Transaction transactions) {
        repo.getTransactionDao().insert(transactions);
    }
    public void insert(People people) {
        repo.getPeopleDao().insert(people);
    }

    public void update(Transaction transactions) {
        repo.getTransactionDao().update(transactions);
    }
    public void update(People people) {
        repo.getPeopleDao().update(people);
    }

    public void delete(Transaction transactions) {
        repo.getTransactionDao().delete(transactions);
    }
    public void delete(People people) {
        repo.getPeopleDao().delete(people);
    }

    public List<Transaction> getAllTransactions() {
        return repo.getTransactionDao().getAll();
    }
    public List<People> getAllPeoples() {
        return repo.getPeopleDao().getAll();
    }
}
