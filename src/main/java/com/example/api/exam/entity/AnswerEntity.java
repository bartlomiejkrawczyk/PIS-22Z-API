package com.example.api.exam.entity;

import com.example.api.exam.entity.id.AnswerId;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@IdClass(AnswerId.class)
@Table(name = "ANSWERS")
public class AnswerEntity implements Serializable {
	@Id
	@Column(name = "EXERCISE_ID", nullable = false, precision = 9)
	int exerciseId; // number of exercise

	@Id
	@Column(name = "ANSWER_NO", nullable = false, precision = 2)
	int number; // number of answer on the list

	@Column(name = "CONTENT", length = 100)
	String content; // content of the answer

	@Column(name = "CORRECT", precision = 1)
	int correct; // boolean description for True/False

	@Column(name = "SEQUENTIAL_NUMBER", nullable = false, precision = 4)
	int sequentialNumber; // wtf?

	@Builder.Default
	@JoinColumn(name = "EXERCISE_ID")
	@JoinColumn(name = "ANSWER_NO")
	@OneToMany(targetEntity = SubAnswerEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	List<SubAnswerEntity> subAnswers = new ArrayList<>();
}
