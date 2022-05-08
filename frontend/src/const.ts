
import { IProduct } from './interface'
const productList: Array<IProduct> = [
  {
    title: ['iPhone'],
    descList: ['Lots to love. Less to spend', 'Starting at $399'],
    imgName: 'iphone',
    theme: {
      fontColor: '#FFF',
      bgColor: 'rgb(18, 18, 18)'
    }
  },
  {
    title: ['Tablet'],
    descList: ['Just the right amount of everything'],
    imgName: 'tablet',
    theme: {
      fontColor: '#000',
      bgColor: 'rgb(249, 249, 249)'
    }

  },
  {
    title: ['Buy a Tablet or iPhone for college.', 'Get airPods'],
    imgName: 'airpods',
    theme: {
      fontColor: '#000',
      bgColor: 'rgb(241, 241, 244)'
    }
  },
]
export default productList;