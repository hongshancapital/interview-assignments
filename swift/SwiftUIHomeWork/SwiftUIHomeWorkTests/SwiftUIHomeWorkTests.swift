//
//  SwiftUIHomeWorkTests.swift
//  SwiftUIHomeWorkTests
//
//  Created by Yu jun on 2022/6/25.
//

import XCTest
@testable import SwiftUIHomeWork

class SwiftUIHomeWorkTests: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
    }
    ///正常请求app列表分页数据
    func testGetMessgaeNormal() throws {
        let e = expectation(description: "GetAppMessage Completed")
        APIClient.shareClient.getAppMessage(page: "1", pageNumber: "10") { responseDict, error in
            XCTAssertNil(error)
            guard let dict = responseDict else {
                XCTFail()
                return
            }
            guard let count = dict["count"] as? Int else {
                XCTFail()
                return
            }
            guard let showAppList = dict["showAppList"] as? [AppMessageModel] else {
                XCTFail()
                return
            }
            XCTAssertEqual(count, 22)
            XCTAssertEqual(showAppList.count, 10)
            e.fulfill()
        }
        waitForExpectations(timeout: 5)
    }
    ///请求app列表，页数传入过大
    func testGetMessgaeWithBigPage() throws {
        let e = expectation(description: "GetAppMessage Empty")
        APIClient.shareClient.getAppMessage(page: "10", pageNumber: "10") { responseDict, error in
            XCTAssertNil(error)
            guard let dict = responseDict else {
                XCTFail()
                return
            }
            guard let count = dict["count"] as? Int else {
                XCTFail()
                return
            }
            guard let showAppList = dict["showAppList"] as? [AppMessageModel] else {
                XCTFail()
                return
            }
            XCTAssertEqual(count, 22)
            XCTAssertEqual(showAppList.count, 0)
            e.fulfill()
        }
        waitForExpectations(timeout: 5)
    }
    ///请求app列表传参有误
    func testGetMessgaeWithErrorParames() throws {
        let e = expectation(description: "GetAppMessage Error")
        APIClient.shareClient.getAppMessage(page: "abc", pageNumber: "10") { responseDict, error in
            XCTAssertNil(responseDict)
            guard let ns_error = error as? NSError else {
                XCTFail()
                return
            }
            
            XCTAssertEqual(ns_error.code, 10006)
            e.fulfill()
        }
        waitForExpectations(timeout: 5)
    }
    ///正常点赞
    func testSelectedFavoriteToAllNormal() throws {
        let e = expectation(description: "SelectedFavoriteToAll Completed")
        var messageModel = AppMessageModel(id: 382617920, trackName: "Viber Messenger: Chats & Calls", artworkUrl60: "https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/cb/c7/94/cbc79405-0d10-6dcb-af1b-4f74be6d2d6d/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/60x60bb.jpg", description: "Viber is a secure, private, fun messaging and calling app, connecting over a billion people worldwide!\n\nWith group chats, disappearing messages, reminders, and more, you can do it all with Viber!\n\nMake Free Audio and Video Calls\nEnjoy unlimited Viber-to-Viber calls with up to 50 people and make crystal-clear audio and video calls to anyone in the world. ")
        messageModel.isFavorite = true
        APIClient.shareClient.selectedFavoriteToAll(model: messageModel) { error in
            XCTAssertNil(error)
            e.fulfill()
        }
        waitForExpectations(timeout: 5)
    }
    ///点赞传入id有误，查不到数据
    func testSelectedFavoriteToAllError() throws {
        let e = expectation(description: "SelectedFavoriteToAll Completed")
        var messageModel = AppMessageModel(id: 38261, trackName: "Viber Messenger: Chats & Calls", artworkUrl60: "https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/cb/c7/94/cbc79405-0d10-6dcb-af1b-4f74be6d2d6d/AppIcon-0-0-1x_U007emarketing-0-0-0-7-0-0-sRGB-0-0-0-GLES2_U002c0-512MB-85-220-0-0.png/60x60bb.jpg", description: "Viber is a secure, private, fun messaging and calling app, connecting over a billion people worldwide!\n\nWith group chats, disappearing messages, reminders, and more, you can do it all with Viber!\n\nMake Free Audio and Video Calls\nEnjoy unlimited Viber-to-Viber calls with up to 50 people and make crystal-clear audio and video calls to anyone in the world. ")
        messageModel.isFavorite = true
        APIClient.shareClient.selectedFavoriteToAll(model: messageModel) { error in
            guard let ns_error = error as? NSError else {
                XCTFail()
                return
            }
            
            XCTAssertEqual(ns_error.code, 10008)
            e.fulfill()
        }
        waitForExpectations(timeout: 5)
    }
    
    ///测试hw_performDataTask方法
    func testhw_performDataTask() throws {
        let e = expectation(description: "hw_performDataTask Completed")
        let request = NSMutableURLRequest(url: URL(string: "https://www.baid.com/getAppMessage")!)
        let urlSession = URLSession(configuration: URLSessionConfiguration.default, delegate: HWURLSessionDelegate(), delegateQueue: OperationQueue.main)
        let parameters = ["page": "1", "pageNumber": "10"] as [String: Any]
        var jsonData: Data?
        do {
            jsonData = try JSONSerialization.data(withJSONObject: parameters, options: JSONSerialization.WritingOptions.prettyPrinted)
        } catch {
            print("转换json失败:\(error)")
        }
        request.httpBody = jsonData
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.timeoutInterval = 30
        urlSession.hw_performDataTask(with: request as URLRequest) { modellist, response, error in
            XCTAssertNil(error)
            XCTAssertNil(response)
            e.fulfill()
        }
        waitForExpectations(timeout: 5)
    }
    ///测试hw_performDataTask方法,接口地址传输错误
    func testhw_performDataTaskError() throws {
        let e = expectation(description: "hw_performDataTask Error")
        let request = NSMutableURLRequest(url: URL(string: "https://www.baid.com/getAppMessage11111")!)
        let urlSession = URLSession(configuration: URLSessionConfiguration.default, delegate: HWURLSessionDelegate(), delegateQueue: OperationQueue.main)
        let parameters = ["page": "1", "pageNumber": "10"] as [String: Any]
        var jsonData: Data?
        do {
            jsonData = try JSONSerialization.data(withJSONObject: parameters, options: JSONSerialization.WritingOptions.prettyPrinted)
        } catch {
            print("转换json失败:\(error)")
        }
        request.httpBody = jsonData
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.timeoutInterval = 30
        urlSession.hw_performDataTask(with: request as URLRequest) { modellist, response, error in
            XCTAssertNil(modellist)
            XCTAssertNil(response)
            guard let ns_error = error as? NSError else {
                XCTFail()
                return
            }
            
            XCTAssertEqual(ns_error.code, 10009)
            e.fulfill()
        }
        waitForExpectations(timeout: 5)
    }
    
    ///测试hw_performDataTask方法,接口地址随意乱传
    func testhw_performDataTaskUrlEmpty() throws {
        let e = expectation(description: "hw_performDataTask Url Empty")
        let request = NSMutableURLRequest(url: URL(string: "123456")!)
        let urlSession = URLSession(configuration: URLSessionConfiguration.default, delegate: HWURLSessionDelegate(), delegateQueue: OperationQueue.main)
        let parameters = ["page": "1", "pageNumber": "10"] as [String: Any]
        var jsonData: Data?
        do {
            jsonData = try JSONSerialization.data(withJSONObject: parameters, options: JSONSerialization.WritingOptions.prettyPrinted)
        } catch {
            print("转换json失败:\(error)")
        }
        request.httpBody = jsonData
        request.httpMethod = "POST"
        request.setValue("application/json", forHTTPHeaderField: "Content-Type")
        request.timeoutInterval = 30
        urlSession.hw_performDataTask(with: request as URLRequest) { modellist, response, error in
            XCTAssertNil(modellist)
            XCTAssertNil(response)
            guard let ns_error = error as? NSError else {
                XCTFail()
                return
            }
            
            XCTAssertEqual(ns_error.code, 10005)
            e.fulfill()
        }
        waitForExpectations(timeout: 5)
    }

    
    


    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
