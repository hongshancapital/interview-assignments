import React from 'react';
import { render } from '@testing-library/react';

import Banner from './index'
import imgIphone from '../../assets/iphone.png';
import imgTablet from '../../assets/tablet.png';
import imgAirpods from '../../assets/airpods.png';


describe('Banner', () => {
    test('Banner render xPhone', () => {
        const { container } = render(
            <Banner 
                title="xPhone" 
                subtitle={['Lots to love. Less to spend.', 'Starting at $399']}
                style={{
                color: 'white',
                backgroundImage: `url(${imgIphone})`,
                backgroundRepeat: 'no-repeat',
                backgroundPosition: 'center bottom 100px',
                backgroundSize: 'auto 600px',
                backgroundColor: '#111111',
                }}
            />
        );
        const title = container.querySelectorAll('.banner-container-title');
        const subtitle = container.querySelectorAll('.banner-container-subtitle');
        expect(title.length).toBe(1);
        expect(subtitle.length).toBe(2);
    })

    test('Banner render Tablet', () => {
        const { container } = render(
            <Banner 
                title="Tablet" 
                subtitle='Just the right amount of everything.'
                style={{
                backgroundImage: `url(${imgTablet})`,
                backgroundRepeat: 'no-repeat',
                backgroundPosition: 'center bottom 100px',
                backgroundSize: 'auto 600px',
                backgroundColor: '#fafafa',
                }}
            />
        );
        const title = container.querySelectorAll('.banner-container-title');
        const subtitle = container.querySelectorAll('.banner-container-subtitle');
        expect(title.length).toBe(1);
        expect(subtitle.length).toBe(1);
    })

    test('Banner render Airpods', () => {
        const { container } = render(
            <Banner 
                title={['Buy a Tablet or xPhone for college.', 'Get arPods.']}
                style={{
                backgroundImage: `url(${imgAirpods})`,
                backgroundRepeat: 'no-repeat',
                backgroundPosition: 'center bottom 100px',
                backgroundSize: 'auto 600px',
                backgroundColor: '#f1f1f3',
                }}
            />
        );
        const title = container.querySelectorAll('.banner-container-title');
        const subtitle = container.querySelectorAll('.banner-container-subtitle');
        expect(title.length).toBe(2);
        expect(subtitle.length).toBe(0);
    })
})