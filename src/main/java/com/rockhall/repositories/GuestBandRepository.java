package com.rockhall.repositories;

import org.springframework.data.repository.CrudRepository;

import com.rockhall.models.Event;
import com.rockhall.models.GuestBand;

public interface GuestBandRepository extends CrudRepository<GuestBand, String>{

	Iterable<GuestBand> findByEvent(Event event);
	GuestBand findByCode(String code);
}
