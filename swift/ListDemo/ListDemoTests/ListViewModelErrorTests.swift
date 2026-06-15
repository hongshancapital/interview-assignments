//
//  ListViewModelErrorTests.swift
//  ListDemoTests
//
//  Created by Chr1s on 2022/3/1.
//

import XCTest
import Combine
@testable import ListDemo

class ListViewModelErrorTests: XCTestCase {
    
    var vm: ListViewModel?
    var service: MockWithErrorAPIService?
    var errorMessage: String = ""
    var cancellables = Set<AnyCancellable>()
    
    // 测试获取列表数据失败情况(fileNotExist)
    func test_ListViewModel_fetchList_WithError_FileNotExist() {
        service = MockWithErrorAPIService(errorType: 1)
        vm = .init(dataService: service!)

        XCTAssertNil(vm!.errorMessage)
        let promise = expectation(description: "fetch should has an file not exist error")
        vm!.$errorMessage
            .sink { [weak self] value in
                if let value = value {
                    self?.errorMessage = value
                    promise.fulfill()
                }
            }
            .store(in: &cancellables)
        wait(for: [promise], timeout: 3)
        XCTAssertEqual(errorMessage, "File not Exist!")
    }
    
    // 测试获取列表数据失败情况(unknown)
    func test_ListViewModel_fetchList_WithError_unknownError() {
        service = MockWithErrorAPIService(errorType: 4)
        vm = .init(dataService: service!)
        
        XCTAssertNil(vm!.errorMessage)
        
        let promise = expectation(description: "fetch should has an unknown error")
        vm!.$errorMessage
            .sink { [weak self] value in
                if let value = value {
                    self?.errorMessage = value
                    promise.fulfill()
                }
            }
            .store(in: &cancellables)
        wait(for: [promise], timeout: 3)
        XCTAssertEqual(errorMessage, "[Error unknown]")
    }
    
    // 测试获取列表数据失败情况(fileReadError)
    func test_ListViewModel_fetchList_WithError_fileReadError() {
        service = MockWithErrorAPIService(errorType: 2)
        vm = .init(dataService: service!)
        
        XCTAssertNil(vm!.errorMessage)

        let promise = expectation(description: "fetch should has an file read error")
        vm!.$errorMessage
            .sink { [weak self] value in
                if let value = value {
                    self?.errorMessage = value
                    promise.fulfill()
                }
            }
            .store(in: &cancellables)
        wait(for: [promise], timeout: 3)
        XCTAssertEqual(errorMessage, "File read error: The operation couldn’t be completed. (2 error 2.)")
    }
    
    // 测试获取列表数据失败情况(fileReadError)
    func test_ListViewModel_fetchList_WithError_decodeError() {
        service = MockWithErrorAPIService(errorType: 3)
        vm = .init(dataService: service!)
        
        XCTAssertNil(vm!.errorMessage)

        let promise = expectation(description: "fetch should has an decode error")
        vm!.$errorMessage
            .sink { [weak self] value in
                if let value = value {
                    self?.errorMessage = value
                    promise.fulfill()
                }
            }
            .store(in: &cancellables)
        wait(for: [promise], timeout: 3)
        XCTAssertEqual(errorMessage, "File decode error: The operation couldn’t be completed. (3 error 3.)")
    }    
}
