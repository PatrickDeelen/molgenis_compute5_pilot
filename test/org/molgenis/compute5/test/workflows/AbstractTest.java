package org.molgenis.compute5.test.workflows;

import java.io.File;
import java.io.IOException;

import org.molgenis.compute5.generators.TasksDiagramGenerator;
import org.molgenis.compute5.generators.WorkflowDiagramGenerator;
import org.molgenis.compute5.model.Compute;
import org.molgenis.compute5.model.Step;
import org.molgenis.compute5.model.Task;
import org.molgenis.util.tuple.Tuple;

public abstract class AbstractTest
{
	public void test(Compute c) throws IOException
	{
		System.out.println("steps:");
		for (Step s : c.getWorkflow().getSteps())
		{
			System.out.println(s);
		}

		System.out.println("parameters (before):");
		for (Tuple v : c.getParameters().getValues())
			System.out.println(v);

		c.generateTasks();
				
		new WorkflowDiagramGenerator().generate(new File("target/doc/"), c.getWorkflow());
		new TasksDiagramGenerator().generate(new File("target/doc/"), c.getTasks());

		System.out.println("parameters (after):");
		for (Tuple v : c.getParameters().getValues())
			System.out.println(v);

		System.out.println("tasks:");
		for (Task t : c.getTasks())
		{
			System.out.println(t);
		}
	}
}