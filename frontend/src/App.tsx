import React, { useEffect, useState } from "react";
import "./App.css";
interface ListProps {
  url: string,
  id: string,
}
const list = [{
  url: 'http://www.gov.cn/xinwen/2022-06/10/5695134/images/5104bb130a1e411aa11fc404c8d286b8.JPG',
  id: '1',
}, {
  url: 'http://www.gov.cn/xinwen/2022-06/12/5695400/images/fd82dd43dab3420bb14ee21b5f28450a.jpg',
  id: '2',
}, {
  url: 'http://www.gov.cn/xinwen/2022-06/12/5695403/images/33965dd2074f4dc99e0c5586beecf02e.jpg',
  id: '3',
}];

const Carousel = () => {
  const [count, setCount] = useState<number>(0);
  useEffect(() => {
    const timer = setInterval(() => {
      if (count > 1) {
        setCount(0);
      } else {
        setCount(count + 1);
      }
    }, 2000);
    return () => clearInterval(timer);
  }, [count]);

  return (
    <div className="box">
      <div className="icon-container">{list.map((el: ListProps, index: number) => <div className='icon-box' key={el.id}><div className={ count === index ? 'icon-item icon-item-active' : 'icon-item' } key={el.id}></div></div> )}</div>
      <ul className="imgs" style={{ marginLeft: -count * 375 }}>
        {list.map((el: ListProps) => {
          return (
            <li className="item" key={el.id}>
              <img src={el.url} />

            </li>
          );
        })}
      </ul>
    </div>
  );
};
function App() {
  return <div className="App"><Carousel/></div>;
}

export default App;
