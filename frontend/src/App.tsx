import './App.css';
import {Cacousel, Item} from './Cacousel'

import imgUrl1 from './assets/iphone.png'
import imgUrl2 from './assets/tablet.png'
import imgUrl3 from './assets/airpods.png'
// 模拟数据
const data = [
  {
    title: 'xPhone',
    dec: 'Lots to love.Less to spend. \n\r Starting at $399',
    imgUrl: imgUrl1,
    ind: 0,
  },
  {
    title: 'Tablet',
    dec: 'Just the right amount of everything',
    imgUrl: imgUrl2,
    ind: 1,
  },
  {
    title: 'Buy a Tablet xPhone for college. \n\r Get airPods.',
    dec: '',
    imgUrl: imgUrl3,
    ind: 2,
  }
]

function App() {
  return <div className='App'>
     <div style={{width: "100vw", height: "100vh"}}>
        <Cacousel count={data.length} delay={2000}>
          {data.map((item)=><Item key={Math.random()} {...item}/>)}
        </Cacousel>
      </div>
  </div>;
}

export default App;
