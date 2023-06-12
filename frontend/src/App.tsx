import "./App.css";
import React from "react";
import Carousel from "./Carousel";

const App: React.FC = () => {
    return (
        <div className="App">
            <Carousel>
                <div className="bg-1">
                    <div className="bg-img bg-img-1">
                        <h1 className="title" style={{ color: "#FFF" }}>
                            xPhone
                        </h1>
                        <h2 style={{ color: "#FFF" }}>Lots to love. Less to spend.</h2>
                        <h2 style={{ color: "#FFF" }}>Starting at $399</h2>
                    </div>
                </div>
                <div className="bg-2">
                    <div className="bg-img bg-img-2">
                        <h1 className="title">Tablet</h1>
                        <h3>Just the right amount of everything.</h3>
                    </div>
                </div>
                <div className="bg-3">
                    <div className="bg-img bg-img-3">
                        <h1 className="title">
                            Buy a Tablet or xPhone for college.
                        </h1>
                        <h1 className="title">Get arPods.</h1>
                    </div>
                </div>
            </Carousel>
        </div>
    );
};

export default App;
