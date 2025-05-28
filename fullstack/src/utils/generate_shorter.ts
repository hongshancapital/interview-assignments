import {Alphabet, GENERATE_LENGTH, MAINFRAME_CODE} from "../server";
import assert from "assert";

function pickUpCarryOver(coord: number[], start: number) {
    if (coord[start] < Alphabet.length - 1) {
        coord[start] += 1;
        for (let i = start + 1; i <= GENERATE_LENGTH - MAINFRAME_CODE.length - 1; i++) {
            coord[i] = 0;
        }
    } else {
        pickUpCarryOver(coord, start - 1)
    }
}

export function GetNextShorterByCurrent(currentName: string) {
    if (!currentName || currentName === "") {
        let generate = "";
        for (let i = 0; i < GENERATE_LENGTH - MAINFRAME_CODE.length; i++) {
            generate += Alphabet[0];
        }

        return MAINFRAME_CODE + generate;
    }

    // 将当前的短域名解析为“座标”数组,并断言一个很难发生的边界
    let coord : number[] = []
    let pureName = currentName.substring(MAINFRAME_CODE.length);
    let sum = 0;
    for (let i of pureName) {
        const pos = Alphabet.indexOf(i);
        coord.push(pos);
        sum += pos;
    }
    assert(sum < coord.length * (Alphabet.length - 1))

    // 通过座标数组的“进位”算法，获取下一个短域名的座标
    pickUpCarryOver(coord, pureName.length - 1);

    // 翻译座标为实际的短域名
    let next = "";
    for (let i of coord) {
        next += Alphabet[i];
    }

    return MAINFRAME_CODE + next;
}