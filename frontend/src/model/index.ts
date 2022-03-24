import iphoneImg from '../assets/img/iphone.png'
import tabletImg from '../assets/img/tablet.png'
import airpodsImg from '../assets/img/airpods.png'
import { ICarouselItemProps } from '../components/CarouselItem'

export const mockData: ICarouselItemProps[] = [
  {
    key: 'iphone',
    header: ['xPhone'],
    content: ['Lots to love. Less to spend.', 'Starting at $399'],
    imageSrc: iphoneImg,
    theme: 'dark',
  },
  {
    key: 'tablet',
    header: ['Tablet'],
    content: ['Just the right amount of everything'],
    imageSrc: tabletImg,
    theme: 'light',
  },
  {
    key: 'airpods',
    header: ['Buy a Tablet or xPhone for college.', 'Get airPods'],
    imageSrc: airpodsImg,
    theme: 'gray',
  },
]
