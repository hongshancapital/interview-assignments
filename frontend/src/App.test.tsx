import React from 'react';
import { render } from '@testing-library/react';
import App from './App';
import Carousel from './Carousel';

const [winWidth, winHeight] = [window.innerWidth, window.innerHeight]
const contents = [
  { id: 1, title: "iphone", text: "iphone xxx", color: "white", imageSrc: '', },
  { id: 2, title: "tablet", text: "tablet xxx", color: "black", imageSrc: '' },
  { id: 3, title: "airpods", text: "airpods xxx", color: "black", imageSrc: '' }
]

test('测试Carousel组件能够正常渲染', () => {
  const wrapper = render(
    <Carousel width={winWidth} height={winHeight} onChange={(index: number) => { }}>
      {contents.map(item => {
        return <div key={item.id}>{item.title}</div>
      })}
    </Carousel>
  )
  const el = wrapper.queryByText('iphone')
  expect(el).toBeTruthy()
})