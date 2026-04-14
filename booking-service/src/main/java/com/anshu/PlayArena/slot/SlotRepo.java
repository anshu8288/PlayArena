package com.anshu.PlayArena.slot;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SlotRepo extends JpaRepository<Slot, Integer> {
    List<Slot> findByArenaIdOrderByStartTime(Integer arenaId);
}
