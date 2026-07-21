//
//  FavouriteManager.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import Foundation

//MARK: -收藏管理器，用来控制用户点击图标收藏/取消某栏目
//因Demo不确定每次服务器返回trackId是否随机故未做存储管理
class FavouriteManager : ObservableObject{
    //MARK: - 实现单例
    static let sharedInstance = FavouriteManager()
    private init(){
    }
    
    //MARK: - 属性
    @Published
    var favourites:Set = Set<Int64>()
    
    //MARK: - 方法
    //此条是否收藏
    func IfFavourite(_ trackId:Int64)->Bool
    {
        return favourites.contains(trackId)
    }
    
    //对此条操作处理
    func ModifyFavourite(_ trackId:Int64)
    {
        if( IfFavourite(trackId) == false )
        {
            favourites.insert(trackId)
        }
        else
        {
            favourites.remove(trackId)
        }
    }
}
