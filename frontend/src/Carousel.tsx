import {useState, useEffect} from 'react';
import './App.css';
import {Progress} from 'antd';
import 'antd/dist/antd.css';
import XPhone from './componentsFor/XPhone';
import Tablet from './componentsFor/Tablet';
import Buy from './componentsFor/Buy';


function App() {
   const [percentage, setPercentage] = useState(0);
   const [fstPercentage, setFstPercentage] = useState(0);
   const [sedPercentage, setSedPercentage] = useState(0);
   const [trdPercentage, setTrdPercentage] = useState(0);

  useEffect(() => {
    
     setTimeout(() => {
      let p = percentage;
      ++p;
      if(p > 300) {
        p = p%300;
      }
      setPercentage(p);
      if(percentage <= 300) {
        if(percentage <= 100) {
          let fstPercentage = percentage;
          setFstPercentage(fstPercentage);
          setSedPercentage(0);
          setTrdPercentage(0);
        }
        else if(percentage > 100 && percentage <= 200) {
          let scdPercentage = percentage - 100;
          setFstPercentage(100);
          setSedPercentage(scdPercentage);
          setTrdPercentage(0);
        }
        else if(percentage > 200 && percentage <= 300) {
          let trdPercentage = percentage - 200;
          setFstPercentage(100);
          setSedPercentage(100);
          setTrdPercentage(trdPercentage);
        }
      }
     }, 50);
  }, [percentage])

  return (
    <div className = {(percentage >=0 && percentage <= 100) ? 'background__black' : 'background__normal'}>
          <div className="main__content">
              {(percentage >=0 && percentage <= 100) && <XPhone/>}
              {(percentage <= 200 && percentage >= 100) && <Tablet/>}
              {(percentage <= 300 && percentage > 200) && <Buy/>}
          </div>

        <div id="container"> 
          <Progress   percent={percentage} showInfo={false}/> 

          <Progress    percent={sedPercentage} showInfo={false}/>

          <Progress    percent={trdPercentage} showInfo={false}/>
        </div>
    </div>
  );
}

export default App;
