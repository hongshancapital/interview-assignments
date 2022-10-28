import React from 'react';
import './App.css';
import Carousel from './Carousel'

function App() {
  const slideList = [
    {
      backgroundColor:'#101010',
      color:'#fff',
      subject:[{text:'xPhone',fontSize:'30px',fontWeight:500},
      {text:'Lots to love.Less to spend.',fontSize:'20px',fontWeight:400},
      {text:'Starting at $399.',fontSize:'20px',fontWeight:400}],
      goodsImgURL:'1.png'
    },
    {
      backgroundColor:'#fafafa',
      color:'#000',
      subject:[{text:'Tablet',fontSize:'30px',fontWeight:500},
      {text:'Just the right amount of everything.',fontSize:'20px',fontWeight:400}],
      goodsImgURL:'2.png'
    },
    {
      backgroundColor:'#f2f2f4',
      color:'#000',
      subject:[{text:'Buy a Tablet or xPhone for college.',fontSize:'30px',fontWeight:500},
      {text:'Get arPods.',fontSize:'30px',fontWeight:500}],
      goodsImgURL:'3.png'
    }
  ]
  return (
    <div className="App">
      <Carousel {...{slideList}}/>
    </div>
  );
}

export default App;
