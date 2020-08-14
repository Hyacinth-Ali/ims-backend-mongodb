package com.ali.hyacinth.ims.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ali.hyacinth.ims.model.CEO;


public interface CEORepository extends MongoRepository<CEO, Long>{

}
