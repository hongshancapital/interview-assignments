#/bin/bash
set -e
java -Xmx512m -Xms512m -XX:-OmitStackTraceInFastThrow -XX:+PrintGC...

exec "$@"