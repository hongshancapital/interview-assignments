import React, { useState, useEffect } from 'react';
import styled from 'styled-components'
import IconAutoPlay from './icon/play.svg'
import IconStopPlay from './icon/suspended.svg'
import IconArrowLeft from './icon/arrow-left-bold.svg'
import IconArrowRight from './icon/arrow-right-bold.svg'
import Sub from './sub'
import Progress, { State as ProgressState, Ctrl as ProgressCtrl } from './progress'

const StyledDivCarousel = styled.div`
    width: 100%;
    height: 100%;
    position: relative;
    overflow: hidden;
`
const StyledDivWin = styled.div<{ left: number }>`
    transition:left 0.5s; 
    left:${(props) => props.left}%;
    position:relative;    
    width: 100%;
    height: 100%;
    display: flex;
    flex-wrap: nowrap;
    justify-content: center;    
    align-items: stretch;
`
const StyledImgLeftArrow = styled.img`
    height: 30%;
    width: 5%;
    position:absolute;
    top:30%;
    left:0%;
    background-color: gray;
    opacity:0.5;
`
const StyledImgRightArrow = styled.img`
    width: 5%;
    height: 30%;
    position:absolute;
    top:30%;
    right:0%;
    background-color: gray;
    opacity:0.5;
`
const StyledDivProgress = styled.div`
    width: 100%;
    height: 10%;
    position:absolute;
    bottom:5%;
    display: flex;
    justify-content: center;    
    align-items: stretch;
`
const StyledImgAutoPlay = styled.img`
    width: 10%;
    height: 100%;
    display: flex;
    justify-content: center;    
    align-items: stretch;
`
interface IProps {
  children: any,
  slideTime: string,
  autoPlay: boolean
}

function Carousel(props: IProps) {

  // console.log('in Carousel()')

  const childrenSub = React.Children.toArray(props.children).map((v, index) => <Sub key={index} subMsg='subMsg'>{v}</Sub>)

  const childrenNumber = childrenSub.length

  const childrenLeft: number[] = []
  const [autoPlay, setAutoPlay] = useState(props.autoPlay)
  const [currentSub, setCurrentSub] = useState(1) //1~n
  const [runNumber, setRunNumber] = useState(1) //1~n

  for (let i = 0; i < childrenNumber; i++) {
    let leftOffset = ((childrenNumber - 1) * 100) / 2 - (100 * i)
    childrenLeft.push(leftOffset)
  }
  const [left, setLeft] = useState(childrenLeft[0])
  // console.log(childrenLeft)

  function onArrowLeftClicked(e: any) {
    // console.log(e.target.id)
    if (currentSub > 1) {
      // console.log(`onArrowLeftClicked: currentSub>1 ${currentSub}, childrenLeft[currentSub-1]:${childrenLeft[currentSub - 1]}`)
      setLeft(childrenLeft[(currentSub - 1) - 1])
      setCurrentSub(currentSub - 1)
      setRunNumber(runNumber + 1)
    }
  }
  function onArrowRightClicked(e: any) {
    if (currentSub < childrenNumber) {
      setLeft(childrenLeft[(currentSub + 1) - 1])
      setCurrentSub(currentSub + 1)
      setRunNumber(runNumber + 1)
    }
  }

  function onProgressAnimState(progressState: ProgressState) {
    // console.log(`onProgressAnimEnd: ${currentSub}`)
    if (currentSub < childrenNumber) {
      // console.log(`onProgressAnimState: currentSub>1 ${currentSub}, childrenLeft[currentSub-1]:${childrenLeft[currentSub - 1]}`)
      setLeft(childrenLeft[currentSub])
      setCurrentSub(currentSub + 1)
      setRunNumber(runNumber + 1)
    }
  }

  function enableAutoPlay() {
    setAutoPlay(!autoPlay)
  }
  return <StyledDivCarousel>
    <StyledDivWin left={left}>
      {childrenSub}
    </StyledDivWin>
    <StyledImgLeftArrow src={IconArrowLeft} onClick={onArrowLeftClicked} id='leftArrow' />
    <StyledImgRightArrow src={IconArrowRight} onClick={onArrowRightClicked} id='rightArrow' />
    <StyledDivProgress>
      <Progress
        childrenNum={childrenNumber}
        currentSub={currentSub}
        callback={onProgressAnimState}
        slideTime={props.slideTime}
        progressCtrl={autoPlay ? ProgressCtrl.RUN : ProgressCtrl.PAUSE}
        runNumber={runNumber}
      >
      </Progress>
      <StyledImgAutoPlay src={autoPlay ? IconStopPlay : IconAutoPlay} onClick={enableAutoPlay} />
    </StyledDivProgress>
  </StyledDivCarousel>
}


export default Carousel

