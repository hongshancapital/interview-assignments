#! /usr/bin/awk -f

BEGIN {
    line = ""
}

{
    processId = 0
    deviceName = ""
    processName = ""
    description = ""
    timeWindow = ""
}


{
    delete a
    split($3, a, ":")
    if (a[1] == "00")
        timeWindow = "0000-0100"
    else if (a[1] == "01")
        timeWindow = "0100-0200"
    ...
    
    for (i = 4; i<=NF; i++) {
        if (i == 4) {
            line = $i
        } else {
            line = line" "$i
        }
    }
    if ((line, timeWindow) in num) {
        num[line, timeWindow] = num[line, timeWindow] + 1
    } else {
        num[line, timeWindow] = 1
    }
}

END {
    print "["
    n = 1
    sum = 0
    for (k in num) {
        processId = 0
        deviceName = ""
        processName = ""
        description = ""
        timeWindow = ""
        c = length(num)
        delete a
        split(k, a, SUBSEP)
	numberOfOccurrence = num[a[1], a[2]]
        timeWindow = a[2]
        delete aa
        split(a[1], aa, " ")
        deviceName = aa[1]
        delete aaa
        split(aa[2], aaa, "]")
        delete aaaa
        split(aaa[1], aaaa, "[")
        processName = aaaa[1]
        processId = aaaa[2]
        for (i = 3; i<=length(aa); i++) {
            gsub(/"/, "\\\"", aa[i])
            if (i == 3) {
                description = aa[i]
            } else {
                description = description" "aa[i]
            }
        }
    	printf "  {\n    \"deviceName\":\"%s\", \n    \"processId\":%d, \n    \"processName\":\"%s\", \n    \"description\":\"%s\", \n    \"timeWindow\":\"%s\", \n    \"numberOfOccurrence\":%d \n  }", deviceName, processId, processName, description, timeWindow, numberOfOccurrence
        sum = sum + numberOfOccurrence}
    print "]"
}
