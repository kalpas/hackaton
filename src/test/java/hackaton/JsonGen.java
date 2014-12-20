package hackaton;

import java.io.File;
import java.net.URL;

import org.jsonschema2pojo.SchemaMapper;
import org.junit.Test;

import com.sun.codemodel.JCodeModel;

public class JsonGen {

	@Test
	public void test() throws Exception {

		JCodeModel codeModel = new JCodeModel();

		URL source = new URL("file:///D:/PERSONAL/ws/hackaton/error.json");

		new SchemaMapper().generate(codeModel, "VKErrorJson", "com.example", source);

		codeModel.build(new File("output"));

	}

}
