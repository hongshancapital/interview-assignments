import Carousel from './components/Carousel'
import iPhone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import airPods from './assets/airpods.png'
import './App.css';

function App() {
  const data = [
    {
      serviceUrl: iPhone, // 图片url
      title: ['xPhone'], // 标题
      desc: ['Lots to love.Less to spend.','Starting at $399.'],
      color: "#ffffff"
    },
    {
      serviceUrl: tablet, // 图片url
      title: ['Tablet'], // 标题
      desc: ['Just the right amount of everything.'],
      color: "#000000"
    },
    {
      serviceUrl: airPods, // 图片url
      title: ['Buy a Tablet or xPhone for college Get.','Get arPods.'], // 标题
      color: "#000000"
    },
  ]
  return <div className='App'>
      {/* write your component here */}
      <Carousel data={data}/>
    </div>;
}

export default App;
