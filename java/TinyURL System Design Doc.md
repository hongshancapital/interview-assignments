# TinyURL System Design Doc
## Background
This is a homework before I enter the interview process of SCDT-China. The goal is to design and implement a group of Tiny URL backend service APIs.
## Requirement
### Functional Requirements
1. Maximum length of TinyURL is 8;
2. Coding part, use SpringBoot framework, integrated with Swagger API;
3. Testing part, use JUnits framework, integrated with Jacoco;
4. Data storage part, store data in JVM is enough, but need to avoid JVM memory overflow.
### Non-Functional Requirements
1. Performance part, a performance test solution and result is a plus.
### Goal
1. Submit the neat source code according to production level requirements, with gitignore to filter out unnecessary submissions, such as ".class" files;
2. Jacoco unit test result snapshot with bar of 85%+ for both line coverage and branch coverage;
3. A Markdown formatted design doc with thoughts on architecture and design with assumption.
## Use Cases
### 1. Transform Original URL to Tiny URL
As a user of TinyURL, I'll be able to use TinyURL product to transform my original URL to tiny URL.
### 2. Transform Tiny URL to Original URL
As a user of TinyURL, I'll be able to use TinyURL product to take-back my original URL using the Tiny URL it returned to me in use case 1.
### Use Case Graph
![](https://huatu.98youxi.com/markdown/work/uploads/upload_7d71147d4a79c246e1c0c3e5827b82d8.jpg)

## Scope & Assumption
### Latency
#### Assumption and In Scope
Latency for use case 1 (transform original URL to tiny URL) should be low, specifically on API level, it should be on 10 ms level or lower (TP90);
Latency for use case 2 (transform tiny URL to original URL) should be low, specifically on API level, it should be on 1 ms level or lower (TP90).
### Scalability
#### Assumption and In Scope
The system should be scalable for both larger TPS and larger data storage, specifically on infra/hardware side, we can consider to deploy the system onto a higher performance server; specifically on data storage side, we can consider to transform original URL into 100M (Mega) level tiny URL as standard configure via 10 bits encoding, when data storage comes larger, we can scale the system to support 1P (Pita) level tiny URL as higher configure via 62 bits encoding.
#### Out of Scope
Distributed deployment mode is out of scope of this design.
### Concurrency
#### Assumption and In Scope
The system should be able to support 1G (Giga) level read concurrency, and 100M (Mega) level write concurrency on stand-alone PC/server.
### Timeout
#### Out of Scope
The timeout of the mapping relationship between original URL and tiny URL is out of scope of this design.
### Security
#### Out of Scope
Security requirement is not clear yet, hence -
1. The data security and encryption of the data transmission between client and server is out of scope of this design;
2. The data security and encryption of the data storage of the mapping relationship between original URL and tiny URL are out of scope of this design.
### Compliance
Compliance requirement is not clear yet, hence out of scope of this design.
### Data Audit
Data Audit requirement is not clear yet, hence out of scope of this design.
## Architecture
![](https://huatu.98youxi.com/markdown/work/uploads/upload_fe36234191fc7e8468e26cc60e7c5f4a.jpg)

### TinyURL Portal
This is a frontend web portal to support common customers who will access TinyURL system via web browser. Basically, it includes the frontend resources, such as TS/JS/HTML/CSS files. This is out of scope of this design doc, will not elaborate more here.

### TinyURL Gateway Service
This is a Restful service to support professional customers who will access TinyURL system via restful API. Basically, it includes functions like AuthN & AuthZ, Request Queue management, Throttle management, etc. This is out of scope of this design doc, will not elaborate more here.

### TinyURL Service
This is a Restful service to support the requests -
1. Transform customer's original URL to tiny URL, process diagram as below
![](https://huatu.98youxi.com/markdown/work/uploads/upload_1e544d7196081a2dc8217f70d93a20d3.jpg)


2. Transform tiny URL to customer's original URL
This is a simple get method, in case the queried tiny URL is existed in the system, the API will return the original URL, otherwise, null value will be returned to tell Customer the tiny URL is invalid which does not map to any original URL.

Comparison among different transforming modes


|  | Incremental ID (10 Hexadecimal) | Incremental ID (62 Hexadecimal) | Random ID (10 Hexadecimal) |
| -------- | -------- | -------- | -------- |
| Maximum Count of Tiny URL      | 100M     | ~2.2PB     | 100M     | 
| Calculation Velocity     | Fast     | Slow     | Slow     |
| Security     | Low     | Low     | High     |


### TinyURL Data Storage Service
This is a Restful service to support the data storage. If the system have to be scaled to use 3rd party data storage framework like Redis/Amazon DDB, this service will encapsulate the framework(s) and will expose read/write APIs for other service to call. Given the current requirement of restoring the data into memory is acceptable, this service is out of scope of this design doc, will not elaborate more here.

### TinyURL Management Service
This is an internal management service to support internal management like calculation and maintenance of tiny URL, timeout management, service mode cold/hot switching, etc. This is out of scope of this design doc, will not elaborate more here.

## Appendix
### Thoughts on system optimization in the future
1. Use distributed computation
The current design restore mapping data in computer memory, in the future, when the system requests higher availability, reliability and throughput, distributed computation is a potential direction of optimization. This optimization could be included in each service the above doc mentioned.

2. Data partition
The current design has got only one partition, the system could consider to parition the data storage into different regions, such as in countries or regions, this will help on the higher and higher regulation of data protection trend, and reduce the risk of single point failure. This optimization could be included in the service of "TinyURLDataStorageService" which is not implementated in the current phase.

3. Calculate the unused tiny URL asynchronously 
The current design use real time calculation to get a tiny URL, when concurrency becomes a problem in the future, this system could try to asynchronously pre-compute the unused tiny URLs and restore them in a pool, any new request to get a unused tiny URL could directly pulled from the pool instead of real time calculate with risk of conflict, this will help on enhancing the conversion efficiency. This optimization could be included in the service of "TinyURLManagementService" which is not implemented in the current phase.

4. Only requester of the tiny URL has permission of getting original URL
The current design does not include the consideration of customer's data access control, anyone who guess/try a valid tiny URL could get corresponding original URL, hence this system could consider to add the "creator - tiny URL" mapping relationship in data storage to prevent other customer unexpective getting original URL. This optimization could be plugged in the service of "TinyURLGatewayService" which is not implemented in the current phase.



