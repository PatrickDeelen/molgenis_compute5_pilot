#string eQtlConfig "The base eQTL config file. The permutation options will be set to false"
#string metaQtlJar "The jar with the metaqtl software"


onlyPermutationOptionCount=$(grep '<onlypermutations>.*</onlypermutations>' eQtlConfigExample.xml | wc -l)

if [ $onlyPermutationOptionCount -eq 0 ]
then
	echo "Error: onlypermutations option not present in xml file."
	exit -1
elif [ $onlyPermutationOptionCount -ge 2 ]
then
	echo "Error: onlypermutations option present more than once in xml file"
	exit -1
fi


startwithpermutationOptionCount=$(grep '<startwithpermutation>.*</startwithpermutation>' eQtlConfigExample.xml | wc -l)

if [ $startwithpermutationOptionCount -eq 0 ]
then
	echo "Error: startwithpermutation option not present in xml file."
	exit -1
elif [ $startwithpermutationOptionCount -ge 2 ]
then
	echo "Error: startwithpermutation option present more than once in xml file"
	exit -1
fi

permutationsOptionCount=$(grep '<permutations>.*</permutations>' eQtlConfigExample.xml | wc -l)

if [ $permutationsOptionCount -eq 0 ]
then
	echo "Error: permutations option not present in xml file."
	exit -1
elif [ $permutationsOptionCount -ge 2 ]
then
	echo "Error: permutations option present more than once in xml file"
	exit -1
fi

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




resultFolder=$(gawk '

	{
		found = match($0, "<outputdirectory>(.*)</outputdirectory>", groups)
		if(found != 0){
			print groups[1]
		}
	}

' < eQtlConfigExample.xml)

echo "Using result folder: $resultFolder"



currentEqtlConfigXml="$resultFolder/eQtlConfigRealMapping.xml"


cp ${eQtlConfig}  $currentEqtlConfigXml
#check return code of copy
if [ $? -ne 0 ]
then
	echo "Error copying $eQtlConfig to: $currentEqtlConfigXml"
	exit -1
fi


sed -i 's/<onlypermutations>.*<\/onlypermutations>/<onlypermutations>false<\/onlypermutations>/g' $currentEqtlConfigXml
sed -i 's/<startwithpermutation>.*<\/startwithpermutation>/<startwithpermutation>0<\/startwithpermutation>/g' $currentEqtlConfigXml
sed -i 's/<permutations>.*<\/permutations>/<permutations>0<\/permutations>/g' $currentEqtlConfigXml


java -Xms80g -Xmx80g -jar  --mode metaqtl --settings $currentEqtlConfigXml

