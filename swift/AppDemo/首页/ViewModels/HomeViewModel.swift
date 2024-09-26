//
//  HomeViewModel.swift
//  AppDemo
//
//  Created by 操喜平 on 2022/8/10.
//

import UIKit
typealias completedBlock = (NSArray)->()
typealias errorBlock = (Error)->()

class HomeViewModel: NSObject {
    
    public func simulationRequest(pageIndex:Int,page:Int,timeOut:Int,success:@escaping completedBlock,mistake:errorBlock) {
        let currentPage = pageIndex * page
        let location = Bundle.main.path(forResource: "data", ofType: "json")
        let url = URL(fileURLWithPath: location!)
        do {
                let data = try Data(contentsOf: url)
                let jsonData:Any = try JSONSerialization.jsonObject(with: data, options: JSONSerialization.ReadingOptions.mutableContainers)
                let json = jsonData as! NSDictionary
            let arr :NSArray = json.object(forKey: "results") as! NSArray
            let modelArr = NSMutableArray()
            if ((arr.count - pageIndex * page) > page) {
                let subArr = arr.subarray(with: NSRange(location:currentPage,length:page))
                for dic in subArr {
                    let model: HomeModel = HomeModel.init(dict: dic as! [String : AnyObject])
                    modelArr.add(model)
                }
            }else{
                let subArr = arr.subarray(with: NSRange(location:currentPage,length:arr.count - pageIndex * page))
                for dic in subArr {
                    let model: HomeModel = HomeModel.init(dict: dic as! [String : AnyObject])
                    modelArr.add(model)
                }
            }
            
            let dispatchAfter = DispatchTimeInterval.milliseconds(timeOut)
            DispatchQueue.main.asyncAfter(deadline: .now() + dispatchAfter, execute: {
                success(modelArr as NSArray)
            })
            } catch let error {
                mistake(error)
            }
    }
}
