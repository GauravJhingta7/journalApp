package com.edigest.myProject.service;
import com.edigest.myProject.entity.JournalEntry;
import com.edigest.myProject.entity.User;
import com.edigest.myProject.repository.JournalEntryRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalEntryRepo;

    @Autowired
    private UserService userService;
    @Transactional
    public void saveEntry(JournalEntry journalEntry,String username)
    {
        try {

            User user= userService.findByUsername(username);
            JournalEntry saved = journalEntryRepo.save(journalEntry);
            user.getJournalEntries().add(saved);
            userService.saveUser(user);
        }
        catch (Exception e)
        {
            System.out.println(e);
            throw new RuntimeException("An error occurred while saving!!!",e);
        }
    }
    public void saveEntry(JournalEntry journalEntry)
    {
        journalEntryRepo.save(journalEntry);
    }
    public List<JournalEntry> getAll()
    {
        return journalEntryRepo.findAll();
    }
    public Optional<JournalEntry> findById(ObjectId myId)
    {
        return journalEntryRepo.findById(myId);
    }
    @Transactional
    public boolean deleteById(ObjectId myId,String username)
    {
        boolean b=false;
        try {
            User user = userService.findByUsername(username);
            boolean removed=user.getJournalEntries().removeIf(x-> x.getId().equals(myId));
            if(removed) {
                userService.saveUser(user);
                journalEntryRepo.deleteById(myId);
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("An Error occured while deleting the entry.",e);
        }
        return b;
    }
}
