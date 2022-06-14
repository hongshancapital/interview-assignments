import React, { useState, useEffect } from "react"
import { ShufflingProps } from "./types"
import "./index.css"

function Shuffling(props: ShufflingProps) {
  const { data, intervalTime } = props
  const [curIndex, setCurIndex] = useState(0)
  const [percent, setPercent] = useState(0)
  const reAnimate = (index: number) => {
    setPercent(0)
    setCurIndex(index)
  }

  const getItemOffset = (itemIndex: number, targetIndex: number) => {
    return `${(itemIndex - targetIndex) * 100}vw`
  }

  const start = () => {
    setPercent(0)
    if (curIndex < data.length - 1) {
      setCurIndex(curIndex + 1)
    } else {
      setCurIndex(0)
    }
  }

  useEffect(() => {
    const timer = setInterval(() => {
      start()
    }, intervalTime)
    const percentTimer = setInterval(() => {
      setPercent((per) => {
        const newPrecent = per + (16.6 / intervalTime) * 100
        return newPrecent > 100 ? 100 : newPrecent
      })
    }, 16)
    return () => {
      clearInterval(timer)
      clearInterval(percentTimer)
    }
  }, [curIndex])

  return (
    <nav className="shuffling">
      <ul className="shuffling-item-container">
        {data.map((item, index) => {
          return (
            <li
              style={{ left: getItemOffset(index, curIndex), ...item.style }}
              key={item.img}
            >
              <div>
                <article>
                  <h1
                    className="title"
                    dangerouslySetInnerHTML={{ __html: item.title }}
                  ></h1>
                  <p
                    className="text"
                    dangerouslySetInnerHTML={{ __html: item.text }}
                  ></p>
                </article>
                <div
                  style={{
                    width: "200px",
                    height: "200px",
                    background: `url(${item.img})`,
                    ...item.imgStyle,
                  }}
                ></div>
              </div>
            </li>
          )
        })}
      </ul>
      <div className="shuffling-controller-container">
        {data.map((item, index) => {
          return (
            <div
              className="shuffling-controller"
              onClick={() => {
                reAnimate(index)
              }}
              key={item.img}
            >
              <div className="shuffling-controller-item">
                <span
                  style={{
                    width: curIndex === index ? `${percent}%` : 0,
                  }}
                ></span>
              </div>
            </div>
          )
        })}
      </div>
    </nav>
  )
}
export default Shuffling
