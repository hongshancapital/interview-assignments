import fetch from 'node-fetch';

type CreateUrlResponse = {
    shortUrl: String,
    code: String
}

test('获取短链长度等于8(去除域名)', async () => {
    const response = await fetch("http://localhost:5000/api/url/shorten", {
        method: 'POST',
        body: JSON.stringify({
            longUrl: "https://www.google.com"
        }),
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
        },
    });

    if (!response.ok) {
        const e = new Error(`error! status: ${response.status}`);
        expect(e).toMatch('error');
    }

    const result = (await response.json()) as CreateUrlResponse;
    
    expect(result.code.length).toBe(8);
});

test('传空域名，正常触发报错提示', async () => {
    expect.assertions(1);
    const response = await fetch("http://localhost:5000/api/url/shorten", {
        method: 'POST',
        body: JSON.stringify({
            longUrl: ""
        }),
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
        },
    });

    if (!response.ok) {
        const e = new Error(`error! status: ${response.status}`);
        expect(e).toMatch('error');
    }
});

test('传特殊字符域名，正常触发报错提示', async () => {
    expect.assertions(1);
    const response = await fetch("http://localhost:5000/api/url/shorten", {
        method: 'POST',
        body: JSON.stringify({
            longUrl: "https://www.google*&^.com"
        }),
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
        },
    });

    if (!response.ok) {
        const e = new Error(`error! status: ${response.status}`);
        expect(e).toMatch('error');
    }
});

test('传非域名，正常触发报错提示', async () => {
    expect.assertions(1);
    const response = await fetch("http://localhost:5000/api/url/shorten", {
        method: 'POST',
        body: JSON.stringify({
            longUrl: "hsasdxc"
        }),
        headers: {
            'Content-Type': 'application/json',
            Accept: 'application/json',
        },
    });

    if (!response.ok) {
        const e = new Error(`error! status: ${response.status}`);
        expect(e).toMatch('error');
    }
});