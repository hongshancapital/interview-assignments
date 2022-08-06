import React from "react"
import { text } from "stream/consumers"
import { useCarousel, useCarouselDispatch } from "../../store/AppContext"
import { ECarouselActionType } from "../../store/types"
import "./styles.css"

export default function Poster({ pid } : { pid: number}) {
  const { posters } = useCarousel()
  const poster = posters[pid]
  const { descs, pic } = poster
  return (
    <div className="Poster" style={{background:poster.bgColor}}>
      <div className="poster-wrap">
        {
          descs.map(d => {
            return <div className="poster-des" key={d.text} style={d.style}>{d.text}</div>
          })
        }
        {/* <img 
          className="poster-pic" 
          src={require(`../../assets/${pic.name}`)}
          style={{ width: pic.width, height: pic.height }}
        /> */}
      </div>
    </div>
  )
}