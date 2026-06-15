//
//  DataProtocol.swift
//  ListDemo
//
//  Created by Chr1s on 2022/2/24.
//

import Foundation
import Combine

protocol ApiProtocol {
    func fetchListData() -> AnyPublisher<[ListCell], FetchListError>
    func fetchFavoriteData() -> [Bool]
    func updateListData(id: Int, isFavorite: Bool) -> AnyPublisher<[Bool], Never> 
}
