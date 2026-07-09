//
//  ViewRequestResultState.swift
//  AppHunter
//
//  Created by zhang shijie on 2023/5/24.
//

import Foundation
enum ViewRequestResultState<T, E> {
    case loading
    case success(content: [T])
    case failed(error: E)
}
