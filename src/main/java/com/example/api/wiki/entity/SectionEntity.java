package com.example.api.wiki.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
@Table(name = "SECTIONS")
public class SectionEntity implements Serializable {

	@Id
	@GeneratedValue
	@Column(name = "SECTION_ID", nullable = false, unique = true, precision = 6)
	int id;

	@Column(name = "NAME", length = 50)
	String name;

	@Column(name = "SUPER_SECTION_ID", precision = 6)
	Integer superSectionId;
}
