import { describe, it, expect } from '@jest/globals';
import axios from 'axios';
import fetch from 'node-fetch';

it('should work in total flow', async () => {
    const long = 'https://github.com/scdt-china/interview-assignments/tree/master/fullstack';

    const res1 = await axios.post('http://localhost:8080/api/long_to_short', { long });
    const short = res1?.data?.data?.short;
    expect(typeof short).toBe('string');

    const res2 = await axios.get('http://localhost:8080/api/short_to_long', { params: { short } });
    expect(res2?.data?.data?.long).toBe(long);

    const res3 = await fetch(`http://localhost:8080/${short}`, { redirect: 'manual' });
    expect(res3.headers.get('location')).toBe(long);
})