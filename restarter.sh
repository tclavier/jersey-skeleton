cd ~/fghfghfgh
v=`git pull | wc -l`

if [ "$v" != 1 ];then
    echo "Restart"
    mvn jetty:stop
    mvn jetty:start
fi

