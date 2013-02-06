package org.molgenis.compute5.test.workflows;

import java.io.IOException;

import org.molgenis.compute5.ComputeCommandLine;
import org.molgenis.compute5.model.Compute;
import org.testng.annotations.Test;

public class eQtlMappingBeta {

	/**
	 * @param args
	 * @throws IOException 
	 */
	@Test
	public void test() throws IOException {
		
		Compute c = ComputeCommandLine.create("workflows/eQtlMappingBeta/workflow.csv", new String[]
				{ "workflows/eQtlMappingBeta/parameters.csv" }, "workflows/eQtlMappingBeta/test/");

	}

}
