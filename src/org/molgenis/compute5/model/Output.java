package org.molgenis.compute5.model;

/** Output definition. The value can (and often is) a freemarker template*/
public class Output
{
	//unique name within a protocol
	private String name;
	
	//value, can be a freemarker template
	private String value;

	public Output(String name)
	{
		this.setName(name);
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		if (Protocol.reservedNames.contains(name)) throw new RuntimeException("output name cannot be '" + name
				+ "' because it is a reserved word");
		this.name = name;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}
