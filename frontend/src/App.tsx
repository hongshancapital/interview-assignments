import React from 'react';
import './App.css';
import Carousel from './Carousel';

function App() {

  const list = [
    'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg9.51tietu.net%2Fpic%2F2019-091308%2Fsxlz2kc14l2sxlz2kc14l2.jpg&refer=http%3A%2F%2Fimg9.51tietu.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652279079&t=f8ffc85a33f04bc39a71553b74282241',
    'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg9.51tietu.net%2Fpic%2F2019-090922%2Fwytsmiioxlzwytsmiioxlz.jpg&refer=http%3A%2F%2Fimg9.51tietu.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652279163&t=ce1711c1117bb39654a839b9bd2481d9',
    'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg9.51tietu.net%2Fpic%2F2019-091301%2Fyveyszeonwhyveyszeonwh.jpg&refer=http%3A%2F%2Fimg9.51tietu.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652279163&t=0105cdfc46c93f61bbb220e3c2ee2800]',
    'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg9.51tietu.net%2Fpic%2F2019-091106%2Fp1l4v34axxrp1l4v34axxr.jpg&refer=http%3A%2F%2Fimg9.51tietu.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652279163&t=5143f7f1f6bb3a60eb08bddc79aa9418',
    'https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg9.51tietu.net%2Fpic%2F2019-090919%2Fnivjpmc04evnivjpmc04ev.jpg&refer=http%3A%2F%2Fimg9.51tietu.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1652279163&t=2437c749b2f8cfc6322bfb6752c19924',
  ]
  return (
    <Carousel list={list} />
  );
}

export default App;
