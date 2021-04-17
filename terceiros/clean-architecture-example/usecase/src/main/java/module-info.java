module coffeeandit.example.usecase {
	exports com.coffeeandit.example.usecase;
	exports com.coffeeandit.example.usecase.exception;
	exports com.coffeeandit.example.usecase.port;

	requires coffeeandit.example.domain;
	requires org.apache.commons.lang3;
}
