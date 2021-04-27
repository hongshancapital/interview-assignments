let request = require('request');


describe('/toolbox/create & /toolbox', function() {

    test.concurrent('case 1: create when tool is null. it will prompt error message with http code 200.', function(){
        var testData = { name: "hello", tools: ""}

        var p = new Promise(function(resolve) {
            request.post({
                url: 'http://localhost:5000/toolbox/create',
                form: testData,
                async : false
            }, function(error, response, body) {
                resolve(response);

            })
        });

        return p.then(function(res) {
            // console.log(res.body)
            expect(res.statusCode).toBe(400);
            expect(JSON.parse(res.body).error).toBe('No tools specified');

        });
    });

    test.concurrent('case 2: create when user name exists, it will be changed successfully.', function() {
        var testData1 = { name: "lingling", tools: "Selenium"}
        var testData2 = { name: "lingling", tools: "E2E"}

        // first create
        var p = new Promise(function(resolve) {
            request.post({
                url: 'http://localhost:5000/toolbox/create',
                form: testData1,
                async : false
            }, function(error, response, body) {
                resolve(response);
            })
        });

        return p.then(function(res) {
            // console.log(res.body)
            expect(res.statusCode).toBe(200);
            expect(JSON.parse(res.body).message).toBe('success');

            // second create
            var p2 = new Promise(function(resolve) {
                request.post({
                    url: 'http://localhost:5000/toolbox/create',
                    form: testData2,
                    async : false
                }, function(error, response, body) {
                    resolve(response);
                })
            })
            return p2.then(function(res) {
                // console.log(res.body)
                expect(res.statusCode).toBe(200);
                expect(JSON.parse(res.body).message).toBe('success');

                var p3 = new Promise(function(resolve) {
                    request.get({
                        url: 'http://localhost:5000/toolbox',
                        async : false
                    }, function(error, response, body) {
                        resolve(response);
                    })
                })
                return p3.then(function(res) {
                    // console.log(res.body)
                    var result = JSON.parse(res.body)
                    var isFind = false
                    var tool = ""
                    result.data.forEach(item => {
                        if (item.name == testData2.name) {
                            isFind = true
                            tool = item.tools
                        }
                    })
                    expect(res.statusCode).toBe(200);
                    expect(result.message).toBe('success');
                    expect(isFind).toBe(true);
                    expect(tool).toBe(testData2.tools);
                })
            })

        });

    });

    test.concurrent('case 3: request create api twice, the two will be saved.', function() {
        var testData1 = { name: "brady", tools: "Selenium"}
        var testData2 = { name: "alen", tools: "E2E"}

        // first create
        var p = new Promise(function(resolve) {
            request.post({
                url: 'http://localhost:5000/toolbox/create',
                form: testData1,
                async : false
            }, function(error, response, body) {
                resolve(response);
            })
        });

        return p.then(function(res) {
            // console.log(res.body)
            expect(res.statusCode).toBe(200);

            // second create
            var p2 = new Promise(function(resolve) {
                request.post({
                    url: 'http://localhost:5000/toolbox/create',
                    form: testData2,
                    async : false
                }, function(error, response, body) {
                    resolve(response);
                })
            })
            return p2.then(function(res) {
                // console.log(res.body)
                expect(res.statusCode).toBe(200);

                var p3 = new Promise(function(resolve) {
                    request.get({
                        url: 'http://localhost:5000/toolbox',
                        async : false
                    }, function(error, response, body) {
                        resolve(response);
                    })
                })
                return p3.then(function(res) {
                    //   console.log(res.body)
                    var result = JSON.parse(res.body)
                    var findCount = 0
                    result.data.forEach(item => {
                        if (item.name == testData1.name || item.name == testData2.name) {
                            findCount++;
                        }
                    })
                    expect(res.statusCode).toBe(200);
                    expect(findCount).toBe(2);
                })
            })
        });
    });

    test.concurrent('case 4: create when user is null, it will prompt error message with http code 200.', function() {
        var testData = { name: "", tools: "E2E"}

        var p = new Promise(function(resolve) {
            request.post({
                url: 'http://localhost:5000/toolbox/create',
                form: testData,
                async : false
            }, function(error, response, body) {
                resolve(response);
            })
        });

        return p.then(function(res) {
            expect(res.statusCode).toBe(400);
            expect(JSON.parse(res.body).error).toBe('No name specified');
        })
    });

});

describe('/toolbox/options', function() {

    test.concurrent('case 1: api return list data with http code 200.', function() {

        var p = new Promise(function(resolve) {
            request.get({
                url: 'http://localhost:5000/toolbox/options',
                async : false
            }, function(error, response, body) {
                resolve(response);
            })
        });

        return p.then(function(res) {
            expect(res.statusCode).toBe(200);
            expect(JSON.parse(res.body).length >= 1).toBe(true);
        })
    });

});


describe('data security testing', function() {

    test.concurrent('case 1: SQL injection', function() {

        var testData = { name: "concat('bar',(select @@version)),'E2E') --", tools: 'E2E'}

        var p = new Promise(function(resolve) {
            request.post({
                url: 'http://localhost:5000/toolbox/create',
                form: testData,
                async : false
            }, function(error, response, body) {
                resolve(response);
            })
        });

        return p.then(function(res) {
            expect(res.statusCode).toBe(200);

            var p2 = new Promise(function(resolve) {
                request.get({
                    url: 'http://localhost:5000/toolbox',
                    async : false
                }, function(error, response, body) {
                    resolve(response);
                })
            })
            return p2.then(function(res) {

                var result = JSON.parse(res.body)
                var isFind = false
                var tool = ""
                result.data.forEach(item => {
                    if (item.name == testData.name) {
                        isFind = true
                    }
                })
                expect(res.statusCode).toBe(200);
                expect(isFind).toBe(true);
            })
        })
    });

});
