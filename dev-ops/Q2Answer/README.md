# Q2 Answer
typescript project is under folder fargate, please help review files under fargate folder.

# Steps
## init project
```bash
 # install typescript
 $ npm install -g typescript

# init cdk project
$ cdk init app --language typescript

# generate aws cloud formation template files
$ cdk synth

# deploy the stack
$ cdk deploy

# destroy the stack
$ cdk destroy

```

Note: WAF configuration part has not include yet.