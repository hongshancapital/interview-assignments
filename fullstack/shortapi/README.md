#### 0. Please install mongodb on local

---

#### 1. Then npm install what you need please follow on paacage.json file first

---

#### 2. And npm i --save-dev @types/express @types/hapi\_\_joi @types/shortid @types/valid-url @types/config

---

#### 3. POST 接口

```
curl --location --request POST 'localhost:8000/shortLink' \
--header 'Content-Type: application/json' \
--header 'Cookie: _VI=4HlbZKzL' \
--data-raw '{
    "longUrl": "https://www.bilibili.com/video/BV11B4y1w77M/?spm_id_from=333.788.recommend_more_video.14&vd_source=bbb985ceadc8e3199d1d5a091b58155b"
}'
```

---

#### 4. GET API

```
curl --location --request GET 'http://localhost:8000/jkC6lbj7' \
--header 'Cookie: _VI=4HlbZKzL'
```
# short-api
