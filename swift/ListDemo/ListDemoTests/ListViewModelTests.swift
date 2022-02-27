//
//  ListViewModelTests.swift
//  ListDemoTests
//
//  Created by Chr1s on 2022/2/25.
//

import XCTest
import Combine
@testable import ListDemo

class ListViewModelTests: XCTestCase {

    var vm: ListViewModel?
    var service: MockAPIService = MockAPIService()
    var cells: [ListCell] = []
    
    var cancellables = Set<AnyCancellable>()
    
    override func setUp() {
        vm = .init(dataService: service)
    }

    // 测试在3次内接受完30条列表数据
    func test_ListViewModel_addListData_shouldLoadCorrectWith3Times() {
        
        XCTAssertEqual(vm!.isLoading, .Loading, "isLoading init state should be `Loading` ")
        
        var promise = expectation(description: "fetch first 10 items")
        vm!.$datas
            .sink(receiveValue: { [weak self] value in
                self?.cells.append(contentsOf: value)
                if value.count > 0{
                    promise.fulfill()
                }
            })
            .store(in: &cancellables)
        
        wait(for: [promise], timeout: 1)
        XCTAssertEqual(cells.count, 10)

        vm!.$isFavorites
            .sink { value in
                XCTAssertEqual(value.count, 30, "isFavorites count should be 30")
                for i in 0..<30 {
                    XCTAssertEqual(value[i], false)
                }
            }
            .store(in: &cancellables)

        XCTAssertEqual(vm!.isLoading, .LoadMore, "isLoading init state should be `LoadMore` ")

        vm!.loadMoreSubject.send()
        promise = expectation(description: "fetch more 10 items")
        vm!.$datas
            .sink { [weak self] value in
                self?.cells.append(contentsOf: value)
                promise.fulfill()
            }
            .store(in: &cancellables)
        
        wait(for: [promise], timeout: 2)
        XCTAssertEqual(cells.count, 20)
        
        XCTAssertEqual(vm!.isLoading, .LoadMore, "isLoading init state should be `LoadMore` ")
        
        vm!.loadMoreSubject.send()
        promise = expectation(description: "fetch last 10 items")
        vm!.$datas
            .sink { [weak self] value in
                self?.cells.append(contentsOf: value)
                promise.fulfill()
            }
            .store(in: &cancellables)
        
        wait(for: [promise], timeout: 4)
        XCTAssertEqual(cells.count, 30)
        
    }
    
    func test_ListViewModel_updateLikeSubject() {
        
        for i in 0..<30 {
            vm!.updateLikeSubject.send((i, true))
            
            let promise = expectation(description: "update \(i) favorite to be true")
            vm!.$isFavorites
                .sink( receiveValue: { value in
                    XCTAssertEqual(value.count, 30)
                    if value[i] == true {
                        promise.fulfill()
                    }
                })
                .store(in: &cancellables)
            
            wait(for: [promise], timeout: 1)
        }
    }
}
