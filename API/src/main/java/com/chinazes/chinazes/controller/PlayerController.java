package com.chinazes.chinazes.controller;

import com.chinazes.chinazes.model.Playeralt;
import com.chinazes.chinazes.model.Players;
import com.chinazes.chinazes.repository.CommsRepository;
import com.chinazes.chinazes.repository.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    private PlayersRepository playersRepository;
    @Autowired
    private CommsRepository commsRepository;

    @GetMapping("/getPlayers")
    public List<Players> getAllPlayers()
    {
        return playersRepository.findAll();
    }
    @GetMapping("/getPlayerById/{id}")
    public Optional<Players> getPlayerById(@PathVariable long id)
    {
        return playersRepository.findById(id);
    }
    @PostMapping("/postPlayer")
    public String postPlayer(@RequestBody Players player)
    {
        playersRepository.save(player);
        return "player " + player.getId() + " added";
    }
    @PatchMapping("/updatePlayer")
    public Players updatePlayer(@RequestBody Players player)
    {
        return playersRepository.save(player);
    }
    @PatchMapping("/recalculatePlayer/{id}")
    public Players recalculatePlayer(@PathVariable long id)
    {
        int votes = commsRepository.votesCount(id);
        int comms_count = commsRepository.commsCount(id);
        Players p = new Players(id, votes, comms_count);
        playersRepository.save(p);
        return p;
    }
    @GetMapping("/getPlayerIDs")
    public List<Integer> getPlayerIDs(@RequestParam int page)
    {
        return playersRepository.getPlayerIDs(page);
    }
    @GetMapping("/getPlayersByVotes")
    public List<Players> getPlayersByVotes()
    {
        return playersRepository.getPlayersByVotes();
    }
    @GetMapping("/getPlayersByVotesAlt")
    public List<Playeralt> getPlayersByVotesAlt()
    {
        return playersRepository.getPlayersByVotesAlt();
    }
    @GetMapping("/getPlayersByCountAlt")
    public List<Playeralt> getPlayersByCountAlt()
    {
        return playersRepository.getPlayersByCountAlt();
    }
    @GetMapping("/getPlayersByCount")
    public List<Players> getPlayersByCount()
    {
        return playersRepository.getPlayersByCount();
    }
}
