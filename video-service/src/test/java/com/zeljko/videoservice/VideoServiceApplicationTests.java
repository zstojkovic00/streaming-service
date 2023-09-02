package com.zeljko.videoservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zeljko.videoservice.repository.VideoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
class VideoServiceApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private VideoRepository videoRepository;

	@Container
	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");

	@DynamicPropertySource
	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry){
		dynamicPropertyRegistry.add("spring.data.mongo.uri", mongoDBContainer::getReplicaSetUrl);
	}


	@Test
	void shouldCreateVideo() throws Exception {
		VideoRequest videoRequest = getVideoRequest();
		String videoRequestString = objectMapper.writeValueAsString(videoRequest);

		mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/video")
				.contentType(MediaType.APPLICATION_JSON)
				.content(videoRequestString)
				)
				.andExpect(status().isCreated());
		Assertions.assertTrue(videoRepository.findAll().size() == 1);
	}


	private VideoRequest getVideoRequest() {
		return VideoRequest.builder()
				.name("Spiderman-2")
				.description("Marvel movie")
				.url("url-to-movie")
				.category("marvel")
				.build();
	}


}
