import  airpods  from '../assets/airpods.png'
import  iphone  from '../assets/iphone.png'
import  tablet  from '../assets/tablet.png'
import  { ImageInfo } from './ImageInfo'


const bannerList: ImageInfo[] =[ {
    key: 1,
    title: "xPhone",
    subTitle: "Lots to love.Less to spend.<br/>Starting at $399.",
    src: iphone,
    color: "rgb(255, 255, 255)",
  },
  {
    key: 2,
    title: "Tablet",
    subTitle: "Just the right amount of everything.",
    src: airpods,
    color: "rgb(0, 0, 0)",
  },
  {
    key: 3,
    title: "Buy a Tablet or xPhone for college.<br/> Get arPods.",
    subTitle: "",
    src: tablet,
    color: "rgb(0, 0, 0)",
  }]

  export default  bannerList