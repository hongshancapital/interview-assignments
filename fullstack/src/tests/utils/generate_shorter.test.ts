import chai from 'chai';
import {GetNextShorterByCurrent} from "../../utils/generate_shorter";
import {Alphabet, GENERATE_LENGTH, MAINFRAME_CODE} from "../../server";

chai.should();

describe("generate_shorter file test", () => {
    it('should get first shorter', () => {
        const first = GetNextShorterByCurrent("");
        first.length.should.equal(GENERATE_LENGTH);
    });

    it('should get next shorter by current shorter', () => {
        const current = MAINFRAME_CODE + "AAAAAAA";
        const next = GetNextShorterByCurrent(current);
        next.length.should.equal(GENERATE_LENGTH);
        next.should.equal(MAINFRAME_CODE + "AAAAAAB")
    });

    it('should carry over by current shorter', () => {
        const current = MAINFRAME_CODE + "AAAAAA" + Alphabet[Alphabet.length - 1];
        const next = GetNextShorterByCurrent(current);
        next.length.should.equal(GENERATE_LENGTH);
        next.should.equal(MAINFRAME_CODE + "AAAAABA");
    });
});
