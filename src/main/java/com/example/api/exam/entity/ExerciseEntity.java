package com.example.api.exam.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "EXERCISES")
public class ExerciseEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EXERCISE_ID", unique = true, precision = 9)
	Integer id;

	@Column(name = "QUESTION", length = 300)
	String question;

	@Column(name = "TYPE", length = 1)
	char type;

	@Column(name = "SECTION_ID", nullable = false, precision = 6)
	int sectionId;

	@Builder.Default
	@JoinColumn(name = "EXERCISE_ID")
	@OneToMany(targetEntity = AnswerEntity.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	List<AnswerEntity> entities = new ArrayList<>();
}
