DIR="$( cd "$( dirname "<#noparse>${BASH_SOURCE[0]}</#noparse>" )" && pwd )"
touch $DIR/compute.started

<#foreach t in tasks>
echo "--- begin step: ${t.name} ---"
echo " "
sh ${t.name}.sh
echo " "
echo "--- end step: ${t.name} ---"
</#foreach>