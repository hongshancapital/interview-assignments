import 'should';
import { generate } from '../untils/shortIdGen.js';

describe('获取短链接path',function(){
    it('输出端链接path',function(){
        generate(1,8).should.be.not.empty()

    });
})