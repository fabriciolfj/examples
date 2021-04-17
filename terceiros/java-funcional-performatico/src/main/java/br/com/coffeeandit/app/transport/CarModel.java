package br.com.coffeeandit.app.transport;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CarModel {

	@JsonProperty(value = "fipe_marca", access = JsonProperty.Access.AUTO)
	private String fipeMarca;
	private String marca;
	private String key;
	private String name;
	private String id;
	@JsonProperty(value = "fipe_name", access = JsonProperty.Access.AUTO)
	private String fipeName;

}