package org.remoteme.utils.com.arLite.rest.v1;


import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import io.swagger.annotations.ApiOperation;
import org.remoteme.utils.com.arLite.rest.v1.dto.HelloDto;


public interface IHelloRest {


	@Headers("Content-Type: application/json")
	@RequestLine("GET /NO_AUTH/getWithName/{name}")
	@ApiOperation(value = "say hello to You", notes = "")
	HelloDto getWithName(@Param("name") String name);


	@Headers("Content-Type: application/json")
	@RequestLine("GET /NO_AUTH/getHelloDto")
	@ApiOperation(value = "return authentificated user", notes = "")
	HelloDto getHelloDto();

	@Headers("Content-Type: application/json")
	@RequestLine("POST /NO_AUTH/modify")
	@ApiOperation(value = "return authentificated user", notes = "")
	HelloDto modify(HelloDto hello);
}
