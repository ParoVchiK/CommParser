package com.chinazes.chinazes.controller;

import com.chinazes.chinazes.model.Comms;
import com.chinazes.chinazes.model.DateJSON;
import com.chinazes.chinazes.model.GroupedCommentableType;
import com.chinazes.chinazes.repository.CommsRepository;
import com.chinazes.chinazes.repository.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comms")
public class CommsController {
    @Autowired
    private CommsRepository commsRepository;
    @Autowired
    private PlayersRepository playersRepository;
    private PlayerController playerController;

    @GetMapping("/getComms")
    public List<Comms> getComms()
    {
        return commsRepository.findAll();
    }
    @GetMapping("/getComm/{id}")
    public ResponseEntity<Comms> getComm(@PathVariable long id)
    {
        Optional<Comms> oc = commsRepository.findById(id);
        if(oc.isPresent())
        {
            return new ResponseEntity<>(oc.get(), HttpStatus.OK);
        }
        else
        {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/postComms")
    public HttpStatus postComms(@RequestBody Comms comms)
    {
        commsRepository.save(comms);
        return HttpStatus.CREATED;
    }
    @PatchMapping("/updateComm")
    public HttpStatus updateComm(@RequestBody Comms comms)
    {
        commsRepository.save(comms);
        return HttpStatus.OK;
    }
    @GetMapping("/getCommByPlayer/{id}")
    public List<Comms> commsByPlayer(@PathVariable long id)
    {
        return commsRepository.findByUserID(id);
    }
    @GetMapping("/getCommsCountByPlayer/{id}")
    public int commsCountByPlayer(@PathVariable long id)
    {
        return commsRepository.commsCount(id);
    }
    @GetMapping("/getVotesCountByPlayer/{id}")
    public int votesCountByPlayer(@PathVariable long id)
    {
        return commsRepository.votesCount(id);
    }
    @GetMapping("/getCommsByVotes")
    public List<Comms> getCommsByVotes()
    {
        return commsRepository.commsByVotes();
    }
    @GetMapping("/getGroupedCommentableTypesBy")
    public List<GroupedCommentableType> getGroupedCommentableTypesByVotes(@RequestParam(defaultValue="beatmapset") String commentable_type, @RequestParam(defaultValue="votes") String sort_by)
    {
        return commsRepository.groupedCommentableTypeByVotes(commentable_type, PageRequest.of(0, 100, Sort.by(sort_by).descending()));
    }
    @GetMapping("/getGroupedCommentableTypesByCount")
    public List<GroupedCommentableType> getGroupedCommentableTypesByCount()
    {
        return commsRepository.groupedCommentableTypeByCount("beatmapset");
    }
    @GetMapping("/getCommsBetweenDates")
    public List<Comms> getCommsBetweenDates(@RequestBody DateJSON dateJSON)
    {
        return commsRepository.commsBetweenDates(dateJSON.getStartDate(), dateJSON.getEndDate(), PageRequest.of(0, 5, Sort.by("votes").descending()));
    }
}
