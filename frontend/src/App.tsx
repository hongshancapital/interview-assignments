import React, { useMemo } from "react";
import Carousel from '@/components/Carousel'
import Center from "@/components/Center";
import iphoneImg from '@/assets/iphone.png'
import tabletImg from '@/assets/tablet.png'
import airpodsImg from '@/assets/airpods.png'
import "./App.css";

const dataConfig = [
  {
    titles: ['xPhone'],
    bgColor: 'black',
    color: '#fff',
    subtitles: ['Lots to love.Less to spend.', 'Starting at $399.'],
    sourceImg: iphoneImg
  },
  {
    titles: ['Tablet'],
    bgColor: 'white',
    color: '#000',
    subtitles: ['Just the right amount of everything.'],
    sourceImg: tabletImg
  },
  {
    titles: ['Buy a Tablet or xPhone for college.', 'Get arPods.'],
    bgColor: 'white',
    color: '#000',
    subtitles: [],
    sourceImg: airpodsImg
  },
]

function Contents() {
  return dataConfig.map(config => {
        return (
          <div style={{ width: '100vw', height: '100vh',color: config.color, background: config.bgColor, display: 'flex', flexDirection: 'column' }}>
          <div style={{ fontWeight: 'bold', fontSize: '60px', marginTop: '15%' }}>
            {
              config.titles.map(title => <Center>{title}</Center>)
            }
          </div>
          <div style={{ fontSize: '22px' }}>
            {
              config.subtitles.map(subtitle => <Center>{subtitle}</Center>)
            }
          </div>
          <Center>
            <img alt="" src={config.sourceImg} style={{ width:'80px',position: 'absolute', top: '70%' }} />
          </Center>
          
        </div>
        )
      })
}

function App() {
  const childrensEle = useMemo(() => {
    return Contents()
  }, [])

  return <div className="App">
    <Carousel scrollDirection={"row"} onPageChanged={function (currentIndex: number): void {
      throw new Error("Function not implemented.");
    } } initialPage={0} children={childrensEle} />
  </div>;
}

export default App;
