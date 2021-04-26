
# DevOps Assignment Q2 AWS CDK project

This is a project for DevOps Assignment Q2 Python development with CDK.

The `cdk.json` file tells the CDK Toolkit how to execute your app.

## Setup Python virtualenv
This project is set up like a standard Python project. The initialization
process also creates a virtualenv within this project, stored under the `.venv`
directory.  To create the virtualenv it assumes that there is a `python3`
(or `python` for Windows) executable in your path with access to the `venv`
package. If for any reason the automatic creation of the virtualenv fails,
you can create the virtualenv manually.

To manually create a virtualenv on MacOS and Linux:

```
$ python -m venv .venv
```

After the init process completes and the virtualenv is created, you can use
the following step to activate your virtualenv.

```
$ source .venv/bin/activate
```

If you are a Windows platform, you would activate the virtualenv like this:

```
% .venv\Scripts\activate.bat
```

Once the virtualenv is activated, you can install the required dependencies.

```
$ pip install -r requirements.txt
```

## Change SNS Email Address
Change the value of `SNS_EMAIL` in ./q2_aws_cdk/q2_aws_cdk_stack.py.

## Deploy CloudFormation Template
At this point you can now deploy the CloudFormation template for this project.

```
$ cdk deploy
```
