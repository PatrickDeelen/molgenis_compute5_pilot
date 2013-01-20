package org.molgenis.compute5.model;

import java.util.HashSet;
import java.util.Set;

import org.molgenis.util.tuple.Tuple;

import com.google.gson.Gson;

/**
 * Generated tasks from steps, with the inputs/outpus prefilled. Includes data dependency
 * graph via previousTasks.
 */
public class Task
{
	public static String TASKID_COLUMN = "taskId";
	
	//unique name of the task
	String name;
	
	//reference to previousTasks (i.e. outputs from previous tasks this task depends on)
	Set<String> previousTasks = new HashSet<String>();
	
	//copy of the local input/outputs used
	Tuple parameters;
	
	//the body of the script (backend independent)
	String script;
	
	public Task(String name)
	{
		this.setName(name);
	} 
	
	public String toString()
	{
		return new Gson().toJson(this);
	}

	// List<TaskDependency> dependencies;
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getScript()
	{
		return script;
	}

	public void setScript(String script)
	{
		this.script = script;
	}

	public Set<String> getPreviousTasks()
	{
		return previousTasks;
	}

	public void setPreviousTasks(Set<String> previousTasks)
	{
		this.previousTasks = previousTasks;
	}

	public Tuple getParameters()
	{
		return parameters;
	}

	public void setParameters(Tuple parameters)
	{
		this.parameters = parameters;
	}
}
