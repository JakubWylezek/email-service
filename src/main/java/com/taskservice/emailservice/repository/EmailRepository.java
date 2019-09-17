package com.taskservice.emailservice.repository;

import com.taskservice.emailservice.model.Email;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface EmailRepository extends CrudRepository<Email, Long> {

    Optional<Email> findByName(String name);

    Set<Email> getByNameIn(Set<String> emailSet);
}
