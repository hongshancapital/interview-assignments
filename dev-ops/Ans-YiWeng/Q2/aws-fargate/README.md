# How to setup a web stack

1. Install Sceptre version 1.3.1: `pip install sceptre==1.3.1` or `pip3 install sceptre==1.3.1`

2. Run the creation/update command `sceptre launch-env <environment>`.(eg. `sceptre launch-env helloworld`)

3. Run the creation/update command `sceptre launch-stack <environment> <stack>`.(eg. `sceptre launch-env helloworld alb`)

4. Run the creation/update command `sceptre delete-env <environment>`.(eg. `sceptre delete-env helloworld`)

5. Run the creation/update command `sceptre delete-stack <environment> <stack>`.(eg. `sceptre delete-env helloworld alb`)
