//
//  ContentRow.swift
//  SequoiaDemo
//
//  Created by 王浩沣 on 2023/4/24.
//

import Foundation
import SwiftUI

/*
 每一行内容
 */
struct ContentInfoRow: View {
    @EnvironmentObject var store: Store
    let model: AppInfoViewModel
    var body: some View {
        GeometryReader { proxy in
            HStack {
                ///App图片
                Spacer(minLength: 0)
                AsyncImage(url: URL(string: model.appIconUrl)) { image in
                    image
                        .resizable(capInsets: EdgeInsets(top: 2, leading: 2, bottom: 2, trailing: 2))
                        .frame(width:50 , height: 50, alignment: .center)
                    
                    
                } placeholder: {
                    ProgressView()
                }.cornerRadius(8)
                Spacer(minLength: 0)
                
                ///文字区域
                VStack(alignment: .leading) {
                    Spacer()
                    ///App名
                    Text(self.model.appName)
                        .font(.system(size: 16)).fontWeight(.bold).lineLimit(1).padding(EdgeInsets())
                    Spacer(minLength: 1)
                    ///App描述
                    Text(self.model.description).font(.system(size: 12)).lineLimit(2).padding(EdgeInsets())
                    Spacer()
                }
                .frame(width: proxy.size.width * 0.68,alignment: .leading)
                Spacer(minLength: 0)
                ///喜欢按钮
                Button(action: {
                    ///触发事件
                    self.store.dispatch(.tapLike(appId: model.appId))
                }) {
                    if(!model.isLiked) {
                        Image(systemName: "heart").foregroundColor(.gray)
                    } else {
                        Image(systemName: "heart.fill").foregroundColor(.red)
                    }
                }.padding(.trailing, 14)
                
            }
        }
        
    }
}

struct ContentRow_Previews: PreviewProvider {
    static let dataModel = AppInfo(trackId: 1, trackName: "测试App", description: "好用", artworkUrl512: "")
    static var previews: some View {
        ContentInfoRow.init(model: AppInfoViewModel(with: self.dataModel))
    }
}
