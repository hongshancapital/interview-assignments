var index_map:Map<string, number> = new Map([["0", 0], ["1", 1], ["2", 2], ["3", 3], ["4", 4], ["5", 5], ["6", 6], ["7", 7], ["8", 8], ["9", 9],
                ["a", 10], ["b", 11], ["c", 12], ["d", 13], ["e", 14], ["f", 15], ["g", 16], ["h", 17], ["i", 18], ["j", 19], ["k", 20], ["l", 21], ["m", 22], ["n", 23], ["o", 24], ["p", 25], ["q", 26], ["r", 27], ["s", 28], ["t", 29], ["u", 30], ["v", 31], ["w", 32], ["x", 33], ["y", 34], ["z", 35],
                ["A", 36], ["B", 37], ["C", 38], ["D", 39], ["E", 40], ["F", 41], ["G", 42], ["H", 43], ["I", 44], ["J", 45], ["K", 46], ["L", 47], ["M", 48], ["N", 49], ["O", 50], ["P", 51], ["Q", 52], ["R", 53], ["S", 54], ["T", 55], ["U", 56], ["V", 57], ["W", 58], ["X", 59], ["Y", 60], ["Z", 61]])

var global_db_arr:Map<string, string>[] = new Array(62)
for (var i = 0; i < global_db_arr.length; i++) {
    global_db_arr[i] = new Map()
}

function LoadBalancer(key:string): Map<string, string> {
    if (key.length == 0) {
        return null
    }
    var index_str:string = key.charAt(0)
    if (index_map.has(index_str)) {
        return global_db_arr[index_map.get(index_str)]
    }
    return null
}

export function DBSet(key:string, val:string) {
    var db:Map<string, string> = LoadBalancer(key)
    if (db != null) {
        console.log("my key", key)
        db.set(key, val)
    }
}

export function DBGet(key:string): string {
    var val:string = ""
    var db:Map<string, string> = LoadBalancer(key)
    if (db != null) {
        val = db.get(key)
    }
    return val
}