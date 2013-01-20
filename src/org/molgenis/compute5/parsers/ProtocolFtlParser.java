package org.molgenis.compute5.parsers;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.molgenis.compute5.model.Input;
import org.molgenis.compute5.model.Output;
import org.molgenis.compute5.model.Protocol;

/**
 * Parser for protocol ftl file
 */
//FIXME: add parsing for cores, mem, etc
public class ProtocolFtlParser
{

	/**
	 * 
	 * @param workflowDir
	 *            , used as primary search path. If missing it uses runtime
	 *            path/absolute path
	 * @param protocolFile
	 * @return
	 * @throws IOException
	 */
	public static Protocol parse(File workflowDir, String protocolFile) throws IOException
	{
		try
		{
			// first test path within workflowDir
			File templateFile = new File(workflowDir.getAbsolutePath() + "/" + protocolFile);
			if (!templateFile.exists())
			{
				templateFile = new File(protocolFile);
				if (!templateFile.exists()) throw new IOException("protocol '" + protocolFile + "' cannot be found");
			}

			// start reading
			BufferedReader reader = new BufferedReader(new FileReader(templateFile));
			Protocol p = new Protocol(protocolFile);

			// need to harvest all lines that start with #
			// need to harvest all other lines
			String line;
			String description = "";
			String template = "";
			while ((line = reader.readLine()) != null)
			{

				if (line.startsWith("#"))
				{
					// remove #, trim spaces, then split on " "
					line = line.substring(1).trim();
					String[] els = line.split(" ");

					if (els.length > 0)
					{
						if (els[0].equals("MOLGENIS"))
						{
							// todo
						}
						// description?
						else if (els[0].equals("description") && els.length > 1)
						{
							// add all elements
							for (int i = 1; i < els.length; i++)
							{
								description += els[i] + " ";
							}
							description += "\n";
						}

						// input
						else if (els[0].equals("input") || els[0].equals("string") || els[0].equals("list"))
						{
							// assume name column
							if (els.length < 2) throw new IOException("param requires 'name', e.g. '#param input1'");

							Input input = new Input(els[1]);
							
							input.setType(els[0]);

							// description is everything else
							String inputDescription = "";
							for (int i = 2; i < els.length; i++)
							{
								inputDescription += " " + els[i];
							}
							if (inputDescription.length() > 0) input.setDescription(inputDescription.trim());

							p.getInputs().add(input);
						}

						// MOLGENIS
						else if (els[0].equals("MOLGENIS"))
						{
							// TODO
						}

						// output
						else if (els[0].equals("output"))
						{
							if (els.length < 2) throw new IOException("output requires 'name', e.g. '#output output1'");
							if (els.length < 3) throw new IOException(
									"output requires 'output', e.g. '#output output1 ${input1}'");

							Output o = new Output(els[1]);
							o.setValue(els[2]);
							//allow spaces in the value (risky???)
							for(int i = 3; i < els.length; i ++) o.setValue(o.getValue()+" "+els[i]);
							p.getOutputs().add(o);
						}

						// otherwise we don't understand
						else
						{
							throw new IOException("# " + els[0] + " is unknown annotation");
						}

					}
				}

				// otherwise just add to template
				else
				{
					template += line + "\n";
				}

			}
			p.setDescription(description);
			p.setTemplate(template);
			return p;
		}
		catch (Exception e)
		{
			throw new IOException("Parsing of protocol "+protocolFile+" failed: "+e.getMessage());
		}

	}
}