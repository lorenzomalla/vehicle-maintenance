package it.lorenzomalla.event.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "invite")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InviteEntity extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "invite_id")
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "email")
	private String email;

}
