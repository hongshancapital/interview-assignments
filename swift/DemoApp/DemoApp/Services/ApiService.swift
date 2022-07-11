    //
    //  ApiService.swift
    //  DemoApp
    //
    //  Created by Gao on 2022/7/11.
    //

import Foundation
import Combine
import SwiftUI

class ApiService {
    let userDefault = UserDefaults.standard
    var favoriteArr: [Bool]? = nil
    
        // 获取列表数据
    func getListData() -> AnyPublisher<[AppModel], Error> {
        return Future<[AppModel], Error> { promise in
            promise(.success(ModelData().appListData))
            self.initFavoriteArr(ModelData().appListData.count)
        }.eraseToAnyPublisher()
    }
    
    //MARK: favorite
    func initFavoriteArr(_ count: Int) {
        if favoriteArr == nil && count > 0 {
            favoriteArr = [Bool](repeating: false, count: count)
            userDefault.set(favoriteArr, forKey: "favoriteArr")
        }
    }

    func getFavoriteData() ->[Bool] {
        let favoriteArr = userDefault.object(forKey: "favoriteArr") as? [Bool]
        guard favoriteArr != nil else{
            return []
        }
        return favoriteArr!
    }
    
    func updateFavoriteArr(index: Int, isFavorite: Bool) -> AnyPublisher<[Bool], Never> {
        var favoriteArr = userDefault.object(forKey: "favoriteArr") as? [Bool]
        guard favoriteArr != nil && index >= 0 && index < favoriteArr!.count else{
            return Just([]).eraseToAnyPublisher()
        }
        favoriteArr![index] = isFavorite
        userDefault.set(favoriteArr, forKey: "favoriteArr")
        return Just(favoriteArr!).eraseToAnyPublisher()
    }
    
    
}
