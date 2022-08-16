import React from "react"
import { text } from "stream/consumers"
import { useCarousel, useCarouselDispatch } from "../../stores/CarouselContext"
import { ECarouselActionType } from "../../stores/types"
import "./styles.css"

export default function Poster({ pid } : { pid: number}) {
  const { posters } = useCarousel()
  const poster = posters[pid]
  const { descs, pic } = poster
  const picName = require(`../../assets/${pic.name}`)
  const size = (pid + 1) * 50
  const bgSize = `${size}vw`
  return (
    <div className="Poster" style={{ background: poster.bgColor }}>
      <div 
        className="poster-wrap"
        style={{
          backgroundImage: `url(${picName})`,
          backgroundSize: bgSize
        }}
      >
        {
          descs.map(d => {
            return <div className="poster-des" key={d.text} style={d.style}>{d.text}</div>
          })
        }
      </div>
    </div>
  )
}