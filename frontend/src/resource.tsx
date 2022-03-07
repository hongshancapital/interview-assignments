import type { RenderData } from './components/Carousel/Demo'

import airpods from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'

/**
 * 不知具体数据格式，这里按照自己自定义了一份
 */
export const data: RenderData[] = [
  {
    id: 1,
    backgroundColor: '#111111',
    textColor: '#fff',
    backgroundImage: iphone,
    title: 'xPhone',
    subTitle: 'Lots to love. Less to spend.\n\rStarting at $399.'
  },
  {
    id: 2,
    backgroundColor: '#FAFAFA',
    textColor: '#000',
    backgroundImage: tablet,
    title: 'Tablet',
    subTitle: 'Just the right amount of everything.'
  },
  {
    id: 3,
    backgroundColor: '#F1F1F1',
    textColor: '#000',
    backgroundImage: airpods,
    title: 'Buy a Tablet or xPhone for Collerge.\nGet arPords.'
  }
]