import boto3
import os
import json
import zlib
import base64

sns = boto3.client('sns')
topic_arn = os.environ['SNS_TOPIC_ARN']

def handler(event, context):
    msg = event['awslogs']['data']
    msg = zlib.decompress(base64.b64decode(msg), wbits=zlib.MAX_WBITS|16)
    msg = json.loads(msg)
    print(msg)
    
    for event in msg['logEvents']:
        sns.publish(
            TopicArn=topic_arn,
            Message=event['message'],
            Subject=msg['logStream']
        )
    
    