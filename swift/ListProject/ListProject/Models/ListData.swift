//
//  ListData.swift
//  ListProject
//
//  Created by shencong on 2022/6/9.
//

import SwiftUI
import Combine

final class ListData:ObservableObject {
    @Published var items: [ItemModel] = []
    @Published var allFavorite: [Int:Bool] = [Int:Bool]()
}
