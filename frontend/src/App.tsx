import Carousel from './components/Carousel';

import "./App.css";

export type ImgList = {
  text: JSX.Element,
  pngClassName: string
}[]

function App() {
  const imgList: ImgList = [{
    pngClassName: 'iphone-bg',
    text: (
      <div className="text-box" style={{ color: '#fff' }}>
        <div className="title">xPhone</div>
        <div className="text">Losts to Love. Less to spend.<br />Starting at $399.</div>
      </div>
    ),
  }, {
    pngClassName: 'tablet-bg',
    text: (
      <div className="text-box" style={{ color: '#000' }}>
        <div className="title">Tablet</div>
        <div className="text">Just the Right amount of everything</div>
      </div>
    )
  }, {
    pngClassName: 'airpods-bg',
    text: (
      <div className="text-box" style={{ color: '#000' }}>
        <div className="title">Buy a Tablet of Xphone for college <br />Get arPods.</div>
      </div>
    )
  }]
  return (
    <div className="App">
      <Carousel imgList={imgList} />
    </div>
  );
}

export default App;
