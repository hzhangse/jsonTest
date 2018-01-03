package com.bigsec.json;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *  JacksonMapper
 *

 */
public class JacksonMapper {

	private static final ObjectMapper mapper = new ObjectMapper();

	private JacksonMapper() {
	}

	public static ObjectMapper getInstance() {
		return mapper;
	}

	/**
	 * Object to Json String
	 * 
	 * @param obj
	 * @return
	 * @throws JsonProcessingException 
	 */
	public static String toJson(Object obj) throws JsonProcessingException {

		ObjectMapper mapper = JacksonMapper.getInstance();
		//SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss"); 
		//mapper.setDateFormat(df);
		return mapper.writeValueAsString(obj);
	}

	/**
	 * Json to List
	 * 
	 * @param <T>
	 * @param json
	 * @param clazz
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static <T> List<T> toList(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = JacksonMapper.getInstance();
		//mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		//mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		List<T> list = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz));
		return list;
	}

	/**
	 * Json String to Object<br>
	 * String json = "..."; ObjectMapper mapper = JacksonMapper.getInstance();<br>
	 * YourBean bean = mapper.readValue(json, new YourBean().getClass());
	 * 
	 * @param json
	 * @param clazz
	 * @return 
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static <T> T toObj(String json, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = JacksonMapper.getInstance();
		//mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		//mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		return mapper.readValue(json, clazz);
	}
	
	/**
	 * JsonNode to Object
	 * @param node
	 * @param clazz
	 * @return
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public static <T> T toObj(JsonNode node, Class<T> clazz) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = JacksonMapper.getInstance();
		return mapper.readValue(node.toString(), clazz);
	}
	
	/**
	 * Json String to JsonNode
	 * @param json
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public static JsonNode toNode(String json) throws JsonProcessingException, IOException{
		ObjectMapper mapper = JacksonMapper.getInstance();
		return mapper.readTree(json);
	}
	
	/**
	 * Object to JsonNode
	 * @param obj
	 * @return
	 * @throws JsonProcessingException
	 * @throws IOException
	 */
	public static JsonNode toNode(Object obj) throws JsonProcessingException, IOException{
		String json = toJson(obj);
		return toNode(json);
	}
}
