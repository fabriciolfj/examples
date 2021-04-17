module coffeeandit.example.config {
	exports com.coffeeandit.config;

	requires coffeeandit.example.usecase;
	requires coffeeandit.example.domain;
	requires coffeeandit.example.jug;
	requires coffeeandit.example.uuid;
	requires coffeeandit.example.db.simple;
	requires coffeeandit.example.db.hazelcast;
	requires coffeeandit.example.encoder;
}
