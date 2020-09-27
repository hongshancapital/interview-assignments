import React from "react";
import "./App.css";
import styled from 'styled-components'
import Carousel from './view/carousel'
import Img1 from './assets/1.png'
import Img2 from './assets/2.png'
import Img3 from './assets/3.png'
import Img4 from './assets/4.png'
import Img5 from './assets/5.png'
import Img6 from './assets/6.png'

const UserApp = styled.div`
    width: 90vw;
    height: 90vh;
    margin-left: 5vw;
    margin-top: 5vh;
    display: flex;
    flex-wrap: nowrap;
    justify-content: center;    
    align-items: center;
    background-color: gray;
`

const UserDiv = styled.div`
    flex: 0 0 auto;
    width: 50%;
    height: 50%;
    margin: 5%;
    padding: 5px;
    background-color: ${props => props.color};
`

const UserSub = styled.div`
    width: 100%;
    height: 100%;
    text-align: center;
`
const UserText = styled.div`
    width: 100%;
    height: 50%;
    text-align: left;
`
const UserImg = styled.img`
    width: 100px;
    height: 100px;    
`

function App() {
  return (
    <UserApp>
      <UserDiv color='#d2c394'>

        <Carousel slideTime={'1000ms'} autoPlay={false} >

          <UserSub>
            <UserText>1. This is the first chapter in a step-by-step guide about main React concepts. You can find a list of all its chapters in the navigation sidebar. If you’re reading this from a mobile device, you can access the navigation by pressing the button in the bottom right corner of your screen.</UserText>
            <UserImg src={Img1} />
          </UserSub>
          <UserSub>
            <UserText>2. This guide occasionally uses some of the newer JavaScript syntax in the examples. If you haven’t worked with JavaScript in the last few years, these three points should get you most of the way.</UserText>
            <UserImg src={Img2} />
          </UserSub>
          <UserSub>
            <UserText>3. It is called JSX, and it is a syntax extension to JavaScript. We recommend using it with React to describe what the UI should look like. JSX may remind you of a template language, but it comes with the full power of JavaScript.</UserText>
            <UserImg src={Img3} />
          </UserSub>
          <UserSub>
            <UserText>4. Instead of artificially separating technologies by putting markup and logic in separate files, React separates concerns with loosely coupled units called “components” that contain both. We will come back to components in a further section, but if you’re not yet comfortable putting markup in JS, this talk might convince you otherwise</UserText>
            <UserImg src={Img4} />
          </UserSub>
          <UserSub>
            <UserText>5. By default, React DOM escapes any values embedded in JSX before rendering them. Thus it ensures that you can never inject anything that’s not explicitly written in your application. Everything is converted to a string before being rendered. This helps prevent XSS (cross-site-scripting) attacks.</UserText>
            <UserImg src={Img5} />
          </UserSub>
          <UserSub>
            <UserText>6. By default, React DOM escapes any values embedded in JSX before rendering them. Thus it ensures that you can never inject anything that’s not explicitly written in your application. Everything is converted to a string before being rendered. This helps prevent XSS (cross-site-scripting) attacks.</UserText>
            <UserImg src={Img6} />
          </UserSub>

        </Carousel>

      </UserDiv>
    </UserApp>
  );
}

export default App;