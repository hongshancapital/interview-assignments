var global_id = 100000000

export function GetUniqID(): number {
    return global_id++;
}