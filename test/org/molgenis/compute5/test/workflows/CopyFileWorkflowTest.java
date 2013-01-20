package org.molgenis.compute5.test.workflows;

import java.io.IOException;

import org.molgenis.compute5.ComputeCommandline;
import org.molgenis.compute5.model.Compute;
import org.testng.annotations.Test;

public class CopyFileWorkflowTest extends AbstractTest
{
	@Test
	public void test() throws IOException
	{
		Compute c = ComputeCommandline.create("workflows/copyFile/workflow.csv", new String[]
				{ "workflows/copyFile/parameters.csv", "workflows/copyFile/constants.csv" });
		
		this.test(c);
	}
}
