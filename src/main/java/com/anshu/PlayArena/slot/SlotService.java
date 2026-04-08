package com.anshu.PlayArena.slot;

import java.time.LocalTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.anshu.PlayArena.arena.Arena;
import com.anshu.PlayArena.arena.ArenaRepo;

@Service
public class SlotService {

    private final SlotRepo slotRepo;
    private final ArenaRepo arenaRepo;

    public SlotService(SlotRepo slotRepo, ArenaRepo arenaRepo) {
        this.slotRepo = slotRepo;
        this.arenaRepo = arenaRepo;
    }

    public void createSlotsForArena(Arena arena) {
        for (int hour = 17; hour < 22; hour++) {
            Slot slot = new Slot(
                    arena,
                    LocalTime.of(hour, 0),
                    LocalTime.of(hour + 1, 0),
                    SlotStatus.AVAILABLE);

            slotRepo.save(slot);
        }

    }

    // keep in mind
    public Slot createSlot(Integer arenaId, Slot slot) {
        Arena arena = arenaRepo.findById(arenaId)
                .orElseThrow(() -> new RuntimeException("Arena not found"));
        slot.setArena(arena);
        return slotRepo.save(slot);
    }

    public List<Slot> findByArenaIdOrderByStartTime(Integer arenaId) {
        return slotRepo.findByArenaIdOrderByStartTime(arenaId);
    }

}
