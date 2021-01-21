//
//  AppAction.swift
//  HJJSearchThird
//
//  Created by haojiajia on 2021/1/18.
//

import Foundation

enum AppAction {
    case startServer(result: Result<Bool,AppError>)
    case loadGoods
    case loadGoodsFinished(result: Result<[GoodsModel],AppError>)
    case filterGoods(goods:[GoodsModel])
}
