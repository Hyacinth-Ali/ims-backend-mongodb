package com.ali.hyacinth.ims.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ali.hyacinth.ims.model.CEO;

@Repository
public interface CEORepository extends CrudRepository<CEO, Long>{

}
