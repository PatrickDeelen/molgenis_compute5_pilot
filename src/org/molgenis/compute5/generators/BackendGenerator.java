package org.molgenis.compute5.generators;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.molgenis.compute5.backends.Backend;
import org.molgenis.compute5.model.Task;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class BackendGenerator
{
	public static void generate(Backend backend, List<Task> tasks, File targetDir) throws IOException
	{
		Configuration conf = new Configuration();

		// get templates for header and footer
		Template header = new Template("header", new StringReader(backend.getHeaderTemplate()), conf);
		Template footer = new Template("header", new StringReader(backend.getFooterTemplate()), conf);
		Template submit = new Template("submit", new StringReader(backend.getSubmitTemplate()), conf);

		// generate the submit script
		try
		{
			File outFile = new File(targetDir.getAbsolutePath() + "/submit.sh");
			Writer out = new BufferedWriter(new FileWriter(outFile));
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("tasks", tasks);

			submit.process(map, out);
			System.out.println("Generated " + outFile);
		}
		catch (TemplateException e)
		{
			throw new IOException("Backend generation failed for " + backend.getClass().getSimpleName());
		}
		
		// generate the tasks scripts
		for (Task task : tasks)
		{
			try
			{
				File outFile = new File(targetDir.getAbsolutePath() + "/" + task.getName() + ".sh");
				Writer out = new BufferedWriter(new FileWriter(outFile));

				header.process(task.getParameters(), out);

				out.write("\n" + task.getScript() + "\n");

				footer.process(task.getParameters(), out);

				out.close();

				System.out.println("Generated " + outFile);
			}
			catch (TemplateException e)
			{
				throw new IOException("Backend generation of task '" + task.getName() + "' failed for "
						+ backend.getClass().getSimpleName() + ": " + e.getMessage());
			}
		}

	}
}
