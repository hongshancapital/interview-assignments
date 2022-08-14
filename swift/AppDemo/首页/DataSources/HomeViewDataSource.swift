//
//  HomeViewDataSource.swift
//  AppDemo
//
//  Created by 操喜平 on 2022/8/10.
//

import UIKit
import RxSwift



public let footerHeight = 60
let bottomHeight = screenHeight >= 812 ? 34.0 : 0

typealias registerCellBlock = (String)->()
typealias configCellBlock = (HomeViewCell,HomeModel,NSIndexPath)->()



let screenWidth = UIScreen.main.bounds.size.width
let screenHeight = UIScreen.main.bounds.size.height

fileprivate let homeViewCellID = "homeViewCellID"
class HomeViewDataSource: NSObject {
    let thisConfigCell: configCellBlock
    let thisDataArr: NSMutableArray
    let tableView: UITableView

    
    
    init(dataArr: NSMutableArray, register: registerCellBlock ,configCell: @escaping configCellBlock, tableView: UITableView) {
        self.tableView = tableView
        thisConfigCell = configCell
        thisDataArr = dataArr
        register(homeViewCellID)
          
    }


}
//MARK: - UITableViewDelegate
extension HomeViewDataSource: UITableViewDelegate {
    func tableView(_ tableView: UITableView, heightForRowAt indexPath: IndexPath) -> CGFloat {
        return 90
    }
}

//MARK: - UITableViewDataSource
extension HomeViewDataSource: UITableViewDataSource {
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return self.thisDataArr.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: homeViewCellID, for: indexPath) as! HomeViewCell
        if self.thisDataArr.count > indexPath.row {
            let model = self.thisDataArr.object(at: indexPath.row) as! HomeModel
            self.thisConfigCell(cell,model,indexPath as NSIndexPath)
        }
        return cell
    }
}

