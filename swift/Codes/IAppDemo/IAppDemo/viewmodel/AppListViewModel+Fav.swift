//
//  AppListViewModel+Fav.swift
//  IAppDemo
//
//  Created by lee on 2023/3/3.
//

import SwiftUI


extension AppListViewModel {
    
    func updateFav(_ model: inout ListItemModel) {
        
        if  let index =  list.firstIndex(where: { it in
            it.id == model.id
        }) {
            if model.isFavorite == nil {
                model.isFavorite = false
            }
            model.isFavorite?.toggle()
            list.remove(at: index)
            list.insert(model, at: index)
        }
    }
}

extension ListItemModel {
    var favoriteIconName: String {
        (isFavorite ?? false) ? "heart.fill" : "heart"
    }
    var favoriteIconColor: Color? {
        (isFavorite ?? false) ? Color.red : Color.gray
    }
    var favoriteIconScaleEffect: CGFloat {
        (isFavorite ?? false) ? 1.3 : 1.1
    }
}
