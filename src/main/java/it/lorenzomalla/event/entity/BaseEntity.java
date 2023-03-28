package it.lorenzomalla.event.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@CreationTimestamp
	private LocalDateTime creationDate;

	@UpdateTimestamp
	private LocalDateTime updateDate;

	private String modifiedBy;

}
