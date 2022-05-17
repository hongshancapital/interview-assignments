import React from 'react'

import Carousel from './components/Carousel'
import phoneImg from '../src/assets/iphone.png'
import tabletImg from '../src/assets/tablet.png'
import airpodsImg from '../src/assets/airpods.png'
import './App.css'

function App() {
  const imgList = [
    {
      url: phoneImg, 
      title: <p>xPhone</p>, description: <><p>Lots to love. less to spend</p><p>Starting at $399</p></>,
      color: '#fff'
    }, 
    {
      url: tabletImg, 
      title: <p>Tablet</p>, 
      description: 'Just the right amount of everything.',
      color: '#000'
    }, 
    {
      url: airpodsImg, 
      title: <><p>Buy a Tablet or xPhone for college.</p><p>Get airPods.</p></>,
      color: '#000'
    }
  ]

  return (
    <div className='App'>
      <Carousel>
        {imgList.map((item, index) => {
          return <Carousel.Item key={index}>
            <div className="carousel__item-content" style={{backgroundImage: `url(${item.url})`, color: item.color}}>
              <h3>{item.title}</h3>
              <div>{item.description}</div>
            </div>  
          </Carousel.Item>  
        })}
      </Carousel>
    </div>
  )
}

export default App
