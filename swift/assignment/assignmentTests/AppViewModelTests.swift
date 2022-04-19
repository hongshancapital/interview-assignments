//
//  AppViewModelTests.swift
//  assignmentTests
//
//  Created by 干饭人肝不完DDL on 2022/4/19.
//

import XCTest
@testable import assignment
import Combine

class AppViewModelTests: XCTestCase {
    var vm: AppViewModel?
    var cancellable = Set<AnyCancellable>()
    
    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
        vm = AppViewModel()
    }
    
    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }
    
    func test_AppViewModel_isLoading_SholdBeTrue(){
        if let vm = vm{
            XCTAssertTrue(vm.isLoading)
        }else{
            XCTFail()
        }
    }
    func test_AppViewModel_getResult_ShouldSetResultsCorrect(){
        if let vm = vm{
            let expectation = XCTestExpectation(description: "load result")
            DispatchQueue.main.asyncAfter(deadline: .now()+2) {
                expectation.fulfill()
            }
            
            wait(for: [expectation], timeout: 5)
            XCTAssertNotNil(vm.results)
        }else {
            XCTFail()
        }
    }
    
    func test_AppViewModel_getResult_ShouldSetAppsCorrect(){
        if let vm = vm {
            var i = 0
            DispatchQueue.main.asyncAfter(deadline: .now()+2){
                vm.$apps
                    .dropFirst()
                    .sink(receiveValue: { _ in
                        Timer
                            .publish(every: 1, on: .main, in: .common)
                            .sink { _ in
                                i = i+1
                                XCTAssertEqual(vm.apps.count, i)
                            }
                            .store(in: &self.cancellable)
                    })
                    .store(in: &self.cancellable)
            }
        }else{
            XCTFail()
        }
    }
    
    
}
