//
//  PageSelect.swift
//  SwiftUI_SearchExample
//
//  Created by Hongwei Chen on 2021/11/3.
//

import Foundation

/**
 搜索实现分页
 
 基于数据可能随时会发生更新，例如第一页的数据可能会在第二页显示，如何才能保证查询分页没有重复数据
 
 目前只考虑请求来源只有这一个的情况 而不是多个来源请求者
 */

var cacheUUIDs: [UUID] = []

func selectPageList(index: Int, count: Int, searchList: [SearchModel]) -> [SearchModel] {
    /// 从index * count作为起始点
    /// 如此处理可以保证每次遍历获取数据更加高效，缺点是假设数据从第二页变为第一页时，会缺失该数据
    /// 解决此问题可以将 let start = 0 从0位置开始遍历，缺点每次都需要遍历前面的数据，但是因为存在过滤处理，效率影响体验不会太大
    let start = index * count
    if start == 0 {
        cacheUUIDs = []
    }
    var result: [SearchModel] = []
    if searchList.count == 0 || start >= searchList.count {
        return result
    }
    for index in start...searchList.count - 1 {
        let searchModel = searchList[index]
        /// 判断数据是否已经使用过，并且保证返回给用户的数据是10条（除非数据不够）
        if !cacheUUIDs.contains(searchModel.id) {
            result.append(searchModel)
            cacheUUIDs.append(searchModel.id)
            if result.count == count {
                break
            }
        }
    }
    return result
}
