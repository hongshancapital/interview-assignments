import React from "react"
import { text } from "stream/consumers"
import { useCarousel, useCarouselDispatch } from "../../store/AppContext"
import { ECarouselActionType } from "../../store/types"
import "./styles.css"

export default function Poster({ pid } : { pid: number}) {
  const { posters } = useCarousel()
  const poster = posters[pid]
  const { descs } = poster
  return (
    <div className="poster" style={{background:poster.bgColor}}>
      <div className="poster-wrap">
        {
          descs.map(d => {
            return <div className="poster-des" key={d.text} style={d.style}>{d.text}</div>
          })
        }
        <div className="poster-pic"></div>
      </div>
    </div>
  )
}