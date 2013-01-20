package org.molgenis.compute5.model;

import java.io.IOException;
import java.util.List;

import org.molgenis.compute5.generators.TaskGenerator;


public class Compute
{
	Workflow workflow;
	Parameters parameters;
	List<Task> tasks;

	public List<Task> generateTasks() throws IOException
	{
		tasks = TaskGenerator.generate(workflow, parameters);
		return tasks;
	}

	public List<Task> getTasks()
	{
		return tasks;
	}

	public void setTasks(List<Task> tasks)
	{
		this.tasks = tasks;
	}

	public Workflow getWorkflow()
	{
		return workflow;
	}

	public void setWorkflow(Workflow workflow)
	{
		this.workflow = workflow;
	}

	public Parameters getParameters()
	{
		return parameters;
	}

	public void setParameters(Parameters parameters)
	{
		this.parameters = parameters;
	}
}
