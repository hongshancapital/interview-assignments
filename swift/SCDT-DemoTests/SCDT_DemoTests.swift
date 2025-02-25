//
//  SCDT_DemoTests.swift
//  SCDT-DemoTests
//
//  Created by wuzhe on 10/17/22.
//

import XCTest
import Combine

@testable import SCDT_Demo

class SCDT_DemoTests: XCTestCase {
    var cancellables = Set<AnyCancellable>()
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        try super.setUpWithError()
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
        try super.tearDownWithError()
    }
    
    func testNetApiBadResponse() throws {
        let expectation = XCTestExpectation(description: "Bad Response.")
        
        let mockRes = (Data(), HTTPURLResponse(url: URL(string: "https://www.google.com")!, statusCode: 400, httpVersion: "2.0", headerFields: nil)! as URLResponse)
        let pub = Just<NetApi.DTOutput>(mockRes).setFailureType(to: NetApi.DTFail.self)
        NetApi.shared.mapDataTaskPubisher(pub.eraseToAnyPublisher())
            .receive(on: DispatchQueue.main)
            .sink { completion in
                switch completion{
                case .failure(let err):
                    let testerror = err as? NetApiError
                    XCTAssertEqual(testerror?.description.starts(with:"Invalid Response"), true)
                    expectation.fulfill()
                    
                case .finished:
                    print("reload data finished")
                }
            } receiveValue: { result in
                
            }
            .store(in: &cancellables)
        
        wait(for: [expectation], timeout: 5.0)
        
    }
    
    func testNetApiDecodeError() throws {
        let expectation = XCTestExpectation(description: "Bad json.")
        
        let mockRes = ("{}".data(using: .utf8)!, HTTPURLResponse(url: URL(string: "https://www.google.com")!, statusCode: 200, httpVersion: "2.0", headerFields: nil)! as URLResponse)
        let pub = Just<NetApi.DTOutput>(mockRes).setFailureType(to: NetApi.DTFail.self)
        NetApi.shared.mapDataTaskPubisher(pub.eraseToAnyPublisher())
            .receive(on: DispatchQueue.main)
            .sink { completion in
                switch completion{
                case .failure(let err):
                    let testerror = err as? NetApiError
                    XCTAssert(testerror!.description.starts(with:"Invalid JSON"))
                    expectation.fulfill()
                case .finished:
                    print("reload data finished")
                }
            } receiveValue: { result in
                
            }
            .store(in: &cancellables)
        
        wait(for: [expectation], timeout: 5.0)
        
    }
    
    func testNetApiReturnCorrect() throws {
        let expectation = XCTestExpectation(description: "Give 3 app.")
        let mock = AppListResponse(results: [AppInfomation(id:1,title:"BUmble",subtitle:"Millisons of people have signed up for Bumble to start building valuable relationships", thumbnail: "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/c2/1d/14/c21d1495-6354-ca11-82ce-6695ea9d88fa/AppIcon-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg"),
                                             AppInfomation(id:2,title:"BUmble2",subtitle:"Millisons of people have signed up for Bumble to start building valuable relationships", thumbnail: "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/c2/1d/14/c21d1495-6354-ca11-82ce-6695ea9d88fa/AppIcon-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg"),
                                             AppInfomation(id:3,title:"BUmble3",subtitle:"Millisons of people have signed up for Bumble to start building valuable relationships", thumbnail: "https://is2-ssl.mzstatic.com/image/thumb/Purple122/v4/c2/1d/14/c21d1495-6354-ca11-82ce-6695ea9d88fa/AppIcon-1x_U007emarketing-0-7-0-85-220.png/512x512bb.jpg")], hasMore: true, page: 1)
        let data = try? JSONEncoder().encode(mock)
        XCTAssertNotNil(data)
        let mockRes = (data!, HTTPURLResponse(url: URL(string: "https://www.google.com")!, statusCode: 200, httpVersion: "2.0", headerFields: nil)! as URLResponse)
        let pub = Just<NetApi.DTOutput>(mockRes).setFailureType(to: NetApi.DTFail.self)
        NetApi.shared.mapDataTaskPubisher(pub.eraseToAnyPublisher())
            .receive(on: DispatchQueue.main)
            .sink { completion in
                switch completion{
                case .failure(let err):
                    let testerror = err as? NetApiError
                    XCTFail()
                case .finished:
                    print("reload data finished")
                }
            } receiveValue: { result in
                XCTAssert(result.hasMore)
                XCTAssertEqual(result.page, 1)
                XCTAssertEqual(result.results.count, 3)
                expectation.fulfill()
            }
            .store(in: &cancellables)

        
        wait(for: [expectation], timeout: 5.0)
    }
    
    func testAppListViewModelLoad() throws {
        let expectation = XCTestExpectation(description: "apps")
        let expectation2 = XCTestExpectation(description: "hasmore")
        let appVM = AppListViewModel()
            
        appVM.$appInfomations
            .dropFirst()
            .first()
            .sink { value in
                XCTAssertGreaterThan(value.count, 0)
                expectation.fulfill()
            }
            .store(in: &cancellables)
        
        appVM.$hasMore
            .dropFirst()
            .first()
            .sink { value in
                XCTAssert(value)
                expectation2.fulfill()
            }
            .store(in: &cancellables)
        
        appVM.reload()
        wait(for: [expectation, expectation2], timeout: 30.0)

    }
}
