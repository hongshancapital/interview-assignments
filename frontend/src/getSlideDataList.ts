import { Slide } from "./types/Slide"
import iphoneImage from './assets/iphone.png'
import airpodsImage from './assets/airpods.png'
import tabletImage from './assets/tablet.png'

export const getSlideDataList=()=>{
  const result:Slide[]=[{
    id: 'iphone',
    title: ['xPhone'],
    content: ['Lots to love. Less to spend.', 'Starting at $399.'],
    image: iphoneImage,
    color: '#ffffff',
    backgroundColor: '#111111'
  },
  {
    id: 'tablet',
    title: ['Tablet'],
    content: ['Just the right amount of everything.'],
    image: tabletImage,
    color: '#000000',
    backgroundColor: '#fafafa'
  },
  {
    id: 'airpods',
    title: ['Buy a Tablet or xPhone for college.', 'Get airPods'],
    content:[],
    image:  airpodsImage,
    color: '#000',
    backgroundColor: '#f1f1f3'
  }]
  return result
}