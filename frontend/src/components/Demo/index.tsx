import React from "react";
import Container from "../Container";
import Carousel from "../Carousel";

let demoDataSource = [
  {
    title: 'xPhone',
    desc: (<div>Lots to love.Less to spend.<br />Starting at $399.</div>),
    color: '#fff',
    backgroundColor: '#000',
    imgUrl: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.fromgeek.com%2Fuploadfile%2F2020%2F2021%2F0924%2F20210924150030366G.png&refer=http%3A%2F%2Fwww.fromgeek.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1656147389&t=2ef5fe13a25013bc2b33d5b8bdc3bb74'
  },
  {
    title: 'Tablet',
    desc: 'Just the right amount of everything.',
    color: '#000',
    backgroundColor: '#fff',
    imgUrl: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.fromgeek.com%2Fuploadfile%2F2020%2F2021%2F0924%2F20210924150030366G.png&refer=http%3A%2F%2Fwww.fromgeek.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1656147389&t=2ef5fe13a25013bc2b33d5b8bdc3bb74'
  },
  {
    title: (<div>Buy a Tablet or xPhone for college.<br />GetArPods</div>),
    desc: '',
    color: '#fff',
    backgroundColor: '#000',
    imgUrl: 'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fwww.fromgeek.com%2Fuploadfile%2F2020%2F2021%2F0924%2F20210924150030366G.png&refer=http%3A%2F%2Fwww.fromgeek.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1656147389&t=2ef5fe13a25013bc2b33d5b8bdc3bb74'
  }
]

function Demo() {
  return <div className="App">
    <Container>
      <Carousel dataSource={demoDataSource} />
    </Container>
  </div>;
}

export default Demo;
