package org.molgenis.compute5.test.workflows;

import java.io.IOException;

import org.molgenis.compute5.ComputeCommandline;
import org.molgenis.compute5.model.Compute;
import org.testng.annotations.Test;

public class NgsWorkflowTest extends AbstractTest
{
	@Test
	public void test() throws IOException
	{
		Compute c = ComputeCommandline.create("workflows/ngs/workflow.csv", new String[]
				{ "workflows/ngs/parameters.csv", "workflows/ngs/constants.csv" });
		
		this.test(c);
	}
}
