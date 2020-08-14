package com.ali.hyacinth.ims.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ali.hyacinth.ims.model.CEO;

@Repository
public interface CEORepository extends MongoRepository<CEO, String>{

}
