//package com.example.collectronic.repository;
//
//import com.example.collectronic.entity.Item;
//import com.example.collectronic.entity.User;
//import com.example.collectronic.entity.UserCollection;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;
//
//import java.util.Collection;
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface ItemRepository extends JpaRepository<Item, Long> {
//    List<Item> findAllByUserCollectionOrderByCreatedDateDesc(UserCollection userCollection);
//    List<Item> findAllByOrderByCreatedDate();
//    Optional<Collection> findItemByIdAndUser(Long id, User user);
//}
