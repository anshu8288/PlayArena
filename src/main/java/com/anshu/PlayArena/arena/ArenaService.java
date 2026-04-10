package com.anshu.PlayArena.arena;

import java.util.List;

import org.springframework.stereotype.Service;

import com.anshu.PlayArena.slot.Slot;
import com.anshu.PlayArena.slot.SlotService;

import jakarta.annotation.PostConstruct;

@Service
public class ArenaService {
    private final ArenaRepo arenaRepo;
    private final SlotService slotService;

    public ArenaService(ArenaRepo arenaRepo, SlotService slotService) {
        this.arenaRepo = arenaRepo;
        this.slotService = slotService;
    }

    @PostConstruct
    public void initArenas() {

        // prevent duplicate insert
        if (arenaRepo.count() > 0)
            return;

        // 2 Turfs
        for (int i = 1; i <= 2; i++) {
            Arena turf = new Arena();
            turf.setName("Turf " + i);
            turf.setSportsType(SportsType.CRICKET);

            Arena saved = arenaRepo.save(turf);
            slotService.createSlotsForArena(saved);
        }

        // 5 Courts
        for (int i = 1; i <= 5; i++) {
            Arena court = new Arena();
            court.setName("Court " + i);
            court.setSportsType(SportsType.BADMINTON);

            Arena saved = arenaRepo.save(court);
            slotService.createSlotsForArena(saved);
        }

        // 3 Tennis Courts
        for (int i = 1; i <= 2; i++) {
            Arena court = new Arena();
            court.setName("Tennis Court " + i);
            court.setSportsType(SportsType.TENNIS);

            Arena saved = arenaRepo.save(court);
            slotService.createSlotsForArena(saved);
        }

        // 2 TT Courts
        for (int i = 1; i <= 2; i++) {
            Arena court = new Arena();
            court.setName("TT Court " + i);
            court.setSportsType(SportsType.TT);

            Arena saved = arenaRepo.save(court);
            slotService.createSlotsForArena(saved);
        }
    }

    public Arena addArena(Arena arena) {
        Arena savedArena = arenaRepo.save(arena);

        slotService.createSlotsForArena(savedArena);

        return savedArena;
    }

    public List<Arena> getAllArenas() {
        return arenaRepo.findAll();
    }

    public List<Slot> findSlotsByArenaId(Integer id) {
        return slotService.findByArenaIdOrderByStartTime(id);
    }

}
