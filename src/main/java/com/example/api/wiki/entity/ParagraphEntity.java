package com.example.api.wiki.entity;

import com.example.api.wiki.entity.id.ParagraphId;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
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
@IdClass(ParagraphId.class)
@Table(name = "PARAGRAPHS")
public class ParagraphEntity implements Serializable {

	@Id
	@JoinColumn(name = "CONCEPT_ID")
	@Column(name = "CONCEPT_ID", nullable = false, unique = true, precision = 8)
	int conceptId;

	@Id
	@Column(name = "PARAGRAPH_NO", nullable = false, precision = 3)
	int number;

	@Column(name = "SEQUENTIAL_NUMBER", nullable = false, precision = 4)
	int sequentialNumber;

	@Column(name = "HEADER", length = 100)
	String header;

	@Column(name = "DESCRIPTION", length = 2000)
	String description;
}
