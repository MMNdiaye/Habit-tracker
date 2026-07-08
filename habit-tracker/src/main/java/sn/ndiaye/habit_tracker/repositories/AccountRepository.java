package sn.ndiaye.habit_tracker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sn.ndiaye.habit_tracker.entities.Account;

import java.util.UUID;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    boolean existsByUsername(String username);
}
