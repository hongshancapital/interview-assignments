import React from 'react'
import Carousel from '../Carousel'
import Banner from '../Components/Banner'
import {theme} from '../../styles'
import styled,{ ThemeProvider } from 'styled-components'
import iphone_ico from '../../assets/iphone.png'
import tablet_ico from '../../assets/tablet.png'
import airpods_ico from '../../assets/airpods.png'
const StyledLayout = styled.main`
  height:100vh;
  margin: 0 auto;
`



function App (){
  return (
    <ThemeProvider theme={theme}>
      <StyledLayout>
        <Carousel
        duration = {3000}
        items = {
          [
            <Banner theme = {'dark'} img = {iphone_ico} title = {'xPhone'} subTitle = {`Lots to love. Less to spend. \n Starting at $399.`}/>,
            <Banner theme = {'#fafafa'}img = {tablet_ico} title = {'Tablet'} subTitle = {`Just the right amount of everything.`}/>,
            <Banner theme = {'#f1f1f3'} img = {airpods_ico} title = {'Buy a Tablet or xPhone for college.\n Get arPods.'}/>,
          ]
        }/>
      </StyledLayout>

    </ThemeProvider>
  )
}

export default App
