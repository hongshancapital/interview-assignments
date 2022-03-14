import React from 'react'
import Carousel from './components/Carousel'
import airPods from './assets/airpods2.png'
import iphone from './assets/iphone2.png'
import tablet from './assets/tablet2.png'
import './App.css'

function App() {
  const contents: React.ReactNode[] = []
  contents.push(
    <div className="component-cell component-cell-1">
      <div>
        <h1>xPhone</h1>
        <div>Lots to love. Less to spend.</div>
        <div>Starting at $399.</div>
      </div>
      <img className="imgs" src={iphone} width={76} />
    </div>,
  )
  contents.push(
    <div className="component-cell component-cell-2">
      <div>
        <h1>Tablet</h1>
        <div>Just the right amount of everything.</div>
      </div>
      <img className="imgs" src={tablet} width={76} />
    </div>,
  )
  contents.push(
    <div className="component-cell component-cell-3">
      <h1>
        Buy a Tablet or xPhone for colleage.
        <br />
        Get airPods
      </h1>
      <img className="imgs" src={airPods} width={76} />
    </div>,
  )
  return (
    <div className="App">
      <div className="component-container">
        <Carousel contents={contents} />
      </div>
    </div>
  )
}

export default App
