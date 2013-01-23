package org.molgenis.compute5.backends.local;

import java.io.IOException;

import org.molgenis.compute5.backends.Backend;

public class LocalBackend extends Backend
{

	public LocalBackend() throws IOException
	{
		super("header.ftl", "footer.ftl", "submit.ftl");
	}

}
