package com.example.Inves.persistence.repositories;

import com.example.Inves.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Bossowski
 * @version 1.0
 * @email Mbossowski01@gmail.com
 * @date 29/11/2024 - 12:32
 */
@Repository
public interface TransactionDAORepository extends JpaRepository<Transaction, Long> {
}
