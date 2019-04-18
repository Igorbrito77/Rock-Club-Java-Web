package com.rockhall.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.rockhall.models.Event;
import com.rockhall.models.GuestBand;
import com.rockhall.repositories.EventRepository;
import com.rockhall.repositories.GuestBandRepository;

@Controller
public class EventController {
	
	@Autowired EventRepository eventRepository;
	@Autowired GuestBandRepository bandRepository;
	
	@GetMapping("/cadastrarEvento")
	public String addEvents() {
		return "events/form_event";
	}
	
	@PostMapping("/cadastrarEvento")
	public String registerEvent(Event event) {
		eventRepository.save(event);
		return "redirect:/cadastrarEvento";
	}
	
	@GetMapping("/eventos")
	public ModelAndView listEvents() {
		ModelAndView mv = new ModelAndView("events/events_list");
		Iterable<Event> events = eventRepository.findAll();
		mv.addObject("events", events);
		return mv;
	}
	
	@RequestMapping("/excluirEvento")
	public String removeEvent(long id) {
		Event event = eventRepository.findById(id);
		eventRepository.delete(event);
		return "redirect:/eventos";
	}
	

	@GetMapping("/{id}")
	public ModelAndView detailEvent(@PathVariable("id") long id) {
		ModelAndView mv = new ModelAndView("events/event_details");
		Event event = eventRepository.findById(id);
		mv.addObject("event", event);
		
		Iterable <GuestBand> bands = bandRepository.findByEvent(event);
		mv.addObject("guests", bands);
		
		return mv;
	}
	
	@PostMapping("/{id}")
	public String addBand(@PathVariable("id") long id, GuestBand guestBand) {
		
		Event event = eventRepository.findById(id);
		guestBand.setEvent(event);
		bandRepository.save(guestBand);
		
		return "redirect:/{id}";
	}
	
	@RequestMapping("/excluirBanda")
	public String removeBand(String code) {
		
		GuestBand band = bandRepository.findByCode(code);
		bandRepository.delete(band);
		
		Event event = band.getEvent();
		long codeLong = event.getId(); 
		String str_code = "" +codeLong ;
		return "redirect:/" + str_code;
	}
}
