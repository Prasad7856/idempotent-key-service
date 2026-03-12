package com.prasad.repository;

import com.prasad.entity.IdempotencyKey;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdempotencyRepository extends JpaRepository<IdempotencyKey,Long> {

    Optional<IdempotencyKey> findByIdempotencyKey(String key);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT i FROM IdempotencyKey i WHERE i.idempotencyKey=:key")
    Optional<IdempotencyKey> lockByKey(@Param("key") String key);
}
