package com.example.collectronic.repository;

import com.example.collectronic.entity.User;
import com.example.collectronic.entity.UserCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserCollectionRepository extends JpaRepository<UserCollection, Long> {

    List<UserCollection> findAllByCollectronicuserOrderByCreatedDateDesc(User user);
    List<UserCollection> findAllByOrderByCreatedDate();
    Optional<UserCollection> findUserCollectionByIdAndCollectronicuser(Long id, User user);

}
