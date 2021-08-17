#!/usr/bin/env python3
# encoding: utf-8
# ===============================================================================
#
#         FILE:
#
#        USAGE:
#
#  DESCRIPTION:
#
#      OPTIONS:  ---
# REQUIREMENTS:  ---
#         BUGS:  ---
#        NOTES:  ---
#       AUTHOR:  x2x4
#      COMPANY:
#      VERSION:  1.0
#      CREATED:
#     REVISION:  ---
# ===============================================================================

import click
import requests
import os
import gzip
import analysis
import json


@click.group()
@click.option('-f', "--file", default="./interview_data_set.gz", help="默认目标文件", show_default=True)
# @click.option('-c', "--gzip", type=bool, is_flag=True, default=True, show_default=True, help="是否为gzip压缩文件")
@click.pass_context
def cli(ctx, file):
    ctx.ensure_object(dict)
    ctx.obj['full_path_file'] = os.path.abspath(file)
    if not os.path.exists(ctx.obj['full_path_file']):
        print("{} not existed, please check...".format(file))
        raise SystemExit(127)


@cli.command()
@click.option('-u', "--upload", type=bool, default=True, show_default=True, help="是否上传至服务器")
@click.option('-s', "--server_url", default='https://foo.com/bar', show_default=True, help="默认上传url")
@click.pass_context
def run_analysis(ctx, upload, server_url):
    file = ctx.obj['full_path_file']
    try:
        with gzip.open(file) as _fh:
            contents = _fh.read()
    except gzip.BadGzipFile:
        print("{} not a gzip file, Stopped".format(file))
        raise SystemExit(1)
    
    # print(contents)
    if type(contents) is bytes:
        contents = contents.decode()
    data: analysis.DataStruct = analysis.run(contents)
    # for _k, _v in data.items():
    #     _v: analysis.LogDetail
    #     print("key: {}{}:{}".format(_v.process_name, _v.process_id, _v.to_json()))
    # for test com.apple.mdworker.bundles+12513
    # from hashlib import sha1
    # _key = sha1("com.apple.mdworker.bundles12513".encode()).hexdigest()
    # # print(data[_key].to_json())
    # _key = sha1("com.apple.xpc.launchd.domain.pid.mdmclient53157".encode()).hexdigest()
    # # print(data[_key].to_json())
    # _key = sha1("com.apple.xpc.launchd.domain.user914945058".encode()).hexdigest()
    # # print(data[_key].to_json())
    #
    # _key = sha1("VTDecoderXPCService960".encode()).hexdigest()
    #
    # _key = sha1("timed158".encode()).hexdigest()
    #
    # _key = sha1("syslogd113".encode()).hexdigest()
    #
    # print(data[_key].to_json())
    for _k, _v in data.items():
        data[_k] = _v.__dict__()

    # print(json.dumps(data))
    if not upload:
        print(data)
        return
    try:
        ret = requests.post(server_url, json=json.dumps(data))
        if ret.status_code != requests.codes.ok:
            raise RuntimeError("Failed to post data, server return: %s, text: %s" % (ret.status_code, ret.text))
        print(ret.text)
    except Exception as e:
        print(e)
    

if __name__ == '__main__':
    cli(obj={})
    cli.add_command(run_analysis)