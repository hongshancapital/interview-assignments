import React from 'react'
import Carousel from './components/Carousel'
import airPods from './assets/airpods2.png'
import iphone from './assets/iphone2.png'
import tablet from './assets/tablet2.png'
import './App.css'

type renderData = {
  title: string
  className: string
  description?: string
  img: string
}
function App() {
  const contentsData: renderData[] = [
    {
      title: 'xPhone',
      className: 'component-cell-1',
      description: 'Lots to love. Less to spend.\nStarting at $399.',
      img: iphone,
    },
    {
      title: 'Tablet',
      className: 'component-cell-2',
      description: 'Just the right amount of everything.',
      img: tablet,
    },
    {
      title: 'Buy a Tablet or xPhone for colleage.\nGet airPods',
      className: 'component-cell-3',
      img: airPods,
    },
  ]
  const getContents = (datas: renderData[]) =>
    datas.map((data) => {
      return (
        <div className={['component-cell', data.className || ''].join(' ')}>
          <div>
            <h1>{data.title}</h1>
            <div>{data.description}</div>
          </div>
          <img className="imgs" src={data.img} width={76} />
        </div>
      )
    })
  return (
    <div className="App">
      <div className="component-container">
        <Carousel contents={getContents(contentsData)} />
      </div>
    </div>
  )
}

export default App
