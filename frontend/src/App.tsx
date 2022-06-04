import React from "react";
import "./App.css";
import Carousel from './components/Carousel'

function App() {
  const data = [
    {
      id: '1',
      title: 'xPhone',
      content: ['Lots to love. Less to spend.', 'Starting at $399.'],
      background: `#111 url(${require('./assets/iphone.png')}) no-repeat center bottom / 50%`,
      color: '#fff',
    },
    {
      id: '2',
      title: 'Tablet',
      content: ['Just the right amount of everything.'],
      color: '#000',
      background: `#fafafa url(${require('./assets/tablet.png')}) no-repeat center bottom / 100%`,
    },
    {
      id: '3',
      title: ['Tablet or XPhone for college.', 'Get arPods.'],
      content: '',
      color: '#000',
      background: `#f1f1f3 url(${require('./assets/airpods.png')}) no-repeat center bottom / 150%`,
    }
  ];
  const onChange = (data: number) => {
    console.log(data, 'get current')
  }
  interface ItemProps {
    title: string[] | string
    content: string[] | string
    color: string
    background: string
  }
  const Item = ({ title = '', content = [], color = '', background = '' }: ItemProps) => {
    const style = {
      color,
      background
    }
    const titles = Array.isArray(title) ? title : [title]
    let contents = Array.isArray(content) ? content : [content]
    return <div className={'item'} style={style}>
      {titles.map((title, ind) => <h3 className="title" key={ind}>{title}</h3>)}
      {contents.map((content, ind) => <p className="text" key={ind}>{content}</p>)}
    </div>
  }
  const contentRender = data.map(item => {
    return <Item
      key={item.id}
      title={item.title}
      content={item.content}
      color={item.color}
      background={item.background}
    />
  })
  return <div className="App">
    <Carousel
      className="container"
      autoPlay={3000}
      dots={null}
      onChange={onChange}
    >
      {contentRender}
    </Carousel>
  </div>;
}

export default App;
