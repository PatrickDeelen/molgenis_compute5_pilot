package org.molgenis.compute5.generators;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.molgenis.compute5.model.Task;


/** Generates graphvis diagram */
public class TasksDiagramGenerator extends Generate
{
	public void generate(File dir, List<Task> tasks) throws IOException
	{
		//model
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		model.put("tasks", tasks);

		//apply
		File dotFile = new File(dir + "/tasks.dot");
		applyTemplate(model, "TasksDiagramGenerator.ftl", dotFile);
		GraphvizHelper.executeDot(dotFile, "png", true);
	}
}
