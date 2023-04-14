package it.lorenzomalla.app.entity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vehicle", uniqueConstraints = { @UniqueConstraint(columnNames = "licensePlate") })
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class VehicleEntity extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	@Column(name = "vehicle_id")
	private UUID id;

	private Integer year;

	private String licensePlate;

	private String brand;

	private String model;

	private String color;

	private String image;

	private LocalDateTime registerDate;

	@OneToMany(mappedBy = "vehicle", cascade = CascadeType.MERGE)
	private List<InterventionEntity> interventions;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	private CustomerEntity customer;

}