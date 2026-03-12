package com.prasad.service;

import com.prasad.entity.IdempotencyKey;
import com.prasad.exception.InvalidRequestException;
import com.prasad.repository.IdempotencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class IdempotencyService {

    @Autowired
    private IdempotencyRepository repository;

    public IdempotencyKey validateAndCreateKey(String key, String requestHash){

        Optional<IdempotencyKey> existing =
                repository.findByIdempotencyKey(key);

        if(existing.isPresent()){

            IdempotencyKey record = existing.get();

            if(!record.getRequestHash().equals(requestHash)){
//                Payload mismatch for same idempotency key
                throw new InvalidRequestException(
                        "This request was already submitted with different details. Please create a new request."
                );
            }

            return record;
        }

        IdempotencyKey newKey = new IdempotencyKey();

        newKey.setIdempotencyKey(key);
        newKey.setRequestHash(requestHash);
        newKey.setStatus("PROCESSING");
        newKey.setCreatedAt(LocalDateTime.now());

        return repository.save(newKey);
    }
}
