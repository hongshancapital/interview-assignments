const valueToChar = (value: number): string => {
    if (value > 9) {
        var asciiDec = 55 + value
        if (asciiDec > 90) {
            asciiDec += 6;
        }
        return String.fromCharCode(asciiDec)
    }
    else {
        return value.toString();
    }
}

const valueToString = (value: number): string => {
    var x = value % 62
    var y = Math.floor(value / 62)

    if (y > 0) {
        return valueToString(y) + valueToChar(x)
    } else {
        return valueToChar(x)
    }
}

export const generate = (minLength: number, maxLength: number): string => {

    if (minLength <= 0 || minLength > maxLength) {
        return '-1'
    }

    if (typeof minLength === 'undefined') {
        minLength = 4
        maxLength = 6
    }
    
    if (typeof maxLength === 'undefined') {
        maxLength = minLength
    }

    var minRandom, maxRandom, random

    if (minLength == 1) {
        minRandom = 0
    } else {
        minRandom = Math.pow(62, minLength - 1)
    }

    maxRandom = Math.pow(62, maxLength) - 1
    random = Math.floor(Math.random() * (maxRandom - minRandom) + minRandom)

    return valueToString(random)
}