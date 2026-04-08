package com.anshu.PlayArena.arena;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/arena")
public class ArenaController {
    private final ArenaService arenaService;

    public ArenaController(ArenaService arenaService) {
        this.arenaService = arenaService;
    }

    @PostMapping
    Arena addArena(@RequestBody Arena arena) {
        return arenaService.addArena(arena);
    }

    @GetMapping
    List<Arena> getAllArenas() {
        return arenaService.getAllArenas();
    }

    @GetMapping("/{id}")
    Arena getArenaById(@PathVariable Integer id) {
        return arenaService.getArenaById(id);
    }

}
