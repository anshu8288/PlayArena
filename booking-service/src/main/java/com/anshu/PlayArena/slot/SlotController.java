package com.anshu.PlayArena.slot;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/slots")
public class SlotController {

    private final SlotService slotService;

    public SlotController(SlotService slotService) {
        this.slotService = slotService;
    }

    @GetMapping("/arena/{arenaId}")
    public List<Slot> getSlotsByArenaId(@PathVariable Integer arenaId) {
        return slotService.findByArenaIdOrderByStartTime(arenaId);
    }

    // keep in mind
    @PostMapping("/arena/{arenaId}/slot")
    public Slot createSlot(@PathVariable Integer arenaId, @RequestBody Slot slot) {
        return slotService.createSlot(arenaId, slot);
    }

}
