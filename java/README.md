# Java Assignment

## 1.	Background

### What is Tiny URL

The URL shorter (Tiny URL) was produced with the development of the mobile Internet. Many years ago, due to the limit of 140 bytes on the social network Twitter, the use of shortened URLs among users became more and more popular.
Wikipedia defines short URLs: “Tiny URL is a URL shortening web service, which provides short aliases for redirection of long URLs.” This means that the short domain name service has two characteristics: 

* The first is that Tiny URL is shorter than the URL usually used by browsers. 
* The second is that the access of Tiny URL can be redirected to the long URL through a specific domain name service for users to access.

### Advantages and disadvantages of Tiny URL
* Tiny URL is widely used in the Internet and it promotes Internet culture. Because sharing shortened URLs is more conducive to user dissemination than long and complex URLs.
* It is more advantageous to use Tiny URL to monitor links. For example, follow the statistics of clicks, dates, device types and browsers used.
* Tiny URL may become a disguise method for some illegal URL addresses. For example, users often receive spoofed text messages containing short domain names.
* Tiny URL relies on shortener URL service, and there are hidden dangers in availability. For example, the service that the short domain name depends on cannot be redirected to the normal URL because it cannot run.
	
### Popular Tiny URL service provider
tinyurl.com, tiny.cc, bitly.com, rebrandly.com, firebase.google.com/products/dynamic-links/


## 2.	Tiny URL solution

### Aim
This project is a demonstration project to practice the characteristics of Tiny URL. The goal is to realize the conversion of URL to Tiny URL and the conversion of Tiny URL into URL by providing API services.

### Development environment
Software: JDK-1.8, Spring boot-2.4.4, H2database-1.4.196, JPA-2.4.4, Ehcache-3.9.2, etc.
Tools: Idea-2020.2, Postman-7.36.5

The Tiny URL service consists of two parts. 
* The first is the user authentication service. The system authenticates the user's identity and issues a Token which is the basis for the user to perform URL-related services.
* The second service is URL service, which is used to shorten and obtain URL.
The process is shown in the figure below.

![image](https://github.com/jade75/interview-assignments/blob/master/java/process.png?raw=true)

### URL conversion
In the research on shortening the URL, there are three methods in summary.
* Use a hash function to hash the original URL.
* Use UUID.
* Use numbers to convert from technology 10 to 62, 62 contains 0-9, a-z, A-Z

This demo project uses the hash algorithm steps as follows.
1. Perform MD5 conversion on the original URL and store it in the database including the inserted timestamp, user ID and other information.

2. Perform a 32-bit hash conversion on the MD5 splicing timestamp of the database to generate an 8 digit and alphanumeric Tiny URL.

The above steps are to reduce the conflict of hash transfer. The MD5 result is not unique, and there may be two different URLs with the same MD5 value. The MD5+timeStamp combination once again generates a 32-byte value


### 3.	Evaluation

Unit test the service and controller in the project, and generate test coverage statistics in the directory test-cover.

The test result using POST simulation request is as follows.

![image](https://github.com/jade75/interview-assignments/blob/master/java/user.png?raw=true)

![image](https://github.com/jade75/interview-assignments/blob/master/java/url.png?raw=true)

![image](https://github.com/jade75/interview-assignments/blob/master/java/tinyurl.png?raw=true)


MD5 comparison of all user request data in a specific order ensures that there will be no tampering during transmission, and xss filtering is performed on user submitted data. If the confidentiality of data transmission is high, 3DES confidential transmission of all requested data can be performed. This project does not use Spring Security, Shrio and other authorization verifications. This is just to demonstrate the security of Tiny URL to a certain extent.

### 4.	Future work
In the future, this project may be optimized in terms of coding and deployment.

* Software method: use Spring Data Redis and WebFlux asynchronous method for code refactoring.

* Deployment method:

#### Usually deploy solutions
Since the project is very small and it is not urgent to split the project, it is mainly for single application scaling.

![image](https://github.com/jade75/interview-assignments/blob/master/java/deploy1.jpg?raw=true)


#### Deploy to AWS solution 1
The deployment plan shown in the figure below relies on AWS service components.
S3 is responsible for storing code and triggering updates or publishing to Lambda.
Lambda provides serverless computing containers. You only need to add Lambda development kits and inherit specific classes to achieve computing functions. It supports development languages such as JAVA, NodeJS, and Python. Lambda can automatically expand or contract the computing service according to the set parameters.
API Gateway binds Lambda's URL app to provide external API functions. At the same time, API Gateway supports content caching, access limiting speed and other functions.
DynamoDB provides key-value storage services, and the service supports backup, automatic fault handling capabilities, etc.

![image](https://github.com/jade75/interview-assignments/blob/master/java/lambda.jpg?raw=true)


#### Deploy to AWS solution 2
AWS solution 2 tiny URL application is deployed with docker container technology, and AWS ECR is responsible for the storage management of docker application image. AWS ECS is responsible for the operation and management of images. ECS can use Fargate server less computing or traditional virtual host EC2 to run container services. ECS can dyFnamically expand or shrink the server according to the set threshold, and has a perfect monitoring mechanism and alarm mechanism.

![image](https://github.com/jade75/interview-assignments/blob/master/java/deploy2.jpg?raw=true)



### References

1.	https://moz.com/blog/the-benefits-and-pitfalls-of-url-shorteners
2.	https://www.goneon.lu/why-you-should-use-url-shorteners/
3.	https://onpassive.com/blog/what-are-the-advantages-and-disadvantages-of-url-shorteners/
4.	https://dzone.com/articles/url-shortener-detailed-explanation
