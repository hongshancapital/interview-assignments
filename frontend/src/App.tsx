import React from "react"
import "./App.css"
import { Group as Bar } from "./components/bar/group"
import { Group as Slider } from "./components/slider/group"

const mockConfig = [{
  title: 'xPhone',
  subtitle: 'Lots to love.Less to spend.\nStarting as $399',
  backgroundColor: '#111',
  backgroundImage: require('./assets/iphone.png')
}, {
  title: 'Tablet',
  subtitle: 'Just the right amount of everything.',
  backgroundColor: '#fafafa',
  backgroundImage: require('./assets/tablet.png')
}, {
  title: 'Buy a Tablet or xPhone for college.\nGet arPods',
  subtitle: '',
  backgroundColor: '#f1f1f3',
  backgroundImage: require('./assets/airpods.png')
}]

function App() {
  return (
    <div className="App">
      <Slider config={mockConfig} duration={3000} ></Slider>
      <Bar duration={3000} barCount={3} />
    </div>
  )
}

export default App
