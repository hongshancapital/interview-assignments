//
//  Model.swift
//  SearchDemo
//
//  Created by chx on 2021/4/17.
//
import Foundation
import HandyJSON

class GoodsModel : HandyJSON {
    var name : String?
    var isHave : Bool = false
    var price : Double?
    
    required init() {
        
    }
}
class Model : HandyJSON {
    var categoryName : String?
    var goods : [GoodsModel]?
    
    required init() {
    }
    
    static func getSearchModel() -> [Categorys]?{
        guard let path = Bundle.main.path(forResource: "SearchModel", ofType: "plist")
        else { return nil }
        if let array = NSArray.init(contentsOfFile: path){
            var categroyModels = Array<Categorys>.init()
            
            var index = 0
            for item in array {
                if let modelDic = item as? Dictionary<String, Any> {
                    if let model = Model.deserialize(from: modelDic) {
                        let goods = getGoodsModel(items: model.goods)
                        let category = Categorys.init(id: index, goods: goods, name: model.categoryName)
                        index += 1
                        categroyModels.append(category)
                    }
                }
            }
            return categroyModels
        }
        return nil
    }
//
    static func getGoodsModel(items : Array<GoodsModel>?) -> [Goods]?{
        guard let goodItems = items else {
            return nil
        }
        var goodsModels  = Array<Goods>.init()
        var index = 0
        
        for item in goodItems {
            var goods = Goods.init(id: index)
            goods.desc = item.isHave ? "in-stock" : "out-of-stock"
            goods.name = item.name
            goods.price = "   $\(item.price ?? 0)   "
            goods.isHave = item.isHave
            goodsModels.append(goods)
            index+=1
        }
        return goodsModels
    }
}
