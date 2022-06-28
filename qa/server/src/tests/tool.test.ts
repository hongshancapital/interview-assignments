import {get, post} from "./axios-client";

it('create a new tools', async () => {
    const response = await post('http://localhost:5000/toolbox/create',
        { "name": "Appium"+Date.parse(new Date().toString()), "tools": "E2E"});
    expect(response.status).toEqual(200);
    expect(response.data).toEqual({"message": "success"});
});

it('returns all tools', async () => {
    const response = await get('http://localhost:5000/toolbox');
    expect(response.status).toEqual(200);
    expect(response.data).toHaveProperty('data')
});