package com.example.api.wiki.controller;

import com.example.api.wiki.service.SectionService;
import com.example.model.Section;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/section")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class SectionController {

	private final SectionService sectionService;

	@GetMapping
	public Flux<Section> getRootSections() {
		return sectionService.getRootSections();
	}

	@GetMapping("/parent/{parentId}")
	public Flux<Section> getSectionsByParentId(@PathVariable int parentId) {
		return sectionService.getSectionByParentId(parentId);
	}

	@GetMapping("/id/{id}")
	public Mono<Section> getSectionById(@PathVariable int id) {
		return sectionService.getSectionById(id);
	}

	@DeleteMapping("{id}")
	public Mono<Integer> deleteSection(@PathVariable int id) {
		return sectionService.deleteSection(id);
	}

	@PostMapping({"{parentId}", ""})
	public Mono<Section> saveSection(@PathVariable(required = false) Integer parentId, @RequestBody Section section) {
		return sectionService.saveSection(parentId, section);
	}
}
