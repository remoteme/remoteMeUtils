package org.remoteme.utils.com.arLite.rest.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StandardResponse implements Serializable {
	public static final StandardResponse OK = new StandardResponse(true);
	boolean succeed;

}