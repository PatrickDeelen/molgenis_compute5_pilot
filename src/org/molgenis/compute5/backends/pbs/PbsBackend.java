package org.molgenis.compute5.backends.pbs;

import java.io.IOException;

import org.molgenis.compute5.backends.Backend;

public class PbsBackend extends Backend
{
	public PbsBackend() throws IOException
	{
		super("header.ftl","footer.ftl","submit.ftl");
	}
}
