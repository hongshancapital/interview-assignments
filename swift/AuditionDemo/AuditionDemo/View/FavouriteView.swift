//
//  FavouriteView.swift
//  AuditionDemo
//
//  Created by dian bao on 2022/7/28.
//

import SwiftUI

//MARK: -收藏View
struct FavouriteView: View {
    //MARK: - 属性
    @State
    var trackId:Int64                                   //条目标识
    @State
    private var favourite = false                       //是否被收藏
    @ObservedObject
    private var fm = FavouriteManager.sharedInstance    //收藏管理器
    
    //MARK: - Body
    var body: some View {
        Button(action: {
            fm.ModifyFavourite(trackId)
            favourite.toggle()
            //震动一下，提升体验^_^
            Util.sharedInstance.feedbackGenerator()
        }, label: {
            //判断此条是否被收藏
            if( fm.IfFavourite(trackId) == true )
            {
                Image.init(systemName: "suit.heart.fill")
                    .renderingMode(.template)                               //忽略原图颜色信息
                    .foregroundColor(Color.red)                             //采用红色填充
                    .scaleEffect(favourite == false ? 1.0 : 1.2)            //控制放大缩小效果
            }
            else
            {
                Image.init(systemName: "suit.heart")
                    .scaleEffect(favourite == false ? 1.0 : 1.2)
            }
        })
    }
}

//MARK: - Preview
struct FavouriteView_Previews: PreviewProvider {
    static var previews: some View {
        FavouriteView(trackId: 1)
    }
}
