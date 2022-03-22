import React, { useEffect, useState } from "react";
import Carousel from './Carousel';
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'
import airpods from './assets/airpods.png'
import "./App.css";

// 获取屏幕宽高
const [winWidth, winHeight] = [window.innerWidth, window.innerHeight]

// 假数据，真实情况下可以发起ajax请求
const contents = [
  { id: 1, title: "iphone", text: "iphone xxx", color: "white", imageSrc: iphone, },
  { id: 2, title: "tablet", text: "tablet xxx", color: "black", imageSrc: tablet },
  { id: 3, title: "airpods", text: "airpods xxx", color: "black", imageSrc: airpods }
]

// 定义数据类型
interface IContent {
  id: number,
  title: string,
  text: string,
  color: string,
  imageSrc: string
}

function App() {

  // 根据page和list刷新视图
  const [page, setPage] = useState(0)
  const [list, setList] = useState<Array<IContent>>([])

  // 模拟网络请求
  useEffect(() => {
    setTimeout(() => {
      setList(contents)
    }, 500);
  }, [])

  // 可以封装成loading组件
  if (!list.length) {
    return (
      <div className="loading">加载中...</div>
    )
  }

  // 轮播图组件
  return (
    <Carousel width={winWidth} height={winHeight} onChange={(index: number) => setPage(index)}>
      {list.map(item =>
        <div key={item.id} className="App">
          <div className="title" style={{ color: item.color }}>{item.title}</div>
          <div className="text" style={{ color: item.color }}>{item.text}</div>
          <img className="image" src={item.imageSrc}></img>
          <div className="bar">
            {list.map((item, index) =>
              <div key={index} className="progress" style={{ backgroundColor: page === index ? 'green' : 'grey' }}></div>
            )}
          </div>
        </div>
      )
      }
    </Carousel >
  )
}

export default App;
