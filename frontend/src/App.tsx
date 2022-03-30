import React from 'react'
import './App.css'
import Carousel from './components/Carousel/Carousel'

function App () {
  return (
    <div className='App'>
      <Carousel>
        <div key='iphone' style={{ backgroundColor: '#0E0E0E' }}>
          <div>
            <div className='title white'>xPhone</div>
            <div className='text white'>Lots to love. Less to spend.</div>
            <div className='text white'>Starting at $399.</div>
          </div>
          <div>
            <img className='icon' src={require('./assets/iphone_icon.png')} />
          </div>
        </div>
        <div key='tablet' style={{ backgroundColor: '#F9F9F9' }}>
          <div>
            <div className='title'>Tablet</div>
            <div className='text'>Just the right amount of everything.</div>
          </div>
          <div>
            <img className='icon' src={require('./assets/tablet_icon.png')} />
          </div>
        </div>
        <div key='airpods' style={{ backgroundColor: '#EFEFF1' }}>
          <div>
            <div className='title'>
              Buy a Tablet or Phone for college.
              <br />
              Get arPods
            </div>
          </div>
          <div>
            <img className='icon' src={require('./assets/airpods_icon.png')} />
          </div>
        </div>
      </Carousel>
    </div>
  )
}

export default App
