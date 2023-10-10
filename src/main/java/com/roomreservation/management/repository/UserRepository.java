package com.roomreservation.management.repository;

import com.roomreservation.management.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByNameAndLastname(String name , String lastname);

    Optional<User> findByEmail(String email);

//    Optional<User> findByIdIn(List<Long> Id);

    Optional<User> findByNameOrId(String name, UUID id);

//    //HQL implementation because we want a different functionality of what is existing
//    @Query(value = "select p from User p where p.name =: name and p.lastname =: lastname")
//    Optional<User> findByIdxxx(String name , String lastname);

//    @Query("select p from User p order by p.creationDate desc")
//    Optional<User> findByCreationDate();

//    Optional<User> findTopByCreationDateIsNotNull(Sort sort);
//    // For example,findByEmail(String email) to find a user by email address
}
