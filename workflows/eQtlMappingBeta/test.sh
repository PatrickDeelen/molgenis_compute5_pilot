outputdirectoryOptionCount=$(grep '<outputdirectory>.*</outputdirectory>' eQtlConfigExample.xml | wc -l)

if [ $outputdirectoryOptionCount -eq 0 ]
then
	echo "Error: outputdirectory option not present in xml file."
	exit -1
elif [ $outputdirectoryOptionCount -ge 2 ]
then
	echo "Error: outputdirectory option present more than once in xml file"
	exit -1
fi

echo "outputdirectory count: $outputdirectoryOptionCount"

resultFolder=$(gawk '

	{
		found = match($0, "<outputdirectory>(.*)</outputdirectory>", groups)
		if(found != 0){
			print groups[1]
		}
	}

' < eQtlConfigExample.xml)
echo "Using result folder: $resultFolder"

