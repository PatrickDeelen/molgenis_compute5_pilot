



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

permutationsOptionCount=$(grep '<startwithpermutation>.*</startwithpermutation>' eQtlConfigExample.xml | wc -l)

if [ $permutationsOptionCount -eq 0 ]
then
	echo "Error: permutations option not present in xml file."
	exit -1
elif [ $permutationsOptionCount -ge 2 ]
then
	echo "Error: permutations option present more than once in xml file"
	exit -1
fi

cp ${eQtlConfig} ${realEqtlMappingConfig}
#check return code of copy
if [ $? -ne 0 ]
then
	echo "Error copying ${eQtlConfig} to: ${realEqtlMappingConfig}"
	exit -1
fi


sed -i 's/<onlypermutations>.*<\/onlypermutations>/<onlypermutations>false<\/onlypermutations>/g' ${realEqtlMappingConfig}
sed -i 's/<startwithpermutation>.*<\/startwithpermutation>/<startwithpermutation>0<\/startwithpermutation>/g' ${realEqtlMappingConfig}
sed -i 's/<startwithpermutation>.*<\/startwithpermutation>/<startwithpermutation>0<\/startwithpermutation>/g' ${realEqtlMappingConfig}




