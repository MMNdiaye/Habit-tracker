package sn.ndiaye.habit_tracker.repositories;

import org.springframework.data.repository.CrudRepository;
import sn.ndiaye.habit_tracker.entities.Habit;

import java.util.Optional;
import java.util.UUID;

public interface HabitRepository extends CrudRepository<Habit, Long> {
    Optional<Habit> findByOwnerIdAndName(UUID ownerId, String name);
}
