//
//  AuditionDemoTests.swift
//  AuditionDemoTests
//
//  Created by dian bao on 2022/7/28.
//

import XCTest
import Combine
@testable import AuditionDemo

class SequoiaSwiftDemoTests: XCTestCase {
    
    var nrLoader:NetResourceLoader!                     //网络资源加载器
    var plManager:PageLoaderManager!                    //分页加载器
    var dictCancellable: AnyCancellable!                //字典发布者
    var imgCancellable: AnyCancellable!                 //图片发布者
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        nrLoader = NetResourceLoader.sharedInstance
        plManager = PageLoaderManager.sharedInstance
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        nrLoader = nil
        plManager = nil
        dictCancellable = nil
        imgCancellable = nil
    }
    
    //MARK: -测试业务数据加载功能
    func testLoadBusinessData() throws
    {
        let netWorkExpection = expectation(description: "BusinessDateLoad")
        //在后台工作线程进行
        DispatchQueue.global().async{
            
            self.dictCancellable = self.nrLoader.dictPublisher(for: URL.init(string: "https://itunes.apple.com/search?entity=software&limit=50&term=chat")!)
                .sink(receiveCompletion: { completion in
                    
                    DispatchQueue.main.async {
                        switch completion {
                        case .failure(let error):
                            XCTFail("\(error.localizedDescription)")
                        case .finished:
                            break
                        }
                        netWorkExpection.fulfill()
                    }
                    
                }, receiveValue: { dict in
                    
                    XCTAssertNotNil(dict)
                    XCTAssertEqual(dict.object(forKey: "resultCount") as? Int, 50, "从服务器获取的返回总数不对！")
                    XCTAssertNotNil(dict.object(forKey: "results") as? [NSDictionary])
                    
                })
            
        }
        
        let result = XCTWaiter(delegate: self).wait(for: [netWorkExpection], timeout:  3)
        if result == .timedOut
        {
            print("超时")
        }
    }
    
    //MARK: -测试图片数据加载功能
    func testLoadImageData() throws
    {
        let netWorkExpection = expectation(description: "ImageDateLoad")
        //在后台工作线程进行
        DispatchQueue.global().async{

            self.imgCancellable = self.nrLoader.imgPublisher(for: URL.init(string: "https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/c1/1a/2c/c11a2c71-c250-d676-50ce-0848888b2c2c/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/512x512bb.jpg")!)
                .sink(receiveCompletion: { completion in
                    
                    DispatchQueue.main.async {
                        switch completion {
                        case .finished:
                            break
                        case .failure(let error):
                            XCTFail("\(error.localizedDescription)")
                        }
                        netWorkExpection.fulfill()
                    }
                }, receiveValue: { image in
                    
                    XCTAssertNotNil(image)
                    
                })
            
        }
        
        let result = XCTWaiter(delegate: self).wait(for: [netWorkExpection], timeout:  3)
        if result == .timedOut
        {
            print("超时")
        }
    }
    
    //MARK: -测试分页加载功能
    func testPageLoadData() throws
    {
        let backgroundWorkExpection = expectation(description: "PageLoad")
        //在后台工作线程进行
        DispatchQueue.global().async{
            let item:Item = Item.init(trackId: 0, trackName: "baodian", description: "hello", artworkUrl60: "https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/c1/1a/2c/c11a2c71-c250-d676-50ce-0848888b2c2c/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/512x512bb.jpg", artworkUrl100: "https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/c1/1a/2c/c11a2c71-c250-d676-50ce-0848888b2c2c/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/512x512bb.jpg", artworkUrl512: "https://is4-ssl.mzstatic.com/image/thumb/Purple112/v4/c1/1a/2c/c11a2c71-c250-d676-50ce-0848888b2c2c/logo_chat_2020q4_color-0-1x_U007emarketing-0-0-0-6-0-0-85-220.png/512x512bb.jpg")
            var items:[Item] = []
            for i in 0..<50                                                     //简单造50个测试条目，内容不影响分页加载功能
            {
                var newone = item
                newone.trackId = Int64(i)
                items.append(item)
            }
            self.plManager.results = items
            while(self.plManager.LoadingFinish == false)
            {
                self.plManager.LoadPageData()
            }
            backgroundWorkExpection.fulfill()
        }
        
        let result = XCTWaiter(delegate: self).wait(for: [backgroundWorkExpection], timeout:  1)
        if result == .timedOut
        {
            print("超时")
        }
        
        XCTAssertEqual(self.plManager.LoadingBegin, true , "加载分页状态错误！")
        XCTAssertEqual(self.plManager.LoadingFinish, true , "加载分页状态错误！")
        XCTAssertEqual(plManager.PageEndIndex, plManager.results.count , "加载分页数量错误！")
    }
    
    //no use
    /*
    func testExample() throws {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        // Any test you write for XCTest can be annotated as throws and async.
        // Mark your test throws to produce an unexpected failure when your test encounters an uncaught error.
        // Mark your test async to allow awaiting for asynchronous code to complete. Check the results with assertions afterwards.
    }
    
    func testPerformanceExample() throws {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }
    */
}
