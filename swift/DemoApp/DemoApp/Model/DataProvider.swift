//
//  DataProvider.swift
//  DemoApp
//
//  Created by liang on 2022/5/18.
//

import Foundation

protocol DataProvider {
    func fetchAppModel(from last: AppModel?, count: Int, on completion: @escaping ([AppModel]?, Error?)->Void);
}
