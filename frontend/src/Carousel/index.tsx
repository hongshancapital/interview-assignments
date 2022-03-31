import React from "react";
import "./index.css"

function Carousel() {
  const [leftPos, setLeftPos] = React.useState(0)
  const [progs, setProgs] = React.useState([0,0,0])
  const [dur, setDur] = React.useState('3s')

  React.useEffect(() => {
    setProgs([100,0,0])
    let timer = setInterval(() => {
      setLeftPos(leftPos => {
        if(leftPos >= -100) {
          leftPos -= 100
          if(leftPos >= -100 && leftPos < 0) {
            setProgs([100,100,0])
          } else if(leftPos >= -200 && leftPos < -100) {
            setProgs([100,100,100])
          }
        } else if(leftPos >= -200 && leftPos < -100) {
          setDur('0s')
          leftPos = 0
          setProgs([0,0,0])
          setTimeout(() => {
            setDur('3s')
            setProgs([100,0,0])
          }, 20)
        }
        return leftPos
      })
    }, 3000)
    return () => {
      clearInterval(timer)
    }
  }, [])

  return (
    <div className="main-page">
      <div className="wrap" style={
        {
          left: leftPos + "vw",
          transition: 'all .5s'
        }
      }>
        <div className="iphone">
          <p className="title line-1">xPhone</p>
          <p className="text">Lots to Love.Less to Spend.</p>
          <p className="text">Starting at $399.</p>
        </div>
        <div className="tablet">
          <p className="title line-1">Tablet</p>
          <p className="text">Just the right amount of everything.</p>
        </div>
        <div className="airpods">
          <p className="title line-1">Buy A Tablet or xPhone for college.</p>
          <p className="title">Get arPods.</p>
        </div>
      </div>
      <ul>
        <li>
            <div className="bar" style={{
              width: progs[0] + '%',
              transitionDuration: dur
            }}></div>
        </li>
        <li>
            <div className="bar" style={{
              width: progs[1] + '%',
              transitionDuration: dur
            }}></div>
        </li>
        <li>
            <div className="bar" style={{
              width: progs[2] + '%',
              transitionDuration: dur
            }}></div>
        </li>
      </ul>
    </div>
  )
}

export default Carousel
