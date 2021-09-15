import json
import requests
import os
import pytest


URL = "http://127.0.0.1:5000"
CREATE_API  = "/toolbox/create"
TOOLBOX_API = "/toolbox"


# 依据当前文件名寻找到对应的测试用例文件
file_name = os.path.splitext(os.path.basename(__file__))[0] + ".json"
path = os.getcwd() + "\\scripts\\test\\testcase\\" + file_name


def get_json_data(path):
    with open(path, "r", encoding= 'utf-8') as f:
        data = json.load(f)
        return data['CASES']


@pytest.mark.parametrize("data", get_json_data(path))
def test_toolbox_create(data):
    name = data["name"]
    input = data["input"]
    expect = data["expect"]
    print("CaseName: " + name)

    with requests.Session() as s:
        res = s.post(URL + CREATE_API, data=input)
        response = json.loads(res.content)
        print(response)
        print( expect["message"] )
        assert res.status_code==expect["code"]

        