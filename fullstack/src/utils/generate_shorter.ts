export const GENERATE_LENGTH = 3;
export const Alphabet: string[] = ["A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
   "S", "T", "U", "V", "W", "X", "Y", "Z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "~", "-", "_"];

export function ShorterListGenerator() {
    let ids: string[] = [];
    pickupChar(ids, 0, "");

    return ids;
}

function pickupChar(ids: string[], depth: number, id: string) {
    for (let c of Alphabet) {
        if (depth === GENERATE_LENGTH - 1) {
            ids.push(id + c);
        } else {
            pickupChar(ids, depth + 1, id + c)
        }
    }
}