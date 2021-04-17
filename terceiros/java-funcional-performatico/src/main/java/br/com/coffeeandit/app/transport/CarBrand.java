package br.com.coffeeandit.app.transport;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CarBrand {

	private Integer id;
	private String name;
	@JsonProperty(value = "fipe_name", access = Access.AUTO)
	private String fipeName;
	private Integer order;
	private String key;

}
