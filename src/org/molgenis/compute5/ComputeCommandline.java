package org.molgenis.compute5;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;
import org.molgenis.compute5.backends.local.LocalBackend;
import org.molgenis.compute5.generators.BackendGenerator;
import org.molgenis.compute5.generators.TaskGenerator;
import org.molgenis.compute5.generators.TasksDiagramGenerator;
import org.molgenis.compute5.generators.WorkflowDiagramGenerator;
import org.molgenis.compute5.model.Compute;
import org.molgenis.compute5.model.Parameters;
import org.molgenis.compute5.model.Task;
import org.molgenis.compute5.model.Workflow;
import org.molgenis.compute5.parsers.ParametersCsvParser;
import org.molgenis.compute5.parsers.WorkflowCsvParser;

/** Commandline program for compute5.
 * Usage: -w workflow.csv -p parameters.csv [-p moreParameters.csv]
 * 
 * NB parameters will be 'natural joined' when overlapping columns.
 */
public class ComputeCommandLine
{
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws ParseException
	{
		// setup commandline options
		Options options = new Options();
		Option w = OptionBuilder.hasArg().isRequired(true).withLongOpt("workflow")
				.withDescription("path to workflow csv").create("w");
		Option p = OptionBuilder.hasArgs().isRequired(true).withLongOpt("parameters")
				.withDescription("path to parameter csv file(s)").create("p");
		Option o = OptionBuilder.hasArg().isRequired(true).withLongOpt("outputDir")
				.withDescription("path to directory this generates to").create("o");
		options.addOption(w);
		options.addOption(p);
		options.addOption(o);

		// parse options
		try
		{
			CommandLineParser parser = new PosixParser();
			CommandLine cmd = parser.parse(options, args);

			String workflowCsv = cmd.getOptionValue("w");
			String[] parametersCsv = cmd.getOptionValues("p");
			String outputdir = cmd.getOptionValue("o");

			System.out.println("### MOLGENIS COMPUTE ###");
			System.out.println("Using workflow:   " + workflowCsv);
			System.out.println("Using parameters: "
					+ Arrays.asList(parametersCsv).toString().replace("[", "").replace("]", ""));
			System.out.println("Using outputDir:   " + outputdir);
			

			System.out.println(""); // newline

			// parse workflow and parameters
			Workflow workflow = WorkflowCsvParser.parse(workflowCsv);
			Parameters parameters = ParametersCsvParser.parse(parametersCsv);

			// generate the tasks
			List<Task> tasks = TaskGenerator.generate(workflow, parameters);
			
			// get the backend
			
			// write the task for the backend
			BackendGenerator.generate(new LocalBackend(), tasks, new File(outputdir));
			
			//generate documentation
			new WorkflowDiagramGenerator().generate(new File(outputdir+"/doc"), workflow);
			new TasksDiagramGenerator().generate(new File(outputdir+"/doc"), tasks);

			System.out.println("Generation complete");

			// output scripts + docs
		}
		catch (Exception e)
		{	
			System.out.println(e.getMessage());
			
			e.printStackTrace();

			System.out.println("");

			new HelpFormatter().printHelp("compute -w workflow.csv -p parameters.csv[ -p parameters2.csv]", options);
		}
	}

	public static Compute create(String workflowCsv, String[] parametersCsv) throws IOException
	{
		Compute compute = new Compute();
		compute.setWorkflow(WorkflowCsvParser.parse(workflowCsv));
		compute.setParameters(ParametersCsvParser.parse(parametersCsv));
		return compute;
	}
}
