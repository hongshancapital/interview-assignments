//
//  InterviewDemoTests.swift
//  InterviewDemoTests
//
//  Created by 陈凝 on 2022/8/1.
//

import XCTest
@testable import InterviewDemo

final class InterviewDemoTests: XCTestCase {

    func test_countSmallerThen50_canLoadMore(){
        let sut = HomeViewModel()
        
        sut.homeDataListFetchCount = 49
        
        let expected = true
        
        let actual = sut.canLoadMore()
        
        XCTAssertEqual(expected, actual)
    }
    
    func test_countGreaterThen50_canLoadMore(){
        let sut = HomeViewModel()
        
        sut.homeDataListFetchCount = 51
        
        let expected = false
        
        let actual = sut.canLoadMore()
        
        XCTAssertEqual(expected, actual)
    }
    
    func test_afterRefresh_countEqual20() async{
        let sut = HomeViewModel()
        
        sut.homeDataListFetchCount = 51
        
        //reset to 10,after network,it plus 10 = 20
        let expected = 20
        
        await sut.refresh()
        
        let actual = sut.homeDataListFetchCount
        
        XCTAssertEqual(expected, actual)
    }
    
    func test_fetchData_withSpecifiedCount() async{
        let sut = HomeViewModel()
        
        sut.homeDataListFetchCount = 34
        
        let expected = 34
        
        await sut.fetchHomeDataList()
        
        let actual = sut.homeDataList?.count ?? 0
        
        XCTAssertEqual(expected, actual)
    }
}
