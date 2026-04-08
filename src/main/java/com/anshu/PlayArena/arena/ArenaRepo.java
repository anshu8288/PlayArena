package com.anshu.PlayArena.arena;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArenaRepo extends JpaRepository<Arena, Integer> {

}
