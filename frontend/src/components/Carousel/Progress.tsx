import React from "react"
import { ShowTimeType } from "./types"

/**
 * 进度条容器组件
 */
export const Progress: React.FC<{
  size: number
  handleClick: Function
  activeIndex: number
  showTime: ShowTimeType
}> = ({ size, showTime, activeIndex, handleClick }) => {

  return (
    <div className="progress-wrapper">
      {[...Array(size)].map((item, index) => {
        const isActive = activeIndex === index
        const activeClass = "run-line " + (isActive ? "active" : "")

        return (
          <div key={index}
            className="progress-item"
            onClick={() => handleClick(index)}
          >
            <div className="click-wrapper">
              <div className={activeClass} 
                data-testid={`click-wrapper-${index}`}
                style={{ animationDuration: `${showTime}s` }}
              ></div>
            </div>
          </div>
        )
      })}
    </div>
  )
}

export default Progress
