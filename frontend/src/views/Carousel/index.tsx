import React,{FunctionComponent,useState,useCallback} from 'react';
import ProgessBar from '../Components/ProgressBar'
import Slider from '../Components/Slider'
import {Container} from '../../styles'
import styled from 'styled-components'

const Wrapper = styled(Container)`
  height:100vh;
`

const ProgressWrapper = styled(Container)`
  position: absolute;
  width: 100%;
  position: absolute;
  bottom: 10%;
  display: flex;
  justify-content: center;
`
type Props = {
  duration:number;
  items:JSX.Element[];
}

const App:FunctionComponent<Props> = (props) => {
  const [percent,setPercent] = useState<{[name:number]:number}>({})
  //倒计时指示器处理
  const counterHandler = useCallback((timePercent:number,id?:number)=> {
      setPercent((value)=>{
        const content = timePercent === 100?0:timePercent//到100复位
        if(id !== undefined){
          return {...value,[id]:content}
        }else{
          return {...value,id:content}
        }
      })
  },[])

  return (
    <Wrapper>
      <Slider {...props} countingCallback = {counterHandler}/>
      <ProgressWrapper>
        {
          props.items.map((_,index) =>{
            return  <ProgessBar key = {index} percent={percent[index]}/>
          })
        }
      </ProgressWrapper>
    </Wrapper>

  )

};

export default App
