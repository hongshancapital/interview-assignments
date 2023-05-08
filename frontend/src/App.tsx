import './App.css';
import Carousel from './components/carousel';
import {BannerProps} from './components/carousel/state'

const options: Array<BannerProps> = [
  {
    element: <div className='banner blank'>
      <div className='h2'>xPhone</div>
      <div className='h5'>Lots to love.Less to spend.</div>
      <div className='h5'>Starting at $399.</div>
      <div className='img'>
        <img src="https://p7.itc.cn/images01/20211018/aa50fb51a7ea46ee90b636a8831fea36.jpeg" />
      </div>
    </div>
  },
  {
    element: <div className='banner white'>
      <div className='h2'>Tablet</div>
      <div className='h5'>just the right amount of everything.</div>
      <div className='img'>
        <img src="https://img1.baidu.com/it/u=2638509734,1137963095&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=667" />
      </div>
    </div>
  },
  {
    element: <div className='banner gray'>
      <div className='h3'>Buy a Tablet or xPhone for collage.</div>
      <div className='h3 top'>Get arPods.</div>
      <div className='img'>
        <img src="https://www.2008php.com/2020_Website_appreciate/2020-03-24/20200324225739tVvIc17prC8M.jpg" />
      </div>
    </div>
  }
]

function App() {
  return <div className='App'>
    <Carousel options={options} height={450} time={5} />
  </div>;
}

export default App;
