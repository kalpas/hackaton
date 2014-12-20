package mygalaxy.vk.api.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class VKError extends Exception {

	/**
	 * 
	 */
	private static final long   serialVersionUID = -1360758972832819238L;

	@JsonProperty("error_code")
	public Integer              errorCode;
	@JsonProperty("error_msg")
	public String               errorMsg;
	@JsonProperty("request_params")
	public List<VKRequestParam> requestParams    = new ArrayList<VKRequestParam>();

}
