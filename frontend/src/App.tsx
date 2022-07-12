import React from 'react'
import './App.css'
import Rotation from './Rotation'
import airpods from './assets/airpods.png'
import iphone from './assets/iphone.png'
import tablet from './assets/tablet.png'

enum Theme {
  dark = 'dark',
  light = 'light',
}

function App() {
  const rotationData: RotationItem[] = [
    {
      title: 'xPhone',
      subTitle: `Lots to love.Less to pend.<br/>Starting at $399.`,
      image: iphone,
      theme: Theme.dark,
      color: '#111111',
    },
    {
      title: `Tablet`,
      subTitle: `Just the right amount of everything.`,
      image: tablet,
      theme: Theme.light,
      color: '#FAFAFA',
    },
    {
      title: `Buy a Tablet or xPhone for collage.<br/>Get arPods.`,
      image: airpods,
      theme: Theme.light,
      color: '#F1F1F3',
    },
  ]
  return (
    <div className="App" title="learn react">
      {<Rotation rotationData={rotationData} stepTime={5} />}
    </div>
  )
}

export default App
