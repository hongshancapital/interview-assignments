//
//  ContentViewModel.swift
//  Demo
//
//  Created by mac on 2022/7/26.
//

import UIKit

class ContentViewModel: ObservableObject {

    var datas:[AppModel] = [];
    let size:Int = 10
    var noMoreData:Bool = false
    
    func loadMore(){
        let length:Int = (datas.count + size)>apps.count ? apps.count-datas.count: size;
        let start:Int = datas.count
        let end:Int = datas.count + length-1
        if start >= end {
            noMoreData = true
            objectWillChange.send()
            return
        }
        datas.append(contentsOf: apps[start...end])
        objectWillChange.send()
    }
    
    func refresh()  {
        datas.removeAll();
        datas.append(contentsOf: apps[0...size-1])
        if datas.count % size != 0 {
            noMoreData = true
        }else{
            noMoreData = false
        }
        objectWillChange.send()
    }
}
