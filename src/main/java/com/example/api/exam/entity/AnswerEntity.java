package com.example.api.exam.entity;

import com.example.api.exam.entity.id.AnswerId;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
	int exerciseId;

	@Id
	@Column(name = "ANSWER_NO", nullable = false, precision = 2)
	int number;

	@Column(name = "CONTENT", length = 100)
	String content;

	@Column(name = "CORRECT", precision = 1)
	int correct;

	@Column(name = "SEQUENTIAL_NUMBER", nullable = false, precision = 4)
	int sequentialNumber;
}
