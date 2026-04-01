import Carousel from './components/Carousel';
import Page1 from './pages/page1';
import Page2 from './pages/page2';
import Page3 from './pages/page3';

import './App.css';

function App() {
  return (
    <div className='App'>
      <Carousel>
        <Page1/>
        <Page2/>
        <Page3/>
      </Carousel>
      <p>
        完成功能：
        1.轮播自动播放
        2.组件大小自适应
        3.鼠标悬浮暂停离开恢复
      </p>
    </div>
  )
}

export default App;
