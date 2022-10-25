/*
 * @Author: NoManPlay
 * @Date: 2022-10-09 10:51:48
 * @LastEditors: NoManPlay
 * @LastEditTime: 2022-10-09 17:14:10
 * @Description:
 */
import React from 'react'
import './App.css'
import {Carousel, CarouselItem} from './components'
import {data} from './mocks'

function App() {
  return (
    <div className="App">
      <Carousel>
        {data.map((item, index) => (
          <CarouselItem key={index} {...item} />
        ))}
      </Carousel>
    </div>
  )
}

export default App
