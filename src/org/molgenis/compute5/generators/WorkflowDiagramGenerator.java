package org.molgenis.compute5.generators;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import org.molgenis.compute5.model.Workflow;


/** Generates graphvis diagram */
public class WorkflowDiagramGenerator extends Generator
{
	public void generate(File dir, Workflow workflow) throws IOException
	{
		//model
		Map<String, Object> model = new LinkedHashMap<String, Object>();
		model.put("workflow", workflow);

		//apply
		File dotFile = new File(dir.getAbsoluteFile() + "/workflow.dot");
		applyTemplate(model, "WorkflowDiagramGenerator.ftl", dotFile);
		
		System.out.println("Generated "+dotFile);
		
		GraphvizUtil.executeDot(dotFile, "png", true);
	}
}
