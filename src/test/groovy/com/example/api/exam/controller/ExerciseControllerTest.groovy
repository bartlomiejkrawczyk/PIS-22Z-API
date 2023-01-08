//package com.example.api.exam.controller
//
//import com.example.api.exam.service.ExerciseService
//import com.example.model.exam.ExerciseDto
//import reactor.core.publisher.Mono
//import spock.lang.Specification
//import spock.lang.Subject
//
//class ExerciseControllerTest extends Specification {
//
//	def service = Mock(ExerciseService)
//
//	@Subject
//	def controller = new ExerciseController(service)
//
//	def 'When getting section id, the application shall answer with exercises in it'() {
//		given:
//		service.getExerciseBySectionId(_ as Integer) >> response// załadając że zwróci taką odpowiedź
//
//		expect: // co oczekujesz po wyjściu
//		controller.getExerciseBySectionId(id) == response // TODO: There seems to be an issue with comparison
//
//		where: // tabelka parametrów (każdy kolejny jest innym wierszem) TODO: Insert to DB some exercises & return them here
//		id || response
//		1  || new ExerciseDto()
//		2  || new ExerciseDto()
//		3  || new ExerciseDto()
//	}
//
//	def 'When saving exercise, controller should propagate method call to service'() {
//		given: //TODO: Implement the method in service
//		service.saveExercise(_ as ExerciseDto) >> Mono.justOrEmpty(exercise)
//		service.saveExercise(null) >> Mono.empty()
//
//		expect:
//		controller.saveExercise(exercise).block() == exercise
//
//		where:
//		id | exercise
//		0 | null
//		1 | new ExerciseDto()
//	}
//}
