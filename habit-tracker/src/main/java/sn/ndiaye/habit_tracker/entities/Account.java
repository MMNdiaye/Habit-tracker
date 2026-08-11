package sn.ndiaye.habit_tracker.entities;

import jakarta.persistence.*;
import lombok.*;
import sn.ndiaye.habit_tracker.exceptions.DuplicateHabitNameException;
import sn.ndiaye.habit_tracker.exceptions.HabitNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Builder.Default
    @OneToMany(mappedBy = "owner", orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Habit> habits = new ArrayList<>();

    public void changeUsername(String username) {
        this.username = username;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void registerHabit(Habit habit) {
        if (hasHabit(habit.getName()))
            throw new DuplicateHabitNameException(habit.getName());
        habits.add(habit);
        habit.setOwner(this);
    }

    public void renameHabit(String habitName, String newHabitName) {
        var habit = findHabit(habitName);
        if (hasHabit(newHabitName))
            throw new DuplicateHabitNameException(newHabitName);
        habit.setName(newHabitName);
    }

    private boolean hasHabit(String habitName) {
        return habits.stream()
                .anyMatch(habit -> habit.getName().equals(habitName));
    }

    private Habit findHabit(String habitName) {
        for (var habit : habits)
            if (habit.getName().equals(habitName))
                return habit;
        throw new HabitNotFoundException();
    }

    public void deleteHabit(String habitName) {
        var habit = findHabit(habitName);
        habits.remove(habit);
        habit.setOwner(null);
    }
}
