package com.edigest.myProject.repository;

import com.edigest.myProject.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface JournalEntryRepo extends MongoRepository<JournalEntry, ObjectId> {
}
