import {UrlInfo} from '../model';
import {getOriginUrl, createShortUrl} from "../controller";
const fakeUrlInfo = jest.fn();

jest.mock('../model')

const findOne = jest.mocked(UrlInfo.findOne)
describe("getOriginUrl",()=>{
    it('should get exsits url ',async()=>{
        const _doc = {
            _id: 'F1nrXpH',
            origin: 'https://github.com/ladjs/supertest',
          };
          findOne.mockResolvedValue(_doc);
          const obj = await getOriginUrl('F1nrXpH')
          expect(obj).toEqual(_doc)
    })
})
