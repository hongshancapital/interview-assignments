var char_arr:string[] = new Array("0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
            "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")

export function ConvertToBase62String(id: number): string {
    var base62_str:string = ""

    while (id > 0)
    {
        var index:number = id % 62;
        base62_str = char_arr[index] + base62_str
        id = Math.floor(id / 62)
    }

    return base62_str
}