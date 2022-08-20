import React from "react"
import "./styles.css"
import { initData } from "../stores/data"
/**
 * 轮播图组件，用于显示轮播列表数据中的图片和文案
 * @param posterId 轮播图id {number}  
 */
export default function Poster({ posterId }: { posterId: number }) {
  const { posters } = initData;
  const poster = posters[posterId]
  const { contents, pic } = poster
  const picName = require(`../../assets/${pic.name}`)
  const size = (posterId + 1) * 50
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
          contents.map(content => {
            return <div
              className="poster-content"
              key={content.text}
              style={content.style}>{content.text}</div>
          })
        }
      </div>
    </div>
  )
}