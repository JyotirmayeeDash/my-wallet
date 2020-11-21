package com.wallet.repository;

import com.wallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Transaction entity.
 */
@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

    @Query("Select t from Transaction t inner join t.user u where u.userName=:userName")
    List<Transaction> findByUserName(@Param("userName") String userName);

}
