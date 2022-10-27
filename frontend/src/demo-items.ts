// carousel demo items
import {type Item} from './Carousel'
import iphoneUrl from '../src/assets/iphone.png'
import tabletUrl from '../src/assets/tablet.png'
import airpodsUrl from '../src/assets/airpods.png'

const items: Item[]=[
  {
    title: 'xPhone',
    desc: 'Lots to love.Less to spend.',
    desc2: 'Starting at $399.',
    thumbnail: iphoneUrl,
    bgColor: '#111',
    color: 'white'
  },
  {
    title: 'Tablet',
    desc: 'Just the right amount of everything.',
    thumbnail: tabletUrl,
    bgColor: '#FAFAFA'
  },
  {
    title: 'Buy a Tablet or a xPhone for college.',
    desc: 'Get arPods.',
    descSize: 'large',
    thumbnail: airpodsUrl,
    bgColor: '#F1F1F3'
  }
]

export default items