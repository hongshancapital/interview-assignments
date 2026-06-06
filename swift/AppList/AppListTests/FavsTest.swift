//
//  FavsTest.swift
//  AppListTests
//
//  Created by dc on 2022/3/23.
//

import XCTest
import Combine
@testable import AppList

class FavsTest: XCTestCase {
    
    private let FavoritesSaveKey = "FavoritesSaveKey"
    
    func test_saveFavoriteData () {
        let favs = ["111": false]
        UserDefaults.standard.set(favs, forKey: FavoritesSaveKey)
        UserDefaults.standard.synchronize()
        
        test_fetchFavoriteData()
    }
    
    func test_fetchFavoriteData () {
        let favs = UserDefaults.standard.object(forKey: FavoritesSaveKey) as? [String: Bool]
        XCTAssertNotNil(favs)
    }
    
    
    
    
}
