package sn.ndiaye.habit_tracker.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
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

    public void registerHabit(Habit habit) {
        habits.add(habit);
        habit.setOwner(this);
    }

    public void deleteHabit(Habit habit) {
        habits.remove(habit);
        habit.setOwner(null);
    }
}
