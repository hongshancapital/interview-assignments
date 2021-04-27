import React from 'react';
import { render, screen } from '@testing-library/react';
import App from '@/App';

var fs = require('fs');
var setup = (url:string, data:string) => {
    fs.writeFile(url, data, function(err) {
        if (err) {
            return console.error(err);
        }
    });
}
test.concurrent('case 6: when tool options is null. expect no tool to be selected', async () => {

    const optionsTestData = [];

    setup('src/mock/options.json', JSON.stringify(optionsTestData))

    render(<App />);

    // wait for options loaded
    await screen.getByPlaceholderText("Please enter Engineer's name");

    var inputElement = screen.getByPlaceholderText("Please enter Engineer's name")
    expect(inputElement.nextElementSibling.childNodes.length).toBe(0);

});
