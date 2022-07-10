
import React, { memo, FC } from "react";
import {imagesList} from '../../interface/carousel'
import './index.css'

const Progress: FC<{
  currentIndex: number,
  list:Array<imagesList>
}> = memo(function ProgressBar({currentIndex, list}) {
  return <div className="progress-bar-wrap">
    {
      list.map((item, i)=><div key={item.id} className="progress-bar">
        <div className={`fill ${currentIndex == i?'animation':'noanimation'}`}></div>
    </div>)
    }
  </div>
})

export default Progress