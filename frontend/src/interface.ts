export interface IProduct {
  title: Array<string>
  descList?: Array<string>
  imgName: 'iphone' | 'airpods' | 'tablet',
  theme:{
    fontColor:string,
    bgColor:string
  }
  
}