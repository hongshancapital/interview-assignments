import {get} from "./axios-client";

it('returns all options', async () => {
    const response = await get('http://localhost:5000/toolbox/options');
    expect(response.status).toEqual(200);
    expect(response.data).toEqual( ["E2E", "Headless Chrome", "Jest", "Selenium"]);
});