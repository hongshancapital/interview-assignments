import React from "react";
import Carousel from '@/components/Carousel';
import styled from 'styled-components';

const HomeWrapper = styled.div`
  border: none;
`
const Home = () => {
  return (
    <HomeWrapper className="home">
      <Carousel />
    </HomeWrapper>
  )
}

export default Home;
