/**
 * @description 模拟数据 
 */
import IphonePic from '../assets/iphone.png';
import TabletPic from '../assets/tablet.png';
import AirpodsPic from '../assets/airpods.png';
import { CarouselItemFace } from '../components/carousel/type/fullScreen/index';

export const CAROUSEL_LIST_DATA: CarouselItemFace[] = [
  {
    title: 'xPhone',
    bgImg: IphonePic,
    fontColor: '#fff',
    bgColor: '#101010',
    desc: 'Lots to love.Less to spend. \n Starting at $399.'
  },
  {
    title: 'Tablet',
    bgImg: TabletPic,
    fontColor: '#000',
    bgColor: '#fafafa',
    desc: 'Just the right amount of everything.'
  },
  {
    bgImg: AirpodsPic,
    fontColor: '#000',
    bgColor: '#f2f2f4',
    title: 'Buy a Tablet or a xPhone for college.\nGet arPods.'
  }
];