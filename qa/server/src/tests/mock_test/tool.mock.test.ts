import axios from "axios";
import {get, post} from "../axios-client";

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;
it('create a new tools', async () => {
    mockedAxios.post.mockResolvedValue({data: {"message": "success"}, status: 200});
    const response = await post('http://localhost:5000/toolbox/create',
        {"name": "Appium" + Date.parse(new Date().toString()), "tools": "UI Test"});
    expect(response.status).toEqual(200);
    expect(response.data).toEqual({"message": "success"});
});

it('returns all tools', async () => {
    mockedAxios.get.mockResolvedValue({
        data: {
            "message": "success",
            "data": [{"id": 2, "name": "Appium", "tools": "UI Test"}, {
                "id": 1,
                "name": "Jest",
                "tools": "Integration Test"
            }]
        }, status: 200
    });
    const response = await get('http://localhost:5000/toolbox');
    expect(response.status).toEqual(200);
    expect(response.data).toHaveProperty('data')
});