import React from 'react'

function generateSlick(length: number, currentStep: number, duration: number) {
  const styles = { animationDuration: `${duration}ms` }
  return (
    <ul>
      {
        new Array(length).fill(0).map((_, index) => (
          <li
            key={index}
            className={`carousel-lite__dot ${index === currentStep ? 'running' : 'paused'}`}
          >
            <span style={styles} />
          </li>
        ))
      }
    </ul>
  )
}

interface ISlick {
  count: number
  step: number
  duration: number
}

function Slick(_props: ISlick) {
  const slickNodes = generateSlick(_props.count, _props.step, _props.duration)
  return (
    <div className="carousel-lite__dots">
      {
        slickNodes
      }
    </div>
  )
}

export default Slick
