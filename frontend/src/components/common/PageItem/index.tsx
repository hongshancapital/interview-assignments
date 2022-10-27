import "./index.scss"

import { IItemProps } from "./interfaces"
import React from "react"

/**
 * 幻灯片单个卡片组件
 */
export const PageItem: React.FC<{ data: IItemProps }> = ({
  data
}) => {
  const { title, descList, bgImgUrl, className } = data
  const itemClass = "page-item " + (className || "")

  return (
    <div className={itemClass}>
      <div className="title-wrapper">
        <div className="title">{title}</div>
        
        {descList?.map((str, index) => (
          <p key={`desc${index}`}>{str}</p>
        ))}
      </div>


      <div
        className="img"
        style={{ backgroundImage: `url(${bgImgUrl})` }}
      />
    </div>
  )
}

export default PageItem
