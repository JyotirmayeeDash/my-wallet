package com.wallet.repository;

import com.wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for User entity.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUserName(String userName);


}
