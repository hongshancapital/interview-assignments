import axios from "axios";
import {get} from "../axios-client";

jest.mock('axios');
const mockedAxios = axios as jest.Mocked<typeof axios>;
it('return all options', async () => {
    mockedAxios.get.mockResolvedValueOnce({data: ["Appium", "Macaca", "Jest"], status: 200});
    const response = await get('http://localhost:5000/toolbox/options');
    expect(response.status).toEqual(200);
    expect(response.data).toEqual(["Appium", "Macaca", "Jest"]);
});