import './App.scss';
import { Carousel } from './components';
import airpodsImg from './assets/airpods.png';
import iphoneImg from './assets/iphone.png';
import tabletImg from './assets/tablet.png';
import { useMemo } from 'react';

const renderInfo = [
  {
    id: 'xPhone',
    color: '#FFFFFF',
    title: ['xPhone'],
    desc: ['Lots to love.Less to spend.', 'Starting at $399'],
    background: iphoneImg
  },
  {
    id: 'Tablet',
    color: '#000000',
    title: ['Tablet'],
    desc: ['Just the right amount of everything'],
    background: tabletImg
  },
  {
    id: 'airPods',
    color: '#000000',
    title: ['Buy a Tablet or xPhone for collage.', 'Get airPods.'],
    background: airpodsImg
  }
];

// 解除插件相关注释，可获取hover暂停、手动翻页功能
// const { createHoverPausePlugin, createDraggablePlugin } = Carousel.plugins;

function App() {
  const plugins = useMemo(()=>[
    // createHoverPausePlugin(), createDraggablePlugin()
  ], []);
  return <div className='App'>
    <Carousel plugins={plugins}>
      {
        renderInfo.map(item=>{
          const { id, color, title = [], desc = [], background } = item;
          return (
            <Carousel.Option key={id}>
              <article className={`billboard ${id}`} style={{ backgroundImage: `url(${background})`, color }}>
                <main className='billboard-main'>
                  <section className='title-area'>
                    {
                      title.map((text, i)=>(<div key={text + i}>{text}</div>))
                    }
                  </section>
                  <section className='desc-area'>
                    {
                      desc.map((text, i)=>(<div key={text + i}>{text}</div>))
                    }
                  </section>
                </main>
              </article>
            </Carousel.Option>
          );
        })
      }
    </Carousel>
  </div>;
}

export default App;
