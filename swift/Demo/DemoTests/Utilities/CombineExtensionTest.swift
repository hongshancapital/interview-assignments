//
//  CombineExtensionTest.swift
//  DemoTests
//
//  Created by 葬花桥 on 2023/3/16.
//

import XCTest
@testable import Demo
import Combine

final class CombineExtensionTest: XCTestCase {
    var sut: PassthroughSubject<Bool, NSError>!
    private var subscribers = Set<AnyCancellable>()
    
    override func setUp() {
        super.setUp()
        sut = .init()
    }
    
    func test_weakSinkOn() {
        let `self` = SinkObject()
        sut.weakSinkOn(self) { (self, completion) in
            self.fulfill()
        } receiveValue: { (self, result) in
            XCTAssertTrue(result)
            self.fulfill()
        }.store(in: &subscribers)
        
        sut.send(true)
        sut.send(completion: .finished)
        wait(for: [self.exp], timeout: 1)
    }
    
    func test_weakSinkOn_deinit() {
        var `self`: SinkObject? = SinkObject()
        self!.exp.isInverted = true
        let exp = self!.exp
        sut.weakSinkOn(self!) { (self, completion) in
            self.fulfill()
        } receiveValue: { (self, result) in
            XCTAssertTrue(result)
            self.fulfill()
        }.store(in: &subscribers)
        
        self = nil
        
        sut.send(true)
        sut.send(completion: .finished)
        wait(for: [exp], timeout: 1)
    }
    
    
    fileprivate class SinkObject {
        var result = false {
            didSet {
                exp.fulfill()
            }
        }
        let exp: XCTestExpectation
        init(function: String = #function) {
            exp = XCTestExpectation(description: function)
        }
        func fulfill() {
            exp.fulfill()
        }
    }

}
