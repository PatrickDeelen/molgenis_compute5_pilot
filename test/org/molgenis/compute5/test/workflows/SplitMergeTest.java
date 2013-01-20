package org.molgenis.compute5.test.workflows;

import java.io.IOException;

import org.molgenis.compute5.ComputeCommandline;
import org.molgenis.compute5.model.Compute;
import org.testng.annotations.Test;

public class SplitMergeTest extends AbstractTest
{
	@Test
	public void test() throws IOException
	{
		Compute c = ComputeCommandline.create("workflows/splitmerge/workflow.csv", new String[]{"workflows/splitmerge/parameters.csv"});
	
		this.test(c);
	}
}
