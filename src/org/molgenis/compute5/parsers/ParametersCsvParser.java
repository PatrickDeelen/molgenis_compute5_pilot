package org.molgenis.compute5.parsers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.molgenis.compute5.model.Parameters;
import org.molgenis.io.csv.CsvReader;
import org.molgenis.util.tuple.KeyValueTuple;
import org.molgenis.util.tuple.Tuple;
import org.molgenis.util.tuple.WritableTuple;

/** Parser for parameters csv file(s). Includes the solving of templated values.*/
public class ParametersCsvParser
{

	public static Parameters parse(File... files) throws IOException
	{
		try
		{
			final List<String> parameters = new ArrayList<String>();
			final List<WritableTuple> values = new ArrayList<WritableTuple>();

			if (files.length == 0) throw new IOException("Parameters.parse expects at least one file");

			// first file, just read
			for (Tuple t : new CsvReader(files[0]))
			{
				KeyValueTuple value = new KeyValueTuple();
				value.set(t);
				values.add(value);
			}

			// other files, join or combine
			for (int i = 1; i < files.length; i++)
			{
				File f = files[i];
				boolean first = true;

				// remember fields for natural join, if any
				final List<String> joinFields = new ArrayList<String>();

				// if join, we create combinations in this new list
				final List<WritableTuple> newValues = new ArrayList<WritableTuple>();

				for (Tuple t : new CsvReader(f))
				{
					if (first)
					{
						for (String col : t.getColNames())
						{
							// check for natural join
							if (parameters.contains(col))
							{
								joinFields.add(col);
							}
							// verify no '.' in name
							if (col.contains("."))
							{
								throw new IOException("parsing " + f.getName()
										+ " failed: column names cannot contain '.'");
							}

							parameters.add(col);
						}

						first = false;
					}

					if (joinFields.size() > 0)
					{
						// remember joined, if fails union
						boolean joined = false;
						for (WritableTuple original : values)
						{

							// check for join
							boolean join = true;

							for (String col : joinFields)
							{
								if (original.isNull(col) || !original.get(col).equals(t.get(col)))
								{
									join = false;
								}
							}

							// join
							if (join)
							{
								KeyValueTuple newValue = new KeyValueTuple(original);
								newValue.set(t);
								newValues.add(newValue);
								joined = true;
							}
						}
						if (!joined)
						{
							// join failed, added at the end
							// TODO should we warn?
							values.add(new KeyValueTuple(t));
						}
					}
					else
					{
						for (Tuple original : values)
						{
							WritableTuple newValue = new KeyValueTuple(original);
							newValue.set(t);
							newValues.add(newValue);
						}
					}

					if (newValues.size() > 0)
					{
						values.clear();
						values.addAll(newValues);
					}
				}
			}

			// solve the templates
			TupleUtils.solve(values);

			// mark all columns as 'user.*'
			List<WritableTuple> result = new ArrayList<WritableTuple>();
			for (Tuple v : values)
			{
				KeyValueTuple t = new KeyValueTuple();
				for (String col : v.getColNames())
				{
					t.set("user." + col, v.get(col));
				}
				result.add(t);
			}

			// finaly, add row ids
			int count = 0;
			for (WritableTuple value : result)
			{
				value.set(Parameters.ID_COLUMN, count++);
			}

			Parameters p = new Parameters();
			p.setValues(result);
			return p;
		}
		catch (IOException e)
		{
			throw new IOException("Parsing of parameters csv failed: " + e.getMessage());
		}
	}

	public static Parameters parse(String... strings) throws IOException
	{
		File[] files = new File[strings.length];
		for (int i = 0; i < strings.length; i++)
			files[i] = new File(strings[i]);
		return parse(files);
	}
}