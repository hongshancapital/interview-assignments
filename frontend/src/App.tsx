import React from "react"
import "./App.css"
import Sarousel from './component/carousel/Index'

interface CarouselItem {
    title: string
    info?: string
    icon: string
    theme?: string
}

const carouselItems: CarouselItem[] = [
    {
        title: 'xPhone',
        info: 'Losts to Love.Less to Spend. Starting at $399.',
        icon: require('./assets/iphone.png'),
        theme: 'black'
    },
    {
        title: 'Tablet',
        info: 'Just the right amount of everything',
        icon: require('./assets/tablet.png')
    },
    {
        title: 'Buy a Tablet or xPhone for college. <br/> Get arPods.',
        icon: require('./assets/airpods.png'),
        theme: 'grey'
    }
]

function App() {
    const [stop, setStop] = React.useState<boolean>(false)
  return (
      <div className="App">
        <Sarousel items= {carouselItems} duration= {3000} stop={stop}/>
        <button className="app-button" onClick={()=> setStop(stop => !stop)}>{`${stop? '关闭' : '开启'}手动切换`}</button>
      </div>
  )
}

export default App
