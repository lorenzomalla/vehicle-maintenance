package it.lorenzomalla.event.pojo;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class EventPojo {

	private String name;
	private LocalDate data;
	private String place;
	private List<String> guests;
}
