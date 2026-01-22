import React from 'react';
import './CarouselItem.css'

// 轮播 item
const SwipeItem: React.FC = () => (
  <React.Fragment>
    <div className="scroll_item scroll_item_1">
      <h2 className='title'>xPhone</h2>
      <section className='desc'>Lots to love. Less to spend <br /> Starting at $399.</section>
    </div>
    <div className="scroll_item scroll_item_2">
    <h2 className='title'>Tablet</h2>
      <section className='desc'>Just the right amount of everything.</section>
    </div>
    <div className="scroll_item scroll_item_3">
    <section className='desc'>Buy a Tablet or xPhone for college. <br />Get arPods.</section>
    </div>
  </React.Fragment>
)

export default SwipeItem
