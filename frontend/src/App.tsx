import React from "react";
import "./App.css";
import styled from 'styled-components'
import Carousel from './view/carousel'
import Img1 from './assets/iphone.png'
import Img2 from './assets/tablet.png'
import Img3 from './assets/airpods.png'

const UserApp = styled.div`
    width: 100vw;
    height: 100vh;
    display: flex;
    flex-wrap: nowrap;
    justify-content: center;    
    align-items: center;
    background-color: green;
`

const UserDiv = styled.div`
    flex: 0 0 auto;
    width: 100%;
    height: 100%;
`

const UserSub1 = styled.div`
    width: 100%;
    height: 100%;
    text-align: center;
    background-color: black;
    color: white;
`
const UserSub2 = styled.div`
    width: 100%;
    height: 100%;
    text-align: center;
    background-color: #eeeeee;
`
const UserSub3 = styled.div`
    width: 100%;
    height: 100%;
    text-align: center;
    background-color: #eeeeee;
`
const UserTextArea = styled.div`
    width: 100%;
    height: 60%;
    display: flex;
    flex-direction: column;
    justify-content: center;    
    align-items: center;
`
const UserTextTitle = styled.div`
    margin-top: 5px;
    margin-bottom: 10px;
    flex: 0 0 auto;
    font-size:2em;
`
const UserTextBody = styled.div`
    margin-top: 3px;
    flex: 0 0 auto;
    font-size:1.2em;
`
const UserImg = styled.img`
    width: 100px;
    height: 100px;    
`

function App() {
  return (
    <UserApp>
      <UserDiv>

        <Carousel slideTime={'2000ms'} autoPlay={true} >

          <UserSub1>
            <UserTextArea>
              <UserTextTitle>xPhone</UserTextTitle>
              <UserTextBody>Los to love. Less to spend.</UserTextBody>
              <UserTextBody>Starting at $399.</UserTextBody>
            </UserTextArea>
            <UserImg src={Img1} />
          </UserSub1>
          <UserSub2>
          <UserTextArea>
              <UserTextTitle>Tablet</UserTextTitle>
              <UserTextBody>Just the right amount of everything.</UserTextBody>
            </UserTextArea>
            <UserImg src={Img2} />
          </UserSub2>
          <UserSub3>
          <UserTextArea>
              <UserTextTitle>Buy a Tablet or xPhone for college.</UserTextTitle>
              <UserTextTitle>Get arPods.</UserTextTitle>
            </UserTextArea>
            <UserImg src={Img3} />
          </UserSub3>
        </Carousel>

      </UserDiv>
    </UserApp>
  );
}

export default App;