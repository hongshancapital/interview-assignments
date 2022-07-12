import React, { useState } from 'react'
import RotiationItem from './components/RotationItem'
import Progress from './components/Progress'
import './Rotation.scss'

interface Props {
  rotationData: RotationItem[]
  stepTime?: number
}

function Rotiation(props: Props) {
  const { rotationData, stepTime = 3 } = props
  const [activeItem, setActiveItem] = useState(0)
  return (
    <div className="rotation">
      <div
        className="list"
        style={{
          width: `${rotationData.length * 100}vw`,
          left: `-${activeItem * 100}vw`,
        }}
      >
        {rotationData.map((rotation) => {
          return <RotiationItem key={rotation.title} data={rotation} />
        })}
      </div>
      <div className="progress-list">
        {rotationData.length > 1 &&
          rotationData.map((rotation, index) => {
            return (
              <Progress
                key={rotation.title}
                theme={rotation.theme}
                stepTime={stepTime}
                index={index}
                active={index === activeItem}
                onClick={() => setActiveItem(index)}
                handleEnd={() => {
                  setActiveItem((activeItem + 1) % rotationData.length)
                }}
              />
            )
          })}
      </div>
    </div>
  )
}

export default Rotiation
