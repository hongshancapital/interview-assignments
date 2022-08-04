import React from "react"
import './styles.css'

export default function Poster({ pid } : { pid: number}) {
  return (
    <div className="poster">
      <div className="poster-wrap">
        {pid}
      </div>
    </div>
  )
}