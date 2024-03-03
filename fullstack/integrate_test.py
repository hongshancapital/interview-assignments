#!/usr/bin/env python3

import json
import requests
import redis


class TestIntegration:
    redisClient = redis.Redis(host='localhost', port=6379, db=0)

    def testLongStore(self):
        map = {
            'shortDomain': 'x.xx/AAAAAAAA',
            'longDomain': 'www.google.com',
        }

        r = requests.post("http://localhost:6789/long-to-short", json={
            'longDomain': map['longDomain']
        })

        res = r.json()
        assert res['longDomain'] == map['longDomain']
        assert res['shortDomain'] == map['shortDomain']

    def testSameLongStore(self):
        map = {
            'shortDomain': 'x.xx/AAAAAAAA',
            'longDomain': 'www.google.com',
        }
        r = requests.post("http://localhost:6789/long-to-short", json={
            'longDomain': map['longDomain']
        })

        res = r.json()
        assert res['longDomain'] == map['longDomain']
        assert res['shortDomain'] == map['shortDomain']


    def testDiffLongStore(self):
        map = {
            'shortDomain': 'x.xx/AAAAAAAB',
            'longDomain': 'www.baidu.com',
        }
        r = requests.post("http://localhost:6789/long-to-short", json={
            'longDomain': map['longDomain']
        })

        res = r.json()
        assert res['longDomain'] == map['longDomain']
        assert res['shortDomain'] == map['shortDomain']


    def testGetByShortFromCache(self):
        map = {
            'shortDomain': 'x.xx/AAAAAAAA',
            'longDomain': 'www.google.com',
        }

        assert self.redisClient.get(map['shortDomain'][5:]).decode("utf-8") == map['longDomain']
        r = requests.get("http://localhost:6789/short-to-long?query=" + json.dumps({
            'shortDomain': map['shortDomain']
        }))
        res = r.json()
        assert res['longDomain'] == map['longDomain']
        assert res['shortDomain'] == map['shortDomain']


    def testGetByShortFromDb(self):
        map = {
            'shortDomain': 'x.xx/AAAAAAAB',
            'longDomain': 'www.baidu.com',
        }

        self.redisClient.delete(map['shortDomain'][5:])
        r = requests.get("http://localhost:6789/short-to-long?query=" + json.dumps({
            'shortDomain': map['shortDomain']
        }))
        res = r.json()
        assert res['longDomain'] == map['longDomain']
        assert res['shortDomain'] == map['shortDomain']
