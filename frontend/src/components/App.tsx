import React, { useCallback, useMemo, useState } from "react";
import { CAROUSEL_LIST } from "../constants";
import "../styles/App.css";
import IndicatorLines from "./IndicatorLines";
import PageItem from "./PageItem";

function App() {
  const DEFAULT_INDEX = 0;
  const [currentIndex,setCurrentIndex] = useState(DEFAULT_INDEX);
  const list = CAROUSEL_LIST;
  const gotoNextPage = useCallback(
    ()=>setCurrentIndex((index)=>(index+1)%list.length),[list.length]
  )
  const carouselStyle = useMemo(
    ()=>({
    width:`${list.length*100}vw`,
    transform:`translateX(-${currentIndex*100}vw)`,
    }),[list,currentIndex]
  )
  return (
    <div className="App" onClick = {gotoNextPage}>
      <div className='carousel' style={carouselStyle}>
        {
          list.map(({
            iconType,fontColor,backgroundColor,title,text
          })=>(
            <PageItem
               key={iconType}
               iconType = {iconType}
               fontColor={fontColor}
               backgroundColor={backgroundColor}
               title={title}
               text={text}
            />
          ))}
      </div>
      <IndicatorLines
          activeIndex={currentIndex}
          itemsCount = {list.length}
          onAnimationEnd = {gotoNextPage}
      />
    </div>
  );
}

export default App;
