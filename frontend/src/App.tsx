import React from "react";
import "./App.scss";
import Carousel from "./components/Carousel";

function App() {
  return (
      <div className="App">
        <Carousel>
            <div className="iphone">
                <div className="content">
                    <div className="title">
                    xPhone
                    </div>
                    <div className="desc">
                        Lots to love. Less to spend.
                    </div>
                    <div className="desc">
                        Starting at $399.
                    </div>
                </div>
            </div>
            <div className="tablet">
                <div className="content">
                    <div className="title">Tablet</div>
                    <div className="desc">Just the right amount of everything.</div>
                </div>
            </div>
            <div className="airpods">
                <div className="content">
                    <div className="title">
                        Buy a Tablet or xPhone for college.
                    </div>
                    <div className="title">
                        Get arPods.
                    </div>
                </div>
            </div>
        </Carousel>
      </div>
  )
}

export default App;
