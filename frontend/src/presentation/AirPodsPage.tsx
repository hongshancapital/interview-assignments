import React from "react"
import styled from 'styled-components'

import airPods from '../assets/airpods.png'

const Container = styled.div`
  display: flex;
  justify-content: center;
  height: 100%;
  background: #f1f1f3;
`

const TitleArea = styled.div`
  position: absolute;
  top: 15%;
  left: 10%;
  width: 80%;
  text-align: center;
  color: #111111;
`

const Title = styled.h1`
  margin: 0 0 10px 0;
  font-size: 60px;
  font-weight: 600;
`

export const AirPodsPage = () => {
  return (
    <Container>
      <TitleArea>
        <Title>Buy a Tablet or xPhone for college.</Title>
        <Title>Get arPods.</Title>
      </TitleArea>
      <img src={airPods} alt='a pair of wireless earphones' />
    </Container>
  )
}