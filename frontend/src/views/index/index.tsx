import React from 'react';
import Carousel from '../../components/carousel'
import './index.scss';

const Index = () => {

    return (
        <Carousel duration={2}>
            <div className="content content1">
                <h1>xPhone</h1>
                <p>Lots to love.Less to spend.</p>
                <p>Starting at $399</p>
            </div>
            <div className="content content2">
                <h1>Tablet</h1>
                <p>Just the right amount of everything.</p>
            </div>
            <div className="content content3">
                <h1>Buy a Tablet or xPhone for college.</h1>
                <h1>Get arPods.</h1>
            </div>
        </Carousel>
    );
}

export default Index;