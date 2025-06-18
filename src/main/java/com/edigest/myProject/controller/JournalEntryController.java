package com.edigest.myProject.controller;

import com.edigest.myProject.entity.JournalEntry;
import com.edigest.myProject.entity.User;
import com.edigest.myProject.service.JournalEntryService;
import com.edigest.myProject.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {
//    private Map<Long, JournalEntry> journalEntries = new HashMap<>();

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<?> getAllJournalsOfUser()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> all= user.getJournalEntries();
        if(all!=null&&!all.isEmpty())
        {
            return new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<?> createEntry(@RequestBody JournalEntry newEntry)
    {
        try{
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        newEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(newEntry,username);
        return new ResponseEntity<>(newEntry,HttpStatus.CREATED);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> findById(@PathVariable ObjectId myId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        List<JournalEntry> collect= user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).toList();
        if(!collect.isEmpty())
        {
            Optional<JournalEntry> journalEntry=journalEntryService.findById(myId);
            if(journalEntry.isPresent())
            {
                return new ResponseEntity<>(journalEntry.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        boolean b= journalEntryService.deleteById(myId,username);
        if(b){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    }
    @PutMapping("id/{myId}")
    public ResponseEntity<?> updateById(@PathVariable ObjectId myId,@RequestBody JournalEntry update)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        User user = userService.findByUsername(username);
        List<JournalEntry> collect= user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).toList();
        if(!collect.isEmpty())
        {
            Optional<JournalEntry> journalEntry=journalEntryService.findById(myId);
            if(journalEntry.isPresent())
            {
                JournalEntry oldEntry=journalEntry.get();
                oldEntry.setTitle(update.getTitle()!=null&& !update.getTitle().equals("")? update.getTitle() : oldEntry.getTitle());
                oldEntry.setContent(update.getContent()!=null&& !update.getContent().equals("")? update.getContent() : oldEntry.getContent());
                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry,HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

}
