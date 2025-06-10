import Carousel from '../Carousel';
import Goods from '../Goods';
import iphone from '../../assets/iphone.png';
import tablet from '../../assets/tablet.png';
import airpods from '../../assets/airpods.png';
import './index.css'

const items = [{
  title: ['xPhone'],
  description: ['Lots to love. Less to spend.', 'Starting at $399.'],
  picture: iphone,
  wrapStyles: {
    color: '#fff',
    backgroundColor: '#101010',
    backgroundSize: '800px',
  }
}, {
  title: ['Tablet'],
  description: ['Just the right amount of everything.'],
  picture: tablet,
  wrapStyles: {
    backgroundColor: '#fafafa',
    backgroundSize: '1600px',
  }
}, {
  title: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
  description: [],
  picture: airpods,
  wrapStyles: {
    backgroundColor: '#f2f2f4',
    backgroundSize: '2200px',
  }
}]

export default function Gallery() {
  return (
    <div className='Gallery'>
      <Carousel autoPlay>
        {items.map((item, index) => <Goods {...item} key={index} />)}
      </Carousel>
    </div>
  )
}
