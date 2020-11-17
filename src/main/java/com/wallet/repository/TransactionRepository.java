package com.wallet.repository;

import com.wallet.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

  /*  @Query("Select t from OrderTransaction t inner join t.user u where u.userName=:userName")
    public List<Transaction> findByUserName();*/

}
