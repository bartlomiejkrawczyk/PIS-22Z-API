package com.example.api.users.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.io.Serializable;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "USER")
public class UserEntity implements Serializable {

	@Id
	@GeneratedValue // automatycznie będzie podbijało wartość
	@Column(name = "USER_ID", nullable = false, unique = true, precision = 8)
	int id;

	@Column(name = "LOGIN", nullable = false)
	int login;

	@Column(name = "EMAIL", nullable = false, length = 150)
	String email;

	@Column(name = "PASSWORD", nullable = false, length = 400)
	String password;

	@Column(name = "TYPE", nullable = false, length = 400)
	String type;

//	@Builder.Default // gdy korzystasz z buildera przyjmij że pole nie jest nullem tylko new array list (jak nie chcesz mieć null pointerów)
//	@JoinColumn(name = "CONCEPT_ID") // gdy zaciągam tabelę, to joinuję z inną tabelą po zadanym kluczu obcym z takim kluczem jakim jest target entity
//	@OneToMany(targetEntity = ParagraphEntity.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
//	List<ParagraphEntity> paragraphs = new ArrayList<>(); // zmapowana tabela
}
