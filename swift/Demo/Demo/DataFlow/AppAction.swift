//
//  AppAction.swift
//  Demo
//
//  Created by Kai on 2022/2/22.
//

import Foundation

enum AppAction {
    case loadAllList
    case loadPageList(page: Int)
    case changeLikeState(model: KKModel)
    
    case loadAllListDone(result: Result<KKData, AppError>)
    case loadPageListDone(result: Result<[KKModel], AppError>)
    case changeLikeStateDone(result: String)
    
}
