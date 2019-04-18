package com.rockhall.repositories;
//
import org.springframework.data.repository.CrudRepository;

import com.rockhall.models.Event;

public interface EventRepository extends CrudRepository<Event, String>{
	
	Event findById(long id);

}
