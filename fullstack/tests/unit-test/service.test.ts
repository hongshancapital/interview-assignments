import { dataSource } from '../../src/data/datasource'
import { getShortId, getUrlByShortId } from '../../src/service';
import { MockShortUriEntity } from '../helpers/mockConstant';

test('test getShortId', async () => {
   const spy = jest.spyOn(dataSource, 'getRepository').mockImplementation(() => {
      return {
         save: jest.fn().mockResolvedValue(MockShortUriEntity),
         findOneBy: jest.fn().mockResolvedValue(MockShortUriEntity)
      } as any
   })
   expect((await getShortId(MockShortUriEntity.url)).length).not.toEqual(0);
   
   spy.mockImplementation(() => {
      return {
         save: jest.fn().mockResolvedValue(MockShortUriEntity),
         findOneBy: jest.fn().mockResolvedValue(null)
      } as any
   })
   expect((await getShortId(MockShortUriEntity.url)).length).not.toEqual(0);

   spy.mockImplementation(() => {
      return {
         save: jest.fn().mockResolvedValue(MockShortUriEntity),
         findOneBy: jest.fn().mockRejectedValue(null)
      } as any
   })
   expect((await getShortId(MockShortUriEntity.url)).length).not.toEqual(0);
   

   spy.mockImplementationOnce(() => {
      return {
         findOneBy: jest.fn().mockResolvedValue(null)
      } as any
   }).mockImplementationOnce(() => {
      return {
         save: jest.fn().mockRejectedValue(null),
      } as any
   }).mockImplementationOnce(() => {
      return {
         save: jest.fn().mockResolvedValue(MockShortUriEntity),
      } as any
   })
   expect((await getShortId(MockShortUriEntity.url)).length).not.toEqual(0);

   spy.mockRestore()
})

test('test getUrlByShortId', async () => {
   const spy = jest.spyOn(dataSource, 'getRepository').mockImplementationOnce(() => {
      return {
         save: jest.fn().mockResolvedValue(MockShortUriEntity),
         findOneBy: jest.fn().mockResolvedValue(null)
      } as any
   }).mockImplementationOnce(() => {
      return {
         save: jest.fn().mockResolvedValue(MockShortUriEntity),
         findOneBy: jest.fn().mockRejectedValue(null)
      } as any
   }).mockImplementationOnce(() => {
      return {
         save: jest.fn().mockResolvedValue(MockShortUriEntity),
         findOneBy: jest.fn().mockResolvedValue(MockShortUriEntity)
      } as any
   })
   expect((await getUrlByShortId(MockShortUriEntity.shortId))).toEqual(null)
   expect((await getUrlByShortId(MockShortUriEntity.shortId))).toEqual(null)
   expect((await getUrlByShortId(MockShortUriEntity.shortId))).toEqual(MockShortUriEntity.url)
   spy.mockRestore()
})