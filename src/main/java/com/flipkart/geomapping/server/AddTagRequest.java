package com.flipkart.geomapping.server;

import io.dropwizard.jackson.JsonSnakeCase;

import java.io.IOException;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.flipkart.geomapping.utils.JsonUtil;


/**
 * @author deepak.shevani on Nov 19, 2014
 *
 */


@Getter
@Setter
@ToString
@JsonSnakeCase
public class AddTagRequest {

	@NotNull
	@NotEmpty
	private String location;
	
	@NotNull
	@NotEmpty
	private String group;

	@NotNull
	@NotEmpty
	private String tagType;

	@NotNull
	@NotEmpty
	private String value;

	public static AddTagRequest deserializeFromJson(String json) throws JsonParseException,
	JsonMappingException, IOException {
		return JsonUtil.deserializeJson(json, AddTagRequest.class);
	}

}
